package ru.gb.makulin.myphotooftheday.facade.mars

import retrofit2.Callback
import ru.gb.makulin.myphotooftheday.facade.retrofit.RemoteDataSource

class MarsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : MarsRepository {
    override fun getMarsPhotosFromRemote(
        callback: Callback<MarsPhotosListDTO>,
        roverName: String,
        solNum: Int,
        page: Int
    ) {
        remoteDataSource.getMarsPhotos(callback, roverName, solNum, page)
    }

}