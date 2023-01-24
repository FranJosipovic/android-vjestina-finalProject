package agency.five.codebase.android.data.di

import agency.five.codebase.android.data.repository.category.CategoryRepository
import agency.five.codebase.android.data.repository.category.CategoryRepositoryImpl
import agency.five.codebase.android.data.database.category.CategoryDao
import agency.five.codebase.android.data.database.category.CategoryDaoImpl
import agency.five.codebase.android.data.database.todo.TodoDao
import agency.five.codebase.android.data.database.todo.TodoDaoImpl
import agency.five.codebase.android.data.mapper.SnapshotMapper
import agency.five.codebase.android.data.mapper.SnapshotMapperImpl
import agency.five.codebase.android.data.repository.todo.TodoRepository
import agency.five.codebase.android.data.repository.todo.TodoRepositoryImpl
import agency.five.codebase.android.data.repository.user.UserRepository
import agency.five.codebase.android.data.repository.user.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dataModule = module {

    single<TodoRepository> {
        TodoRepositoryImpl(
            userRepository = get(),
            todoDao = get(),
            bgDispatcher = Dispatchers.IO
        )
    }

    single<CategoryRepository> {
        CategoryRepositoryImpl(
            userRepository = get(),
            categoryDao = get(),
            bgDispatcher = Dispatchers.IO
        )
    }

    single<UserRepository> {
        UserRepositoryImpl(
            dispatcher = Dispatchers.IO,
        )
    }

    single<TodoDao> {
        TodoDaoImpl(snapshotMapper = get())
    }

    single<CategoryDao> {
        CategoryDaoImpl()
    }

    single<SnapshotMapper> { SnapshotMapperImpl() }
}
