package ru.gb.makulin.myphotooftheday.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarsPhotosList(val photos: List<MarsPhoto>) : Parcelable
