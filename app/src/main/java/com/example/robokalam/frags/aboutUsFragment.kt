package com.example.robokalam.frags

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat.isNestedScrollingEnabled
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.robokalam.data.model.Program
import com.example.robokalam.adapter.ProgramAdapter
import com.example.robokalam.R
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
        setupPrograms()
        setupClickListeners()
    }

    private fun setupPrograms() {
        val programs = listOf(
            Program(
                id = "1",
                imageResId = R.drawable.img_1,
                title = "Electro Ashaers",
                description = "Join our interactive live classes led by industry experts. " +
                        "Learn in small batches with personalized attention and real-time doubt clearing. " +
                        "Practice with hands-on exercises and get immediate feedback.",
                learnMoreUrl = "https://robokalam.com/electro.html"
            ),
            Program(
                id = "2",
                imageResId = R.drawable.img,
                title = "Robotics Summer Camp",
                description = "Get ready for an exciting summer at Robokalam. " +
                        "Join us to enhance your child's learning" +
                        "Hands-on experience in electronics , IoT.",
                learnMoreUrl = "https://robokalam.com/summercamp.html"
            ),
            Program(
                id = "3",
                imageResId = R.drawable.img_2,
                title = "Robo Ashaers",
                description = "Get comprehensive placement preparation and support. " +
                        "Access to exclusive job opportunities and internships. " +
                        "Mock interviews and resume building workshops.",
                learnMoreUrl = "https://robokalam.com/learn.html"
            )
        )

        binding.rvPrograms.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter = ProgramAdapter(programs) { url ->
                openUrl(url)
            }
            isNestedScrollingEnabled = false
        }
    }
    private fun setupVideo() {
        lifecycle.addObserver(binding.youtubePlayerView)

        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo("NxJRyGbdi-U", 0f)
                youTubePlayer.play()
            }
        })

        binding.youtubePlayerView.enableAutomaticInitialization = true
    }

    private fun setupClickListeners() {
        binding.apply {
            llFacebook.setOnClickListener { openUrl("https://www.facebook.com/robokalam") }
            llYoutube.setOnClickListener { openUrl("https://www.youtube.com/robokalam") }
            llInstagram.setOnClickListener { openUrl("https://www.instagram.com/robokalam") }
            llLinkedin.setOnClickListener { openUrl("https://www.linkedin.com/company/robokalam") }
            llEmail.setOnClickListener {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:support@robokalam.com")
                }
                startActivity(Intent.createChooser(intent, "Send email"))
            }
            llPhone.setOnClickListener {
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:+911234567890")))
            }
            llAddress.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Robokalam+Bangalore")))
            }
        }
    }

    private fun openUrl(url: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
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