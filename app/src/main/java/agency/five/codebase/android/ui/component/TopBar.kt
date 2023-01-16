package agency.five.codebase.android.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBar(onMenuClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(MaterialTheme.colors.primary)
    ) {
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .align(alignment = Alignment.CenterStart)
                .padding(start = 10.dp)
                .clickable { onMenuClick() },
            tint = MaterialTheme.colors.onSecondary
        )
        Text(
            text = "TodoDo",
            fontSize = 24.sp,
            letterSpacing = 1.sp,
            modifier = Modifier
                .align(alignment = Alignment.Center),
            color = MaterialTheme.colors.onSecondary
        )
    }
}
