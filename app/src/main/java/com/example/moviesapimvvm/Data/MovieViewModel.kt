package com.example.moviesapimvvm.Data


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapimvvm.Dataclass.Data
import com.example.moviesapimvvm.Dataclass.Details
import kotlinx.coroutines.launch
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MovieViewModel(
    private val repository: MovieRepository
) : ViewModel() {

  //  var state by mutableStateOf(ScreenState())
    private val _state = MutableStateFlow(ScreenState())
    val state = _state.asStateFlow()

    var id by mutableIntStateOf(0)

    val response = repository.getMoviesPaging().cachedIn(viewModelScope)


    fun getMovieDetail(id: Int) {
        viewModelScope.launch {

            println("ID SENT = $id")

            val response = repository.getDetailMovie(id)

            println("CODE = ${response.code()}")
            println("BODY = ${response.body()}")

            if (response.isSuccessful && response.body() != null) {
                _state.value = _state.value.copy(
                    details = response.body()!!
                )
            }
        }
    }
}

data class ScreenState(
    val movies: List<Data> = emptyList(),
    val page: Int = 1,
    val details: Details= Details()
)
