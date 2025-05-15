// PurchasedCoursesFragment.kt
package com.example.robokalam.frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager  // Add this import
import com.example.robokalam.GenericAdapter
import com.example.robokalam.ItemModel
import com.example.robokalam.R
import com.example.robokalam.databinding.FragmentPurchasedCoursesBinding

class PurchasedCoursesFragment : Fragment() {
    private var _binding: FragmentPurchasedCoursesBinding? = null
    private val binding get() = _binding!!
    private lateinit var courseAdapter: GenericAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPurchasedCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        courseAdapter = GenericAdapter()
        binding.rvPurchasedCourses.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = courseAdapter
        }
        courseAdapter.submitList(getSamplePurchasedCourses())
    }

    private fun getSamplePurchasedCourses(): List<ItemModel> {
        return listOf(
            ItemModel(1, "Python Advanced", "Progress: 60%", R.drawable.robokalam_logo),
            ItemModel(2, "Web Development", "Progress: 30%", R.drawable.robokalam_logo)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}