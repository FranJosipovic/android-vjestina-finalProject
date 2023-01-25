package agency.five.codebase.android.data.mapper

import agency.five.codebase.android.model.Todo
import com.google.firebase.firestore.QuerySnapshot

interface SnapshotMapper {
    fun mapToTodo(snapshot: QuerySnapshot): List<Todo>
}
