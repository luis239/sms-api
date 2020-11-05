package com.caliente.ui.domain

import com.caliente.ui.domain.model.ResponseRequestPinModel
import kotlin.Exception

class SendPinUseCase (private val smsVerificationRepository: SmsVerificationRepository):
    BaseUseCase<ResponseRequestPinModel, SendPinUseCase.Params>() {
    override suspend fun run(params: Params): Either<Failure, ResponseRequestPinModel> {
        return try {
            val response = smsVerificationRepository.sendPin(params.username,params.productType,params.pin)
           /* if(response.result == "ERROR"){
                Either.Left(SignUpFailure(java.lang.Exception(response.err)))
            }else {*/
                Either.Right(response)
            //}
        }catch (e:Exception){
            e.printStackTrace()
            Either.Left(SignUpFailure(e))
        }

    }

    data class Params (val username : String , val pin : String, val productType: String)

    data class SignUpFailure(val error : Exception) : Failure.FeatureFailure(error)

}