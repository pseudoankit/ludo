package com.android.winmo.util

import android.content.ClipData
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.text.ClipboardManager
import android.view.View
import android.widget.Toast

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun Context.setClipboard(text: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
    val clip = ClipData.newPlainText("Copied Text", text)
    clipboard.setPrimaryClip(clip)
    toast(text)
}

fun Context.toast(text: String){
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun randomId(): String = List(16) {
    (('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
}.joinToString("")

fun Context.displayDialog(title: String, message: String, isYes: (DialogInterface, Int) -> Unit) {
    val builder = android.app.AlertDialog.Builder(this)
    builder.apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton(android.R.string.yes, DialogInterface.OnClickListener(isYes))
        setNegativeButton(android.R.string.no) { dialog, _ ->
            dialog.dismiss()
        }
        setCancelable(false)
    }
    return builder.create().show()
}