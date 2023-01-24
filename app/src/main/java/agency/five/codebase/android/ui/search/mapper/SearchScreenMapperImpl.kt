package agency.five.codebase.android.ui.search.mapper

import agency.five.codebase.android.model.Category
import agency.five.codebase.android.model.Todo
import agency.five.codebase.android.ui.home.TodoCardViewState
import agency.five.codebase.android.ui.search.SearchScreenViewState

class SearchScreenMapperImpl : SearchScreenMapper {
    override fun toSearchScreenViewState(
        todos: List<Todo>,
        categories: List<Category>
    ): SearchScreenViewState {
        return SearchScreenViewState(
            todos = toTodoCardViewState(todos = todos, categories = categories)
        )
    }

    private fun toTodoCardViewState(
        todos: List<Todo>,
        categories: List<Category>
    ): List<TodoCardViewState> =
        todos.map { todo ->
            TodoCardViewState(
                todo = todo, categoryTitle = getCategoryTitle(
                    categoryId = todo.category_id,
                    categories = categories
                )
            )
        }


    private fun getCategoryTitle(categoryId: String, categories: List<Category>): String {
        val category: Category? = categories.find { category -> category.id == categoryId }
        return category?.title ?: "No category found"
    }
}
