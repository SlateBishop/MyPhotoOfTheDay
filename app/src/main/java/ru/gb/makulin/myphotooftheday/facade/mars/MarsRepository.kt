package ru.gb.makulin.myphotooftheday.facade.mars

import retrofit2.Callback

interface MarsRepository {
    fun getMarsPhotosFromRemote(
        callback: Callback<MarsPhotosListDTO>,
        roverName: String,
        solNum: Int = 1,
        page: Int = 1
    )
}