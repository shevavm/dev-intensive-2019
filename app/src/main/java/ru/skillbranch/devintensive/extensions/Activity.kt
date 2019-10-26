package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val view = currentFocus
    if( view != null) {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.isKeyboardOpen(): Boolean {
    val rootView = window.decorView.findViewById<View>(android.R.id.content)
    val rect = Rect()
    rootView.getWindowVisibleDisplayFrame(rect)
    return (rootView.rootView.height - rect.height() > 500)
}

fun Activity.isKeyboardClosed(): Boolean {
    return !isKeyboardOpen()
}