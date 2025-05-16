package com.example.robokalam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.robokalam.R
import com.example.robokalam.data.model.Course
import com.example.robokalam.databinding.ItemCardBinding

class CourseAdapter(
    private val courses: List<Course>,
    private val onLearnMoreClick: (Course) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    inner class CourseViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course) {
            binding.apply {
                tvTitle.text = course.title
                tvDescription.text = course.description
                tvPrice.text = "₹%.2f".format(course.price)

                // Load image using Glide
                Glide.with(ivCourse)
                    .load(course.imageUrl)
                    .placeholder(R.drawable.course_placeholder)
                    .error(R.drawable.course_error)
                    .centerCrop()
                    .into(ivCourse)

                btnLearnMore.setOnClickListener {
                    onLearnMoreClick(course)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(courses[position])
    }

    override fun getItemCount() = courses.size
}