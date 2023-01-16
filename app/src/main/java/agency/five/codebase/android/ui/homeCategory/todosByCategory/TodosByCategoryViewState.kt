package agency.five.codebase.android.ui.homeCategory.todosByCategory

import agency.five.codebase.android.ui.home.TodoCardViewState

data class TodosByCategoryViewState(
    val todos: List<TodoCardViewState>,
    val categoryTitle: String
)
