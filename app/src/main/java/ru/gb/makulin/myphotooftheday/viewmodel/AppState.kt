package ru.gb.makulin.myphotooftheday.viewmodel

import ru.gb.makulin.myphotooftheday.model.PhotoOfTheDay

sealed class AppState {
    data class Success(val photoOfTheDay: PhotoOfTheDay) : AppState()
    data class Error(val error: String) : AppState()
    object Loading : AppState()
}
