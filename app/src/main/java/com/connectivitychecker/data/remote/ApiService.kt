package com.connectivitychecker.data.remote

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.ExperimentalCoroutinesApi

class ApiService(private val context: Context) {
    data class CatFactResponse(
        @SerializedName("fact")
        val fact: String,
        @SerializedName("length")
        val length: Int
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun fetchCatFact(): CatFactResponse {
        return kotlinx.coroutines.suspendCancellableCoroutine { continuation ->
            fetchData { response ->
                continuation.resume(response, onCancellation = null)
            }
        }
    }

    fun fetchData(callback: (CatFactResponse) -> Unit) {
        val url = "https://catfact.ninja/fact"
        val requestQueue: RequestQueue = Volley.newRequestQueue(context)
        var apiResponse : CatFactResponse
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    apiResponse = Gson().fromJson(response.toString(), CatFactResponse::class.java)
                    Log.d("FetchData", "Fact: ${apiResponse.fact}")
                    Log.d("FetchData", "Length: ${apiResponse.length}")
                    callback(apiResponse)
                } catch (e: Exception) {
                    Log.e("FetchData", "Error parsing JSON response: ${e.localizedMessage}")
                    callback(CatFactResponse(fact = "Error parsing response", length = 0))
                }
            },
            { error ->
                error.networkResponse?.let {
                    val statusCode = it.statusCode
                    val errorMsg = String(it.data, Charsets.UTF_8)
                    Log.e("FetchData", "Error: $statusCode, $errorMsg")
                    callback(CatFactResponse(fact = "Error: $errorMsg", length = 0))
                } ?: run {
                    Log.e("FetchData", "Network error: ${error.message}")
                    callback(CatFactResponse(fact = "Network error", length = 0))
                }
            }
        )
        requestQueue.add(jsonObjectRequest)
    }

}