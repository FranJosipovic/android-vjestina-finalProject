package agency.five.codebase.android.data.database.todo

import agency.five.codebase.android.model.Todo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface TodoDao {
    fun getTodos(userId: String): Flow<List<Todo>>
    fun getTodosByCategory(userId: String, categoryId: String): Flow<List<Todo>>
    fun insertTodo(categoryId: String, title:String, dueDate: LocalDateTime, note:String, userId:String)
    fun removeTodo(todoId: String)
    fun toggleTodoCompletion(todo: Todo)
}
