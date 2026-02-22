package com.example.moviesapimvvm.di


import com.example.moviesapimvvm.Data.MovieRepository
import com.example.moviesapimvvm.Data.MovieViewModel
import com.example.moviesapimvvm.Utills.RetrofitInstance
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule=module{

    single{
        MovieRepository()
    }
    single{
        MovieViewModel(get())
    }
}