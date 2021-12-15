package ru.gb.makulin.myphotooftheday.facade

import retrofit2.Callback

class PhotoRepositoryImpl(private val remoteDataSource: RemoteDataSource) : PhotoRepository {
    override fun getPhotoOfTheDayFromRemote(callback: Callback<PhotoOfTheDayDTO>, date: String) {
        remoteDataSource.getPhotoOfTheDay(callback, date)
    }
}