package com.caliente.data

import com.caliente.data.model.ResponseRequestPinData
import com.caliente.domain.model.ResponseRequestPinModel

interface SmsVerificationRemote {
    suspend fun validateUser(
        user: String,
        needSMS: String,
        productType: String
    ) : ResponseRequestPinData


    suspend fun requestPin(
         user: String,
         method: String,
         number:String?
    ) :ResponseRequestPinData


    suspend fun resendPin(
        user: String,
        method: String
    ) : ResponseRequestPinData


    suspend fun sendPin(
       user: String,
       smsClientType: String,
       pin: String
    ) : ResponseRequestPinData

}