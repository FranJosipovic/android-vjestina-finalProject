package agency.five.codebase.android.data.database.todo

import agency.five.codebase.android.data.mapper.SnapshotMapper
import agency.five.codebase.android.model.Todo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.time.LocalDateTime

const val TODOS_COLLECTION = "todos"

class TodoDaoImpl(private val snapshotMapper: SnapshotMapper) : TodoDao {
    private val db = Firebase.firestore

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getTodos(userId: String): Flow<List<Todo>> {
        return callbackFlow {
            val registration = db.collection(TODOS_COLLECTION).whereEqualTo("user_id", userId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        cancel()
                        return@addSnapshotListener
                    } else if (snapshot != null) {
                        val data = snapshotMapper.mapToTodo(snapshot)
                        trySend(data)
                    }
                }
            awaitClose { registration.remove() }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getTodosByCategory(userId: String, categoryId: String): Flow<List<Todo>> {
        return callbackFlow {
            val registration = db.collection(TODOS_COLLECTION).whereEqualTo("user_id", userId)
                .whereEqualTo("category_id", categoryId).addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        cancel()
                        return@addSnapshotListener
                    }
                    if (snapshot != null) {
                        val data = snapshotMapper.mapToTodo(snapshot)
                        trySend(data)
                    }
                }
            awaitClose { registration.remove() }
        }
    }

    override fun insertTodo(categoryId: String, title:String, dueDate: LocalDateTime, note:String,userId:String) {
        val data = hashMapOf(
            "category_id" to categoryId,
            "user_id" to userId,
            "due_date" to dueDate,
            "title" to title,
            "note" to note,
            "is_completed" to false
        )
        db.collection(TODOS_COLLECTION).add(data)
    }

    override fun removeTodo(todoId: String) {
        Log.i("deleteTodo", todoId)
        db.collection(TODOS_COLLECTION).document(todoId).delete()
    }

    override fun toggleTodoCompletion(todo: Todo) {
        db.collection(TODOS_COLLECTION).document(todo.id).update("is_completed", !todo.is_completed)
    }
}
