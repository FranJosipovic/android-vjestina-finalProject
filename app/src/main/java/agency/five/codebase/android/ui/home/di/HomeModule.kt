package agency.five.codebase.android.ui.home.di

import agency.five.codebase.android.ui.home.HomeViewModel
import agency.five.codebase.android.ui.home.mapper.HomeScreenMapper
import agency.five.codebase.android.ui.home.mapper.HomeScreenMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        HomeViewModel(
            todoRepository = get(),
            homeMapper = get(),
        )
    }
    single<HomeScreenMapper> { HomeScreenMapperImpl() }
}
