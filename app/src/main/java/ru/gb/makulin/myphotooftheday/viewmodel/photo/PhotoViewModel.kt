package ru.gb.makulin.myphotooftheday.viewmodel.photo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response
import ru.gb.makulin.myphotooftheday.facade.photo.PhotoOfTheDayDTO
import ru.gb.makulin.myphotooftheday.facade.photo.PhotoRepository
import ru.gb.makulin.myphotooftheday.facade.photo.PhotoRepositoryImpl
import ru.gb.makulin.myphotooftheday.facade.retrofit.RemoteDataSource
import ru.gb.makulin.myphotooftheday.utils.convertPhotoOfTheDayDtoToPhotoOfTheDay
import ru.gb.makulin.myphotooftheday.viewmodel.AppState

class PhotoViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData<AppState>(),
    private val photoRepositoryImpl: PhotoRepository = PhotoRepositoryImpl(RemoteDataSource)
) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getPhotoOfTheDay(date: String = "") {
        liveDataToObserve.value = AppState.Loading
        photoRepositoryImpl.getPhotoOfTheDayFromRemote(callback, date)
    }

    private val callback = object : retrofit2.Callback<PhotoOfTheDayDTO> {
        override fun onResponse(
            call: Call<PhotoOfTheDayDTO>,
            response: Response<PhotoOfTheDayDTO>
        ) {
            if (response.isSuccessful && response.body() != null) {
                val photoDTO = response.body()
                photoDTO?.let {
                    liveDataToObserve.value =
                        AppState.PhotoOfTheDaySuccess(convertPhotoOfTheDayDtoToPhotoOfTheDay(it))
                }
            } else {
                liveDataToObserve.value = AppState.Error(response.code().toString())
            }
        }

        override fun onFailure(call: Call<PhotoOfTheDayDTO>, t: Throwable) {
            liveDataToObserve.value = AppState.Error(t.localizedMessage)
        }

    }


}