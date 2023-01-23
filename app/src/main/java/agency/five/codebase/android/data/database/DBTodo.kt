package agency.five.codebase.android.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "todos",
)
data class DBTodo(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "category_id") val categoryId: String,
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "due_date") val dueDate: LocalDateTime,
    @ColumnInfo(name = "note") val note: String,
    @ColumnInfo(name = "is_completed") val isCompleted: Boolean
)
