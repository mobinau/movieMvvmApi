package com.example.moviesapimvvm.Data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviesapimvvm.Dataclass.Data
import com.example.moviesapimvvm.Dataclass.Details
import com.example.moviesapimvvm.Dataclass.MoviesData
import com.example.moviesapimvvm.Utills.RetrofitInstance
import com.example.moviesapimvvm.pagination.MoviePagingSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response


class MovieRepository(){
     fun getMoviesPaging(): Flow<PagingData<Data>>{
       return Pager(
           config = PagingConfig(pageSize = 10),
           pagingSourceFactory = { MoviePagingSource(apiInterface = RetrofitInstance.api)}
       ).flow
    }
    suspend fun getDetailMovie(id: Int): Response<Details>{
        return RetrofitInstance.api.getDetailsById(id = id)
    }
}