package agency.five.codebase.android.ui.create.di

import agency.five.codebase.android.ui.create.CreateViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val createModule = module {
    viewModel {
        CreateViewModel(
            todoRepository = get(),
            categoryRepository = get(),
        )
    }
}
