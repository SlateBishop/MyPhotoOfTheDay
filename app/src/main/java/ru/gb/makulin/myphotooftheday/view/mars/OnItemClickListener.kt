package ru.gb.makulin.myphotooftheday.view.mars

import ru.gb.makulin.myphotooftheday.model.MarsPhoto


interface OnItemClickListener {
    fun onItemClick(photo: MarsPhoto)
}