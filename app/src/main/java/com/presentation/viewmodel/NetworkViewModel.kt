package com.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectivitychecker.core.network.NetworkManager
import com.connectivitychecker.data.remote.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NetworkViewModel(
    private val networkQualityManager: NetworkManager,
    private val apiService: ApiService
) : ViewModel() {

    sealed class NetworkState {
        object Loading : NetworkState()
        data class Success(
            val data: ApiService.CatFactResponse,
            val isValidNetworkType: Boolean
        ) : NetworkState()
        data class Error(val message: String) : NetworkState()
    }


    private val _networkState = MutableStateFlow<NetworkState>(NetworkState.Loading)
    val networkState: StateFlow<NetworkState> = _networkState

    fun fetchApiData() {
        viewModelScope.launch {
            try {
                if (networkQualityManager.isConnectedUsingGnirehtet() && networkQualityManager.isGnirehtetInterfaceActive()) {
                    val data = apiService.fetchCatFact()
                    _networkState.value = NetworkState.Success(data, true)
                } else {
                    _networkState.value = NetworkState.Error("No valid connection")
                }
            } catch (e: Exception) {
                _networkState.value = NetworkState.Error("Network Check Failed: ${e.message}")
            }
        }
    }

}

