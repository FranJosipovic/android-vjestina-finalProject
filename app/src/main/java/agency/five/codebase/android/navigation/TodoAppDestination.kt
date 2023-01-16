package agency.five.codebase.android.navigation

const val CREATE_TODO_ROUTE = "create_todo"
const val SIGN_UP_ROUTE = "signup"
const val LOG_IN_ROUTE = "login"

sealed class TodoAppDestination(open val route: String) {
    object CreateTodoDestination : TodoAppDestination(CREATE_TODO_ROUTE)
    object SignUpDestination : TodoAppDestination(SIGN_UP_ROUTE)
    object LogInDestination : TodoAppDestination(LOG_IN_ROUTE)
}
