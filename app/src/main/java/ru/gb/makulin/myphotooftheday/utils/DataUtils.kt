package ru.gb.makulin.myphotooftheday.utils

import ru.gb.makulin.myphotooftheday.facade.mars.MarsPhotoDTO
import ru.gb.makulin.myphotooftheday.facade.mars.MarsPhotosListDTO
import ru.gb.makulin.myphotooftheday.facade.photo.PhotoOfTheDayDTO
import ru.gb.makulin.myphotooftheday.model.MarsPhoto
import ru.gb.makulin.myphotooftheday.model.MarsPhotosList
import ru.gb.makulin.myphotooftheday.model.PhotoOfTheDay

fun convertPhotoOfTheDayDtoToPhotoOfTheDay(photoDTO: PhotoOfTheDayDTO): PhotoOfTheDay {
    return with(photoDTO) {
        PhotoOfTheDay(date, explanation, mediaType, title, url)
    }
}

fun convertMarsPhotosListDtoToMarsPhotosList(marsDTO: MarsPhotosListDTO): MarsPhotosList {
    return with(marsDTO) {
        MarsPhotosList(photos.map {
            convertMarsPhotoDtoToMarsPhoto(it)
        })
    }
}

fun convertMarsPhotoDtoToMarsPhoto(marsDTO: MarsPhotoDTO): MarsPhoto {
    return with(marsDTO) {
        MarsPhoto(imgSrc)
    }
}