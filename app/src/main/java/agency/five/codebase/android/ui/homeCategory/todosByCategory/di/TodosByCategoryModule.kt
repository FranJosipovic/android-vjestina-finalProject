package agency.five.codebase.android.ui.homeCategory.todosByCategory.di

import agency.five.codebase.android.ui.homeCategory.todosByCategory.TodosByCategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val todosByCategoryModule = module {
    viewModel { (categoryId: String) ->
        TodosByCategoryViewModel(
            todoRepository = get(),
            categoryRepository = get(),
            categoryId = categoryId
        )
    }
}
