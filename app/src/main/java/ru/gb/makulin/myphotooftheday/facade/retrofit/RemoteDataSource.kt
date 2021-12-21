package ru.gb.makulin.myphotooftheday.facade.retrofit


import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.gb.makulin.myphotooftheday.BuildConfig
import ru.gb.makulin.myphotooftheday.facade.photo.PhotoOfTheDayDTO
import ru.gb.makulin.myphotooftheday.utils.BASE_APOD_API_URL

class RemoteDataSource {

    private val nasaApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_APOD_API_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .setLenient().create()
                )
            )
            .build()
            .create(NasaApi::class.java)
    }

    fun getPhotoOfTheDay(callback: Callback<PhotoOfTheDayDTO>, date: String = "") {
        nasaApi.getPhotoOfTheDay(BuildConfig.NASA_API_KEY, date).enqueue(callback)
    }


}