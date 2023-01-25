package agency.five.codebase.android.data.repository.todo

import agency.five.codebase.android.data.database.todo.TodoDao
import agency.five.codebase.android.data.repository.user.UserRepository
import agency.five.codebase.android.model.DateCategory
import agency.five.codebase.android.model.Todo
import agency.five.codebase.android.utilities.Utilities
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class TodoRepositoryImpl(
    private val userRepository: UserRepository,
    private val todoDao: TodoDao,
    private val bgDispatcher: CoroutineDispatcher
) : TodoRepository {

    override fun getUserId(): String = userRepository.currentUser.value?.uid.orEmpty()

    @RequiresApi(Build.VERSION_CODES.O)
    override val todos: Flow<List<Todo>> = userRepository.currentUser.flatMapLatest {
        todoDao.getTodos(it?.uid.orEmpty())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun todosByCategory(categoryId: String): Flow<List<Todo>> {
        return todoDao.getTodosByCategory(getUserId(), categoryId).flowOn(bgDispatcher)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun nextUpcomingTodo(): Flow<Todo?> {
        return todos.flatMapLatest { todoList ->
            flow {
                emit(
                    Utilities.findNextUpcomingTodo(
                        todoList
                    )
                )
            }
        }.flowOn(bgDispatcher)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getTodosByRegex(pattern: Regex): Flow<List<Todo>> {
        return todos.map { todoList ->
            todoList.filter { todo ->
                pattern.containsMatchIn(
                    todo.note
                )
            }
        }
            .flowOn(bgDispatcher)
    }

    override suspend fun addTodo(
        categoryId: String,
        title: String,
        dueDate: LocalDateTime,
        note: String
    ) {
        withContext(bgDispatcher) {
            todoDao.insertTodo(categoryId, title, dueDate, note, getUserId())
        }
    }

    override suspend fun deleteTodo(todo: Todo) {
        withContext(bgDispatcher) {
            todoDao.removeTodo(todo.id)
        }
    }


    override suspend fun toggleTodoCompletion(todo: Todo) {
        withContext(bgDispatcher) {
            todoDao.toggleTodoCompletion(todo)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun todosByDateCategory(category: DateCategory): Flow<List<Todo>> {
        return todos.mapLatest {
            when (category) {
                DateCategory.TODAY -> it.filter { todo -> Utilities.isToday(todo.due_date) }
                DateCategory.TOMORROW -> it.filter { todo -> Utilities.isTomorrow(todo.due_date) }
                DateCategory.UPCOMING -> it
                DateCategory.COMPLETED -> it.filter { todo -> todo.is_completed }
            }
        }.flowOn(bgDispatcher)
    }
}
