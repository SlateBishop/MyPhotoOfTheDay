package ru.gb.makulin.myphotooftheday.facade.mars

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarsPhotoDTO(val imgScr: String) : Parcelable
