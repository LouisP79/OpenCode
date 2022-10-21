package com.quickstore.ui.useCase.onboarding.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.quickstore.R
import com.quickstore.ui.base.activity.BaseActivity
import com.quickstore.ui.useCase.login.activity.LoginActivity
import com.quickstore.ui.useCase.onboarding.adapter.OnBoardingPagerAdapter
import com.quickstore.ui.useCase.onboarding.viewModel.OnBoardingViewModel
import kotlinx.android.synthetic.main.activity_onboarding.*
import org.koin.android.viewmodel.ext.android.viewModel

class OnBoardingActivity : BaseActivity() {

    override val layoutResourceId: Int
        get() = R.layout.activity_onboarding

    private lateinit var pagerAdapter: OnBoardingPagerAdapter
    private val viewModel: OnBoardingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setup()
        addListener()
    }

    private fun setup() {
        pagerAdapter = OnBoardingPagerAdapter()
        restOnBoarding()
    }

    private fun addListener() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
            override fun onPageScrollStateChanged(i: Int) {}
            override fun onPageSelected(i: Int) {
                nextSliderPager(i)
            }
        })

        next.setOnClickListener {nextSlider()}
    }

    private fun nextSliderPager(i: Int) {
        if (i == pagerAdapter.count - 1) next.text = getString(R.string.lets_begin)
        else next.text = getString(R.string.next)
    }

    private fun nextSlider(){
        val currentPage = viewPager.currentItem + 1
        if (currentPage < pagerAdapter.count)
            viewPager.currentItem = currentPage
        else {
            applicationPreferences.onBoarding = true
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun restOnBoarding(){
        viewModel.getSlider()
            .observe(this
            ) { response ->
                when (response) {
                    null -> unknownError(null)
                    else -> {
                        if (response.dataResponse != null) {
                            if (response.dataResponse.isSuccessful) {
                                pagerAdapter.items = response.dataResponse.body()!!
                                init()
                                next.visibility = View.VISIBLE
                                loading.visibility = View.GONE
                            } else errorCode(response.dataResponse.code())
                        } else errorConnection(response.throwable!!)
                    }
                }
            }
    }

    private fun init() {
        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = pagerAdapter.count
        tabDots.setupWithViewPager(viewPager)
    }
}