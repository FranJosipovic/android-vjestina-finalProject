package agency.five.codebase.android.ui.home

import agency.five.codebase.android.data.todo.TodoFirestoreRepository
import agency.five.codebase.android.model.DateCategory
import agency.five.codebase.android.model.Todo
import agency.five.codebase.android.ui.home.mapper.HomeScreenMapper
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val todoFirestoreRepository: TodoFirestoreRepository,
    private val homeMapper: HomeScreenMapper
) : ViewModel() {

    private val userId: String
        get() = todoFirestoreRepository.getUserId()

    private val dateCategorySelected: MutableStateFlow<DateCategory> =
        MutableStateFlow(DateCategory.UPCOMING)

    val data: StateFlow<HomeScreenViewState> =
        dateCategorySelected.flatMapLatest { selectedCategory ->
            todoFirestoreRepository.categories.flatMapLatest { categories ->
                todoFirestoreRepository.todosByDateCategory(selectedCategory).map { todos ->
                    homeMapper.toHomeScreenViewState(
                        dateCategories = DateCategory.values().toList(),
                        selectedDateCategory = selectedCategory,
                        todos = todos,
                        categories = categories
                    )
                }
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = HomeScreenViewState(emptyList(), emptyList())
            )

    @RequiresApi(Build.VERSION_CODES.O)
    val nextUpcomingTodo: StateFlow<Todo?> =
        todoFirestoreRepository.nextUpcomingTodo()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )

    fun changeDateCategory(category: DateCategory) {
        viewModelScope.launch {
            dateCategorySelected.update { category }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun toggleTodoCompletion(todo: Todo) {
        viewModelScope.launch {
            todoFirestoreRepository.toggleTodoCompletion(todo, userId)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            todoFirestoreRepository.deleteTodo(todo, userId)
        }
    }
}
