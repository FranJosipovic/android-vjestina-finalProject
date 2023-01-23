package agency.five.codebase.android.ui.component

import agency.five.codebase.android.model.Todo
import agency.five.codebase.android.ui.theme.todoCategoryTitle
import agency.five.codebase.android.ui.theme.todoNote
import agency.five.codebase.android.ui.theme.todoTitle
import agency.five.codebase.android.utilities.Utilities
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoCard(
    todo: Todo,
    toggleTodoCompletion: () -> Unit,
    deleteTodo: () -> Unit,
    categoryTitle: String
) {

    var expandTodoCard by remember {
        mutableStateOf(false)
    }

    fun shrinkTodoCard() {
        expandTodoCard = !expandTodoCard
    }

    val formatter = DateTimeFormatter.ofPattern("dd.MMM HH:mm")
    val dateOutput = formatter.format(todo.due_date)

    val date = dateOutput.take(6)
    val time = dateOutput.takeLast(5)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { expandTodoCard = !expandTodoCard },
        elevation = 10.dp
    ) {

        Column(
            modifier = Modifier
                .padding(20.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TodoTitle(title = todo.title, dueDate = todo.due_date)
                TodoCompletionIndicator(todoIsCompleted = todo.is_completed)
            }
            TodoCategoryTitle(categoryTitle = categoryTitle)
            Spacer(modifier = Modifier.height(20.dp))
            TodoDueDate(date = date, time = time)
            TodoNote(condition = expandTodoCard, note = todo.note)
            if (expandTodoCard) {
                TodoActionOptions(
                    toggleTodoCompletion = toggleTodoCompletion,
                    deleteTodo = deleteTodo,
                    shrinkTodoCard = { shrinkTodoCard() }
                )
            }
        }
    }
}

@Composable
private fun TodoActionOptions(
    toggleTodoCompletion: () -> Unit,
    deleteTodo: () -> Unit,
    shrinkTodoCard: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {

        Text(
            text = "Toggle completion",
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .weight(1F)
                .clickable {
                    toggleTodoCompletion()
                },
            textAlign = TextAlign.Center
        )
        Text(
            text = "Delete",
            color = Color.Red,
            modifier = Modifier
                .weight(1F)
                .clickable {
                    deleteTodo()
                    shrinkTodoCard()
                },
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun TodoNote(condition: Boolean, note: String) {
    Text(
        text = if (!condition) "${note.take(40)}..." else note,
        style = todoNote,
        modifier = Modifier.widthIn(min = Dp.Unspecified, max = 250.dp),
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
private fun TodoDueDate(date: String, time: String) {
    Row {
        Icon(
            Icons.Outlined.DateRange,
            contentDescription = "",
            tint = Color.LightGray
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = date, color = Color.LightGray)
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = time, color = Color.LightGray)
    }
}

@Composable
private fun TodoCategoryTitle(categoryTitle: String) {
    Text(
        text = categoryTitle,
        style = todoCategoryTitle,
        modifier = Modifier.padding(start = 0.dp)
    )
}

@Composable
private fun TodoCompletionIndicator(todoIsCompleted: Boolean) {
    Box(
        modifier = Modifier
            .padding(end = 20.dp)
            .background(
                color = if (todoIsCompleted) MaterialTheme.colors.secondary else Color.Transparent,
                shape = CircleShape
            )
            .border(width = 1.dp, color = Color.Gray, shape = CircleShape)
            .width(25.dp)
            .height(25.dp)
    ) {
        if (todoIsCompleted) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "",
                modifier = Modifier
                    .align(
                        Alignment.Center
                    )
                    .padding(5.dp),
                tint = Color.White
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun TodoTitle(title: String, dueDate: LocalDateTime) {
    Row {
        Text(
            text = if (Utilities.todoIsOver(dueDate)) "! " else "",
            style = todoTitle,
            color = Color.Red
        )
        Text(
            text = title.uppercase(),
            style = todoTitle,
        )
    }
}
