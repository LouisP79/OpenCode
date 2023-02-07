package com.quickstore.ui.useCase.main.activity

import android.content.*
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.quickstore.BuildConfig
import com.quickstore.R
import com.quickstore.firebaseCloudMessaging.ACTION_KICK_USER
import com.quickstore.firebaseCloudMessaging.ACTION_UPDATE_RECORD
import com.quickstore.ui.base.activity.BaseActivity
import com.quickstore.ui.useCase.login.activity.LoginActivity
import com.quickstore.ui.useCase.main.fragment.home.HomeFragment
import com.quickstore.ui.useCase.main.fragment.profile.ProfileFragment
import com.quickstore.ui.useCase.main.fragment.record.RecordFragment
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
    private lateinit var recordFragment: RecordFragment
    private lateinit var profileFragment: ProfileFragment
    private var broadcast: BroadcastNotifierMain? = null


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

    override fun onDestroy() {
        super.onDestroy()
        //unregisterBroadcast
        if(broadcast!=null)
            unregisterReceiver(broadcast)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)

        if(!applicationPreferences.onBoarding)
            kick(OnBoardingActivity::class.java)
        else if(applicationPreferences.token == null)
            singleSetup()
        else{
            setup()

            //registerBroadcast
            val filter = IntentFilter()
            filter.addAction(ACTION_KICK_USER)
            filter.addAction(ACTION_UPDATE_RECORD)
            broadcast = BroadcastNotifierMain()
            registerReceiver(broadcast, filter)
        }
        addListener()
    }

    private fun singleSetup() {
        navView.visibility = View.GONE
        homeFragment = HomeFragment.newInstance()

        supportFragmentManager.beginTransaction()
            .add(R.id.containerFragment, homeFragment)
            .commit()

        homeFragment.setOnAddProductListener {
            shoppingFragment.resetShoppingList()
        }
    }

    private fun addListener() {
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    callMain()
                    true
                }
                R.id.navigation_shopping -> {
                    supportFragmentManager.beginTransaction()
                        .hide(profileFragment)
                        .hide(homeFragment)
                        .hide(recordFragment)
                        .show(shoppingFragment)
                        .commit()
                    menuChange(SHOPPING)
                    true
                }
                R.id.navigation_record -> {
                    supportFragmentManager.beginTransaction()
                        .hide(profileFragment)
                        .hide(homeFragment)
                        .hide(shoppingFragment)
                        .show(recordFragment)
                        .commit()
                    menuChange(RECORD)
                    true
                }
                R.id.navigation_profile -> {
                    supportFragmentManager.beginTransaction()
                        .hide(homeFragment)
                        .hide(shoppingFragment)
                        .hide(recordFragment)
                        .show(profileFragment)
                        .commit()
                    menuChange(PROFILE)
                    true
                }
                else -> false
            }
        }
        login.setOnClickListener { kick(LoginActivity::class.java) }
        whatsapp.setOnClickListener {
            val msj = getString(R.string.whatsapp_auto_msg)
            val numeroTel = BuildConfig.WHATSAPP_NUMBER
            val intent = Intent(Intent.ACTION_VIEW)
            val uri = "whatsapp://send?phone=$numeroTel&text=$msj"
            intent.data = Uri.parse(uri)
            try {
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                showToastShort(R.string.install_whatsapp_error)
            }
        }
    }

    fun showWhatsapp(){
        whatsapp.show()
    }

    fun hideWhatsapp(){
        whatsapp.hide()
    }

    fun callMain() {
        supportFragmentManager.beginTransaction()
            .hide(profileFragment)
            .hide(shoppingFragment)
            .hide(recordFragment)
            .show(homeFragment)
            .commit()
        menuChange(HOME)
        navView.menu.findItem(R.id.navigation_home).isChecked = true
    }

    fun refreshRecords(){
        recordFragment.resetRecordList()
    }

    private fun setup() {
        login.visibility = View.GONE
        navView.visibility = View.VISIBLE
        homeFragment = HomeFragment.newInstance()
        shoppingFragment = ShoppingFragment.newInstance()
        recordFragment = RecordFragment.newInstance()
        profileFragment = ProfileFragment.newInstance()

        supportFragmentManager.beginTransaction()
            .add(R.id.containerFragment, profileFragment)
            .hide(profileFragment)
            .add(R.id.containerFragment, shoppingFragment)
            .hide(shoppingFragment)
            .add(R.id.containerFragment, recordFragment)
            .hide(recordFragment)
            .add(R.id.containerFragment, homeFragment)
            .commit()

        homeFragment.setOnAddProductListener {
            shoppingFragment.resetShoppingList()
        }
    }

    fun kick(clazz: Any) {
        startActivity(Intent(this, clazz as Class<*>))
        finish()
    }

    private fun menuChange(menu: String){
        if(menuSelected != menu) {
            menuSelected = menu
            if(listener.isNotEmpty())
                listener[listener.size - 1].invoke()
        }
        showWhatsapp()
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

    inner class BroadcastNotifierMain : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (ACTION_KICK_USER == intent.action) {
                showToast(R.string.you_logued_other_device)
                kickUser()
            }else if (ACTION_UPDATE_RECORD == intent.action) {
                recordFragment.resetRecordList()
            }
        }
    }
}