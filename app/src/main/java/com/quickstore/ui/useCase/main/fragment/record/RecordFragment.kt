package com.quickstore.ui.useCase.main.fragment.record

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.quickstore.R
import com.quickstore.data.order.model.OrderModel
import com.quickstore.ui.base.fragment.BaseFragment
import com.quickstore.ui.useCase.main.activity.MainActivity
import com.quickstore.ui.useCase.main.adapter.RecordAdapter
import com.quickstore.ui.useCase.main.viewModel.MainViewModel
import com.quickstore.util.listener.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.content_record.*
import kotlinx.android.synthetic.main.content_record.emptyList
import kotlinx.android.synthetic.main.content_record.swipeRefresh
import kotlinx.android.synthetic.main.content_shopping.*
import org.koin.android.viewmodel.ext.android.viewModel

class RecordFragment : BaseFragment() {

    companion object{
        fun newInstance() = RecordFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_record

    private val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: RecordAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setup()
        addListener()
    }

    private fun setup() {
        loadingRecords.visibility = View.VISIBLE

        adapter = RecordAdapter()
        record.adapter = adapter

        restRecord()
    }

    fun resetRecordList(){
        adapter.cleanItems()
        restRecord()
    }

    private fun restRecord() {
        viewModel.listOrder(applicationPreferences.token!!.accessToken)
            .observe(viewLifecycleOwner
            ) { response ->
                when (response) {
                    null -> unknownError(null)
                    else -> {
                        if (response.dataResponse != null) {
                            if (response.dataResponse.isSuccessful) {
                                if (response.dataResponse.body()!!.isEmpty())
                                    emptyList.visibility = View.VISIBLE
                                else
                                    adapter.items = response.dataResponse.body()!! as MutableList<OrderModel>
                            } else errorCode(response.dataResponse.code())
                        } else errorConnection(response.throwable!!)
                    }
                }
                loadingRecords.visibility = View.GONE
                swipeRefresh.isRefreshing = false
            }
    }

    private fun addListener() {
        swipeRefresh.setOnRefreshListener {resetRecordList()}
        adapter.setOnRecordClickListener { (activity as MainActivity).showWhatsapp() }

        record.addOnScrollListener(object: OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0) {
                    (activity as MainActivity).showWhatsapp()
                } else {
                    (activity as MainActivity).hideWhatsapp()
                }
            }
        })
    }

}