package com.kemalcodes.kmptutorial.di

import com.kemalcodes.kmptutorial.db.NotesDatabase
import org.koin.dsl.module

val platformModule = module {
    single { DatabaseDriverFactory(get()) }
    single { NotesDatabase(get<DatabaseDriverFactory>().createDriver()) }
}
