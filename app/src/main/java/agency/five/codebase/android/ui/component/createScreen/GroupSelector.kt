package agency.five.codebase.android.ui.component.createScreen

import agency.five.codebase.android.model.Category
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialogState

@Composable
fun GroupSelector(
    categories: List<Category>,
    addDialogState: MaterialDialogState,
    selectedCategory: Category?,
    updateSelectedCategory: (Category) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(
                imageVector = Icons.Outlined.List,
                contentDescription = "",
                tint = MaterialTheme.colors.onSurface
            )
            Text(
                text = "Group",
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "",
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier.clickable { addDialogState.show() }
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.secondaryVariant)
            .height(50.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        categories.forEach { category ->
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(125.dp)
                    .background(color = if (category.id == selectedCategory?.id) MaterialTheme.colors.primary else Color.Transparent)
                    .clickable { updateSelectedCategory(category) }
            ) {
                Text(
                    text = category.title,
                    modifier = Modifier.align(Alignment.Center),
                    color = if (category.id == selectedCategory?.id) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface
                )
            }
        }
    }
}
