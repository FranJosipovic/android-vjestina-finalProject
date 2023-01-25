package agency.five.codebase.android.data.repository.todo

import agency.five.codebase.android.model.DateCategory
import agency.five.codebase.android.model.Todo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface TodoRepository {
    val todos: Flow<List<Todo>>
    fun getUserId(): String
    fun todosByDateCategory(category: DateCategory): Flow<List<Todo>>
    fun todosByCategory(categoryId: String): Flow<List<Todo>>
    fun nextUpcomingTodo(): Flow<Todo?>
    fun getTodosByRegex(pattern: Regex): Flow<List<Todo>>
    suspend fun addTodo(categoryId: String, title: String, dueDate: LocalDateTime, note: String)
    suspend fun deleteTodo(todo: Todo)
    suspend fun toggleTodoCompletion(todo: Todo)
}
