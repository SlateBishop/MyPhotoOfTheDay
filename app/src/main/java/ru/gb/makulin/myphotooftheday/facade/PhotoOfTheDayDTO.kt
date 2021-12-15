package ru.gb.makulin.myphotooftheday.facade

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoOfTheDayDTO(
    val date: String,
    val explanation: String,
    val mediaType: String,
    val title: String,
    val url: String
) : Parcelable
