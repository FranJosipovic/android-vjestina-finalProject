package agency.five.codebase.android.ui.homeCategory.todosByCategory

import agency.five.codebase.android.model.Category
import agency.five.codebase.android.model.Todo
import agency.five.codebase.android.ui.component.TodosList
import agency.five.codebase.android.ui.home.TodoCardViewState
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodosByCategoryRoute(
    viewModel: TodosByCategoryViewModel,
    onNavigateBack: () -> Unit
) {
    val todos: List<Todo> by viewModel.todosByCategory.collectAsState()
    val category: Category by viewModel.category.collectAsState()
    TodosByCategoryScreen(
        todosByCategoryViewState = TodosByCategoryViewState(
            todos.map {
                TodoCardViewState(
                    todo = it,
                    categoryTitle = category.title
                )
            },
            categoryTitle = category.title
        ),
        onNavigateBack = onNavigateBack,
        toggleTodoCompletion = { todo -> viewModel.toggleTodoCompletion(todo) },
        deleteTodo = { todo -> viewModel.deleteTodo(todo) }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodosByCategoryScreen(
    todosByCategoryViewState: TodosByCategoryViewState,
    onNavigateBack: () -> Unit,
    toggleTodoCompletion: (Todo) -> Unit,
    deleteTodo: (Todo) -> Unit
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "",
                modifier = Modifier
                    .align(alignment = Alignment.CenterStart)
                    .clickable { onNavigateBack() },
                tint = MaterialTheme.colors.primary
            )
            Text(
                text = todosByCategoryViewState.categoryTitle,
                color = MaterialTheme.colors.primary,
                fontSize = 24.sp,
                letterSpacing = 1.sp,
                modifier = Modifier.align(alignment = Center),
            )
        }
        TodosList(
            todos = todosByCategoryViewState.todos,
            toggleTodoCompletion = toggleTodoCompletion,
            deleteTodo = deleteTodo
        )
    }
}
