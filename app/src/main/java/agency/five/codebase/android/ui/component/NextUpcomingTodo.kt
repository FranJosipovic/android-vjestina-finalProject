package agency.five.codebase.android.ui.component

import agency.five.codebase.android.model.Todo
import agency.five.codebase.android.utilities.Utilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NextUpcomingTodo(todo: Todo?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(MaterialTheme.colors.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        ) {
            if (todo != null) {
                Text(
                    text = Utilities.getUpcomingText(todo.dueDate),
                    color = MaterialTheme.colors.onSecondary,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = todo.title, color = MaterialTheme.colors.onSecondary)
                    Text(
                        text = "Due in " + Utilities.getTimeTillEvent(todo.dueDate),
                        color = MaterialTheme.colors.onSecondary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.widthIn(Dp.Unspecified, 150.dp)
                    )
                }
            } else {
                Text(
                    text = "You don't have any upcoming todos",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSecondary,
                )
            }
        }
    }
}

