package com.example.moviemvvm.ui.single_movie_details

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.moviemvvm.R
import com.example.moviemvvm.data.repository.NetworkState
import com.example.moviemvvm.data.vo.MovieDetails
import com.oxcoding.moviemvvm.data.api.POSTER_BASE_URL
import com.oxcoding.moviemvvm.data.api.TheMovieDBClient
import com.oxcoding.moviemvvm.data.api.TheMovieDBInterface
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*


class SingleMovie : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val movieId: Int = intent.getIntExtra("id", 1)

        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()

        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)
        viewModel.movieDetails.observe(this, Observer {
            Log.d("httpp", "คืน4")
            bindUI(it)
        })
        viewModel.networkState.observe(this, Observer {
            Log.d("httpp", it.msg)
            if (it == NetworkState.LOADING) {
                progress_bar.visibility = View.VISIBLE
                linearLayout.visibility = View.GONE
            } else {
                val animFadeOut: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
                progress_bar.visibility = View.GONE
                linearLayout.visibility = View.VISIBLE
                linearLayout.startAnimation(animFadeOut)
                val handler = Handler()
                handler.postDelayed(Runnable { // Do something after 5s = 5000ms
                    info.visibility = View.VISIBLE
                    info.startAnimation(animFadeOut)
                }, 30)
            }
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })

    }

    fun bindUI(it: MovieDetails){
        movie_title.text = it.title
        movie_tagline.text = it.tagline
        if(it.tagline.isEmpty()) movie_tagline.visibility = View.GONE
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.rating.toString()
        movie_runtime.text = it.runtime.toString() + " minutes"
        movie_overview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster);

    }


    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return  ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRepository, movieId) as T
            }
        })[SingleMovieViewModel::class.java]

    }
}
