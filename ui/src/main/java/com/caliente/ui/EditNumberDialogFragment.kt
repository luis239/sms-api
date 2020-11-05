package com.caliente.ui

import androidx.appcompat.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.caliente.ui.databinding.FragmentNumberInputBinding
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_number_input.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class EditNumberDialogFragment : DialogFragment(){
    private var param1: Boolean = false
    private var param2: String? = null

    var listener:OnDialogLoginClickListener? = null

    private val registerViewModel: SmsViewModel by sharedViewModel()

    interface OnDialogLoginClickListener{
        fun submit(number:String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getBoolean(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater,
            R.layout.fragment_number_input, container, false)
        binding.setVariable(BR.vm, registerViewModel)
        binding.setVariable(BR.fragment, this)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.layoutInflater?.inflate(R.layout.fragment_number_input,null,false) as View
        val binding = FragmentNumberInputBinding.bind(view)
        binding.setVariable(BR.vm, registerViewModel)
        binding.setVariable(BR.fragment, this)
        binding.lifecycleOwner = this
        val login = view?.findViewById<Button>(R.id.button)
        val numberText = view?.findViewById<TextInputLayout>(R.id.textInputLayout)
        val closeButton = view?.findViewById<ImageView>(R.id.close)
        login?.setOnClickListener {
            listener?.submit(numberText.editText?.text.toString())
        }
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
        builder.setView(view)

        val dialog = builder.create()
        closeButton?.setOnClickListener {
            if(param1)
                activity?.finish()
            dialog.dismiss()
        }

        return dialog


    }

    companion object {

        const val TAG = "DialogFragmentLogin"
        @JvmStatic
        fun newInstance(param1: Boolean, param2: String,l: OnDialogLoginClickListener) =
            EditNumberDialogFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
                listener = l
            }

        fun showDialog(fm: FragmentManager?, isFb: Boolean = false, message: String? = null,l: OnDialogLoginClickListener) {
            val fragment = fm?.findFragmentByTag(TAG)
            if (fm != null) {
                val dialog = newInstance(isFb,"",l = l)
                dialog.isCancelable = true
                dialog.show(fm, TAG)
            }
        }

        fun hideDialog(fm: FragmentManager?) {
            (fm?.findFragmentByTag(TAG) as? EditNumberDialogFragment)?.dismiss()
        }
    }

}
