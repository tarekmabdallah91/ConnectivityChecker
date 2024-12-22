package com.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.connectivitychecker.core.network.NetworkManager
import com.connectivitychecker.data.remote.ApiService

class ViewModelFactory(
    private val networkQualityManager: NetworkManager,
    private val apiService: ApiService
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NetworkViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NetworkViewModel(networkQualityManager, apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}