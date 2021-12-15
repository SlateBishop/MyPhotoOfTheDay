package ru.gb.makulin.myphotooftheday.facade

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.gb.makulin.myphotooftheday.utils.API_DATE_NAME
import ru.gb.makulin.myphotooftheday.utils.API_KEY_NAME
import ru.gb.makulin.myphotooftheday.utils.API_URL_END_POINT_APOD

interface NasaApi {

    @GET(API_URL_END_POINT_APOD)
    fun getPhotoOfTheDay(
        @Query(API_KEY_NAME) apikey: String,
        @Query(API_DATE_NAME) date: String = ""
    ): Call<PhotoOfTheDayDTO>
}