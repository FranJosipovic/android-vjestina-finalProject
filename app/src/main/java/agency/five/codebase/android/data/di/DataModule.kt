package agency.five.codebase.android.data.di

import agency.five.codebase.android.data.database.CategoryFirestoreDao
import agency.five.codebase.android.data.database.TodoFirestoreDao
import agency.five.codebase.android.data.todo.TodoFirestoreRepository
import agency.five.codebase.android.data.todo.TodoFirestoreRepositoryImpl
import agency.five.codebase.android.data.user.UserRepository
import agency.five.codebase.android.data.user.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dataModule = module {
    single<TodoFirestoreRepository> {
        TodoFirestoreRepositoryImpl(
            userRepository = get(),
            todoFirestoreDao = get(),
            categoryFirestoreDao = get(),
            bgDispatcher = Dispatchers.IO
        )
    }
    single<UserRepository> {
        UserRepositoryImpl(
            dispatcher = Dispatchers.IO,
        )
    }
    single<TodoFirestoreDao> {
        TodoFirestoreDao()
    }
    single<CategoryFirestoreDao> {
        CategoryFirestoreDao()
    }
}
