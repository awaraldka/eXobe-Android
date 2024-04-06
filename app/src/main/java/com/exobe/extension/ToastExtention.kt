package com.exobe.extension

import android.app.Activity
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

fun Fragment.diasplay_toast(message:String?)
{

    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}
var currentToast: Toast? = null

fun Activity.displayToast(message: String?) {
    currentToast?.cancel() // Cancel the current Toast if it exists

    currentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    currentToast?.show()
}


fun ImageView.load(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}


fun ImageView.loadImageResource(image:Int) {
    Glide.with(this)
        .load(image)
        .into(this)
}

fun EditText.setTextValue(value: String?) {
    if (!value.isNullOrEmpty() && value.toDouble() > 0) {
        this.setText(value)
    } else {
        this.text.clear()
    }
}
