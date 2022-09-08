package com.quickstore.ui.useCase.onboarding.adapter

import android.view.LayoutInflater
import androidx.viewpager.widget.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.quickstore.BuildConfig
import com.quickstore.R
import com.quickstore.data.RestConstant
import com.quickstore.data.onBoarding.model.OnBoardingModel
import com.quickstore.util.core.glide

class OnBoardingPagerAdapter : PagerAdapter() {

    var items  = listOf<OnBoardingModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getCount(): Int = items.size
    override fun isViewFromObject(view: View, o: Any): Boolean = view === o
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) = container.removeView(`object` as LinearLayout)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = LayoutInflater.from(container.context).inflate(R.layout.item_onboarding, container, false)
        val image = itemView.findViewById<ImageView>(R.id.img)
        val title = itemView.findViewById<TextView>(R.id.title)
        val msg = itemView.findViewById<TextView>(R.id.text)

        glide(container.context, image,
            BuildConfig.URL_IMAGES + RestConstant.ONBOARDING + items[position].image + RestConstant.ALT)

        title.text = items[position].title
        msg.text = items[position].message

        container.addView(itemView)
        return itemView
    }

}
