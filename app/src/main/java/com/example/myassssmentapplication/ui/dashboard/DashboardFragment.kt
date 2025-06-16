package com.example.myassssmentapplication.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myassssmentapplication.R
import com.example.myassssmentapplication.databinding.FragmentDashboardBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()
    private val args: DashboardFragmentArgs by navArgs()
    private val gson = Gson()
    private val adapter = EntityAdapter { entity ->
        try {
            Log.d("DashboardFragment", "Navigating to details for entity: $entity")
            val entityJson = gson.toJson(entity)
            val action = DashboardFragmentDirections.actionDashboardToDetails(entityJson)
            findNavController().navigate(action)
        } catch (e: Exception) {
            Log.e("DashboardFragment", "Error navigating to details", e)
            Toast.makeText(context, "Error navigating to details: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            Log.d("DashboardFragment", "Initializing dashboard with keypass: ${args.keypass}")
            setupRecyclerView()
            observeDashboardState()
            viewModel.loadDashboard(args.keypass)
            binding.logoutButton.setOnClickListener {
                try {
                    findNavController().navigate( R.id.loginFragment)
                } catch (e: Exception) {
                    Log.e("DashboardFragment", "Navigation error on logout", e)
                    Toast.makeText(context, "Navigation error", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Log.e("DashboardFragment", "Error initializing dashboard", e)
            Toast.makeText(context, "Error initializing dashboard: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        try {
            binding.entitiesRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@DashboardFragment.adapter
            }
        } catch (e: Exception) {
            Log.e("DashboardFragment", "Error setting up RecyclerView", e)
            Toast.makeText(context, "Error setting up list: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeDashboardState() {
        viewModel.dashboardState.observe(viewLifecycleOwner) { state ->
            try {
                when (state) {
                    is DashboardState.Loading -> {
                        Log.d("DashboardFragment", "Loading dashboard data")
                        binding.progressBar.isVisible = true
                        binding.errorTextView.isVisible = false
                    }
                    is DashboardState.Success -> {
                        Log.d("DashboardFragment", "Dashboard data loaded successfully: ${state.entities.size} items")
                        binding.progressBar.isVisible = false
                        binding.errorTextView.isVisible = false
                        adapter.submitList(state.entities)
                    }
                    is DashboardState.Error -> {
                        Log.e("DashboardFragment", "Error loading dashboard: ${state.message}")
                        binding.progressBar.isVisible = false
                        binding.errorTextView.apply {
                            isVisible = true
                            text = state.message
                        }
                        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("DashboardFragment", "Error handling dashboard state", e)
                Toast.makeText(context, "Error updating UI: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 