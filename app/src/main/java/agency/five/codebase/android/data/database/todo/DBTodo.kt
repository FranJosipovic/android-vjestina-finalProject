package agency.five.codebase.android.data.database.todo

import java.sql.Timestamp

class DBTodo {
    var id: String = ""
    var categoryId: String = ""
    var userId: String = ""
    var title: String = ""
    var dueDate: Timestamp = Timestamp(0)
    var note: String = ""
    var isCompleted: Boolean = false
}

