package glailton.io.github.domus.core.di

import glailton.io.github.domus.firebase.FirebaseAuthRepository
import glailton.io.github.domus.firebase.FirebaseAuthRepositoryImpl
import glailton.io.github.domus.firebase.FirestoreRepository
import glailton.io.github.domus.firebase.FirestoreRepositoryImpl
import glailton.io.github.domus.ui.presentation.screens.home.HomeViewModel
import glailton.io.github.domus.ui.presentation.screens.login.LoginViewModel
import glailton.io.github.domus.ui.presentation.screens.profile.ProfileViewModel
import glailton.io.github.domus.ui.presentation.screens.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val domusModules = module {
    single<FirestoreRepository> { FirestoreRepositoryImpl() }
    single<FirebaseAuthRepository> {
        FirebaseAuthRepositoryImpl(
            firestoreRepository = get()
        )
    }
    viewModel { LoginViewModel(firebaseAuth = get()) }

    viewModelOf(::RegisterViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::ProfileViewModel)
}
