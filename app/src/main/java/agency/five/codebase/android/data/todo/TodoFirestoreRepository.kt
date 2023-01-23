package agency.five.codebase.android.data.todo

import agency.five.codebase.android.model.Category
import agency.five.codebase.android.model.DateCategory
import agency.five.codebase.android.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface TodoFirestoreRepository {
    val categories: Flow<List<Category>>
    fun getUserId(): String
    fun todosByDateCategory(category: DateCategory): Flow<List<Todo>>
    val todos: Flow<List<Todo>>
    fun todosByCategory(categoryId: String, userId: String): Flow<List<Todo>>
    fun nextUpcomingTodo(): Flow<Todo?>
    fun getTodosByRegex(pattern: Regex): Flow<List<Todo>>
    suspend fun addTodo(todo: Todo, userId: String)
    suspend fun deleteTodo(todo: Todo, userId: String)
    suspend fun addCategory(categoryTitle: String, userId: String)
    fun categoryById(categoryId: String, userId: String): Flow<Category>
    suspend fun toggleTodoCompletion(todo: Todo, userId: String)
    suspend fun deleteCategory(category: Category, userId: String)
}
