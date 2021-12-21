package ru.gb.makulin.myphotooftheday.view.mars

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
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
            binding.apply {
                cardImageView.load(photo.imgSrc)
            }
        }
    }
}
