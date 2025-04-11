package com.example.coincapapp.services

import com.example.coincapapp.models.AssetsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

class CoinCapApiService @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getAssets(): AssetsResponse {
        val response: HttpResponse = client.get(urlString = "https://rest.coincap.io/v3/assets?apiKey=e8489470057ceafffeb5afa036063c4d06d8bfed98f0355021e7e3f02e4c6607")
        return response.body()
    }
}