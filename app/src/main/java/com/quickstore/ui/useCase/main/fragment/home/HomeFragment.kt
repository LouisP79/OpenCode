package com.quickstore.ui.useCase.main.fragment.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.arlib.floatingsearchview.FloatingSearchView.OnSearchListener
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.quickstore.R
import com.quickstore.data.Pageable
import com.quickstore.data.product.model.ProductModel
import com.quickstore.ui.base.fragment.BaseFragment
import com.quickstore.ui.useCase.main.activity.MainActivity
import com.quickstore.ui.useCase.main.adapter.ProductAdapter
import com.quickstore.ui.useCase.main.model.SuggestionModel
import com.quickstore.ui.useCase.main.viewModel.MainViewModel
import com.quickstore.util.listener.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class HomeFragment : BaseFragment() {

    companion object{
        fun newInstance() = HomeFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    private var listener: (()->Unit)? = null

    private val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: ProductAdapter
    private var isLast = true
    private var page = 1
    private var categoryId = 0L
    private var searchQuery = ""
    private val categoriesFragment = CategoriesFragment.newInstance()
    private val suggestionModels = mutableListOf<SuggestionModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        (activity as MainActivity).setOnSearchBackListener {backHome(it)}

        setup()
        addListener()
    }

    private fun setup() {
        showLoadingProducts()

        adapter = ProductAdapter()
        products.adapter = adapter
        products.layoutManager = gatLayoutManager()

        restProduct()
    }

    private fun backHome(eval: Boolean){
        if((categoryId != 0L || searchQuery.isNotEmpty()))
            resetProductListWidthLoadingCleaningSearch()
        else{
            if(!eval)
                requireActivity().finish()
        }
    }

    private fun resetProductListWidthLoadingCleaningSearch(){
        showLoadingProducts()
        resetProductListWidthFloatingSearchClean()
    }

    private fun resetProductListWidthOutLoadingCleaningSearch(){
        showLoadingProducts()
        resetProductList()
    }

    private fun resetProductListWidthFloatingSearchClean(){
        floatingSearch.setSearchText("")
        resetProductList()
    }

    private fun resetProductList(){
        page = 1
        categoryId = 0L
        searchQuery = ""
        isLast = true
        cleanList()
    }

    private fun reloadList(){
        showLoadingProducts()
        cleanList()
    }

    private fun showLoadingProducts(){
        loadingProducts.visibility = View.VISIBLE
        emptyList.visibility = View.GONE
    }

    private fun cleanList(){
        adapter.cleanItems()
        suggestionModels.clear()
        restProduct()
    }

    private fun addListener() {
        swipeRefresh.setOnRefreshListener {resetProductListWidthFloatingSearchClean()}
        products.addOnScrollListener(object : EndlessRecyclerViewScrollListener(products.layoutManager as GridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) = loadMoreProducts()
        })
        adapter.setOnProductClickListener {
            val fragment = ProductDetailFragment.newInstance(it)
            addFragmentWithEffect(fragment)
            fragment.setOnAddProductListener { listener?.invoke() }
        }

        //###########################################FLOATINGSEARCH
        floatingSearch.setOnMenuItemClickListener {when(it.itemId){
            R.id.menu_categories -> addFragmentWithEffect(
                categoriesFragment
            )}}
        floatingSearch.setOnQueryChangeListener{ _, newQuery ->
            if(newQuery.isEmpty()) {
                floatingSearch.clearSuggestions()
                if(categoryId!=0L || searchQuery.isNotEmpty())
                    resetProductListWidthOutLoadingCleaningSearch()
            }else{
                floatingSearch.showProgress()
                floatingSearch.swapSuggestions(showSuggestions(newQuery))
                floatingSearch.hideProgress()
            }
        }
        floatingSearch.setOnSearchListener(object : OnSearchListener {
            override fun onSearchAction(query: String) = onSearch(query)
            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion){
                floatingSearch.setSearchText(searchSuggestion.body)
                onSearch(searchSuggestion.body)
                floatingSearch.clearSearchFocus()
            }
        })
        categoriesFragment.setOnCategoryClickListener { id, name ->
            floatingSearch.setSearchText(name)
            categoryId = id
            reloadList()
        }
        //###########################################FLOATINGSEARCH
    }

    private fun onSearch(query: String){
        categoryId = 0L
        searchQuery = query
        reloadList()
    }

    private fun showSuggestions(search: String): MutableList<SuggestionModel>{
        val aux = mutableListOf<SuggestionModel>()
        for(suggestion in suggestionModels){
            if (suggestion.value.uppercase(Locale.ROOT).contains(search.uppercase(Locale.ROOT)))
                aux.add(suggestion)
        }
        return aux
    }

    private fun loadMoreProducts() {if (!isLast){ page++; restProduct() }}

    private fun gatLayoutManager(): GridLayoutManager{
        val layoutManger = GridLayoutManager(context, 2)
        layoutManger.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int  = if(adapter.items[position] is String) 2 else 1
        }
        return layoutManger
    }

    private fun restProduct() {
        if(page>1) loadingMoreProducts.visibility = View.VISIBLE
        viewModel.getProductList(applicationPreferences.token!!.accessToken, page, searchQuery, categoryId)
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
                loadingProducts.visibility = View.GONE
                loadingMoreProducts.visibility = View.GONE
                swipeRefresh.isRefreshing = false
            }
    }


    private fun successResponse(response: Pageable<ProductModel>) {
        if(response.items.isEmpty())
            emptyList.visibility = View.VISIBLE
        else{
            page = response.page
            isLast = response.isLast
            if(response.page == 0) {
                val items = mutableListOf<Any>()
                items.add("Logo")
                items.addAll(response.items)
                adapter.items = items
            }else adapter.items = response.items.toMutableList()
            addSuggestions(response.items)
            emptyList.visibility = View.GONE
        }
    }

    private fun addSuggestions(items: MutableList<ProductModel>){
        for(item in items){
            suggestionModels.add(SuggestionModel(item.name))
        }
    }

    fun setOnAddProductListener(listener: ()->Unit){
        this.listener = listener
    }
}