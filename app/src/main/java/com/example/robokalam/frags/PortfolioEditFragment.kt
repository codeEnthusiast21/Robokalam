package com.example.robokalam.frags

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.robokalam.data.AppDatabase
import com.example.robokalam.data.Portfolio
import com.example.robokalam.data.PortfolioDao
import com.example.robokalam.databinding.FragmentPortfolioEditBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PortfolioEditFragment : Fragment() {
    private var _binding: FragmentPortfolioEditBinding? = null
    private val binding get() = _binding!!
    private var isEditing = false
    private lateinit var portfolioDao: PortfolioDao
    private var currentPortfolio: Portfolio? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPortfolioEditBinding.inflate(inflater, container, false)
        portfolioDao = AppDatabase.getInstance(requireContext()).portfolioDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSavedData()
        setupButtons()
        toggleEditMode(false)
    }

    private fun loadSavedData() {

        val loginPref = requireActivity().getSharedPreferences("login_pref", Context.MODE_PRIVATE)
        val userEmail = loginPref.getString("user_email", "") ?: ""


        lifecycleScope.launch {

            currentPortfolio = withContext(Dispatchers.IO) {
                portfolioDao.getPortfolioByEmail(userEmail)
            } ?: Portfolio(email = userEmail)

            binding.apply {

                etName.setText(loginPref.getString("user_name", ""))
                etEmail.setText(userEmail)

                currentPortfolio?.let { portfolio ->
                    etPhone.setText(portfolio.phone)
                    etSkills.setText(portfolio.skills)
                    etExperience.setText(portfolio.experience)
                    etEducation.setText(portfolio.education)
                    etInterests.setText(portfolio.interests)
                }
            }
        }
    }

    private fun setupButtons() {
        binding.apply {
            btnEdit.setOnClickListener {
                isEditing = true
                toggleEditMode(true)
            }

            btnSave.setOnClickListener {
                if (validateInputs()) {
                    isEditing = false
                    saveData()
                    toggleEditMode(false)
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        binding.apply {
            val name = etName.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val skills = etSkills.text.toString().trim()
            val experience = etExperience.text.toString().trim()
            val education = etEducation.text.toString().trim()

            if (name.isEmpty()) {
                etName.error = "Name required"
                return false
            }

            if (phone.isNotEmpty() && phone.length < 10) {
                etPhone.error = "Enter valid phone number"
                return false
            }

            if (skills.isEmpty()) {
                etSkills.error = "Skills required"
                return false
            }

            if (experience.isEmpty()) {
                etExperience.error = "Experience required"
                return false
            }

            if (education.isEmpty()) {
                etEducation.error = "Education required"
                return false
            }

            return true
        }
    }

    private fun toggleEditMode(editing: Boolean) {
        binding.apply {
            val editableViews = mapOf(
                etName to editing,
                etEmail to false,
                etPhone to editing,
                etSkills to editing,
                etExperience to editing,
                etEducation to editing,
                etInterests to editing
            )

            editableViews.forEach { (view, enabled) ->
                view.isEnabled = enabled
            }

            btnSave.visibility = if (editing) View.VISIBLE else View.GONE
            btnEdit.visibility = if (editing) View.GONE else View.VISIBLE
        }
    }

    private fun saveData() {
        lifecycleScope.launch {
            val loginPref = requireActivity().getSharedPreferences("login_pref", Context.MODE_PRIVATE)
            val name = binding.etName.text.toString().trim()

            // Update name in shared preferences
            loginPref.edit().putString("user_name", name).apply()

            // Create updated portfolio using binding data
            val updatedPortfolio = (currentPortfolio ?: Portfolio(email = binding.etEmail.text.toString().trim())).copy(
                name = name,
                phone = binding.etPhone.text.toString().trim(),
                skills = binding.etSkills.text.toString().trim(),
                experience = binding.etExperience.text.toString().trim(),
                education = binding.etEducation.text.toString().trim(),
                interests = binding.etInterests.text.toString().trim()
            )

            withContext(Dispatchers.IO) {
                portfolioDao.upsert(updatedPortfolio)
            }

            currentPortfolio = updatedPortfolio
            showToast("Portfolio updated successfully")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}