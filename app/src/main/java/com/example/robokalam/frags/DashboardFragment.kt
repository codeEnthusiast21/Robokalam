package com.example.robokalam.frags

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.robokalam.databinding.FragmentDashboardBinding
import androidx.viewpager2.widget.ViewPager2
import com.example.robokalam.DashboardPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProfile()
        setupViewPager()
    }

    private fun setupProfile() {
        val sharedPref = requireActivity().getSharedPreferences("login_pref", Context.MODE_PRIVATE)
        val userName = sharedPref.getString("user_name", "User")
        val userEmail = sharedPref.getString("user_email", "")

        binding.tvUserName.text = userName
        binding.tvUserEmail.text = userEmail
    }

    private fun setupViewPager() {
        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        val pagerAdapter = DashboardPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "My Courses"
                1 -> "Portfolio"
                else -> null
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}