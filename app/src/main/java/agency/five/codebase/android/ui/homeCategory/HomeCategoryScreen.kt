package agency.five.codebase.android.ui.homeCategory

import agency.five.codebase.android.model.Category
import agency.five.codebase.android.ui.component.dialog.AddCategoryDialog
import agency.five.codebase.android.ui.component.homeCategoryScreen.CategoryCard
import agency.five.codebase.android.ui.theme.Shapes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@Composable
fun HomeCategoryScreenRoute(
    viewModel: HomeCategoryViewModel,
    onNavigateToTodosByCategoryScreen: (Int) -> Unit
) {

    val categories: List<Category> by viewModel.categories.collectAsState()

    HomeCategoryScreen(
        categories = categories,
        onNavigateToTodosByCategoryScreen = onNavigateToTodosByCategoryScreen,
        createNewCategory = { categoryTitle -> viewModel.addCategory(categoryTitle = categoryTitle) },
        deleteCategory = { category -> viewModel.deleteCategory(category) }
    )
}

@Composable
fun HomeCategoryScreen(
    categories: List<Category>,
    onNavigateToTodosByCategoryScreen: (Int) -> Unit,
    createNewCategory: (String) -> Unit,
    deleteCategory: (Category) -> Unit
) {

    val addDialogState = rememberMaterialDialogState()

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface)
            .padding(bottom = 10.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                items = categories,
                itemContent = { item ->
                    CategoryCard(
                        category = item,
                        onNavigateToTodosByCategory = onNavigateToTodosByCategoryScreen,
                        deleteCategory = deleteCategory
                    )
                }
            )
            item {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .border(1.dp, Color.Gray, Shapes.small)
                        .background(MaterialTheme.colors.background)
                        .clickable { addDialogState.show() }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AddCircle,
                        contentDescription = "",
                        modifier = Modifier
                            .align(
                                Alignment.Center
                            )
                            .size(50.dp)
                    )
                }
            }
        }
    }
    AddCategoryDialog(addDialogState = addDialogState, createNewCategory = createNewCategory)
}
