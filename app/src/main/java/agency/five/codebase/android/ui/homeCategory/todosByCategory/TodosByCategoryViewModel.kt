package agency.five.codebase.android.ui.homeCategory.todosByCategory

import agency.five.codebase.android.data.repository.category.CategoryRepository
import agency.five.codebase.android.data.repository.todo.TodoRepository
import agency.five.codebase.android.data.repository.user.UserRepository
import agency.five.codebase.android.model.Category
import agency.five.codebase.android.model.Todo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodosByCategoryViewModel(
    private val todoRepository: TodoRepository,
    private val categoryRepository: CategoryRepository,
    categoryId: String
) : ViewModel() {

    val todosByCategory: StateFlow<List<Todo>> =
        todoRepository
            .todosByCategory(categoryId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val category: StateFlow<Category> =
        categoryRepository
            .categoryById(categoryId = categoryId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Category(title = "", id = "", user_id = "")
            )

    fun toggleTodoCompletion(todo: Todo) {
        viewModelScope.launch {
            todoRepository.toggleTodoCompletion(todo)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.deleteTodo(todo)
        }
    }
}
