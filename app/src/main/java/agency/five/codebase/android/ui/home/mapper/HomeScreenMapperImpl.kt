package agency.five.codebase.android.ui.home.mapper

import agency.five.codebase.android.model.Category
import agency.five.codebase.android.model.DateCategory
import agency.five.codebase.android.model.Todo
import agency.five.codebase.android.ui.component.DateCategoryTabViewState
import agency.five.codebase.android.ui.home.HomeScreenViewState
import agency.five.codebase.android.ui.home.TodoCardViewState
import android.util.Log

class HomeScreenMapperImpl : HomeScreenMapper {
    override fun toHomeScreenViewState(
        dateCategories: List<DateCategory>,
        selectedDateCategory: DateCategory,
        todos: List<Todo>,
        categories: List<Category>
    ): HomeScreenViewState {
        return HomeScreenViewState(
            todos = toTodoCardViewState(
                todos = todos,
                categories = categories
            ),
            dateCategories = dateCategories.map { dateCategory ->
                DateCategoryTabViewState(
                    id = dateCategory.ordinal,
                    isSelected = dateCategory == selectedDateCategory,
                    dateCategoryText = when (dateCategory) {
                        DateCategory.TODAY -> "Today"
                        DateCategory.TOMORROW -> "Tomorrow"
                        DateCategory.UPCOMING -> "Upcoming"
                        DateCategory.COMPLETED -> "Completed"
                    }
                )
            }
        )
    }

    private fun toTodoCardViewState(
        todos: List<Todo>,
        categories: List<Category>
    ): List<TodoCardViewState> =
        todos.map { todo ->
            TodoCardViewState(
                todo = todo,
                categoryTitle = getCategoryTitle(
                    categoryId = todo.categoryId,
                    categories = categories
                )
            )
        }


    private fun getCategoryTitle(categoryId: Int, categories: List<Category>): String {
        val category = categories.find { category -> category.id == categoryId }
        return category?.title ?: "No category found"
    }
}
