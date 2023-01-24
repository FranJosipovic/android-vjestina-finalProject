package agency.five.codebase.android.ui.homeCategory.di

import agency.five.codebase.android.ui.homeCategory.HomeCategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeCategoryModule = module {
    viewModel {
        HomeCategoryViewModel(
            categoryRepository = get(),
        )
    }
}
