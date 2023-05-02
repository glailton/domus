package glailton.io.github.domus.core.di

import glailton.io.github.domus.firebase.FirebaseAuthRepository
import glailton.io.github.domus.ui.presentation.screens.login.LoginViewModel
import glailton.io.github.domus.ui.presentation.screens.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domusModules = module {
    singleOf(::FirebaseAuthRepository)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
}
