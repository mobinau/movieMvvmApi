package com.example.moviesapimvvm.Data

import com.example.moviesapimvvm.Dataclass.MoviesData
import com.example.moviesapimvvm.Dataclass.Details
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    //https://moviesapi.ir/api/v1/movies?/page=1

    @GET("movies?")
    suspend fun getMovies(
        @Query("page")page: Int
    ): Response<MoviesData>

    @GET("movies/{movie_id}")
    suspend fun getDetailsById(
        @Path("movie_id")id:Int
    ): Response<Details>
}