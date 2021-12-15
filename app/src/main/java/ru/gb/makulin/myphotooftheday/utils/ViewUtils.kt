package ru.gb.makulin.myphotooftheday.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.makeSnackbarWithAction(text: String, actionText: String, action: View.OnClickListener) {
    Snackbar.make(this, text, Snackbar.LENGTH_LONG).setAction(actionText, action).show()
}

fun View.makeSnackbar(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_SHORT).show()
}

fun View.makeErrSnackbar(action: View.OnClickListener) {
    Snackbar.make(this, ON_FAILED_DATA_LOADING_TEXT, Snackbar.LENGTH_LONG).setAction(
        SNACK_ACTION_TEXT, action
    ).show()
}