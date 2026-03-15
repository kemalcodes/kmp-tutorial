package com.kemalcodes.kmptutorial.di

import com.kemalcodes.kmptutorial.data.local.LocalNoteDataSource
import com.kemalcodes.kmptutorial.data.repository.NoteRepository
import com.kemalcodes.kmptutorial.domain.NoteDetailViewModel
import com.kemalcodes.kmptutorial.domain.NotesViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val sharedModule = module {
    singleOf(::LocalNoteDataSource)
    singleOf(::NoteRepository)
    factoryOf(::NotesViewModel)
    factoryOf(::NoteDetailViewModel)
}
