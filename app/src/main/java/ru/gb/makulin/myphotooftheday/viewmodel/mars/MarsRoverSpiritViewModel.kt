package ru.gb.makulin.myphotooftheday.viewmodel.mars

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response
import ru.gb.makulin.myphotooftheday.facade.mars.MarsPhotosListDTO
import ru.gb.makulin.myphotooftheday.facade.mars.MarsRepository
import ru.gb.makulin.myphotooftheday.facade.mars.MarsRepositoryImpl
import ru.gb.makulin.myphotooftheday.facade.retrofit.RemoteDataSource
import ru.gb.makulin.myphotooftheday.utils.MARS_ROVER_CURIOSITY_NAME
import ru.gb.makulin.myphotooftheday.utils.MARS_ROVER_SPIRIT_NAME
import ru.gb.makulin.myphotooftheday.utils.convertMarsPhotosListDtoToMarsPhotosList
import ru.gb.makulin.myphotooftheday.viewmodel.AppState

class MarsRoverSpiritViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData<AppState>(),
    private val marsRepositoryImpl: MarsRepository = MarsRepositoryImpl(RemoteDataSource())
) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getMarsPhotos(solNum: Int = 1, page: Int = 1) {
        liveDataToObserve.value = AppState.Loading
        marsRepositoryImpl.getMarsPhotosFromRemote(
            callback,
            MARS_ROVER_SPIRIT_NAME,
            solNum,
            page
        )
    }

    private val callback = object : retrofit2.Callback<MarsPhotosListDTO> {
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


}