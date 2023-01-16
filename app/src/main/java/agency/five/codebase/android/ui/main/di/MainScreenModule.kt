package agency.five.codebase.android.ui.main.di

import agency.five.codebase.android.ui.main.MainScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainScreenModule = module {
    viewModel {
        MainScreenViewModel()
    }
}
