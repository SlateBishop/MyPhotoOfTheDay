package ru.gb.makulin.myphotooftheday.viewmodel.mars

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gb.makulin.myphotooftheday.facade.mars.MarsRepository
import ru.gb.makulin.myphotooftheday.facade.mars.MarsRepositoryImpl
import ru.gb.makulin.myphotooftheday.facade.retrofit.RemoteDataSource
import ru.gb.makulin.myphotooftheday.utils.MARS_ROVER_OPPORTUNITY_NAME
import ru.gb.makulin.myphotooftheday.utils.getMartianCallback
import ru.gb.makulin.myphotooftheday.viewmodel.AppState

class MarsRoverOpportunityViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData<AppState>(),
    private val marsRepositoryImpl: MarsRepository = MarsRepositoryImpl(RemoteDataSource)
) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getMarsPhotos(solNum: Int = 1, page: Int = 1) {
        liveDataToObserve.value = AppState.Loading
        marsRepositoryImpl.getMarsPhotosFromRemote(
            getMartianCallback(liveDataToObserve),
            MARS_ROVER_OPPORTUNITY_NAME,
            solNum,
            page
        )
    }
}