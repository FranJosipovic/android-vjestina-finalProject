package agency.five.codebase.android.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

const val HOME_ROUTE = "Home"
const val HOME_CATEGORY_ROUTE = "Home_Category"
const val SEARCH_ROUTE = "Search"

sealed class NavigationItem(
    override val route: String,
    val icon: ImageVector,
    val unselectedTint: Color,
    val label: String,
) : TodoAppDestination(route) {
    object HomeDestination : NavigationItem(
        route = HOME_ROUTE,
        icon = Icons.Outlined.DateRange,
        unselectedTint = Color.LightGray,
        label = "Home"
    )

    object HomeCategoryDestination : NavigationItem(
        route = HOME_CATEGORY_ROUTE,
        icon = Icons.Outlined.List,
        unselectedTint = Color.LightGray,
        label = "Categories"
    )

    object SearchDestination : NavigationItem(
        route = SEARCH_ROUTE,
        icon = Icons.Outlined.Search,
        unselectedTint = Color.LightGray,
        label = "Search"
    )
}
