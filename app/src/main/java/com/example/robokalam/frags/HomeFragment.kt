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
    private var cachedQuote: String? = null



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
        binding.tvWelcome.text = "Welcome back, $userName"
    }

    private fun setupQuote() {

        cachedQuote?.let {
            binding.tvQuote.text = it
            return
        }
        lifecycleScope.launch {
            try {
                val quote = fetchQuote()
                cachedQuote = quote
                if (isAdded) {
                    binding.tvQuote.text = quote
                }
            } catch (e: Exception) {
                if (isAdded) {
                    binding.tvQuote.text = "Failed to load quote"
                }
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
                "Full Stack Web Development",
                "Master MERN Stack Development",
                R.drawable.robokalam_logo
            ),
            ItemModel(
                2,
                "Data Science & Analytics",
                "Python, ML & Data Analysis",
                R.drawable.robokalam_logo
            ),
            ItemModel(
                3,
                "Digital Marketing",
                "Complete Digital Marketing Course",
                R.drawable.robokalam_logo
            ),
            ItemModel(
                4,
                "Programming with Python",
                "Python Programming Fundamentals",
                R.drawable.robokalam_logo
            ),
            ItemModel(
                5,
                "UI/UX Design",
                "Design Thinking & Tools",
                R.drawable.robokalam_logo
            )
        )
    }

    private fun getSampleClasses(): List<ItemModel> {
        return listOf(
            ItemModel(
                1,
                "Web Development Workshop",
                "Build Real Projects with React",
                R.drawable.robokalam_logo
            ),
            ItemModel(
                2,
                "Python Programming Lab",
                "Hands-on Coding Practice",
                R.drawable.robokalam_logo
            ),
            ItemModel(
                3,
                "Machine Learning Basics",
                "Introduction to ML Algorithms",
                R.drawable.robokalam_logo
            ),
            ItemModel(
                4,
                "Digital Marketing Strategy",
                "SEO & Social Media Marketing",
                R.drawable.robokalam_logo
            )
        )
    }


    private suspend fun fetchQuote(): String {
        return withContext(Dispatchers.IO) {
            val response = URL("https://zenquotes.io/api/random").readText()
            JSONArray(response).getJSONObject(0).getString("q")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}