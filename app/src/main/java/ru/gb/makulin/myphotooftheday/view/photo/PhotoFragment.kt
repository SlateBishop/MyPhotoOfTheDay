package ru.gb.makulin.myphotooftheday.view.photo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import ru.gb.makulin.myphotooftheday.R
import ru.gb.makulin.myphotooftheday.databinding.FragmentPhotoMainBinding
import ru.gb.makulin.myphotooftheday.model.PhotoOfTheDay
import ru.gb.makulin.myphotooftheday.utils.BASE_WIKI_URL
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
                photoTitleTextView.text = title
                photoExplanationTextView.text = explanation
            }
        }
    }


}