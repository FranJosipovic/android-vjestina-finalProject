package agency.five.codebase.android.ui.search

import agency.five.codebase.android.data.todo.TodoRepository
import agency.five.codebase.android.model.Todo
import agency.five.codebase.android.ui.search.mapper.SearchScreenMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(
    private val todoRepository: TodoRepository,
    private val mapper: SearchScreenMapper
) : ViewModel() {

    private val userId: String
        get() = todoRepository.getUserId()

    private var regex = MutableStateFlow(Regex(null.toString()))

    val todos: StateFlow<SearchScreenViewState> =
        regex.flatMapLatest {
            todoRepository.categories.flatMapLatest { categories ->
                todoRepository.getTodosByRegex(it).map { todos ->
                    mapper.toSearchScreenViewState(
                        todos,
                        categories
                    )
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            initialValue = SearchScreenViewState(
                emptyList()
            )
        )

    fun updateRegexInput(input: String) {
        regex.update { Regex(input) }
    }

    fun toggleTodoCompletion(todo: Todo) {
        viewModelScope.launch {
            todoRepository.toggleTodoCompletion(todo, userId)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.deleteTodo(todo,userId)
        }
    }
}
