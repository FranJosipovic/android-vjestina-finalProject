package agency.five.codebase.android

import agency.five.codebase.android.data.di.dataModule
import agency.five.codebase.android.ui.create.di.createModule
import agency.five.codebase.android.ui.drawer.di.drawerModule
import agency.five.codebase.android.ui.home.di.homeModule
import agency.five.codebase.android.ui.homeCategory.di.homeCategoryModule
import agency.five.codebase.android.ui.homeCategory.todosByCategory.di.todosByCategoryModule
import agency.five.codebase.android.ui.login.di.loginModule
import agency.five.codebase.android.ui.main.di.mainScreenModule
import agency.five.codebase.android.ui.search.di.searchModule
import agency.five.codebase.android.ui.signup.di.signupModule
import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TodoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TodoApp)
            modules(
                homeModule,
                dataModule,
                createModule,
                todosByCategoryModule,
                homeCategoryModule,
                searchModule,
                loginModule,
                signupModule,
                drawerModule,
                mainScreenModule
            )
        }
        Log.d("App", "app started")
    }
}
