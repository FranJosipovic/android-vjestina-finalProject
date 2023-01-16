package agency.five.codebase.android.mock

import agency.five.codebase.android.model.Category
import agency.five.codebase.android.model.Todo
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

object TodosMock {
    private fun getCategories(): List<Category> = listOf(
        Category(id = 1, title = "School"),
        Category(id = 2, title = "Work"),
        Category(id = 3, title = "Friends")
    )

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTodos(): List<Todo> = listOf(
        Todo(
            id = 1,
            categoryId = 1,
            title = "Homework",
            dueDate = LocalDateTime.of(2023, 1, 17, 5, 5),
            note = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type",
            isCompleted = false
        ),
        Todo(
            id = 2,
            categoryId = 1,
            title = "Linear algebra II",
            dueDate = LocalDateTime.of(2023, 2, 1, 5, 5),
            note = "specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing",
            isCompleted = true
        ),
        Todo(
            id = 3,
            categoryId = 3,
            title = "Cinema",
            dueDate = LocalDateTime.of(2022, 12, 27, 21, 49),
            note = "orem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            isCompleted = false
        ),
        Todo(
            id = 4,
            categoryId = 2,
            title = "Get paid",
            dueDate = LocalDateTime.of(2023, 3, 17, 5, 5),
            note = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using",
            isCompleted = false
        ),
        Todo(
            id = 5,
            categoryId = 2,
            title = "hello there",
            dueDate = LocalDateTime.of(2022, 12, 30, 23, 5),
            note = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using",
            isCompleted = false
        ),
        Todo(
            id = 6,
            categoryId = 2,
            title = "what are you doing",
            dueDate = LocalDateTime.of(2022, 12, 30, 23, 5),
            note = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using",
            isCompleted = false
        ),
        Todo(
            id = 7,
            categoryId = 2,
            title = "this is today",
            dueDate = LocalDateTime.of(2022, 12, 30, 23, 5),
            note = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using",
            isCompleted = false
        ),
        Todo(
            id = 8,
            categoryId = 2,
            title = "this is tomorrow",
            dueDate = LocalDateTime.of(2022, 12, 31, 23, 5),
            note = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using",
            isCompleted = false
        ),
        Todo(
            id = 9,
            categoryId = 2,
            title = "this is somewhere in 2023",
            dueDate = LocalDateTime.of(2023, 3, 31, 5, 5),
            note = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using",
            isCompleted = false
        ),
    )
}
