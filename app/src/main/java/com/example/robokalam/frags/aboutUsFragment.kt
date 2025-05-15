package com.example.robokalam.frags

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.robokalam.Course
import com.example.robokalam.CourseAdapter
import com.example.robokalam.databinding.FragmentAboutUsBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class aboutUsFragment : Fragment() {
    private var _binding: FragmentAboutUsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupVideo()
        setupCourses()
        setupClickListeners()

    }

    private fun setupVideo() {
        lifecycle.addObserver(binding.youtubePlayerView)

        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                // The video ID is "NxJRyGbdi-U" from your URL
                youTubePlayer.loadVideo("NxJRyGbdi-U", 0f)
                youTubePlayer.play()
            }
        })


        binding.youtubePlayerView.enableAutomaticInitialization = true

//        // Optional: Set custom UI settings
//        binding.youtubePlayerView.getPlayerUiController().apply {
//            showFullscreenButton(true)
//            showVideoTitle(true)
//            showYouTubeButton(false)
//        }
    }


    private fun setupCourses() {
        val courses = listOf(
            Course(
                "https://robokalam.com/assets/img/course/course-1.jpg",
                "AI & Machine Learning",
                "Master the fundamentals of AI and ML with hands-on projects",
                "https://robokalam.com/courses/ai"
            ),
            Course(
                "https://robokalam.com/assets/img/course/course-2.jpg",
                "Web Development",
                "Learn full-stack web development from basics to advanced",
                "https://robokalam.com/courses/web"
            ),
            Course(
                "https://robokalam.com/assets/img/course/course-3.jpg",
                "Data Science",
                "Explore data analysis, visualization and statistical modeling",
                "https://robokalam.com/courses/data-science"
            )
        )

        binding.rvCourses.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CourseAdapter(courses) { url ->
                openUrl(url)
            }
        }
    }

    private fun setupClickListeners() {
        binding.apply {
            llFacebook.setOnClickListener {
                openUrl("https://www.facebook.com/robokalam")
            }
            llYoutube.setOnClickListener {
                openUrl("https://www.youtube.com/robokalam")
            }
            llInstagram.setOnClickListener {
                openUrl("https://www.instagram.com/robokalam")
            }
            llLinkedin.setOnClickListener {
                openUrl("https://www.linkedin.com/company/robokalam")
            }

            llEmail.setOnClickListener {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:support@robokalam.com")
                }
                startActivity(Intent.createChooser(intent, "Send email"))
            }

            llPhone.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+911234567890"))
                startActivity(intent)
            }

            llAddress.setOnClickListener {
                val uri = Uri.parse("geo:0,0?q=Robokalam+Bangalore")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        }
    }

    private fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to open URL", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding.youtubePlayerView.release()
        _binding = null
    }
}