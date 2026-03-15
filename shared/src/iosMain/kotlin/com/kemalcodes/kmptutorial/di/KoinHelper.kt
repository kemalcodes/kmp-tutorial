package com.kemalcodes.kmptutorial.di

import com.kemalcodes.kmptutorial.data.repository.NoteRepository
import org.koin.core.Koin
import org.koin.core.context.startKoin

object KoinHelper {
    private lateinit var koin: Koin

    fun start() {
        koin = startKoin {
            modules(platformModule, sharedModule)
        }.koin
    }

    fun getNoteRepository(): NoteRepository = koin.get()
}

fun initKoinIos() {
    KoinHelper.start()
}
