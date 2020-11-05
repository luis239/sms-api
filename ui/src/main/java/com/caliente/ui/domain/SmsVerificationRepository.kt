package com.caliente.ui.domain

import com.caliente.ui.domain.model.ResponseRequestPinModel

interface SmsVerificationRepository {
    suspend fun validateUser(
        user: String,
        needSMS: String = "1",
        productType: String
    ) : ResponseRequestPinModel


    suspend fun requestPin(
        user: String,
        method: String,
        number:String?
    ) : ResponseRequestPinModel


    suspend fun resendPin(
        user: String,
        method: String
    ) : ResponseRequestPinModel


    suspend fun sendPin(
        user: String,
        smsClientType: String,
        pin: String
    ) : ResponseRequestPinModel

}