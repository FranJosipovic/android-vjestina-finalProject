package agency.five.codebase.android.ui.search.di

import agency.five.codebase.android.ui.search.SearchViewModel
import agency.five.codebase.android.ui.search.mapper.SearchScreenMapper
import agency.five.codebase.android.ui.search.mapper.SearchScreenMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    viewModel {
        SearchViewModel(
            todoRepository = get(),
            mapper = get()
        )
    }
    single<SearchScreenMapper> { SearchScreenMapperImpl() }
}
