package ru.gb.makulin.myphotooftheday.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import com.google.android.material.appbar.AppBarLayout

class NestedScrollBehavior(context: Context, attr: AttributeSet) : CoordinatorLayout
.Behavior<NestedScrollView>(context, attr) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: NestedScrollView,
        dependency: View
    ): Boolean {
        return (dependency is AppBarLayout)
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: NestedScrollView,
        dependency: View
    ): Boolean {
        val appBar = dependency as AppBarLayout
        child.y = appBar.height + appBar.y
        return super.onDependentViewChanged(parent, child, dependency)
    }
}