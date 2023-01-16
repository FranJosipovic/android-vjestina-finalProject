package agency.five.codebase.android.data.di

import agency.five.codebase.android.data.database.TodoDatabase
import agency.five.codebase.android.data.todo.TodoRepository
import agency.five.codebase.android.data.todo.TodoRepositoryImpl
import agency.five.codebase.android.data.user.UserRepository
import agency.five.codebase.android.data.user.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dataModule = module {
    single<TodoRepository> {
        TodoRepositoryImpl(
            todoDao = get<TodoDatabase>().todoDao(),
            categoryDao = get<TodoDatabase>().categoryDao(),
            bgDispatcher = Dispatchers.IO,
            userRepository = get()
        )
    }
    single<UserRepository> {
        UserRepositoryImpl(
            dispatcher = Dispatchers.IO,
        )
    }
}
