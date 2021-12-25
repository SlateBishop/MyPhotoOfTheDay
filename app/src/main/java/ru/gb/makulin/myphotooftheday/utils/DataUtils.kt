package ru.gb.makulin.myphotooftheday.utils

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.makulin.myphotooftheday.facade.mars.MarsPhotoDTO
import ru.gb.makulin.myphotooftheday.facade.mars.MarsPhotosListDTO
import ru.gb.makulin.myphotooftheday.facade.photo.PhotoOfTheDayDTO
import ru.gb.makulin.myphotooftheday.model.MarsPhoto
import ru.gb.makulin.myphotooftheday.model.MarsPhotosList
import ru.gb.makulin.myphotooftheday.model.PhotoOfTheDay
import ru.gb.makulin.myphotooftheday.viewmodel.AppState

fun convertPhotoOfTheDayDtoToPhotoOfTheDay(photoDTO: PhotoOfTheDayDTO): PhotoOfTheDay {
    return with(photoDTO) {
        PhotoOfTheDay(date, explanation, mediaType, title, url)
    }
}

fun convertMarsPhotosListDtoToMarsPhotosList(marsDTO: MarsPhotosListDTO): MarsPhotosList {
    return with(marsDTO) {
        MarsPhotosList(photos.map {
            convertMarsPhotoDtoToMarsPhoto(it)
        })
    }
}

fun convertMarsPhotoDtoToMarsPhoto(marsDTO: MarsPhotoDTO): MarsPhoto {
    return with(marsDTO) {
        MarsPhoto(imgSrc)
    }
}

fun getMartianCallback(liveDataToObserve: MutableLiveData<AppState>): Callback<MarsPhotosListDTO> {
    val callback = object : retrofit2.Callback<MarsPhotosListDTO> {
        override fun onResponse(
            call: Call<MarsPhotosListDTO>,
            response: Response<MarsPhotosListDTO>
        ) {
            if (response.isSuccessful && response.body() != null) {
                val photoDTO = response.body()
                photoDTO?.let {
                    liveDataToObserve.value =
                        AppState.MarsSuccess(convertMarsPhotosListDtoToMarsPhotosList(it))
                }
            } else {
                liveDataToObserve.value = AppState.Error(response.code().toString())
            }
        }

        override fun onFailure(call: Call<MarsPhotosListDTO>, t: Throwable) {
            liveDataToObserve.value = AppState.Error(t.localizedMessage)
        }
    }
    return callback
}
