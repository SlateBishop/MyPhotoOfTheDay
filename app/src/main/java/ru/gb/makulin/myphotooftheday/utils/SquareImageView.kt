package ru.gb.makulin.myphotooftheday.utils

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

class SquareImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    style: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, style) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}