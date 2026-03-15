package com.kemalcodes.kmptutorial.di

import com.kemalcodes.kmptutorial.data.local.LocalNoteDataSource
import com.kemalcodes.kmptutorial.data.remote.NoteApiClient
import com.kemalcodes.kmptutorial.data.repository.NoteRepository
import com.kemalcodes.kmptutorial.domain.NoteDetailViewModel
import com.kemalcodes.kmptutorial.domain.NotesViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val sharedModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = false
                    isLenient = true
                })
            }
        }
    }
    singleOf(::LocalNoteDataSource)
    singleOf(::NoteApiClient)
    singleOf(::NoteRepository)
    factoryOf(::NotesViewModel)
    factoryOf(::NoteDetailViewModel)
}
