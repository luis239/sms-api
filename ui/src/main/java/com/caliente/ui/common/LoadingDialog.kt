package com.caliente.ui.common

import android.app.Dialog
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.caliente.ui.R

class LoadingDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTheme_Loading)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnKeyListener { _, _, _ ->
            true
        }
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_loader, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() {
        val title = arguments?.getString(ARG_TITLE)
        val message = arguments?.getString(ARG_MESSAGE)
    }

    fun updateMessage(title: String?, message: String?) {
        arguments?.putString(ARG_TITLE, title)
        arguments?.putString(ARG_MESSAGE, message)
        bindView()
    }



    companion object {

        private const val ARG_TITLE = "arg_title"
        private const val ARG_MESSAGE = "arg_message"
        private const val IS_MAN = "isMan"
        const val TAG = "LoadingDialog"


        private fun newInstance(title: String? = null, message: String? = null,isMale:Boolean = true): LoadingDialog {
            val dialog = LoadingDialog()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putString(ARG_MESSAGE, message)
            args.putBoolean(IS_MAN, isMale)
            dialog.arguments = args
            return dialog
        }

        fun showLoadingDialog(fm: FragmentManager?, title: String? = null, message: String? = null, isMale:Boolean = true) {
            val fragment = fm?.findFragmentByTag(TAG)
            if (fragment is LoadingDialog) {
                fragment.updateMessage(title, message)

            } else if (fm != null) {
                val dialog = newInstance(title, message,isMale)
                dialog.show(fm, TAG)
            }
        }

        fun hideLoadingDialog(fm: FragmentManager?) {
            (fm?.findFragmentByTag(TAG) as? LoadingDialog)?.dismiss()
        }

    }

}