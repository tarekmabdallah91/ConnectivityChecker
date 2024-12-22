package com.connectivitychecker.app

import android.app.Application
import com.connectivitychecker.core.network.NetworkManager
import com.connectivitychecker.data.remote.ApiService
import com.presentation.viewmodel.ViewModelFactory

class NetworkCheckerApp : Application() {
    private lateinit var networkQualityManager: NetworkManager
    private lateinit var apiService: ApiService
    internal lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate() {
        super.onCreate()
        instance = this
        initializeDependencies()
    }

    private fun initializeDependencies() {
        networkQualityManager = NetworkManager(applicationContext)
        apiService = ApiService(applicationContext)
        viewModelFactory = ViewModelFactory(networkQualityManager, apiService)
    }

    companion object {
        private lateinit var instance: NetworkCheckerApp

        fun getInstance(): NetworkCheckerApp {
            return instance
        }
    }
}