package agency.five.codebase.android.ui.home.mapper

import agency.five.codebase.android.model.Category
import agency.five.codebase.android.model.DateCategory
import agency.five.codebase.android.model.Todo
import agency.five.codebase.android.ui.home.HomeScreenViewState

interface HomeScreenMapper {
    fun toHomeScreenViewState(
        dateCategories: List<DateCategory>,
        selectedDateCategory: DateCategory,
        todos: List<Todo>,
        categories: List<Category>
    ): HomeScreenViewState
}
