package ru.gb.makulin.myphotooftheday.viewmodel

import ru.gb.makulin.myphotooftheday.model.MarsPhotosList
import ru.gb.makulin.myphotooftheday.model.PhotoOfTheDay

sealed class AppState {
    data class PhotoOfTheDaySuccess(val photoOfTheDay: PhotoOfTheDay) : AppState()
    data class MarsSuccess(val photos: MarsPhotosList) : AppState()
    data class Error(val error: String) : AppState()
    object Loading : AppState()
}
