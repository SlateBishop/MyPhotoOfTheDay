package ru.gb.makulin.myphotooftheday.view.mars

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import ru.gb.makulin.myphotooftheday.R
import ru.gb.makulin.myphotooftheday.viewmodel.mars.MarsRoverOpportunityViewModel

class MarsRoverOpportunityFragment : MarsRoverCuriosityFragment() {

    private val viewModel: MarsRoverOpportunityViewModel by lazy {
        ViewModelProvider(this).get(MarsRoverOpportunityViewModel::class.java)
    }

    companion object {
        fun newInstance(): MarsRoverOpportunityFragment = MarsRoverOpportunityFragment()
    }

    override fun observeOnViewModel() {
        viewModel.getLiveData().observe(viewLifecycleOwner, {
            renderData(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.roverNameTextView.text = resources.getString(R.string.mars_rover_opportunity)
    }

    override fun getMarsPhotos(solNum: Int, page: Int) {
        viewModel.getMarsPhotos(solNum, page)
    }
}