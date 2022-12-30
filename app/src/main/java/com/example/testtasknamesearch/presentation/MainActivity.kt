package com.example.testtasknamesearch.presentation

import android.R.id.tabs
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.example.testtasknamesearch.R
import com.example.testtasknamesearch.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        setTabLayout(applicationContext)
    }

    private fun setTabLayout(context: Context){
        val icons =
            arrayListOf(R.drawable.vector__stroke_, R.drawable.star)
        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        val tabLayoutMediator =
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.setIcon(icons[position])
                tab.text = resources.getStringArray(R.array.tab_names)[position]
            }
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabIconColor = ContextCompat.getColor(context, R.color.dark_gray)
                tab?.icon?.colorFilter =
                    BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                        tabIconColor,
                        BlendModeCompat.SRC_ATOP
                    )
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabIconColor = ContextCompat.getColor(context, R.color.gray)
                tab?.icon?.colorFilter =
                    BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                        tabIconColor,
                        BlendModeCompat.SRC_ATOP
                    )
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        KeyboardVisibilityEvent.setEventListener(
            this@MainActivity
        ) { isOpen ->
            if (isOpen) {
                binding.tabLayout.visibility = View.GONE
            } else {
                binding.tabLayout.visibility = View.VISIBLE
            }
        }
        tabLayoutMediator.attach()
    }
}