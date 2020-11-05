package com.caliente.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main_ui.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainUiActivity : AppCompatActivity() {

    private val smsViewModel:SmsViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ui)

        val extras = intent.extras

        val user = "SU110701724168420"//intent.getStringExtra("user")!!
        val type = "sports" //intent.getStringExtra("type")!!
        smsViewModel.productType = type
        smsViewModel.verifyUser(user)

        smsViewModel.validUserLiveData.observe(this, Observer {
            buttonSms.isEnabled = it
        })

        buttonSms.setOnClickListener {
            val intent = Intent(this,SmsVerificationActivity::class.java).apply {
                putExtra("user",user)
                putExtra("type",type)
            }
            startActivity(intent)
            finish()
        }
    }
}