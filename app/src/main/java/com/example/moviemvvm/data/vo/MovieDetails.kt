package com.example.moviemvvm.data.vo


import com.google.gson.annotations.SerializedName

data class MovieDetails(
    val budget: Int,
    val id: Int,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val rating: Double
//https://api.themoviedb.org/3/movie/694919?api_key=f2a268e7a29bfb79af844e50c80a77cc
//https://api.themoviedb.org/3/movie/popular?api_key=f2a268e7a29bfb79af844e50c80a77cc
//https://image.tmdb.org/t/p/w342/6CoRTJTmijhBLJTUNoVSUNxZMEI.jpg
)