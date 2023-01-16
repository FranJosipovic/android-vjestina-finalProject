package agency.five.codebase.android.ui.search

import agency.five.codebase.android.model.Todo
import agency.five.codebase.android.ui.component.TodoCard
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchScreenRoute(
    viewModel: SearchViewModel,
) {

    val todos: SearchScreenViewState by viewModel.todos.collectAsState()

    SearchScreen(
        todos = todos,
        updateRegexInput = { input: String -> viewModel.updateRegexInput(input) },
        toggleTodoCompletion = { todo -> viewModel.toggleTodoCompletion(todo) },
        deleteTodo = { todo -> viewModel.deleteTodo(todo) }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchScreen(
    todos: SearchScreenViewState,
    updateRegexInput: (String) -> Unit,
    toggleTodoCompletion: (Todo) -> Unit,
    deleteTodo: (Todo) -> Unit
) {

    var searchInput by remember {
        mutableStateOf(TextFieldValue(""))
    }

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp),
            value = searchInput,
            onValueChange = {
                searchInput = it
                updateRegexInput(searchInput.text)
            },
            singleLine = true,
            trailingIcon = {
                (if (searchInput != TextFieldValue("")) Icons.Filled.Clear else null)?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.clickable { searchInput = TextFieldValue("") }
                    )
                }
            },
            placeholder = { Text(text = "Search for a TODO ...") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.3F),
                focusedBorderColor = Color.Transparent,
                placeholderColor = MaterialTheme.colors.onPrimary,
                cursorColor = MaterialTheme.colors.onPrimary
            )
        )
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            if (searchInput.text == "") {
                Text(
                    text = "There are no Todos by that search criteria",
                    modifier = Modifier.padding(15.dp)
                )
            } else {
                if (todos.todos.isEmpty()) {
                    Text(
                        text = "There are no Todos by that search criteria",
                        modifier = Modifier.padding(15.dp)
                    )
                }
                todos.todos.forEach { todo ->
                    TodoCard(
                        todo = todo.todo,
                        toggleTodoCompletion = { toggleTodoCompletion(todo.todo) },
                        deleteTodo = { deleteTodo(todo.todo) },
                        categoryTitle = todo.categoryTitle
                    )
                }
            }
        }
    }
}
