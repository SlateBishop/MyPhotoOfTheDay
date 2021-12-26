package ru.gb.makulin.myphotooftheday.view.photo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import ru.gb.makulin.myphotooftheday.R
import ru.gb.makulin.myphotooftheday.databinding.FragmentPhotoMainBinding
import ru.gb.makulin.myphotooftheday.model.PhotoOfTheDay
import ru.gb.makulin.myphotooftheday.utils.BASE_WIKI_URL
import ru.gb.makulin.myphotooftheday.utils.MEDIA_TYPE_IMAGE
import ru.gb.makulin.myphotooftheday.utils.makeErrSnackbar
import ru.gb.makulin.myphotooftheday.view.MainActivity
import ru.gb.makulin.myphotooftheday.view.settings.SettingsFragment
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

//    private val behavior by lazy {
//        BottomSheetBehavior.from(binding.includeBottomSheet.bottomSheetContainer)
//    }

//    private var isFabHide = false

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
//        setAppBar()
//        setBottomSheet()
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

//    private fun setBottomSheet() {
//        behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
//    }

//    private fun setAppBar() {
//        (activity as MainActivity).setSupportActionBar(binding.photoToolbar)
//        setHasOptionsMenu(true) //TODO придумать или убрать функционал BottomAppBar
//        initFab()
//    }

//    private fun initFab() {
//        binding.fab.setOnClickListener {
//            if (isFabHide) {
//                behavior.state = BottomSheetBehavior.STATE_EXPANDED
//            } else {
//                behavior.state = BottomSheetBehavior.STATE_HIDDEN
//            }
//            isFabHide = !isFabHide
//        }
//    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(
//            R.menu.menu_bottom_navigation_view,
//            menu
//        ) //TODO придумать или убрать функционал BottomAppBar
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.navPhotoOfTheDay -> {
//                setFragment(PhotoFragment.newInstance())
//            }
//            R.id.navSettings -> {
//                setFragment(SettingsFragment())
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

//    private fun setFragment(fragment: Fragment) {
//        requireActivity().supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.mainContainer, fragment)
//            .addToBackStack("")
//            .commit()
//    }

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
            AppState.Loading -> binding.loading.progressBar.visibility = View.VISIBLE
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
                    photoImgView.load(url) {
                        lifecycle(this@PhotoFragment)
                        error(R.drawable.img_not_found)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                } else {
                    photoImgView.load(R.drawable.img_not_found)
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }
                photoHeaderText.text = title
                photoExplanationText.text = explanation
            }
        }
    }


}