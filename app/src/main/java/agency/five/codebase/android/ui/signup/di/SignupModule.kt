package agency.five.codebase.android.ui.signup.di

import agency.five.codebase.android.ui.signup.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val signupModule = module {
    viewModel {
        SignupViewModel(
            userRepository = get()
        )
    }
}
