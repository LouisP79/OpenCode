package com.quickstore.ui.base.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.quickstore.R
import com.quickstore.preferences.ApplicationPreferences
import com.quickstore.ui.base.activity.BaseActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject

abstract class BaseFragment : Fragment() {

    val applicationPreferences: ApplicationPreferences by inject()

    protected abstract val layoutResourceId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResourceId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh?.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.colorAccent))
    }

    fun addFragmentWithEffect(fragment: Fragment){
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fade_in,0,0,R.anim.fade_out)
            .add(R.id.containerFragment, fragment)
            .commit()
    }

    fun showToast(message: String) = (activity as BaseActivity).showToast(message)
    fun showToast(message: Int) = (activity as BaseActivity).showToast(message)
    fun unknownError(t: Throwable?) = (activity as BaseActivity).unknownError(t)
    fun errorCode(code: Int) = (activity as BaseActivity).errorCode(code)
    fun errorConnection(t: Throwable) = (activity as BaseActivity).errorConnection(t)
    fun kickUser() = (activity as BaseActivity).kickUser()
}
