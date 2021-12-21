package ru.gb.makulin.myphotooftheday.facade.mars

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarsPhotosListDTO(val photos: List<MarsPhotoDTO>) : Parcelable
