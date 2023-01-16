package agency.five.codebase.android.ui.main

import agency.five.codebase.android.navigation.CATEGORY_ID_KEY
import agency.five.codebase.android.navigation.NavigationItem
import agency.five.codebase.android.navigation.TodoAppDestination
import agency.five.codebase.android.navigation.TodoByCategoryDestination
import agency.five.codebase.android.ui.component.BottomNavigationBar
import agency.five.codebase.android.ui.component.TopBar
import agency.five.codebase.android.ui.create.CreateScreenRoute
import agency.five.codebase.android.ui.drawer.Drawer
import agency.five.codebase.android.ui.home.HomeScreenRoute
import agency.five.codebase.android.ui.homeCategory.HomeCategoryScreenRoute
import agency.five.codebase.android.ui.homeCategory.todosByCategory.TodosByCategoryRoute
import agency.five.codebase.android.ui.login.LoginScreenRoute
import agency.five.codebase.android.ui.search.SearchScreenRoute
import agency.five.codebase.android.ui.signup.SignupScreenRoute
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(hasUser: Boolean) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val showTopAndBottomBar by remember {
        derivedStateOf {
            when (navBackStackEntry?.destination?.route) {
                TodoAppDestination.CreateTodoDestination.route,
                TodoAppDestination.LogInDestination.route,
                TodoAppDestination.SignUpDestination.route -> false
                else -> true
            }
        }
    }
    val enableDrawer by remember {
        derivedStateOf {
            when (navBackStackEntry?.destination?.route) {
                TodoAppDestination.CreateTodoDestination.route,
                TodoAppDestination.LogInDestination.route,
                TodoAppDestination.SignUpDestination.route -> false
                else -> true
            }
        }
    }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(

        scaffoldState = scaffoldState,
        drawerGesturesEnabled = enableDrawer,
        drawerContent = {
            Drawer(
                viewModel = getViewModel()
            ) {
                navController.navigate(TodoAppDestination.SignUpDestination.route) {
                    launchSingleTop = true
                }
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }
        },
        topBar = {
            if (showTopAndBottomBar)
                TopBar(onMenuClick = {
                    scope.launch {
                        scaffoldState.drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                })
        },
        bottomBar = {
            if (showTopAndBottomBar)
                BottomNavigationBar(
                    destinations = listOf(
                        NavigationItem.HomeDestination,
                        NavigationItem.HomeCategoryDestination,
                        NavigationItem.SearchDestination
                    ),
                    currentDestination = navBackStackEntry?.destination,
                    onNavigateToDestination = {
                        customNavigator(
                            it.route,
                            navController = navController
                        )
                    }
                )
        },
        floatingActionButton = {
            if (showTopAndBottomBar)
                FloatingActionButton(
                    onClick = { navController.navigate(TodoAppDestination.CreateTodoDestination.route) },
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
        },
        isFloatingActionButtonDocked = true,
    ) { padding ->
        Surface(
            color = MaterialTheme.colors.surface,
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = if (hasUser) NavigationItem.HomeDestination.route else TodoAppDestination.SignUpDestination.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(route = TodoAppDestination.SignUpDestination.route) {
                    SignupScreenRoute(
                        viewModel = getViewModel(),
                        onNavToHomePage = {
                            navController.navigate(NavigationItem.HomeDestination.route) {
                                launchSingleTop = true
                                popUpTo(route = TodoAppDestination.SignUpDestination.route) {
                                    inclusive = true
                                }
                            }
                        },
                        onNavToLoginPage = {
                            navController.navigate(TodoAppDestination.LogInDestination.route) {
                                launchSingleTop = true
                                popUpTo(TodoAppDestination.SignUpDestination.route) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
                composable(route = TodoAppDestination.LogInDestination.route) {
                    LoginScreenRoute(
                        loginViewModel = getViewModel(),
                        onNavToHomePage = {
                            navController.navigate(NavigationItem.HomeDestination.route) {
                                launchSingleTop = true
                                popUpTo(TodoAppDestination.LogInDestination.route) {
                                    inclusive = true
                                }
                            }
                        },
                        onNavToSignUpPage = {
                            navController.navigate(TodoAppDestination.SignUpDestination.route) {
                                launchSingleTop = true
                                popUpTo(TodoAppDestination.LogInDestination.route) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
                composable(route = NavigationItem.HomeDestination.route) {
                    HomeScreenRoute(viewModel = getViewModel())
                }
                composable(route = TodoAppDestination.CreateTodoDestination.route) {
                    CreateScreenRoute(
                        viewModel = getViewModel(),
                        navigateToHomeScreen = navController::popBackStack
                    )
                }
                composable(route = NavigationItem.HomeCategoryDestination.route) {
                    HomeCategoryScreenRoute(
                        viewModel = getViewModel(),
                        onNavigateToTodosByCategoryScreen = { id: Int ->
                            navController.navigate(
                                TodoByCategoryDestination.createNavigationRoute(id)
                            )
                        }
                    )
                }
                composable(route = NavigationItem.SearchDestination.route) {
                    SearchScreenRoute(
                        viewModel = getViewModel(),
                    )
                }
                composable(
                    route = TodoByCategoryDestination.route,
                    arguments = listOf(navArgument(CATEGORY_ID_KEY) { type = NavType.IntType }),
                ) {
                    val categoryId = it.arguments?.getInt(CATEGORY_ID_KEY)!!
                    TodosByCategoryRoute(
                        viewModel = getViewModel(parameters = { parametersOf(categoryId) }),
                        onNavigateBack = navController::popBackStack
                    )
                }
            }
        }
    }
}

fun customNavigator(route: String, navController: NavController) {
    navController.navigate(route = route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
