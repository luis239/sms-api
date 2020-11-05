package com.caliente.ui.inject

import com.caliente.ui.SmsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { SmsViewModel(get(),get(),get(),get())}
    /*viewModel { MainViewModel(get()) }
    viewModel { VerificationViewModel(get(),get(),get()) }*/
}