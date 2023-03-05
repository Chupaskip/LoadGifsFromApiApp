package com.example.loadgifsfromapiapp.repository

import com.example.loadgifsfromapiapp.network.RetrofitInstance

class GiphyRepository {
    suspend fun getSearchGifs(searchQuery: String, offset: Int) =
        RetrofitInstance.api.getSearchGifs(searchQuery, offset)
}