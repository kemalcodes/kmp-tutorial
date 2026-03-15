package com.kemalcodes.kmptutorial

import android.app.Application
import com.kemalcodes.kmptutorial.di.platformModule
import com.kemalcodes.kmptutorial.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NotesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NotesApplication)
            modules(platformModule, sharedModule)
        }
    }
}
