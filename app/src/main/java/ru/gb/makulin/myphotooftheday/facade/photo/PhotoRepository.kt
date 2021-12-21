package ru.gb.makulin.myphotooftheday.facade.photo

import retrofit2.Callback

interface PhotoRepository {
    fun getPhotoOfTheDayFromRemote(callback: Callback<PhotoOfTheDayDTO>, date: String = "")
}