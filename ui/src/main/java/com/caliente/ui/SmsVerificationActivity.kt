package com.caliente.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.caliente.ui.databinding.ActivitySmsVerificationBindingImpl
import com.github.ybq.android.spinkit.style.Circle
import kotlinx.android.synthetic.main.activity_sms_verification.*
import org.koin.android.viewmodel.ext.android.viewModel

class SmsVerificationActivity : AppCompatActivity(),
    EditNumberDialogFragment.OnDialogLoginClickListener {

    private val mSpin: Circle = Circle()
    private val smsViewModel: SmsViewModel by viewModel()

    private var mHandler: Handler? = Handler(Looper.getMainLooper())
    var animation: Runnable = object : Runnable {
        var i = WAIT_SECONDS
        override fun run() {
            if (i > 0) {
               setText( "Por favor espera ${i-1} segundos antes de solicitar otro PIN.")
                i--
            } else {
                mHandler = null
                contadorText.visibility = View.INVISIBLE
                containerTimeUp.visibility = View.VISIBLE
                i = WAIT_SECONDS
            }
            if (mHandler != null)
                mHandler!!.postDelayed(this, 1000)
        }
    }

    private var user = "SU110701724168420"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivitySmsVerificationBindingImpl>(this, R.layout.activity_sms_verification)
        binding.activity = this
        binding.lifecycleOwner = this
        binding.vm = smsViewModel
        animation.run()

        user = intent.getStringExtra("user")!!
        val type = intent.getStringExtra("type")!!
        smsViewModel.productType = type

        smsViewModel.requestPin(user,null)
        showLoadingDialog("Cargando")
        editNumber.setOnClickListener {
            showDialogLogin(this)
        }

        smsViewModel.requestPinLiveData.observe(this, Observer {
            hideLoadingDialog()
            if(it.status == "ok")
                numberText.text = "********${it.phone?.removeRange(0, 8)}"
            else if(it.status == "error"){
                if(it.desc!!.contains("reached max",true))
                    showMessage("¡Lo sentimos! Has alcanzado el máximo de intentos para cambiar el número telefónico.")
                if(it.desc!! == "Number already exists") {
                    showMessage("Este número ya existe. Intenta con otro")
                    showDialogLogin(this)
                }
            }else if(it.status == "info"){
                numberText.text = "Sin número"
                showDialogLogin(this,true)
            }
        })

        retrySms.setOnClickListener {
            mSpin.color = ContextCompat.getColor(this,R.color.titleText)
            mSpin.setBounds(0, 0, 50, 50)
            retrySms.setCompoundDrawables(null,null,mSpin,null)
            smsViewModel.resendPin(user,"sms")

        }

        retryVoice.setOnClickListener {
            mSpin.color = ContextCompat.getColor(this,R.color.titleText)
            mSpin.setBounds(0, 0, 50, 50)
            retryVoice.setCompoundDrawables(null,null,mSpin,null)
            smsViewModel.resendPin(user,"voice")

        }

        smsViewModel.retryPinLiveData.observe(this, Observer {
            if(it.status == "ok"){
                retrySms.setCompoundDrawables(null,null,null,null)
                retryVoice.setCompoundDrawables(null,null,null,null)
                contadorText.visibility = View.VISIBLE
                containerTimeUp.visibility = View.GONE
                mHandler = Handler(Looper.getMainLooper())
                animation.run()
            }else if(it.desc!!.contains("PIN reached",true)){
                showMessage("Máximo número de intentos alcanzado. Por favor, inténtalo de nuevo en 5 minutos.")
            }
        })

        smsViewModel.sendPinLiveData.observe(this, Observer {
            if(it.desc!!.contains("incorrect")){
                showMessage("Tu PIN es incorrecto intenta nuevamente")
            }else if(it.status == "ok"){
                finish()
            }
        })

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                errorTerms.visibility = View.GONE
            }
        }
    }

    private fun setText(text: CharSequence) {
        runOnUiThread { contadorText.text = text }
    }

    fun sendPin(){
        if(!checkBox.isChecked){
            errorTerms.visibility = View.VISIBLE
        }else {
            errorTerms.visibility = View.GONE
            if(smsViewModel.validatePin(pinInput.editText?.text.toString()))
                smsViewModel.sendPin(user,pinInput.editText?.text.toString())
        }
    }

    override fun submit(number:String) {
        if(smsViewModel.validatePhone(number)) {
            smsViewModel.requestPin(user, number)
            hideDialog()
        }
    }

    override fun onResume() {
        super.onResume()
        mSpin.start()
    }

    override fun onStop() {
        super.onStop()
        mSpin.stop()
    }

    companion object{
        const val WAIT_SECONDS = 45
    }

}