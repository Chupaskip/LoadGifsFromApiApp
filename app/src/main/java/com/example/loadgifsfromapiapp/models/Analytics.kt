package com.example.loadgifsfromapiapp.models


import com.google.gson.annotations.SerializedName

data class Analytics(
    val onclick: Onclick,
    val onload: Onload,
    val onsent: Onsent
)