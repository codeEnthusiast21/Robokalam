package com.example.robokalam.frags

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.robokalam.databinding.FragmentDashboardBinding
import androidx.viewpager2.widget.ViewPager2
import com.example.robokalam.adapter.DashboardPagerAdapter
import com.example.robokalam.LoginActivity
import com.example.robokalam.data.AppDatabase
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch

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
        setupLogout()
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
    private fun setupLogout() {
        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { _, _ ->
                    performLogout()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    private fun performLogout() {
        // Clear SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences(
            "login_pref",
            Context.MODE_PRIVATE
        )
        sharedPreferences.edit().clear().apply()

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getInstance(requireContext())
            db.clearAllTables()

            withContext(Dispatchers.Main) {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}