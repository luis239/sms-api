package com.caliente.ui.inject


import com.caliente.ui.data.SmsVerificationRemote
import com.caliente.ui.data.SmsVerificationRepositoryImpl
import com.caliente.ui.domain.*
import com.caliente.ui.remote.ApiCalienteImpl
import com.caliente.ui.remote.SmsVerificationRemoteImpl
import org.koin.dsl.module

val remoteModule = module {

    //retrofit
    single { ApiCalienteImpl().getApiCaliente() }


    //use cases
    single { RequestPinUseCase(get()) }
    single { ValidateUserPinUseCase(get()) }
    single { RetryPinUseCase(get()) }
    single { SendPinUseCase(get()) }

    factory<SmsVerificationRepository>{ SmsVerificationRepositoryImpl(get()) }
    factory<SmsVerificationRemote> { SmsVerificationRemoteImpl(get()) }
   /* //class

    single{TrackerModel()}
    single{
        provideSharedPreferences(androidApplication())
    }

    //com.caliente.ui.data
    factory<RegisterRepository> {
        RegisterRepositoryImpl(
            get()
        )
    }
    factory<ConfigRepository> { ConfigRepositoryImpl(get()) }
    factory<UpdateRepository> { UpdateConfigRepositoryImpl(get()) }
    factory<ValidationRepository>{ValidationRepositoryImpl(get())}

    //com.caliente.ui.remote
    factory<RegisterRemote> { RegisterRemoteImpl(get(),get(),get()) }
    factory<ConfigRemote> { ConfigRemoteImpl() }
    factory<UpdateRemote> { UpdateRemoteImpl() }
    factory<ValidationRemote> { ValidationRemoteImpl(get()) }

    //thread rx
    factory<PostExecutionThread> { UiThread() }*/



}

