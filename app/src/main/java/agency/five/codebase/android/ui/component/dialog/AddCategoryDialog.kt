package agency.five.codebase.android.ui.component.dialog

import androidx.compose.runtime.Composable
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.input

@Composable
fun AddCategoryDialog(
    addDialogState: MaterialDialogState,
    createNewCategory: (String) -> Unit
) {
    var newCategory: String? = null
    MaterialDialog(
        dialogState = addDialogState,
        buttons = {
            positiveButton(text = "Add") {
                newCategory?.let { createNewCategory(it) }
            }
            negativeButton(text = "Cancel")
        }
    ) {
        input(label = "Input category", singleLine = true) {
            newCategory = it
        }
    }
}
