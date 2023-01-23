package agency.five.codebase.android.data.todo

import agency.five.codebase.android.data.database.CategoryFirestoreDao
import agency.five.codebase.android.data.database.TodoFirestoreDao
import agency.five.codebase.android.data.user.UserRepository
import agency.five.codebase.android.model.Category
import agency.five.codebase.android.model.DateCategory
import agency.five.codebase.android.model.Todo
import agency.five.codebase.android.utilities.Utilities
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext


class TodoFirestoreRepositoryImpl(
    private val userRepository: UserRepository,
    private val todoFirestoreDao: TodoFirestoreDao,
    private val categoryFirestoreDao: CategoryFirestoreDao,
    private val bgDispatcher: CoroutineDispatcher
) : TodoFirestoreRepository {

    override val todos: Flow<List<Todo>> = userRepository.currentUser.flatMapLatest {
        todoFirestoreDao.getTodos(it?.uid.orEmpty())
    }

    override fun todosByCategory(categoryId: String, userId: String): Flow<List<Todo>> {
        return todoFirestoreDao.getTodosByCategory(userId, categoryId).flowOn(bgDispatcher)
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

    override suspend fun addTodo(todo: Todo, userId: String) {
        withContext(bgDispatcher) {
            todoFirestoreDao.insertTodo(todo)
        }
    }

    override suspend fun deleteTodo(todo: Todo, userId: String) {
        withContext(bgDispatcher) {
            todoFirestoreDao.removeTodo(todo.id)
        }
    }

    override suspend fun addCategory(categoryTitle: String, userId: String) {
        withContext(bgDispatcher) {
            categoryFirestoreDao.insertCategory(
                userId = userId, title = categoryTitle
            )
        }
    }

    override fun categoryById(categoryId: String, userId: String): Flow<Category> {
        return categoryFirestoreDao.getCategoryById(categoryId = categoryId)
    }

    override suspend fun toggleTodoCompletion(todo: Todo, userId: String) {
        withContext(bgDispatcher) {
            todoFirestoreDao.toggleTodoCompletion(todo)
        }
    }

    override suspend fun deleteCategory(category: Category, userId: String) {
        withContext(bgDispatcher) {
            categoryFirestoreDao.removeCategory(categoryId = category.id)
        }
    }

    override val categories: Flow<List<Category>> =
        userRepository.currentUser.flatMapLatest {
            flow {
                emit(categoryFirestoreDao.getCategories(it?.uid.orEmpty()))
            }
        }

    override fun getUserId(): String = userRepository.currentUser.value?.uid.orEmpty()

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
