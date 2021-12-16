package ru.gb.makulin.myphotooftheday.view.photo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.gb.makulin.myphotooftheday.R
import ru.gb.makulin.myphotooftheday.databinding.FragmentPhotoMainBinding
import ru.gb.makulin.myphotooftheday.model.PhotoOfTheDay
import ru.gb.makulin.myphotooftheday.utils.BASE_WIKI_URL
import ru.gb.makulin.myphotooftheday.utils.makeErrSnackbar
import ru.gb.makulin.myphotooftheday.utils.makeSnackbar
import ru.gb.makulin.myphotooftheday.view.MainActivity
import ru.gb.makulin.myphotooftheday.viewmodel.AppState
import ru.gb.makulin.myphotooftheday.viewmodel.PhotoViewModel

class PhotoFragment : Fragment() {

    private var _binding: FragmentPhotoMainBinding? = null
    private val binding: FragmentPhotoMainBinding
        get() {
            return _binding!!
        }

    private val viewModel: PhotoViewModel by lazy {
        ViewModelProvider(this).get(PhotoViewModel::class.java)
    }

    companion object {
        fun newInstance(): PhotoFragment {
            return PhotoFragment()
        }
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
        _binding = FragmentPhotoMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeOnViewModel()
        getPhotoOfTheDay()
        setWikiListener()
        setBottomAppBar()
        setBottomSheet()
    }

    private fun setBottomSheet() {
        val behavior = BottomSheetBehavior.from(binding.includeBottomSheet.bottomSheetContainer)
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun setBottomAppBar() {
        (activity as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.appBarFav -> binding.root.makeSnackbar("Избранное")       //TODO
            R.id.appBarSettings -> binding.root.makeSnackbar("Настройки")  //TODO
            android.R.id.home -> BottomNavigationDrawerFragment().show(
                requireActivity()
                    .supportFragmentManager, ""
            )
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setWikiListener() {
        binding.wikiTextInputLayout.setEndIconOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW).apply {   //TODO не понял почему без apply не работает..
                    data = Uri.parse(BASE_WIKI_URL + binding.wikiEditText.text.toString())
                }
            )
        }
    }

    private fun getPhotoOfTheDay() {
        viewModel.getPhotoOfTheDay()
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
                    getPhotoOfTheDay()
                }
            }
            AppState.Loading -> binding.loading.progressBar.visibility = View.VISIBLE
            is AppState.Success -> {
                val photo = appState.photoOfTheDay
                setData(photo)
                binding.loading.progressBar.visibility = View.GONE
            }
        }
    }

    private fun setData(photo: PhotoOfTheDay) {
        binding.apply {
            with(photo) {
                photoImgView.load(url) {
                    lifecycle(this@PhotoFragment)
                    error(R.drawable.img_not_found)
                    placeholder(R.drawable.ic_no_photo_vector)
                }
                includeBottomSheet.bottomSheetHeaderText.text = title
                includeBottomSheet.bottomSheetExplanationText.text = explanation
            }
        }
    }


}