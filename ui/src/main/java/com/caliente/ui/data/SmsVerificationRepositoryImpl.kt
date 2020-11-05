package com.caliente.ui.data


import com.caliente.ui.domain.SmsVerificationRepository
import com.caliente.ui.domain.model.ResponseRequestPinModel

class SmsVerificationRepositoryImpl(private val remote: SmsVerificationRemote) :
    SmsVerificationRepository {
    override suspend fun validateUser(user: String, needSMS: String, productType: String): ResponseRequestPinModel {
         val request = remote.validateUser(user,"1",productType)
        print(request)
        return ResponseRequestPinModel(request.phone,request.prefix,request.status,request.desc,request.mcUsed,request.eligible)
    }

    override suspend fun requestPin(user: String, method: String,number:String?): ResponseRequestPinModel {
        val body = remote.requestPin(user, method,number)
        return ResponseRequestPinModel(body.phone,body.prefix,body.status,body.desc)
    }

    override suspend fun resendPin(user: String, method: String): ResponseRequestPinModel {
        val request = remote.resendPin(user,method)
        return ResponseRequestPinModel(request.phone,request.prefix,request.status,request.desc)
    }

    override suspend fun sendPin(user: String, smsClientType: String, pin: String): ResponseRequestPinModel {
        val request = remote.sendPin(user,smsClientType,pin)
        return ResponseRequestPinModel(request.phone,request.prefix,request.status,request.desc)
    }
}