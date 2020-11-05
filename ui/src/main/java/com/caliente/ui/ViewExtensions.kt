package com.caliente.ui

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentActivity
import com.caliente.ui.common.LoadingDialog
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorText")
fun TextInputLayout.setErrorMessage(errorMessage: String?) {
    error = errorMessage
    if (!errorMessage.isNullOrEmpty()) {
        requestFocus()
    }
}

fun FragmentActivity.showLoadingDialog(title: String? = null, message: String? = null) {
    LoadingDialog.showLoadingDialog(supportFragmentManager, title, message)
}

fun FragmentActivity.hideLoadingDialog() {
    LoadingDialog.hideLoadingDialog(supportFragmentManager)
}

fun FragmentActivity.showDialogLogin(listener:EditNumberDialogFragment.OnDialogLoginClickListener,isFb:Boolean = false){
    EditNumberDialogFragment.showDialog(supportFragmentManager,l = listener,isFb = isFb)
}

fun FragmentActivity.hideDialog(){
    EditNumberDialogFragment.hideDialog(supportFragmentManager)
}

fun FragmentActivity.showMessage(message: String?,
                                 acceptListener: DialogInterface.OnClickListener? = null,
                                 dismissListener: DialogInterface.OnDismissListener? = null, cancelable:Boolean = true) {
    if (message != null) {
        AlertDialog.Builder(this)
            .setCancelable(cancelable)
            .setTitle("Aviso")
            .setMessage(message)
            .setPositiveButton("Aceptar", acceptListener)
            .setOnDismissListener(dismissListener)
            .create()
            .show()
    }
}