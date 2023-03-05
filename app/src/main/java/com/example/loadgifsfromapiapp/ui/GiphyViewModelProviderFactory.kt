package com.example.loadgifsfromapiapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.loadgifsfromapiapp.repository.GiphyRepository

@Suppress("UNCHECKED_CAST")
class GiphyViewModelProviderFactory(
    private val repository: GiphyRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GiphyViewModel(repository) as T
    }
}