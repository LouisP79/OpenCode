package com.quickstore.ui.useCase.main.activity

import android.content.Intent
import android.os.Bundle
import com.quickstore.R
import com.quickstore.ui.base.activity.BaseActivity
import com.quickstore.ui.useCase.login.activity.LoginActivity
import com.quickstore.ui.useCase.main.fragment.home.HomeFragment
import com.quickstore.ui.useCase.main.fragment.profile.ProfileFragment
import com.quickstore.ui.useCase.main.fragment.shopping.ShoppingFragment
import com.quickstore.ui.useCase.onboarding.activity.OnBoardingActivity
import kotlinx.android.synthetic.main.activity_main.*

const val HOME = "H"
const val SHOPPING = "S"
const val RECORD = "R"
const val PROFILE = "P"

class MainActivity : BaseActivity() {

    override val layoutResourceId: Int
        get() = R.layout.activity_main

    private lateinit var homeFragment: HomeFragment
    private lateinit var shoppingFragment: ShoppingFragment
    private lateinit var profileFragment: ProfileFragment

    private var listener = mutableListOf<(()->Unit)>()
    private var onBackListener = mutableListOf<(()->Unit)>()
    private var onSearchBackListener: ((eval: Boolean)->Unit)? = null
    private var menuSelected = HOME

    override fun onBackPressed() {
        if(onSearchBackListener != null)
            onSearchBackListener?.invoke(onBackListener.isNotEmpty())

        if(onBackListener.isNotEmpty())
            onBackListener[onBackListener.size - 1].invoke()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)

        if(!applicationPreferences.onBoarding)
            kick(OnBoardingActivity::class.java)
        else if(applicationPreferences.token == null)
            kick(LoginActivity::class.java)

        setup()
        addListener()
    }

    private fun addListener() {
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction()
                        .hide(profileFragment)
                        .hide(shoppingFragment)
                        .show(homeFragment)
                        .commit()
                    menuChange(HOME)
                    true
                }
                R.id.navigation_shopping -> {
                    supportFragmentManager.beginTransaction()
                        .hide(profileFragment)
                        .hide(homeFragment)
                        .show(shoppingFragment)
                        .commit()
                    menuChange(SHOPPING)
                    true
                }
                R.id.navigation_record -> {
                    menuChange(RECORD)
                    true
                }
                R.id.navigation_profile -> {
                    supportFragmentManager.beginTransaction()
                        .hide(homeFragment)
                        .hide(shoppingFragment)
                        .show(profileFragment)
                        .commit()
                    menuChange(PROFILE)
                    true
                }
                else -> false
            }
        }
    }

    private fun setup() {
        homeFragment = HomeFragment.newInstance()
        shoppingFragment = ShoppingFragment.newInstance()
        profileFragment = ProfileFragment.newInstance()

        supportFragmentManager.beginTransaction()
            .add(R.id.containerFragment, profileFragment)
            .hide(profileFragment)
            .add(R.id.containerFragment, shoppingFragment)
            .hide(shoppingFragment)
            .add(R.id.containerFragment, homeFragment)
            .commit()

        homeFragment.setOnAddProductListener {
            shoppingFragment.resetShoppingList()
        }
    }

    private fun kick(clazz: Any) {
        startActivity(Intent(this, clazz as Class<*>))
        finish()
    }

    private fun menuChange(menu: String){
        if(menuSelected != menu) {
            menuSelected = menu
            if(listener.isNotEmpty())
                listener[listener.size - 1].invoke()
        }
    }

    fun addOnNavigationItemChange(listener: (()->Unit)){
        this.listener.add(listener)
    }

    fun addOnBackListener(listener: (()->Unit)): Int{
        this.onBackListener.add(listener)
        return onBackListener.size - 1
    }

    fun removeOnBackListener(posi: Int){
        this.onBackListener.removeAt(posi)
        this.listener.removeAt(posi)
    }

    fun clearOnBackListener(){
        this.onBackListener.clear()
        this.listener.clear()
    }

    fun setOnSearchBackListener(listener: ((eval: Boolean)->Unit)?){
        this.onSearchBackListener = listener
    }
}