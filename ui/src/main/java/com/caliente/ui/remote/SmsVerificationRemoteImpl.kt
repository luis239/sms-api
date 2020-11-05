package com.caliente.ui.remote

import com.caliente.ui.data.SmsVerificationRemote
import com.caliente.ui.data.model.ResponseRequestPinData

class SmsVerificationRemoteImpl(private val service : ApiCaliente) : SmsVerificationRemote {
    override suspend fun validateUser(
        user: String,
        needSMS: String,
        productType: String
    ): ResponseRequestPinData {
        val request = service.validateUser(user, "1", productType).body()
        print(request)
        return ResponseRequestPinData(
            request?.phone,
            request?.prefix,
            request?.status,
            request?.desc,
            request?.mcUsed,
            request?.eligible
        )
    }

    override suspend fun requestPin(user: String, method: String,number:String?): ResponseRequestPinData {
        val body = service.requestPin(user,number= number).body()
        return ResponseRequestPinData(body?.phone, body?.prefix, body?.status, body?.desc)
    }

    override suspend fun resendPin(user: String, method: String): ResponseRequestPinData {
        val request = service.resendPin(user, method).body()
        return ResponseRequestPinData(
            request?.phone,
            request?.prefix,
            request?.status,
            request?.desc
        )
    }

    override suspend fun sendPin(
        user: String,
        smsClientType: String,
        pin: String
    ): ResponseRequestPinData {
        val request = service.sendPin(user, smsClientType, pin).body()
        return ResponseRequestPinData(
            request?.phone,
            request?.prefix,
            request?.status,
            request?.desc
        )
    }
}


