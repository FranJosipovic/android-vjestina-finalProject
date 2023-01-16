package agency.five.codebase.android.ui.component.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.customView

@Composable
fun RemoveCategoryDialog(
    dialogState: MaterialDialogState,
    deleteCategory: () -> Unit,
    categoryTitle: String
) {
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("Delete") {
                deleteCategory()
            }
            negativeButton("Cancel")
        }
    ) {
        this.customView {
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(15.dp)
            ) {
                Text(text = "Are you sure you want to delete $categoryTitle category?\n\nAll todos with that category will be deleted!")
            }
        }
    }
}
