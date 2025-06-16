package com.example.myassssmentapplication.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassssmentapplication.data.model.Entity
import com.example.myassssmentapplication.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _dashboardState = MutableLiveData<DashboardState>()
    val dashboardState: LiveData<DashboardState> = _dashboardState

    fun loadDashboard(keypass: String) {
        viewModelScope.launch {
            try {
                Log.d("DashboardViewModel", "Loading dashboard with keypass: $keypass")
                _dashboardState.value = DashboardState.Loading
                val result = repository.getDashboard(keypass)
                result.onSuccess { response ->
                    Log.d("DashboardViewModel", "API entities: ${response.entities}")
                    _dashboardState.value = DashboardState.Success(response.entities)
                }.onFailure { error ->
                    Log.e("DashboardViewModel", "Failed to load dashboard", error)
                    _dashboardState.value = DashboardState.Error(error.message ?: "Failed to load dashboard")
                }
            } catch (e: Exception) {
                Log.e("DashboardViewModel", "Error loading dashboard", e)
                _dashboardState.value = DashboardState.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }
}

sealed class DashboardState {
    object Loading : DashboardState()
    data class Success(val entities: List<Entity>) : DashboardState()
    data class Error(val message: String) : DashboardState()
} 