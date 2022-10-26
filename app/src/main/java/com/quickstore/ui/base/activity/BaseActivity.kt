package com.quickstore.ui.base.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest

import com.quickstore.R
import com.quickstore.data.AuthenticatorApp
import com.quickstore.data.token.model.TokenModel
import com.quickstore.data.user.model.UserModel
import com.quickstore.preferences.ApplicationPreferences
import com.quickstore.ui.useCase.login.activity.LoginActivity
import com.quickstore.ui.useCase.main.activity.MainActivity
import com.quickstore.util.core.UtilConnectionInterceptor
import com.quickstore.workManager.*
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import java.net.HttpURLConnection

abstract class BaseActivity : AppCompatActivity() {

    val applicationPreferences: ApplicationPreferences by inject()

    protected abstract val layoutResourceId: Int

    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResourceId)

        compositeDisposable = CompositeDisposable()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun showToast(message: Int) {
        Toast.makeText(this, getString(message), Toast.LENGTH_LONG).show()
    }

    fun errorCode(code: Int){
        when(code){
            HttpURLConnection.HTTP_BAD_REQUEST -> showToast(R.string.user_error)
            HttpURLConnection.HTTP_UNAUTHORIZED -> showToast(R.string.unauthorized_error)
            HttpURLConnection.HTTP_BAD_METHOD -> showToast(R.string.bad_method_error)
        }
    }

    fun errorConnection(t: Throwable) {
        when (t) {
            is UtilConnectionInterceptor.NoConnectivityException -> showToast(t.message)
            is AuthenticatorApp.RefreshTokenException, is AuthenticatorApp.UnknownException -> {
                showToast(t.localizedMessage!!)
                kickUser()
            }
            else -> unknownError(t)
        }
    }

    fun kickUser() {
        if(!applicationPreferences.firebaseToken.isNullOrBlank() && applicationPreferences.token != null) {
            runDeleteFirebaseTokenWM()
        }
        applicationPreferences.clearAll()
        finishAffinity()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun runDeleteFirebaseTokenWM() {
        val data = Data.Builder()
        data.putString(ACCESS_TOKEN, applicationPreferences.token!!.accessToken)
        data.putString(FIREBASE_TOKEN, applicationPreferences.firebaseToken)

        val deleteFirebaseTokenWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<DeleteFirebaseTokenWorkManager>()
                .addTag(DFTAG)
                .setInputData(data.build())
                .build()
        WorkManager
            .getInstance(this)
            .enqueue(deleteFirebaseTokenWorkRequest)
    }

    fun validateMessage(message: String?, errorMessage: Int = R.string.str_unknow_rest_error) {
        if (!message.isNullOrBlank()) {
            showToast(message)
            return
        }
        showToast(errorMessage)
    }

    fun unknownError(t: Throwable?){
        showToast(R.string.error_internet)
        if(t!=null)
            Log.e("SERVICE ERROR", t.message!!)
        else
            Log.e("SERVICE ERROR", getString(R.string.unknown_error))
    }

    fun successLoginRegister(tokenModel: TokenModel, userModel: UserModel) {
        applicationPreferences.token = tokenModel
        applicationPreferences.user = userModel
        runCreateFirebaseTokenWM()
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }

    private fun runCreateFirebaseTokenWM() {
        val createFirebaseTokenWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<CreateFirebaseTokenWorkManager>()
                .addTag(CFTAG)
                .build()
        WorkManager
            .getInstance(this)
            .enqueue(createFirebaseTokenWorkRequest)
    }
}