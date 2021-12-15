package ru.gb.makulin.myphotooftheday.facade

import retrofit2.Callback

interface PhotoRepository {
    fun getPhotoOfTheDayFromRemote(callback: Callback<PhotoOfTheDayDTO>, date: String = "")
}