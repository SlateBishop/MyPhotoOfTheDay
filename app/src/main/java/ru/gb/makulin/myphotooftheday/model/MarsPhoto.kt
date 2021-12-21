package ru.gb.makulin.myphotooftheday.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarsPhoto(val imgUrl: String) : Parcelable
