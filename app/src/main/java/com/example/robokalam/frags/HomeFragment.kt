package com.example.robokalam.frags

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.robokalam.data.model.Course
import com.example.robokalam.adapter.CourseAdapter
import com.example.robokalam.adapter.GenericAdapter
import com.example.robokalam.data.model.ItemModel
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

    private lateinit var featuredCoursesAdapter: CourseAdapter
    private lateinit var liveClassesAdapter: CourseAdapter

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
        setupRecyclerViews()
        setWelcomeMessage()
        loadCourses()
        fetchQuote()
    }

    private fun fetchQuote() {
        binding.tvQuote.text = "Loading quote..."

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = URL("https://api.quotable.io/random?maxLength=100").readText()
                val jsonObject = org.json.JSONObject(response)
                val quote = jsonObject.getString("content")
                val author = jsonObject.getString("author")

                withContext(Dispatchers.Main) {
                    binding.tvQuote.text = "\"$quote\"\n- $author"
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.tvQuote.text = "\"Education is not preparation for life; education is life itself.\"\n- John Dewey"
                }
            }
        }
    }

    private fun setupRecyclerViews() {
        featuredCoursesAdapter = CourseAdapter(emptyList()) { course ->

            openCourseDetails(course)
        }

        binding.rvFeaturedCourses.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = featuredCoursesAdapter
        }


        liveClassesAdapter = CourseAdapter(emptyList()) { course ->
            openCourseDetails(course)
        }

        binding.rvLiveClasses.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = liveClassesAdapter
        }
    }

    private fun loadCourses() {

        val featuredCourses = listOf(
            Course(
                id = "1",
                title = "Basic Electronics",
                description = "Learn basic electronics from scratch",
                imageUrl = "https://shop.robokalam.com/wp-content/uploads/2021/11/basic-electronics-416x416.png",
                price = 6000.00
            ),
            Course(
                id = "2",
                title = "Python Development",
                description = "Master Python development",
                imageUrl = "https://shop.robokalam.com/wp-content/uploads/2021/11/ML-with-python-1-416x416.png",
                price = 12500.00
            )
        )


        val liveClasses = listOf(
            Course(
                id = "3",
                title = "Live ML Workshop",
                description = "Interactive ML development session",
                imageUrl = "https://shop.robokalam.com/wp-content/uploads/2021/11/ML-with-python-1-416x416.png",
                price = 18000.00,
                isLive = true
            ),
            Course(
                id = "4",
                title = "Live Game Workshop",
                description = "Interactive Game development session",
                imageUrl = "https://shop.robokalam.com/wp-content/uploads/2021/11/App-building-416x416.png",
                price = 4999.00,
                isLive = true
            )
        )


        featuredCoursesAdapter = CourseAdapter(featuredCourses) { course ->
            openCourseDetails(course)
        }
        binding.rvFeaturedCourses.adapter = featuredCoursesAdapter

        liveClassesAdapter = CourseAdapter(liveClasses) { course ->
            openCourseDetails(course)
        }
        binding.rvLiveClasses.adapter = liveClassesAdapter
    }

    private fun openCourseDetails(course: Course) {

        Toast.makeText(context, "Opening ${course.title}", Toast.LENGTH_SHORT).show()
    }
    private fun setWelcomeMessage() {
        val sharedPreferences = requireActivity().getSharedPreferences("login_pref", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("user_name", "User") ?: "User"
        binding.tvWelcome.text = "Welcome back, $userName!"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}