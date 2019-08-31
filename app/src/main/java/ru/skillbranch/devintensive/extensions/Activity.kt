package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import ru.skillbranch.devintensive.utils.Utils

fun Activity.hideKeyboard() {
    val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)
}

fun Activity.isKeyboardOpen(): Boolean {
    val size = Utils.getDisplaySize(this)
    val root = this.findViewById<ViewGroup>(android.R.id.content)
    val rect = Rect()
    root.getWindowVisibleDisplayFrame(rect)
    return  rect.height() < (size.y - rect.top * 2)
}

fun Activity.isKeyboardClosed(): Boolean {
    return !this.isKeyboardOpen()
}