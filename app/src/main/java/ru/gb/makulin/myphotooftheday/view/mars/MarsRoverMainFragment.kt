package ru.gb.makulin.myphotooftheday.view.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.gb.makulin.myphotooftheday.databinding.FragmentMarsRoverMainBinding

class MarsRoverMainFragment : Fragment() {

    companion object {
        fun newInstance(): MarsRoverMainFragment = MarsRoverMainFragment()
    }

    private var _binding: FragmentMarsRoverMainBinding? = null
    private val binding: FragmentMarsRoverMainBinding
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
        _binding = FragmentMarsRoverMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}