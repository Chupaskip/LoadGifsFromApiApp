package com.example.loadgifsfromapiapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.loadgifsfromapiapp.models.Data
import com.example.loadgifsfromapiapp.models.SearchResponse
import com.example.loadgifsfromapiapp.paging.GiphyPagingSource
import com.example.loadgifsfromapiapp.repository.GiphyRepository
import com.example.loadgifsfromapiapp.util.Constants.Companion.OFFSET_INC
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class GiphyViewModel(
    private val repository: GiphyRepository,
) : ViewModel() {

    fun getSearchGifs(searchQuery: String) =
        Pager(PagingConfig(pageSize = 1, enablePlaceholders = false)) {
            GiphyPagingSource(repository, searchQuery)
        }.liveData
}