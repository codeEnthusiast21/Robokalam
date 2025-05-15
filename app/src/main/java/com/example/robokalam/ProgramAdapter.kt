package com.example.robokalam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.robokalam.databinding.ItemProgramBinding

class ProgramAdapter(
    private val programs: List<Program>,
    private val onLearnMoreClick: (String) -> Unit
) : RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>() {

    inner class ProgramViewHolder(private val binding: ItemProgramBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(program: Program) {
            binding.apply {
                ivProgram.setImageResource(program.imageResId)
                tvProgramTitle.text = program.title
                tvProgramDescription.text = program.description
                btnLearnMore.setOnClickListener {
                    onLearnMoreClick(program.learnMoreUrl)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val binding = ItemProgramBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProgramViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        holder.bind(programs[position])
    }

    override fun getItemCount() = programs.size
}