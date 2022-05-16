package com.rishabhkumar.whatsapp_clone.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rishabhkumar.whatsapp_clone.Fragment.Login
import com.rishabhkumar.whatsapp_clone.Fragment.SignUp
import com.rishabhkumar.whatsapp_clone.R

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPagerAdapter: AuthenticationPagerAdapter
    private val titles = arrayOf("Login", "Signup")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        viewPager = findViewById(R.id.viewPagerAuthentication)
        tabLayout = findViewById(R.id.tabLayoutAuthentication)
        viewPagerAdapter = AuthenticationPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    class AuthenticationPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> Login()
                1 -> SignUp()
                else -> Login()
            }
        }

    }
}