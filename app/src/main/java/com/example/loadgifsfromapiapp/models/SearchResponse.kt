package com.example.loadgifsfromapiapp.models


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    val `data`: MutableList<Data>,
    val meta: Meta,
    val pagination: Pagination
)
