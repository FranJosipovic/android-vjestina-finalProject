package agency.five.codebase.android.model

import java.time.LocalDateTime

data class Todo(
    val id: Int,
    val categoryId: Int,
    val title: String,
    val dueDate: LocalDateTime,
    val note: String,
    var isCompleted: Boolean
)
