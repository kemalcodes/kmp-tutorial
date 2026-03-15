package com.kemalcodes.kmptutorial

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class Screen {
    data object NoteList : Screen()
    data class NoteEdit(val noteId: Long?) : Screen()
}

class Navigator {
    private val _currentScreen = MutableStateFlow<Screen>(Screen.NoteList)
    val currentScreen: StateFlow<Screen> = _currentScreen

    private val backStack = mutableListOf<Screen>()

    fun navigateTo(screen: Screen) {
        backStack.add(_currentScreen.value)
        _currentScreen.value = screen
    }

    fun goBack() {
        if (backStack.isNotEmpty()) {
            _currentScreen.value = backStack.removeLast()
        }
    }
}
