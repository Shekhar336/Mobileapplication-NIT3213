package com.example.myassssmentapplication.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myassssmentapplication.R
import com.example.myassssmentapplication.data.model.LoginResponse
import com.example.myassssmentapplication.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (username.isBlank() || password.isBlank()) {
                Toast.makeText(context, "Please enter both username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            binding.loginButton.isEnabled = false
            binding.progressBar.visibility = View.VISIBLE

            viewModel.login(username, password)
        }

        viewModel.loginResult.observe(viewLifecycleOwner) { result: Result<LoginResponse> ->
            binding.loginButton.isEnabled = true
            binding.progressBar.visibility = View.GONE

            result.onSuccess { response: LoginResponse ->
                Log.d("LoginFragment", "Login successful: ${response.keypass}")
                val action = LoginFragmentDirections.actionLoginToDashboard(response.keypass)
                findNavController().navigate(action)
            }.onFailure { error: Throwable ->
                Log.e("LoginFragment", "Login failed", error)
                Toast.makeText(
                    context,
                    "Login failed: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 