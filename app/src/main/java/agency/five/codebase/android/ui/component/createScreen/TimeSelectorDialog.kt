package agency.five.codebase.android.ui.component.createScreen

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.ui.create.timeList
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeSelectorDialog(
    selectedTime: LocalTime?,
    updateSelectedTime: (LocalTime) -> Unit
) {
    val timeDialogState = rememberMaterialDialogState()
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_access_time_24),
                contentDescription = "",
                tint = MaterialTheme.colors.onSurface
            )
            Text(
                text = "Time",
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
        Text(text = selectedTime?.toString() ?: "", color = MaterialTheme.colors.onSurface)
    }

    Spacer(modifier = Modifier.height(10.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.secondaryVariant)
            .height(50.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        timeList.forEach { time ->
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(125.dp)
                    .background(color = if (time == selectedTime) MaterialTheme.colors.primary else Color.Transparent)
                    .clickable { updateSelectedTime(time) }
            ) {
                Text(
                    text = time.toString(),
                    modifier = Modifier.align(Alignment.Center),
                    color = if (time == selectedTime) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(125.dp)
                .background(color = if (!timeList.contains(selectedTime) && selectedTime != null) MaterialTheme.colors.primary else Color.Transparent)
                .clickable { timeDialogState.show() }
        ) {
            Text(
                text = "Custom",
                modifier = Modifier.align(Alignment.Center),
                color = if (!timeList.contains(selectedTime) && selectedTime != null) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface
            )
        }
    }
    TimeDialog(timeDialogState, updateSelectedTime)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun TimeDialog(
    timeDialogState: MaterialDialogState,
    updateSelectedTime: (LocalTime) -> Unit
) {
    var pickedTime: LocalTime? by remember {
        mutableStateOf(null)
    }
    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(text = "Ok") {
                pickedTime?.let { updateSelectedTime(it) }
            }
            negativeButton(text = "Cancel")
        }
    ) {
        timepicker(
            initialTime = LocalTime.NOON,
            title = "Pick a time",
            timeRange = LocalTime.MIN..LocalTime.MAX
        ) {
            pickedTime = it
        }
    }
}
