package ru.gb.makulin.myphotooftheday.view.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.gb.makulin.myphotooftheday.databinding.FragmentMarsViewPagerBinding

class MarsViewPagerFragment : Fragment() {

    companion object {
        fun newInstance(): MarsViewPagerFragment = MarsViewPagerFragment()
    }

    private var _binding: FragmentMarsViewPagerBinding? = null
    private val binding: FragmentMarsViewPagerBinding
        get() {
            return _binding!!
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
        _binding = FragmentMarsViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPagerAdapter()
    }

    private fun setViewPagerAdapter() {
        binding.viewPager.adapter = MarsPagerAdapter(requireActivity())
    }


}