package agency.five.codebase.android.utilities

import agency.five.codebase.android.model.Todo
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

object Utilities {

    @RequiresApi(Build.VERSION_CODES.O)
    fun isToday(date: LocalDateTime): Boolean {
        return date.toLocalDate() == LocalDate.now()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isTomorrow(date: LocalDateTime): Boolean {
        return date.toLocalDate() == LocalDate.now().plusDays(1)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getUpcomingText(date: LocalDateTime): String {
        return if (isToday(date)) "Today"
        else if (isTomorrow(date)) "Tomorrow"
        else "Soon"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTimeTillEvent(date: LocalDateTime): String {
        val minutes: Long = Duration.between(LocalDateTime.now(), date).toMinutes()
        val hours = minutes / 60
        val days = hours / 24

        return if (hours == 0.toLong() && days == 0.toLong()) "$minutes minutes"
        else if (days == 0.toLong()) "$hours hours and ${minutes - hours * 60} minutes"
        else "$days days"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDistanceToTodo(todo: Todo): Long {
        return Duration.between(LocalDateTime.now(), todo.due_date).toMillis()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun findNextUpcomingTodo(todos: List<Todo>): Todo? {

        var minDistance = 100000000000
        var minTodo: Todo? = null
        todos.forEach { todo ->
            if (getDistanceToTodo(todo) in 1 until minDistance && !todo.is_completed) {
                minDistance = getDistanceToTodo(todo)
                minTodo = todo
            }
        }
        return minTodo
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun todoIsOver(dueDate: LocalDateTime): Boolean {
        return dueDate < LocalDateTime.now()
    }
}
