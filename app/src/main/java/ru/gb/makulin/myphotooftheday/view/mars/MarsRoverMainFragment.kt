package ru.gb.makulin.myphotooftheday.view.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.gb.makulin.myphotooftheday.databinding.FragmentMarsRoverMainBinding
import ru.gb.makulin.myphotooftheday.model.MarsPhotosList
import ru.gb.makulin.myphotooftheday.utils.makeErrSnackbar
import ru.gb.makulin.myphotooftheday.viewmodel.AppState
import ru.gb.makulin.myphotooftheday.viewmodel.mars.MarsRoverMainViewModel

class MarsRoverMainFragment : Fragment() {

    companion object {
        fun newInstance(): MarsRoverMainFragment = MarsRoverMainFragment()
    }

    private var _binding: FragmentMarsRoverMainBinding? = null
    private val binding: FragmentMarsRoverMainBinding
        get() {
            return _binding!!
        }

    private val adapter = MarsPhotosAdapter()
    private val viewModel: MarsRoverMainViewModel by lazy {
        ViewModelProvider(this).get(MarsRoverMainViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsRoverMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        observeOnViewModel()
        getMarsPhotos()
    }

    private fun getMarsPhotos() {
        viewModel.getMarsPhotos()
    }

    private fun observeOnViewModel() {
        viewModel.getLiveData().observe(viewLifecycleOwner, {
            renderData(it)
        })
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.root.makeErrSnackbar {
                    getMarsPhotos()
                }
            }
            AppState.Loading -> binding.loading.progressBar.visibility = View.VISIBLE
            is AppState.MarsSuccess -> {
                val photo = appState.photos
                setData(photo)
                binding.loading.progressBar.visibility = View.GONE
            }
        }

    }

    private fun setData(photo: MarsPhotosList) {
        adapter.setData(photo.photos)
    }

    private fun setAdapter() {
        binding.marsPhotoRecyclerView.adapter = adapter
    }
}