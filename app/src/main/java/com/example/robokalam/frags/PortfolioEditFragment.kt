package com.example.robokalam.frags

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.robokalam.R
import com.example.robokalam.data.AppDatabase
import com.example.robokalam.data.PortfolioDao
import com.example.robokalam.data.PortfolioEntity
import com.example.robokalam.databinding.FragmentPortfolioEditBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class PortfolioEditFragment : Fragment() {
    private var _binding: FragmentPortfolioEditBinding? = null
    private val binding get() = _binding!!
    private val portfolioDao: PortfolioDao by lazy {
        AppDatabase.getInstance(requireContext()).portfolioDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPortfolioEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPortfolio()
        setupSaveButton()
    }

    private fun loadPortfolio() {
        val userId = requireActivity().getSharedPreferences("login_pref", Context.MODE_PRIVATE)
            .getString("user_email", "") ?: return

        lifecycleScope.launch {
            portfolioDao.getPortfolio(userId).collect { portfolio ->
                portfolio?.let {
                    binding.etEducation.setText(it.education)
                    binding.etSkills.setText(it.skills)
                    binding.etExperience.setText(it.experience)
                }
            }
        }
    }

    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            savePortfolio()
        }
    }

    private fun savePortfolio() {
        val userId = requireActivity().getSharedPreferences("login_pref", Context.MODE_PRIVATE)
            .getString("user_email", "") ?: return

        val portfolio = PortfolioEntity(
            userId = userId,
            name = binding.etName.text.toString(),
            email = binding.etEmail.text.toString(),
            phone = binding.etPhone.text.toString(),
            education = binding.etEducation.text.toString(),
            skills = binding.etSkills.text.toString(),
            experience = binding.etExperience.text.toString()
        )

        lifecycleScope.launch {
            portfolioDao.insertPortfolio(portfolio)
            Snackbar.make(binding.root, "Portfolio saved", Snackbar.LENGTH_SHORT).show()
        }
    }
}