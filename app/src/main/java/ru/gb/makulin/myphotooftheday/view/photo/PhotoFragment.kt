package ru.gb.makulin.myphotooftheday.view.photo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.Fade
import androidx.transition.TransitionManager
import coil.load
import coil.transform.GrayscaleTransformation
import com.google.android.material.chip.Chip
import ru.gb.makulin.myphotooftheday.R
import ru.gb.makulin.myphotooftheday.databinding.FragmentPhotoMainBinding
import ru.gb.makulin.myphotooftheday.model.PhotoOfTheDay
import ru.gb.makulin.myphotooftheday.utils.BASE_WIKI_URL
import ru.gb.makulin.myphotooftheday.utils.MEDIA_TYPE_IMAGE
import ru.gb.makulin.myphotooftheday.utils.makeErrSnackbar
import ru.gb.makulin.myphotooftheday.viewmodel.AppState
import ru.gb.makulin.myphotooftheday.viewmodel.photo.PhotoViewModel
import java.text.SimpleDateFormat
import java.util.*

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
        setWikiListener()
        setChipGroupListener()
        initCalendarView()
        getPhotoOfTheDay()
    }

    private fun setChipGroupListener() {
        binding.dateChipGroup.setOnCheckedChangeListener { group, checkedId ->
            group.findViewById<Chip>(checkedId)?.let {
                when (checkedId) {
                    R.id.chipChoiceDayBeforeYesterday -> getPhotoOfTheDay(getDate(-2))
                    R.id.chipChoiceYesterday -> getPhotoOfTheDay(getDate(-1))
                    R.id.chipChoiceToday -> getPhotoOfTheDay()
                }
            }
        }
    }

    private fun getDate(days: Int): String {
        val calendar = Calendar.getInstance()
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        dateFormatter.timeZone = TimeZone.getTimeZone("EST")
        calendar.add(Calendar.DATE, days)
        return dateFormatter.format(calendar.time)
    }

    private fun initCalendarView() {
        val calendar = Calendar.getInstance()
        binding.calendarView.maxDate = calendar.timeInMillis
        setCalendarViewListener()
    }

    private fun setCalendarViewListener() {
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = "$year-${month + 1}-$dayOfMonth"
            getPhotoOfTheDay(selectedDate)
        }
    }

    private fun setWikiListener() {
        binding.wikiTextInputLayout.setEndIconOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(BASE_WIKI_URL + binding.wikiEditText.text.toString())
                }
            )
        }
    }

    private fun getPhotoOfTheDay(date: String = "") {
        viewModel.getPhotoOfTheDay(date)
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
            AppState.Loading -> binding.photoImgView.load(R.drawable.ic_no_photo_vector)
            is AppState.PhotoOfTheDaySuccess -> {
                val photo = appState.photoOfTheDay
                setData(photo)
                binding.loading.progressBar.visibility = View.GONE
            }
        }
    }

    private fun setData(photo: PhotoOfTheDay) {
        binding.apply {
            with(photo) {
                if (mediaType == MEDIA_TYPE_IMAGE) {
                    photoImgView.visibility = View.INVISIBLE
                    photoImgView.load(url) {
                        lifecycle(this@PhotoFragment)
//                        crossfade(true)
//                        crossfade(2000)
                        error(R.drawable.img_not_found)
                        placeholder(R.drawable.ic_no_photo_vector)
                        transformations(GrayscaleTransformation())
                    }
                    setFadeTransition(photoCollapsingToolbar)
                    photoImgView.visibility = View.VISIBLE
                } else {
                    photoImgView.load(R.drawable.img_not_found)
//                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) //FIXME раскомментировать при сдаче ДЗ
                }

                photoHeaderText.text = title
                photoExplanationText.text = explanation
            }
        }
    }

    private fun setFadeTransition(viewGroup: ViewGroup) {
        val fade = Fade()
        fade.duration = 2000
        TransitionManager.beginDelayedTransition(viewGroup, fade)
    }

}