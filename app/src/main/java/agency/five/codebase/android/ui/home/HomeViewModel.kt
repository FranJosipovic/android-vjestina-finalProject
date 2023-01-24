package agency.five.codebase.android.ui.home

import agency.five.codebase.android.data.repository.category.CategoryRepository
import agency.five.codebase.android.data.repository.todo.TodoRepository
import agency.five.codebase.android.data.repository.user.UserRepository
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
    private val todoRepository: TodoRepository,
    private val categoryRepository: CategoryRepository,
    private val homeMapper: HomeScreenMapper
) : ViewModel() {

    private val dateCategorySelected: MutableStateFlow<DateCategory> =
        MutableStateFlow(DateCategory.UPCOMING)

    val data: StateFlow<HomeScreenViewState> =
        dateCategorySelected.flatMapLatest { selectedCategory ->
            categoryRepository.categories.flatMapLatest { categories ->
                todoRepository.todosByDateCategory(selectedCategory).map { todos ->
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
        todoRepository.nextUpcomingTodo()
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
            todoRepository.toggleTodoCompletion(todo)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.deleteTodo(todo)
        }
    }
}
