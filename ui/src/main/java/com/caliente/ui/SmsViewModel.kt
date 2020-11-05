package com.caliente.ui

import androidx.lifecycle.MutableLiveData
import com.caliente.ui.domain.*
import com.caliente.ui.domain.model.ResponseRequestPinModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class SmsViewModel (private val requestPinUseCase: RequestPinUseCase,
                    private val validateUserPinUseCase: ValidateUserPinUseCase,
                    private val sendPinUseCase: SendPinUseCase,
                    private val retryPinUseCase: RetryPinUseCase
): BaseViewModel() {


    val validUserLiveData = MutableLiveData<Boolean>()
    val requestPinLiveData = MutableLiveData<ResponseRequestPinModel>()
    val retryPinLiveData = MutableLiveData<ResponseRequestPinModel>()
    val sendPinLiveData = MutableLiveData<ResponseRequestPinModel>()

    lateinit var user:String

    val phoneError = MutableLiveData<String>()
    val errorPin = MutableLiveData<String>()
    lateinit var productType:String

    fun requestPin(username:String,number:String?){

        launch {
            val params = RequestPinUseCase.Params(username,"sms",number)
            requestPinUseCase.invoke(this,params){
                it.either( ::handleFailureRequestPin,::handleSuccessRequestPin)
            }
        }
    }

    private fun handleFailureRequestPin(f: Failure){

    }

    private fun handleSuccessRequestPin(s: ResponseRequestPinModel){
        requestPinLiveData.postValue(s)
    }

    fun resendPin(username:String,type:String){

        launch {
            val params = RetryPinUseCase.Params(username,type)
            retryPinUseCase.invoke(this,params){
                it.either( ::handleFailureResendPin,::handleSuccessResendPin)
            }
        }
    }

    private fun handleFailureResendPin(f:Failure){

    }

    private fun handleSuccessResendPin(s: ResponseRequestPinModel){

        retryPinLiveData.postValue(s)
    }
    fun sendPin(username:String,pin: String){

        launch {
            val params = SendPinUseCase.Params(username,pin,productType)
            sendPinUseCase.invoke(this,params){
                it.either( ::handleFailureSendPin,::handleSuccessSendPin)
            }
        }
    }

    private fun handleFailureSendPin(f:Failure){

    }

    private fun handleSuccessSendPin(s: ResponseRequestPinModel){
        sendPinLiveData.postValue(s)
    }
    fun verifyUser(username:String){

        launch {
            val params = ValidateUserPinUseCase.Params(username,productType)
            validateUserPinUseCase.invoke(this,params){
                it.either( ::handleFailureVerifyUser,::handleSuccessVerifyUser)
            }
        }
    }

    private fun handleFailureVerifyUser(f:Failure){

    }

    private fun handleSuccessVerifyUser(s: ResponseRequestPinModel){
        validUserLiveData.postValue(s.eligible)
    }

    fun validatePin(pin:String):Boolean{
        var isValid = true
        when {
            pin.isBlank() -> {
                isValid = false
                errorPin.value = "Introduce el pin"
            }
            pin.length < 3 -> {
                isValid = false
                errorPin.value = "El pin debe ser de 3 dígitos"
            }
            else -> {
                errorPin.value = ""
            }
        }

        return isValid

    }

    fun validatePhone(phone:String): Boolean{
        var isValid = true
        if (phone.isNullOrBlank()) {
            phoneError.value = "Requerido"
            isValid = false
        } else if (phone.length < 10 || !isValidPhone(phone)) {
            phoneError.value = ("Número inválido")
            isValid = false
        } else {
            phoneError.value = ""
        }
        return isValid
    }

    private fun isValidPhone(text: String): Boolean {
        val regexValidPhone =
            "^[0-9]*\$"
        val pattern = Pattern.compile(regexValidPhone)
        return pattern.matcher(text).find()
    }
}