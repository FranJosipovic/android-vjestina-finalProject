package agency.five.codebase.android.model

import java.time.LocalDateTime

data class Todo(
    val id: String,
    val category_id: String,
    val user_id:String,
    val title: String,
    val due_date: LocalDateTime,
    val note: String,
    var is_completed: Boolean
)
