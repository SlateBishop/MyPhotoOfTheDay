package ru.gb.makulin.myphotooftheday.view.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.gb.makulin.myphotooftheday.R
import ru.gb.makulin.myphotooftheday.databinding.FragmentMarsRoverMainBinding
import ru.gb.makulin.myphotooftheday.model.MarsPhotosList
import ru.gb.makulin.myphotooftheday.utils.makeErrSnackbar
import ru.gb.makulin.myphotooftheday.utils.makeSnackbar
import ru.gb.makulin.myphotooftheday.viewmodel.AppState
import ru.gb.makulin.myphotooftheday.viewmodel.mars.MarsRoverMainViewModel

class MarsRoverCuriosityFragment : Fragment() {

    companion object {
        fun newInstance(): MarsRoverCuriosityFragment = MarsRoverCuriosityFragment()
    private const val maxPhotosOnPageCount = 25
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
    private var page = 1
    private var photoOnPageCount = 0
    private var solNum = 1

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
        setMarsSolListener()
        initPageButtons()
        getMarsPhotos()
    }

    private fun initPageButtons() {
        binding.marsNextPage.setOnClickListener {
            if (photoOnPageCount == maxPhotosOnPageCount) {
                page++
                getMarsPhotos(solNum,page)
                binding.marsPhotoRecyclerView.smoothScrollToPosition(0)
            } else {
                binding.root.makeSnackbar(getString(R.string.mars_btn_next_snack_text))
            }
        }
        binding.marsPreviousPage.setOnClickListener {
            if (page > 1) {
                page--
                getMarsPhotos(solNum,page)
                binding.marsPhotoRecyclerView.smoothScrollToPosition(0)
            } else {
                binding.root.makeSnackbar(getString(R.string.mars_btn_back_snack_text))
            }
        }
    }

    private fun setMarsSolListener() {
        binding.solTextInputLayout.setEndIconOnClickListener {
            solNum = binding.solEditText.text.toString().toInt()
            page = 1
            getMarsPhotos(solNum)
        }
    }

    private fun getMarsPhotos(solNum: Int = 1, page: Int = 1) {
        viewModel.getMarsPhotos(solNum, page)
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
                val photos = appState.photos
                photoOnPageCount = photos.photos.size
                setData(photos)
                binding.loading.progressBar.visibility = View.GONE
            }
        }

    }

    private fun setData(photos: MarsPhotosList) {
        adapter.setData(photos.photos)
    }

    private fun setAdapter() {
        binding.marsPhotoRecyclerView.adapter = adapter
    }
}