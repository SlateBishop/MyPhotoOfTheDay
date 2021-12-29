package ru.gb.makulin.myphotooftheday.heresy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.ArcMotion
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import ru.gb.makulin.myphotooftheday.R
import ru.gb.makulin.myphotooftheday.databinding.FragmentHeresyBinding

class HeresyFragment : Fragment() {
    private var _binding: FragmentHeresyBinding? = null
    private val binding: FragmentHeresyBinding
        get() {
            return _binding!!
        }

    companion object {
        fun newInstance(): HeresyFragment = HeresyFragment()
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
        _binding = FragmentHeresyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPathButton()


    }

    private fun initPathButton() {
        var isDefault = true
        val constraintSetDefault = ConstraintSet()
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.heresyContainer)
        constraintSet.clear(R.id.heresyPathBtn, ConstraintSet.TOP)
        constraintSet.clear(R.id.heresyPathBtn, ConstraintSet.START)
        constraintSet.connect(
            R.id.heresyPathBtn,
            ConstraintSet.END,
            R.id.heresyContainer,
            ConstraintSet.END
        )
        constraintSet.connect(
            R.id.heresyPathBtn,
            ConstraintSet.BOTTOM,
            R.id.heresyContainer,
            ConstraintSet.BOTTOM
        )
        constraintSetDefault.clone(binding.heresyContainer)
        val transition = ChangeBounds()
        transition.setPathMotion(ArcMotion())
        transition.duration = 3000

        binding.heresyPathBtn.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.heresyContainer, transition)
            if (isDefault) {
                constraintSet.applyTo(binding.heresyContainer)
            } else {
                constraintSetDefault.applyTo(binding.heresyContainer)
            }
            isDefault = !isDefault
        }
    }

}