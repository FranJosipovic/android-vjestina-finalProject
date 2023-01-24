package agency.five.codebase.android.data.mapper

import agency.five.codebase.android.model.Todo
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.QuerySnapshot
import java.time.LocalDateTime

class SnapshotMapperImpl : SnapshotMapper {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun mapToTodo(snapshot: QuerySnapshot): List<Todo> {
        val data = snapshot.documents.map { documentSnapshot ->

            val dueDateMap = documentSnapshot["due_date"] as Map<String, Any>

            val localDateTime: LocalDateTime = LocalDateTime.of(
                (dueDateMap["year"] as Long).toInt(),
                (dueDateMap["monthValue"] as Long).toInt(),
                (dueDateMap["dayOfMonth"] as Long).toInt(),
                (dueDateMap["hour"] as Long).toInt(),
                (dueDateMap["minute"] as Long).toInt(),
                (dueDateMap["second"] as Long).toInt(),
            )

            Todo(
                id = documentSnapshot.id,
                category_id = documentSnapshot["category_id"] as String,
                user_id = documentSnapshot["user_id"] as String,
                title = documentSnapshot["title"] as String,
                due_date = localDateTime,
                note = documentSnapshot["note"] as String,
                is_completed = documentSnapshot["is_completed"] as Boolean,
            )
        }
        return data
    }
}
