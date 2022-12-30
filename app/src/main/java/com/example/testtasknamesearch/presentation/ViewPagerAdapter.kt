package com.example.testtasknamesearch.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testtasknamesearch.presentation.favotitesPage.FavoritesPageFragment
import com.example.testtasknamesearch.presentation.mainPage.MainPageFragment
import java.lang.RuntimeException

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle):
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MainPageFragment()
            1 -> FavoritesPageFragment()
            else -> {
                throw RuntimeException("No matching fragment found")
            }
        }
    }
}