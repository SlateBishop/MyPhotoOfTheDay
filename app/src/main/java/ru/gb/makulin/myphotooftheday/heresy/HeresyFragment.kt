package ru.gb.makulin.myphotooftheday.heresy

import android.animation.ObjectAnimator
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
        setViewAnimate()

    }

    private fun setViewAnimate() {
        var isHide = true
        val duration: Long = 2000
        with(binding) {
            heresyShowBtn.setOnClickListener {
                ObjectAnimator.ofFloat(heresyShowBtn, "translationY", 200f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(heresyShowBtn, "rotation", 0f, 720f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(heresyHideBtn, "translationY", -200f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(heresyHideBtn, "rotation", 720f, 0f)
                    .setDuration(duration).start()

                heresyBackground.animate().alpha(0f).duration = duration
                heresyShowBtn.animate().alpha(0f).duration = duration
                heresyHideBtn.animate().alpha(1f).duration = duration
                heresyShowBtn.isClickable = false
                heresyHideBtn.isClickable = true

            }

            heresyHideBtn.setOnClickListener {
                ObjectAnimator.ofFloat(heresyShowBtn, "translationY", 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(heresyShowBtn, "rotation", 720f, 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(heresyHideBtn, "translationY", 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(heresyHideBtn, "rotation", 0f, 720f)
                    .setDuration(duration).start()

                heresyBackground.animate().alpha(1f).duration = duration
                heresyShowBtn.animate().alpha(1f).duration = duration
                heresyHideBtn.animate().alpha(0f).duration = duration
                heresyShowBtn.isClickable = true
                heresyHideBtn.isClickable = false
            }
        }
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