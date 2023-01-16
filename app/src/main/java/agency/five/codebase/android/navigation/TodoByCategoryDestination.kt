package agency.five.codebase.android.navigation

const val TODO_BY_CATEGORY_ROUTE = "Category"
const val CATEGORY_ID_KEY = "categoryId"
const val TODO_BY_CATEGORY_ROUTE_WITH_PARAMS = "$TODO_BY_CATEGORY_ROUTE/{$CATEGORY_ID_KEY}"

object TodoByCategoryDestination : TodoAppDestination(TODO_BY_CATEGORY_ROUTE_WITH_PARAMS) {
    fun createNavigationRoute(categoryId: Int): String = "$TODO_BY_CATEGORY_ROUTE/$categoryId"
}
