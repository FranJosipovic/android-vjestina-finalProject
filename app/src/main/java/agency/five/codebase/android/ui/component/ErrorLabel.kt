package agency.five.codebase.android.ui.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorLabel(errorMessage: String?) {
    if (errorMessage != null) {
        Text(text = errorMessage, color = MaterialTheme.colors.error)
    }
}
