package com.quickstore.util.extencions

import android.text.Editable
import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText

private fun Editable.isValidEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this.toString()).matches()

fun TextInputEditText.validateEmail(msg: Int): Boolean{
    if(!this.text!!.isValidEmail()){
        this.error = this.context.getString(msg)
        return false
    }
    return true
}

fun TextInputEditText.validateEmpty(msg: Int): Boolean{
    if(this.text!!.isEmpty()){
        this.error = this.context.getString(msg)
        return false
    }
    return true
}

fun TextInputEditText.validateLength(min: Int, msg: Int): Boolean{
    if(this.text!!.toString().length < min){
        this.error = this.context.getString(msg)
        return false
    }
    return true
}