package ru.gb.makulin.myphotooftheday.facade.photo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoOfTheDayDTO(
    val date: String,
    val explanation: String,
    val mediaType: String,
    val title: String,
    val url: String
) : Parcelable
