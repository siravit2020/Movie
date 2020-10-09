package com.oxcoding.moviemvvm.data.api


import android.util.Log
import com.example.moviemvvm.data.vo.MovieDetails
import com.example.moviemvvm.data.vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {
    //https://api.themoviedb.org/3/movie/694919?api_key=f2a268e7a29bfb79af844e50c80a77cc
    //https://api.themoviedb.org/3/movie/popular?api_key=f2a268e7a29bfb79af844e50c80a77cc
    //https://image.tmdb.org/t/p/w342/6CoRTJTmijhBLJTUNoVSUNxZMEI.jpg

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>

}

