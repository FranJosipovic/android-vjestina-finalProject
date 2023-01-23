package agency.five.codebase.android.ui.homeCategory.todosByCategory

import agency.five.codebase.android.data.todo.TodoFirestoreRepository
import agency.five.codebase.android.model.Category
import agency.five.codebase.android.model.Todo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodosByCategoryViewModel(
    private val todoFirestoreRepository: TodoFirestoreRepository,
    categoryId: String
) : ViewModel() {

    private val userId: String
        get() = todoFirestoreRepository.getUserId()

    val todosByCategory: StateFlow<List<Todo>> =
        todoFirestoreRepository
            .todosByCategory(categoryId,userId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val category: StateFlow<Category> =
        todoFirestoreRepository
            .categoryById(categoryId = categoryId,userId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Category(title = "", id = "", user_id = userId)
            )

    fun toggleTodoCompletion(todo: Todo) {
        viewModelScope.launch {
            todoFirestoreRepository.toggleTodoCompletion(todo,userId)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            todoFirestoreRepository.deleteTodo(todo,userId)
        }
    }
}
