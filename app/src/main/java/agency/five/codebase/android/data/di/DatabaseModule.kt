package agency.five.codebase.android.data.di

import agency.five.codebase.android.data.database.TodoDatabase
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private const val APP_DATABASE_NAME = "todo_database.db"
val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            TodoDatabase::class.java,
            APP_DATABASE_NAME,
        ).build()
    }
}
