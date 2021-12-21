package ru.gb.makulin.myphotooftheday.facade.photo

import retrofit2.Callback
import ru.gb.makulin.myphotooftheday.facade.retrofit.RemoteDataSource

class PhotoRepositoryImpl(private val remoteDataSource: RemoteDataSource) : PhotoRepository {
    override fun getPhotoOfTheDayFromRemote(callback: Callback<PhotoOfTheDayDTO>, date: String) {
        remoteDataSource.getPhotoOfTheDay(callback, date)
    }
}