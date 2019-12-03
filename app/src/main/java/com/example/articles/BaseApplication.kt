package com.example.articles

import android.app.Activity
import android.app.Application
import android.content.Context
import com.example.articles.dagger.ApplicationComponent
import com.example.articles.dagger.DaggerApplicationComponent

class BaseApplication : Application() {
    private val applicationComponent : ApplicationComponent by lazy {
        DaggerApplicationComponent.create()

    }
    companion object{
        fun appComponent(context: Context) =
            (context.applicationContext as BaseApplication).applicationComponent
    }

}

fun Activity.getAppComponent() = BaseApplication.appComponent(this)