package ru.gb.makulin.myphotooftheday.view.mars

import androidx.lifecycle.ViewModelProvider
import ru.gb.makulin.myphotooftheday.viewmodel.AppState
import ru.gb.makulin.myphotooftheday.viewmodel.mars.MarsRoverSpiritViewModel

class MarsRoverSpiritFragment : MarsRoverCuriosityFragment() { //FIXME

    private val viewModel: MarsRoverSpiritViewModel by lazy {
        ViewModelProvider(this).get(MarsRoverSpiritViewModel::class.java)
    }


    companion object {
        fun newInstance(): MarsRoverSpiritFragment = MarsRoverSpiritFragment()
    }

    private val adapter = MarsPhotosAdapter()

     override fun observeOnViewModel() {
        viewModel.getLiveData().observe(viewLifecycleOwner, {
            renderData(it)
        })
    }

    override fun setAdapter() {
        binding.marsPhotoRecyclerView.adapter = adapter
    }

    override fun renderData(appState: AppState) {
        super.renderData(appState)
    }

}