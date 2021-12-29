package ru.gb.makulin.myphotooftheday.view.mars

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.load
import ru.gb.makulin.myphotooftheday.databinding.ItemMarsPhotosBinding
import ru.gb.makulin.myphotooftheday.model.MarsPhoto

class MarsPhotosAdapter : RecyclerView.Adapter<MarsPhotosAdapter.MarsRoverMainFragmentHolder>() {

    private var photos: List<MarsPhoto> = listOf()

    fun setData(data: List<MarsPhoto>) {
        photos = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarsRoverMainFragmentHolder {
        val binding = ItemMarsPhotosBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MarsRoverMainFragmentHolder(binding)
    }

    override fun onBindViewHolder(holder: MarsRoverMainFragmentHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    inner class MarsRoverMainFragmentHolder(private val binding: ItemMarsPhotosBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: MarsPhoto) {
            var isExpand = false
            val defaultHeightParam = binding.cardImageView.layoutParams.height
            val defaultWidthParam = binding.cardImageView.layoutParams.width
            binding.apply {
                val url = photo.imgSrc
                cardImageView.load("https" + url.removeRange(0, 4))
                cardImageView.setOnClickListener {
                    var params = cardImageView.layoutParams
                    val transition = TransitionSet()
                    transition.addTransition(ChangeBounds())
                    transition.addTransition(ChangeImageTransform())
                    transition.duration = 2000
                    if (isExpand) {
                        params.height = defaultHeightParam
                        params.width = defaultWidthParam
                    } else {
                        params.height = defaultHeightParam * 2
                        params.width = defaultWidthParam * 2
                    }
                    isExpand = !isExpand
                    TransitionManager.beginDelayedTransition(marsPhotosItem, transition)
                    cardImageView.layoutParams = params
                }
            }
        }
    }
}
