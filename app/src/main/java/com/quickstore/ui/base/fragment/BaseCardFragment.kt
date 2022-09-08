package com.quickstore.ui.base.fragment

import android.os.Bundle

import android.view.View
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.quickstore.ui.useCase.main.activity.MainActivity

abstract class BaseCardFragment : BaseFragment() {

    private var posiListener  = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        posiListener = (activity as MainActivity).addOnBackListener {back()}
        (activity as MainActivity).addOnNavigationItemChange {backSpecial()}
    }

    fun back() {
        if(posiListener != -1) (activity as MainActivity).removeOnBackListener(posiListener)
        parentFragmentManager.beginTransaction().remove(this).commit()
    }

    private fun backSpecial() {
        (activity as MainActivity).clearOnBackListener()
        for(i in parentFragmentManager.fragments.size - 1 downTo 0){
            if(parentFragmentManager.fragments[i] is SupportRequestManagerFragment)
                break
            else parentFragmentManager.beginTransaction().remove(parentFragmentManager.fragments[i]).commit()
        }
    }
}
