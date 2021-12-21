package ru.gb.makulin.myphotooftheday.utils

import ru.gb.makulin.myphotooftheday.facade.photo.PhotoOfTheDayDTO
import ru.gb.makulin.myphotooftheday.model.PhotoOfTheDay

fun convertPhotoOfTheDayDtoToPhotoOfTheDay(photoDTO: PhotoOfTheDayDTO): PhotoOfTheDay {
    return with(photoDTO) {
        PhotoOfTheDay(date, explanation, mediaType, title, url)
    }
}