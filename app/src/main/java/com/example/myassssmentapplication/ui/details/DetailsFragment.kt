package com.example.myassssmentapplication.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myassssmentapplication.R
import com.example.myassssmentapplication.databinding.FragmentDetailsBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()
    private val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayEntityDetails()
        binding.logoutButton.setOnClickListener {
            try {
                findNavController().navigate(R.id.loginFragment)
            } catch (e: Exception) {
                Log.e("DetailsFragment", "Navigation error on logout", e)
                Toast.makeText(context, "Navigation error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayEntityDetails() {
        val entityJson = args.entity
        val type = object : TypeToken<Map<String, Any?>>() {}.type
        val entity: Map<String, Any?> = gson.fromJson(entityJson, type)
        binding.fieldsContainer.removeAllViews()
        for ((key, value) in entity) {
            val textView = TextView(requireContext())
            textView.text = "$key: $value"
            textView.textSize = 16f
            binding.fieldsContainer.addView(textView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 