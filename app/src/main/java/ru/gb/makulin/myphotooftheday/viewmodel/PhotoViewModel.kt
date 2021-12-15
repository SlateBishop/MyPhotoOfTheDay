package ru.gb.makulin.myphotooftheday.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response
import ru.gb.makulin.myphotooftheday.facade.PhotoOfTheDayDTO
import ru.gb.makulin.myphotooftheday.facade.PhotoRepository
import ru.gb.makulin.myphotooftheday.facade.PhotoRepositoryImpl
import ru.gb.makulin.myphotooftheday.facade.RemoteDataSource
import ru.gb.makulin.myphotooftheday.utils.convertPhotoOfTheDayDtoToPhotoOfTheDay

class PhotoViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData<AppState>(),
    private val photoRepositoryImpl: PhotoRepository = PhotoRepositoryImpl(RemoteDataSource())
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
                        AppState.Success(convertPhotoOfTheDayDtoToPhotoOfTheDay(it))
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