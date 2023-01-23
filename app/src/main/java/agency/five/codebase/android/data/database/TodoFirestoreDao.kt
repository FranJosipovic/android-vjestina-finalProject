package agency.five.codebase.android.data.database

import agency.five.codebase.android.model.Todo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

const val TODOS_COLLECTION = "todos"

class TodoFirestoreDao {
    val db = Firebase.firestore

    fun getTodos(userId: String): Flow<List<Todo>> {
        return flow {
            emit(
                db.collection(TODOS_COLLECTION).whereEqualTo("user_id", userId).get().await()
                    .toObjects(Todo::class.java)
            )
        }
    }

    fun getTodosByCategory(userId: String, categoryId: String): Flow<List<Todo>> {
        return flow {
            emit(
                db.collection(TODOS_COLLECTION).whereEqualTo("user_id", userId)
                    .whereEqualTo("category_id", categoryId).get().await()
                    .toObjects(Todo::class.java)
            )
        }
    }

    fun insertTodo(todo: Todo) {
        val data = hashMapOf(
            "category_id" to todo.category_id,
            "user_id" to todo.user_id,
            "due_date" to todo.due_date,
            "title" to todo.title,
            "note" to todo.note,
            "is_completed" to todo.is_completed
        )
        db.collection(TODOS_COLLECTION).add(data)
    }

    fun removeTodo(todoId: String) {
        db.collection(CATEGORIES_COLLECTION).document(todoId).delete()
    }

    fun toggleTodoCompletion(todo: Todo) {
        db.collection(TODOS_COLLECTION).document(todo.id).update("is_completed", !todo.is_completed)
    }
}
