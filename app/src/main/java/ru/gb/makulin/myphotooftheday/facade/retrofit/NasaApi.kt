package ru.gb.makulin.myphotooftheday.facade.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.gb.makulin.myphotooftheday.facade.mars.MarsPhotosListDTO
import ru.gb.makulin.myphotooftheday.facade.photo.PhotoOfTheDayDTO
import ru.gb.makulin.myphotooftheday.utils.*

interface NasaApi {

    @GET(API_URL_END_POINT_APOD)
    fun getPhotoOfTheDay(
        @Query(API_KEY_NAME) apikey: String,
        @Query(API_DATE_NAME) date: String = ""
    ): Call<PhotoOfTheDayDTO>

    @GET(API_URL_END_POINT_MARS)
    fun getMarsPhotos(
        @Path(API_MARS_PATH_ROVER) roverName: String,
        @Query(API_KEY_NAME) apikey: String,
        @Query(API_MARS_SOL_NAME) solNum: Int = 1,
        @Query(API_MARS_PAGE_NAME) page: Int = 1
    ): Call<MarsPhotosListDTO>


}