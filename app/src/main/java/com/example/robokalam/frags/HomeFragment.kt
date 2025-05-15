package com.example.robokalam.frags

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.robokalam.GenericAdapter
import com.example.robokalam.ItemModel
import com.example.robokalam.R
import com.example.robokalam.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.URL


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var courseAdapter: GenericAdapter
    private lateinit var classAdapter: GenericAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWelcomeMessage()
        setupQuote()
        setupRecyclerViews()
    }

    private fun setupWelcomeMessage() {
        val sharedPref = requireActivity().getSharedPreferences("login_pref", Context.MODE_PRIVATE)
        val userName = sharedPref.getString("user_name", "User")
        binding.tvWelcome.text = "Welcome, $userName"
    }

    private fun setupQuote() {
        // Fetch quote from API
        lifecycleScope.launch {
            try {
                val quote = fetchQuote()
                binding.tvQuote.text = quote
            } catch (e: Exception) {
                binding.tvQuote.text = "Loading quote..."
            }
        }
    }

    private fun setupRecyclerViews() {
        courseAdapter = GenericAdapter()
        classAdapter = GenericAdapter()

        binding.rvCourses.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = courseAdapter
        }

        binding.rvClasses.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = classAdapter
        }

        // Sample data
        courseAdapter.submitList(getSampleCourses())
        classAdapter.submitList(getSampleClasses())
    }
    private fun getSampleCourses(): List<ItemModel> {
        return listOf(
            ItemModel(
                1,
                "Python Programming",
                "Learn Python from scratch",
                R.drawable.robokalam_logo
            ),
            ItemModel(
                2,
                "Web Development",
                "Full Stack Development Course",
                R.drawable.robokalam_logo
            ),
            ItemModel(
                3,
                "Machine Learning",
                "AI & ML Fundamentals",
                R.drawable.robokalam_logo
            ),
            ItemModel(
                4,
                "App Development",
                "Android & iOS Development",
                R.drawable.robokalam_logo
            )
        )
    }

    private fun getSampleClasses(): List<ItemModel> {
        return listOf(
            ItemModel(
                1,
                "Live Python Session",
                "Today at 3:00 PM",
                R.drawable.robokalam_logo
            ),
            ItemModel(
                2,
                "Data Structures",
                "Tomorrow at 4:00 PM",
                R.drawable.robokalam_logo
            ),
            ItemModel(
                3,
                "Interview Prep",
                "Sunday at 2:00 PM",
                R.drawable.robokalam_logo
            ),
            ItemModel(
                4,
                "Coding Practice",
                "Monday at 5:00 PM",
                R.drawable.robokalam_logo
            )
        )
    }


    private suspend fun fetchQuote(): String {
        return withContext(Dispatchers.IO) {
            val response = URL("https://zenquotes.io/api/random").readText()
            // Parse JSON response and return quote
            JSONArray(response).getJSONObject(0).getString("q")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}