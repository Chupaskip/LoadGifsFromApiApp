package com.example.loadgifsfromapiapp.network

import com.example.loadgifsfromapiapp.models.SearchResponse
import com.example.loadgifsfromapiapp.util.Constants.Companion.API_KEY
import com.example.loadgifsfromapiapp.util.Constants.Companion.LIMIT_OFFSET
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {

    @GET("search")
    suspend fun getSearchGifs(
        @Query("q")
        searchQuery: String,
        @Query("offset")
        offset: Int = 0,
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("limit")
        limit:Int = LIMIT_OFFSET
    ): Response<SearchResponse>
}