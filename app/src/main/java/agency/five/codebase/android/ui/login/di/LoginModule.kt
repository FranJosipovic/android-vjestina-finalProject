package agency.five.codebase.android.ui.login.di

import agency.five.codebase.android.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel {
        LoginViewModel(
            userRepository = get()
        )
    }
}
