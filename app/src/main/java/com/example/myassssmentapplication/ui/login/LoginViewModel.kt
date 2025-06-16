package com.example.myassssmentapplication.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassssmentapplication.data.model.LoginResponse
import com.example.myassssmentapplication.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                Log.d("LoginViewModel", "Attempting login for user: $username")
                val result = repository.login(username, password)
                _loginResult.value = result
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Login error", e)
                _loginResult.value = Result.failure(e)
            }
        }
    }
}

sealed class LoginState {
    object Loading : LoginState()
    data class Success(val keypass: String) : LoginState()
    data class Error(val message: String) : LoginState()
} 