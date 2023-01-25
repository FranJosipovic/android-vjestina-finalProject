package agency.five.codebase.android.ui.create

import agency.five.codebase.android.data.repository.category.CategoryRepository
import agency.five.codebase.android.data.repository.todo.TodoRepository
import agency.five.codebase.android.model.Category
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class CreateViewModel(
    private val todoRepository: TodoRepository,
    private val categoryRepository: CategoryRepository,
) : ViewModel() {
    var createScreenState by mutableStateOf(CreateScreenViewState())
        private set

    val categories: StateFlow<List<Category>> = categoryRepository.categories.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun updateSelectedCategory(category: Category) {
        createScreenState = createScreenState.copy(category = category)
    }

    fun updateSelectedTime(time: LocalTime) {
        createScreenState = createScreenState.copy(time = time)
    }

    fun updateSelectedDate(date: LocalDate) {
        createScreenState = createScreenState.copy(date = date)
    }

    fun onTodoTitleChange(todoTitle: String) {
        createScreenState = createScreenState.copy(todoTitle = todoTitle)
    }

    fun onTodoNoteChange(todoNote: String) {
        createScreenState = createScreenState.copy(todoNote = todoNote)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addTodo() {
        createScreenState = createScreenState.copy(
            todoTitleError = null,
            todoNoteError = null,
            categoryError = null,
            dateError = null,
            timeError = null
        )
        if (createScreenState.todoTitle.isBlank()) {
            createScreenState = createScreenState.copy(todoTitleError = "title must not be empty")
        }
        if (createScreenState.todoNote.isBlank()) {
            createScreenState = createScreenState.copy(todoNoteError = "note must not be empty")
        }
        if (createScreenState.category == null) {
            createScreenState =
                createScreenState.copy(categoryError = "you need to select category")
        }
        if (createScreenState.date == null) {
            createScreenState =
                createScreenState.copy(dateError = "you need to select date")
        }
        if (createScreenState.time == null) {
            createScreenState =
                createScreenState.copy(timeError = "you need to select time")
        }

        val hasError: Boolean = listOf(
            createScreenState.dateError,
            createScreenState.timeError,
            createScreenState.categoryError,
            createScreenState.todoTitleError,
            createScreenState.todoNoteError
        ).any { error -> error != null }

        if (!hasError) {
            createScreenState = createScreenState.copy(isSuccess = true)
        }

        if (createScreenState.isSuccess) {
            viewModelScope.launch {
                todoRepository.addTodo(
                    categoryId = createScreenState.category!!.id,
                    title = createScreenState.todoTitle,
                    dueDate = LocalDateTime.of(
                        createScreenState.date,
                        createScreenState.time
                    ),
                    note = createScreenState.todoNote,
                )
                createScreenState = createScreenState.copy(createFinished = true)
            }
        }
    }

    fun addCategory(categoryTitle: String) {
        viewModelScope.launch {
            categoryRepository.addCategory(categoryTitle)
        }
    }
}
