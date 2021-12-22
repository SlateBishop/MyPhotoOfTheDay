package ru.gb.makulin.myphotooftheday.view.mars

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MarsPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    private val fragments = listOf(
        MarsRoverCuriosityFragment.newInstance(),
        MarsRoverOpportunityFragment.newInstance(),
        MarsRoverSpiritFragment.newInstance()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}