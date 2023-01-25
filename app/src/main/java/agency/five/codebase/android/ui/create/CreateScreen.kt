package agency.five.codebase.android.ui.create

import agency.five.codebase.android.model.Category
import agency.five.codebase.android.ui.component.ErrorLabel
import agency.five.codebase.android.ui.component.createScreen.DateSelectorDialog
import agency.five.codebase.android.ui.component.createScreen.GroupSelector
import agency.five.codebase.android.ui.component.createScreen.TimeSelectorDialog
import agency.five.codebase.android.ui.component.dialog.AddCategoryDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateScreenRoute(viewModel: CreateViewModel, navigateToHomeScreen: () -> Unit) {

    val createState = viewModel.createScreenState

    val categories: List<Category> by viewModel.categories.collectAsState()

    CreateScreen(
        categories = categories,
        createState = createState,
        updateSelectedCategory = { category -> viewModel.updateSelectedCategory(category) },
        updateSelectedDate = { date -> viewModel.updateSelectedDate(date) },
        updateSelectedTime = { time -> viewModel.updateSelectedTime(time) },
        onTodoTitleChange = { title -> viewModel.onTodoTitleChange(title) },
        onTodoNoteChange = { note -> viewModel.onTodoNoteChange(note) },
        addTodo = { viewModel.addTodo() },
        addCategory = { categoryTitle -> viewModel.addCategory(categoryTitle) },
        navigateToHomeScreen = navigateToHomeScreen
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateScreen(
    categories: List<Category>,
    createState: CreateScreenViewState,
    updateSelectedCategory: (Category) -> Unit,
    updateSelectedDate: (LocalDate) -> Unit,
    updateSelectedTime: (LocalTime) -> Unit,
    onTodoNoteChange: (String) -> Unit,
    onTodoTitleChange: (String) -> Unit,
    addTodo: () -> Unit,
    addCategory: (String) -> Unit,
    navigateToHomeScreen: () -> Unit
) {

    val addDialogState = rememberMaterialDialogState()

    AddCategoryDialog(addDialogState = addDialogState, createNewCategory = addCategory)
    ConstraintLayout {

        val (actionBottomRow, screen) = createRefs()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.surface)
                .padding(horizontal = 20.dp)
                .constrainAs(screen) {}
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                value = createState.todoTitle,
                onValueChange = { onTodoTitleChange(it) },
                placeholder = { Text(text = "Task name") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = MaterialTheme.colors.onSurface,
                    backgroundColor = Color.Transparent,
                    focusedBorderColor = MaterialTheme.colors.onSurface,
                    placeholderColor = MaterialTheme.colors.onSurface,
                    cursorColor = MaterialTheme.colors.onSurface,
                ),
                singleLine = true,
                isError = createState.todoTitleError != null
            )
            ErrorLabel(errorMessage = createState.todoTitleError)
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                value = createState.todoNote,
                onValueChange = { onTodoNoteChange(it) },
                placeholder = { Text(text = "Note") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = MaterialTheme.colors.onSurface,
                    backgroundColor = Color.Transparent,
                    focusedBorderColor = MaterialTheme.colors.onSurface,
                    placeholderColor = MaterialTheme.colors.onSurface,
                    cursorColor = MaterialTheme.colors.onSurface
                ),
                isError = createState.todoNoteError != null
            )
            ErrorLabel(errorMessage = createState.todoNoteError)
            Spacer(modifier = Modifier.height(60.dp))
            GroupSelector(
                categories = categories,
                selectedCategory = createState.category,
                updateSelectedCategory = updateSelectedCategory,
                addDialogState = addDialogState
            )
            ErrorLabel(errorMessage = createState.categoryError)
            Spacer(modifier = Modifier.height(50.dp))
            DateSelectorDialog(
                selectedDate = createState.date,
                updateSelectedDate = updateSelectedDate
            )
            ErrorLabel(errorMessage = createState.dateError)
            Spacer(modifier = Modifier.height(50.dp))
            TimeSelectorDialog(
                selectedTime = createState.time,
                updateSelectedTime = updateSelectedTime
            )
            ErrorLabel(errorMessage = createState.timeError)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .constrainAs(actionBottomRow) {
                    bottom.linkTo(screen.bottom, margin = 20.dp)
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { navigateToHomeScreen() },
                modifier = Modifier
                    .background(
                        color = Color.Black.copy(0.3F),
                        shape = CircleShape
                    )
            ) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = "", tint = Color.White)
            }
            Button(onClick = {
                addTodo()
            }) {
                Text(text = "Add Task")
            }
        }
    }
    LaunchedEffect(key1 = createState.createFinished) {
        if (createState.isSuccess) {
            navigateToHomeScreen()
        }
    }
}
