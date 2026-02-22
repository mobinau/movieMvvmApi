package com.example.moviesapimvvm.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesapimvvm.Data.ApiInterface
import com.example.moviesapimvvm.Dataclass.Data
import com.example.moviesapimvvm.Utills.RetrofitInstance

class MoviePagingSource(
    private val apiInterface: ApiInterface,
) : PagingSource<Int, Data>() {
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val page = params.key ?: 1
            val response = apiInterface.getMovies(page)

            LoadResult.Page(
                data = response.body()!!.data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.body()!!.data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

}