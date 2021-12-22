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
import ru.gb.makulin.myphotooftheday.viewmodel.mars.MarsRoverCuriosityViewModel

open class MarsRoverCuriosityFragment : Fragment() {

    companion object {
        fun newInstance(): MarsRoverCuriosityFragment = MarsRoverCuriosityFragment()
    protected const val maxPhotosOnPageCount = 25
    }

    protected open var _binding: FragmentMarsRoverMainBinding? = null
    protected open  val binding: FragmentMarsRoverMainBinding
        get() {
            return _binding!!
        }

    private  val adapter = MarsPhotosAdapter()
//    protected open  val adapter = MarsPhotosAdapter()
    private  val viewModel: MarsRoverCuriosityViewModel by lazy {
        ViewModelProvider(this).get(MarsRoverCuriosityViewModel::class.java)
    }
    protected open  var page = 1
    protected open  var photoOnPageCount = 0
    protected open  var solNum = 1

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

    protected open  fun initPageButtons() {
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

    protected open  fun setMarsSolListener() {
        binding.solTextInputLayout.setEndIconOnClickListener {
            solNum = binding.solEditText.text.toString().toInt()
            page = 1
            getMarsPhotos(solNum)
        }
    }

    protected open  fun getMarsPhotos(solNum: Int = 1, page: Int = 1) {
        viewModel.getMarsPhotos(solNum, page)
    }

    protected open  fun observeOnViewModel() {
        viewModel.getLiveData().observe(viewLifecycleOwner, {
            renderData(it)
        })
    }

    protected open  fun renderData(appState: AppState) {
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

    protected open  fun setData(photos: MarsPhotosList) {
        adapter.setData(photos.photos)
    }

    protected open  fun setAdapter() {
        binding.marsPhotoRecyclerView.adapter = adapter
    }
}