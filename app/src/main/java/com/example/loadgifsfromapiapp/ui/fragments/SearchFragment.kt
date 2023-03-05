package com.example.loadgifsfromapiapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loadgifsfromapiapp.databinding.FragmentSearchBinding
import com.example.loadgifsfromapiapp.ui.adapters.GifAdapter
import com.example.loadgifsfromapiapp.ui.adapters.LoadMoreAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val viewBinding: FragmentSearchBinding
        get() = FragmentSearchBinding.inflate(layoutInflater)

    private lateinit var gifAdapter: GifAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerViewSearchGifs()
        setSearch()
        handleStatesOfData()
    }

    private fun handleStatesOfData() {
        binding.retryButton.setOnClickListener{gifAdapter.retry()}
        lifecycleScope.launch {
            gifAdapter.loadStateFlow.collect() { loadState ->
                val isListEmpty =
                    loadState.refresh is LoadState.NotLoading && gifAdapter.itemCount == 0
                binding.apply {
                    emptyList.isVisible = isListEmpty
                    rvSearchMeals.isVisible = !isListEmpty
                    progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                    retryButton.isVisible = loadState.source.refresh is LoadState.Error
                }
            }
        }
    }

    private fun setRecyclerViewSearchGifs() {
        gifAdapter = GifAdapter()
        binding.rvSearchMeals.apply {
            adapter = gifAdapter.withLoadStateFooter(
                footer = LoadMoreAdapter {
                    gifAdapter.retry()
                }
            )
        }
        gifAdapter.onItemClick = { data ->
            val action =
                SearchFragmentDirections.actionSearchFragmentToGifDetailsFragment(data)
            findNavController().navigate(action)
        }
    }

    private fun setSearch() {
        binding.etSearch.requestFocus()
        var job: Job? = null
        binding.etSearch.addTextChangedListener { searchQuery ->
            job?.cancel()
            job = lifecycleScope.launch {
                delay(500)
                viewModel.getSearchGifs(searchQuery.toString()).observe(viewLifecycleOwner) {
                    lifecycleScope.launch {
                        gifAdapter.submitData( it)
                    }
                }
            }
        }
    }
}