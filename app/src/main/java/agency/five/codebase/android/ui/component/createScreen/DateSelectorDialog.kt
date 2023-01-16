package agency.five.codebase.android.ui.component.createScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateSelectorDialog(
    selectedDate: LocalDate?,
    updateSelectedDate: (LocalDate) -> Unit
) {

    val dateDialogState = rememberMaterialDialogState()

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Row {
            Icon(
                imageVector = Icons.Outlined.DateRange,
                contentDescription = "",
                tint = MaterialTheme.colors.onSurface
            )
            Text(
                text = "Date",
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
        Text(text = selectedDate?.toString() ?: "", color = MaterialTheme.colors.onSurface)
    }
    Spacer(modifier = Modifier.height(10.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.secondaryVariant)
            .height(50.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1F)
                .background(
                    color = if (selectedDate == LocalDate.now()) MaterialTheme.colors.primary else Color.Transparent
                )
                .clickable { updateSelectedDate(LocalDate.now()) }
        ) {
            Text(
                text = "Today",
                modifier = Modifier.align(Alignment.Center),
                color = if (selectedDate == LocalDate.now()) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1F)
                .background(
                    color = if (selectedDate == LocalDate
                            .now()
                            .plusDays(1)
                    ) MaterialTheme.colors.primary else Color.Transparent
                )
                .clickable {
                    updateSelectedDate(
                        LocalDate
                            .now()
                            .plusDays(1)
                    )
                }
        ) {
            Text(
                text = "Tomorrow",
                modifier = Modifier.align(Alignment.Center),
                color = if (selectedDate == LocalDate
                        .now()
                        .plusDays(1)
                ) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1F)
                .background(
                    color = if (selectedDate != LocalDate
                            .now()
                            .plusDays(1) && selectedDate != LocalDate.now() && selectedDate != null
                    ) MaterialTheme.colors.primary else Color.Transparent
                )
                .clickable { dateDialogState.show() }
        ) {
            Text(
                text = "Custom",
                modifier = Modifier.align(Alignment.Center),
                color = if (selectedDate != LocalDate
                        .now()
                        .plusDays(1) && selectedDate != LocalDate.now() && selectedDate != null
                ) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface
            )
        }
    }

    CalendarDialog(dateDialogState, updateSelectedDate)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun CalendarDialog(
    dateDialogState: MaterialDialogState,
    updateSelectedDate: (LocalDate) -> Unit
) {
    var pickedDate: LocalDate? by remember {
        mutableStateOf(null)
    }
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok") {
                pickedDate?.let { updateSelectedDate(it) }
            }
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a date",
        ) {
            pickedDate = it
        }
    }
}
