package agency.five.codebase.android.ui.component

import agency.five.codebase.android.model.DateCategory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class DateCategoryTabViewState(
    val id: Int,
    val isSelected: Boolean,
    val dateCategoryText: String
)

@Composable
fun DateCategoryTab(
    dateCategoryTabViewState: DateCategoryTabViewState,
    modifier: Modifier,
    onDateCategoryClick: (DateCategory) -> Unit
) {
    Box(modifier = modifier
        .height(60.dp)
        .background(color = MaterialTheme.colors.background)
        .clickable { onDateCategoryClick(DateCategory.values()[dateCategoryTabViewState.id]) }
    ) {
        Text(
            text = dateCategoryTabViewState.dateCategoryText,
            color = if (dateCategoryTabViewState.isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground,
            modifier = Modifier.align(
                Alignment.Center
            )
        )
    }
}
