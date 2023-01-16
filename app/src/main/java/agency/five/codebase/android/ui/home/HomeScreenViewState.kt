package agency.five.codebase.android.ui.home

import agency.five.codebase.android.model.Todo
import agency.five.codebase.android.ui.component.DateCategoryTabViewState

data class HomeScreenViewState(
    val todos: List<TodoCardViewState>,
    val dateCategories: List<DateCategoryTabViewState>
)

data class TodoCardViewState(
    val todo: Todo,
    val categoryTitle: String
)
