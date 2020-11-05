package domain

import com.caliente.domain.model.ResponseRequestPinModel
import kotlin.Exception

class ValidateUserPinUseCase (private val smsVerificationRepository: SmsVerificationRepository):BaseUseCase<ResponseRequestPinModel,ValidateUserPinUseCase.Params>() {
    override suspend fun run(params: Params): Either<Failure, ResponseRequestPinModel> {
        return try {
            val response = smsVerificationRepository.validateUser(user = params.username, productType = params.password)
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

    data class Params (val username : String , val password : String)

    data class SignUpFailure(val error : Exception) : Failure.FeatureFailure(error)

}