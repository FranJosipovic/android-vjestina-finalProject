package agency.five.codebase.android.ui.component

import agency.five.codebase.android.model.Todo
import agency.five.codebase.android.ui.home.TodoCardViewState
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodosList(
    todos: List<TodoCardViewState>,
    toggleTodoCompletion: (Todo) -> Unit,
    deleteTodo: (Todo) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        if (todos.isEmpty()) {
            Text(
                text = "There are no todos",
                modifier = Modifier.padding(15.dp),
                color = MaterialTheme.colors.onBackground
            )
        }
        todos.forEach {
            TodoCard(
                todo = it.todo,
                toggleTodoCompletion = { toggleTodoCompletion(it.todo) },
                deleteTodo = { deleteTodo(it.todo) },
                categoryTitle = it.categoryTitle
            )
        }
    }
}
