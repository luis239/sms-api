package com.caliente.ui.remote

import com.caliente.ui.remote.model.ResponseRequestPin
import retrofit2.Response
import retrofit2.http.*

interface ApiCaliente {

    @GET("smsverify.php")
    suspend fun validateUser(
        @Query("username") user: String,
        @Query("needSMS") needSMS: String,
        @Query("product") productType: String
    ) : Response<ResponseRequestPin>

    @GET("smsverify.php")
    suspend fun requestPin(
        @Query("username") user: String,
        @Query("method") method: String = "sms",
        @Query("prefix") prefix: String = "52",
        @Query("number") number: String?
    ) : Response<ResponseRequestPin>

    @GET("resendpin.php")
    suspend fun resendPin(
        @Query("username") user: String,
        @Query("method") method: String
    ) : Response<ResponseRequestPin>

    @GET("smsverify.php")
    suspend fun sendPin(
        @Query("username") user: String,
        @Query("smsClientType") smsClientType: String,
        @Query("pin") pin: String
    ) : Response<ResponseRequestPin>
}