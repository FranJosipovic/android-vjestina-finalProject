package agency.five.codebase.android.ui.homeCategory.todosByCategory

import agency.five.codebase.android.data.todo.TodoRepository
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
    categoryId: Int
) : ViewModel() {

    private val userId: String
        get() = todoRepository.getUserId()

    val todosByCategory: StateFlow<List<Todo>> =
        todoRepository
            .todosByCategory(categoryId,userId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val category: StateFlow<Category> =
        todoRepository
            .categoryById(categoryId = categoryId,userId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Category(title = "", id = -1)
            )

    fun toggleTodoCompletion(todo: Todo) {
        viewModelScope.launch {
            todoRepository.toggleTodoCompletion(todo,userId)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.deleteTodo(todo,userId)
        }
    }
}
