package com.example.loadgifsfromapiapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.loadgifsfromapiapp.models.Data
import com.example.loadgifsfromapiapp.repository.GiphyRepository
import com.example.loadgifsfromapiapp.util.Constants.Companion.LIMIT_OFFSET
import com.example.loadgifsfromapiapp.util.Constants.Companion.OFFSET_INC
import kotlinx.coroutines.delay
import retrofit2.HttpException


class GiphyPagingSource(
    private val repository: GiphyRepository,
    private val searchQuery: String,
) : PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val currentOffset = params.key ?: 0
            val response = repository.getSearchGifs(searchQuery, currentOffset)
            val gifs = mutableListOf<Data>()
            gifs.addAll(response.body()?.data ?: emptyList())

            return LoadResult.Page(
                data = gifs,
                prevKey = if (currentOffset == 0) null else currentOffset.minus(OFFSET_INC),
                nextKey = if (gifs.size < LIMIT_OFFSET) null else currentOffset.plus(OFFSET_INC)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}