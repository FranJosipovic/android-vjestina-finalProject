package agency.five.codebase.android.ui.home

import agency.five.codebase.android.model.DateCategory
import agency.five.codebase.android.model.Todo
import agency.five.codebase.android.ui.component.DateCategoryTab
import agency.five.codebase.android.ui.component.NextUpcomingTodo
import agency.five.codebase.android.ui.component.TodosList
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreenRoute(
    viewModel: HomeViewModel
) {

    val homeScreenViewState: HomeScreenViewState by viewModel.data.collectAsState()
    val nextUpcomingTodo: Todo? by viewModel.nextUpcomingTodo.collectAsState()

    HomeScreen(
        nextUpcomingTodo = nextUpcomingTodo,
        homeScreenViewState = homeScreenViewState,
        onDateCategoryClick = { category: DateCategory -> viewModel.changeDateCategory(category) },
        toggleTodoCompletion = { todo -> viewModel.toggleTodoCompletion(todo) },
        deleteTodo = { todo -> viewModel.deleteTodo(todo) },
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    nextUpcomingTodo: Todo?,
    homeScreenViewState: HomeScreenViewState,
    onDateCategoryClick: (DateCategory) -> Unit,
    toggleTodoCompletion: (Todo) -> Unit,
    deleteTodo: (Todo) -> Unit
) {
    Column {
        NextUpcomingTodo(todo = nextUpcomingTodo)
        Row(modifier = Modifier.fillMaxWidth()) {
            homeScreenViewState.dateCategories.forEach {
                DateCategoryTab(
                    modifier = Modifier.weight(1F),
                    dateCategoryTabViewState = it,
                    onDateCategoryClick = onDateCategoryClick
                )
            }
        }
        Divider()
        TodosList(
            todos = homeScreenViewState.todos,
            toggleTodoCompletion = toggleTodoCompletion,
            deleteTodo = deleteTodo
        )
    }
}
