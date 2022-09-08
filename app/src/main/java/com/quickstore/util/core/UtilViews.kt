package com.quickstore.util.core

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.quickstore.R

fun showAlertDialogControl(context: Context, title: Int, msg: Int, positiveListener: () -> Unit): AlertDialog {

    val b = AlertDialog.Builder(context)
            .setTitle(context.getString(title))
            .setMessage(context.getString(msg))
            .setCancelable(true)
            .setNegativeButton(context.getString(R.string.cancel)) { _, _ ->}
            .setPositiveButton(context.getString(R.string.ok)) { _, _ -> positiveListener() }

    val dialog = b.create()

    dialog.show()

    return dialog
}