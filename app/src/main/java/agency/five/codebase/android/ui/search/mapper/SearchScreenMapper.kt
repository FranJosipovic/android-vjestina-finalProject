package agency.five.codebase.android.ui.search.mapper

import agency.five.codebase.android.model.Category
import agency.five.codebase.android.model.Todo
import agency.five.codebase.android.ui.search.SearchScreenViewState

interface SearchScreenMapper {
    fun toSearchScreenViewState(
        todos: List<Todo>,
        categories: List<Category>
    ): SearchScreenViewState
}
