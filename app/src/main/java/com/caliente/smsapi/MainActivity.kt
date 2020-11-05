package com.caliente.smsapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.caliente.ui.MainUiActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var type = "sports"
        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            type = if(radioGroup.checkedRadioButtonId== R.id.casino){
                "casino"
            }else{
                "sports"
            }
        }




        button2.setOnClickListener {
            val i = Intent(this,MainUiActivity::class.java).apply {
                putExtra("user",textInputLayout2.editText?.text.toString())
                putExtra("type",type)
            }
            startActivity(i)
        }
    }
}
