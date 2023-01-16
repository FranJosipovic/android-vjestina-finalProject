package agency.five.codebase.android.ui.component.homeCategoryScreen

import agency.five.codebase.android.model.Category
import agency.five.codebase.android.ui.component.dialog.RemoveCategoryDialog
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryCard(
    category: Category,
    onNavigateToTodosByCategory: (Int) -> Unit,
    deleteCategory: (Category) -> Unit
) {

    val removeDialogState = rememberMaterialDialogState()

    Box(
        modifier = Modifier
            .size(150.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface,
                shape = MaterialTheme.shapes.small
            )
            .background(MaterialTheme.colors.background)
            .combinedClickable(
                onClick = { onNavigateToTodosByCategory(category.id) },
                onLongClick = {
                    removeDialogState.show()
                }
            ),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center), text = category.title,
        )

    }

    RemoveCategoryDialog(
        dialogState = removeDialogState,
        deleteCategory = { deleteCategory(category) },
        categoryTitle = category.title
    )
}
