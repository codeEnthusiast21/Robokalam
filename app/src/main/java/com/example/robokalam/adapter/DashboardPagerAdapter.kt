package com.example.robokalam.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.robokalam.frags.PortfolioEditFragment
import com.example.robokalam.frags.PurchasedCoursesFragment

class DashboardPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PurchasedCoursesFragment()
            1 -> PortfolioEditFragment()
            else -> throw IllegalStateException("Invalid position $position")
        }
    }
}