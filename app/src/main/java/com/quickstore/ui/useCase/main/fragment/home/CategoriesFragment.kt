package com.quickstore.ui.useCase.main.fragment.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.quickstore.R
import com.quickstore.data.Pageable
import com.quickstore.data.category.model.CategoryModel
import com.quickstore.ui.base.fragment.BaseCardFragment
import com.quickstore.ui.useCase.main.adapter.CategoryAdapter
import com.quickstore.ui.useCase.main.viewModel.MainViewModel
import com.quickstore.util.listener.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.content_categories.*
import kotlinx.android.synthetic.main.fragment_categories.*
import org.koin.android.viewmodel.ext.android.viewModel

class CategoriesFragment : BaseCardFragment() {

    companion object{
        fun newInstance() = CategoriesFragment()
    }

    private var listener: ((categoryId: Long, name: String)->Unit)? = null

    override val layoutResourceId: Int
        get() = R.layout.fragment_categories

    private val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: CategoryAdapter
    private var isLast = true
    private var page = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        toolbar.setNavigationOnClickListener { back() }

        setup()
        addListener()
    }

    private fun setup() {
        loadingCategories.visibility = View.VISIBLE

        adapter = CategoryAdapter()
        categories.adapter = adapter

        restCategory()
    }

    private fun resetCategoryList(){
        page = 0
        isLast = true
        adapter.cleanItems()
        restCategory()
    }

    private fun addListener() {
        swipeRefresh.setOnRefreshListener {resetCategoryList()}
        categories.addOnScrollListener(object : EndlessRecyclerViewScrollListener(categories.layoutManager as LinearLayoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int) = loadMoreCategories()
        })
        adapter.setOnCategoryClickListener {id, name ->
            listener?.invoke(id, name)
            back()
        }
    }

    private fun loadMoreCategories() {if (!isLast){ page++; restCategory() }}

    private fun restCategory() {
        if(page>0) loadingMoreCategories.visibility = View.VISIBLE
        viewModel.getCategoryList(page)
            .observe(viewLifecycleOwner
            ) { response ->
                when (response) {
                    null -> unknownError(null)
                    else -> {
                        if (response.dataResponse != null) {
                            if (response.dataResponse.isSuccessful) {
                                successResponse(response.dataResponse.body()!!)
                            } else errorCode(response.dataResponse.code())
                        } else errorConnection(response.throwable!!)
                    }
                }
                loadingCategories.visibility = View.GONE
                loadingMoreCategories.visibility = View.GONE
                swipeRefresh.isRefreshing = false
            }
    }

    private fun successResponse(response: Pageable<CategoryModel>) {
        if(response.items.isEmpty())
            emptyList.visibility = View.VISIBLE
        else{
            page = response.page
            isLast = response.isLast
            adapter.items = response.items
            emptyList.visibility = View.GONE
        }
    }

    fun setOnCategoryClickListener(listener: (categoryId: Long, name: String)->Unit){
        this.listener = listener
    }

}