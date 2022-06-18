package com.example.tale.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tale.tabs.HomeFragment
import com.example.tale.tabs.PostFragment
import com.example.tale.tabs.ProfileFragment

class TabPageAdapter(
    activity: FragmentActivity,
    private val tabCount: Int
): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = tabCount
    

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> PostFragment()
            1 -> HomeFragment()
            2 -> ProfileFragment()
            else -> null!!
        }
    }
}