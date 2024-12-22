package com.connectivitychecker

import android.os.Bundle

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.connectivitychecker.app.NetworkCheckerApp
import com.connectivitychecker.data.remote.ApiService

import com.presentation.viewmodel.NetworkViewModel

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: NetworkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            NetworkCheckerApp.getInstance().viewModelFactory
        )[NetworkViewModel::class.java]
        requestNetworkPermissions()
        setContent {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val networkState by viewModel.networkState.collectAsState()
                    NetworkDetailsScreen(
                        networkState = networkState,
                        onRetry = { viewModel.fetchApiData() }
                    )
                }
        }
        viewModel.fetchApiData()
    }

    private fun requestNetworkPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE
        )
        ActivityCompat.requestPermissions(this, permissions, 1001)
    }
}

@Composable
fun NetworkDetailsScreen(
    networkState: NetworkViewModel.NetworkState,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (networkState) {
            is NetworkViewModel.NetworkState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            is NetworkViewModel.NetworkState.Success -> {
                InfoCard(networkState)
                Spacer(modifier = Modifier.height(16.dp))
                DetailsCard(networkState.data)
            }

            is NetworkViewModel.NetworkState.Error -> {
                ErrorView(
                    message = networkState.message,
                    onRetry = onRetry
                )
            }
        }

    }
}

@Composable
private fun InfoCard(state: NetworkViewModel.NetworkState.Success) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Network Status",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("VPN Type? : ${state.isValidNetworkType}")
        }
    }
}

@Composable
private fun DetailsCard(data: ApiService.CatFactResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Fact ${data.fact}",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("length: ${data.length}")
        }
    }
}

@Composable
private fun ErrorView(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}



