package com.example.robokalam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.robokalam.databinding.ItemCourseBinding

class CourseAdapter(
    private val courses: List<Course>,
    private val onLearnMoreClick: (String) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    class CourseViewHolder(val binding: ItemCourseBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]
        holder.binding.apply {
            // Use Glide or similar library to load image
            Glide.with(ivCourse)
                .load(course.imageUrl)
                .centerCrop()
                .into(ivCourse)

            tvTitle.text = course.title
            tvDescription.text = course.description
            btnLearnMore.setOnClickListener {
                onLearnMoreClick(course.learnMoreUrl)
            }
        }
    }

    override fun getItemCount() = courses.size
}