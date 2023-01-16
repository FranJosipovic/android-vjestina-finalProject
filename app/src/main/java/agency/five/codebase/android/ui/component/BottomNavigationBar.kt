package agency.five.codebase.android.ui.component

import agency.five.codebase.android.navigation.NavigationItem
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination

@Composable
fun BottomNavigationBar(
    destinations: List<NavigationItem>,
    onNavigateToDestination: (NavigationItem) -> Unit,
    currentDestination: NavDestination?
) {
    BottomAppBar(
        backgroundColor = MaterialTheme.colors.background,
        cutoutShape = MaterialTheme.shapes.small.copy(
            CornerSize(percent = 50)
        ),
        contentPadding = PaddingValues(end = 70.dp)
    ) {
        destinations.forEach { destination ->
            BottomNavigationItem(
                selected = destination.route == currentDestination?.route,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = null,
                        tint = if (destination.route == currentDestination?.route) MaterialTheme.colors.primary else destination.unselectedTint
                    )
                },
                label = {
                    Text(
                        text = destination.label,
                        color = if (destination.route == currentDestination?.route) MaterialTheme.colors.primary else Color.LightGray
                    )
                }
            )
        }
    }
}
