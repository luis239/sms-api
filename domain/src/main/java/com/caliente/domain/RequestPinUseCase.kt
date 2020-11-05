package domain

import com.caliente.domain.model.ResponseRequestPinModel
import kotlin.Exception

class RequestPinUseCase (private val smsVerificationRepository: SmsVerificationRepository):BaseUseCase<ResponseRequestPinModel,RequestPinUseCase.Params>() {
    override suspend fun run(params: Params): Either<Failure, ResponseRequestPinModel> {
        return try {
            val response = smsVerificationRepository.requestPin(params.username,params.method,params.number)
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

    data class Params (val username : String , val method : String,val number:String?)

    data class SignUpFailure(val error : Exception) : Failure.FeatureFailure(error)

}