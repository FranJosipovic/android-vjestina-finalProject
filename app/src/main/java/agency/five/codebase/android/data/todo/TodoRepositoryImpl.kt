package agency.five.codebase.android.data.todo

import agency.five.codebase.android.data.database.CategoryDao
import agency.five.codebase.android.data.database.DBCategory
import agency.five.codebase.android.data.database.DBTodo
import agency.five.codebase.android.data.database.TodoDao
import agency.five.codebase.android.data.user.UserRepository
import agency.five.codebase.android.model.Category
import agency.five.codebase.android.model.DateCategory
import agency.five.codebase.android.model.Todo
import agency.five.codebase.android.utilities.Utilities
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.util.*

class TodoRepositoryImpl(
    private val todoDao: TodoDao,
    private val categoryDao: CategoryDao,
    private val bgDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository
) : TodoRepository {

    override fun getUserId(): String = userRepository.currentUser.value?.uid.orEmpty()

    override val todos = userRepository.currentUser.flatMapLatest {
        todoDao.todos(it?.uid.orEmpty()).map { todos ->
            todos.map { todo ->
                Todo(
                    id = todo.id,
                    categoryId = todo.categoryId,
                    title = todo.title,
                    dueDate = todo.dueDate,
                    note = todo.note,
                    isCompleted = todo.isCompleted
                )
            }
        }
    }.shareIn(
        scope = CoroutineScope(bgDispatcher),
        started = SharingStarted.WhileSubscribed(1000L),
        replay = 1
    )

    override val categories: Flow<List<Category>> = userRepository.currentUser.flatMapLatest {
        categoryDao.categories(it?.uid.orEmpty()).map { categories ->
            categories.map { category ->
                Category(
                    title = category.title,
                    id = category.id
                )
            }
        }
    }
        .shareIn(
            scope = CoroutineScope(bgDispatcher),
            started = SharingStarted.WhileSubscribed(1000L),
            replay = 1
        )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun todosByDateCategory(category: DateCategory): Flow<List<Todo>> {
        return todos.mapLatest {
            when (category) {
                DateCategory.TODAY -> it.filter { todo -> Utilities.isToday(todo.dueDate) }
                DateCategory.TOMORROW -> it.filter { todo -> Utilities.isTomorrow(todo.dueDate) }
                DateCategory.UPCOMING -> it
                DateCategory.COMPLETED -> it.filter { todo -> todo.isCompleted }
            }
        }.flowOn(bgDispatcher)
    }

    override fun todosByCategory(categoryId: Int, userId: String): Flow<List<Todo>> {
        return todoDao.getTodosByCategory(categoryId, userId).map {
            it.map { todo ->
                Todo(
                    id = todo.id,
                    categoryId = todo.categoryId,
                    title = todo.title,
                    dueDate = todo.dueDate,
                    note = todo.note,
                    isCompleted = todo.isCompleted
                )
            }
        }.flowOn(bgDispatcher)
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

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addTodo(todo: Todo, userId: String) {
        withContext(bgDispatcher) {
            todoDao.insertTodo(
                DBTodo(
                    id = todo.id,
                    categoryId = todo.categoryId,
                    userId = userId,
                    title = todo.title,
                    dueDate = todo.dueDate,
                    note = todo.note,
                    isCompleted = todo.isCompleted
                )
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun deleteTodo(todo: Todo, userId: String) {
        withContext(bgDispatcher) {
            todoDao.deleteTodo(
                DBTodo(
                    id = todo.id,
                    categoryId = todo.categoryId,
                    userId = userId,
                    title = todo.title,
                    dueDate = todo.dueDate,
                    note = todo.note,
                    isCompleted = todo.isCompleted
                )
            )
        }
    }

    override fun categoryById(categoryId: Int, userId: String): Flow<Category> {
        return categoryDao.categoryById(categoryId, userId)
            .flowOn(bgDispatcher)
    }

    override suspend fun addCategory(categoryTitle: String, userId: String) {
        withContext(bgDispatcher) {
            categoryDao.insertCategory(
                DBCategory(
                    id = UUID.randomUUID().hashCode(),
                    title = categoryTitle.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    },
                    userId = userId
                )
            )
        }
    }

    override suspend fun deleteCategory(category: Category, userId: String) {
        withContext(bgDispatcher) {
            todoDao.deleteAllById(category.id)
            categoryDao.deleteCategory(
                category = DBCategory(
                    id = category.id,
                    title = category.title,
                    userId = userId
                )
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun toggleTodoCompletion(todo: Todo, userId: String) {
        withContext(bgDispatcher) {
            val newTodo = DBTodo(
                id = todo.id,
                categoryId = todo.categoryId,
                userId = userId,
                title = todo.title,
                dueDate = todo.dueDate,
                note = todo.note,
                isCompleted = !todo.isCompleted
            )
            todoDao.updateItem(newTodo)
        }
    }
}
