package agency.five.codebase.android.ui.drawer.di

import agency.five.codebase.android.ui.drawer.DrawerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val drawerModule = module {
    viewModel {
        DrawerViewModel(userRepository = get())
    }
}
