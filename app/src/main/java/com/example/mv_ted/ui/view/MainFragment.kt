package com.example.mv_ted.ui.view


import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mv_ted.R
import com.example.mv_ted.databinding.MainFragmentBinding
import com.example.mv_ted.models.data.model.TAG
import com.example.mv_ted.models.data.model.interfaces.OnItemViewClickListener
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MovieResultDTO
import com.example.mv_ted.models.data.model.showSnackBar
import com.example.mv_ted.view_model.AppState
import com.example.mv_ted.view_model.MainViewModel
import com.google.android.material.snackbar.Snackbar
@Suppress("NAME_SHADOWING")
class MainFragment : Fragment() {
    private lateinit var _binding: MainFragmentBinding
    private  val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private var listMoviesNow: MutableList<MovieResultDTO>? = null
    private var listMoviesUpcoming: MutableList<MovieResultDTO>? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel.getData().observe(viewLifecycleOwner, {
            renderData(it as AppState)
        })
        viewModel.loadMovieDataRetrofit()
        return _binding.root
    }

    private fun renderData(appState: AppState)= with(_binding) {
        when (appState) {
            is AppState.Success -> {
                listMoviesNow = appState.movieDataNow
                listMoviesUpcoming = appState.movieDataUpcoming
                loadingLayout.visibility = View.GONE
                initListView()
                recycleViewLayout.showSnackBar(getString(R.string.snack_bar_success_msg), Snackbar.LENGTH_SHORT)
            }
            is AppState.Loading -> {
                loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                loadingLayout.visibility = View.GONE
                recycleViewLayout.showSnackBar(getString(R.string.snack_bar_error_msg), Snackbar.LENGTH_LONG, "reload", viewModel)
            }
        }
    }

    private fun initListView()= with(_binding) {
        val recyclerViewNow = recycleViewLayout
        recyclerViewNow.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewNow.adapter = createAdapter(listMoviesNow)
        Log.d(TAG, listMoviesUpcoming.toString())
        val recyclerViewComingSoon = recycleViewLayoutComingSoon
        recyclerViewComingSoon.adapter = createAdapter(listMoviesUpcoming)
        recyclerViewComingSoon.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }


    private fun createAdapter(listMovies: MutableList<MovieResultDTO>?) = MovieCollectionAdapter(listMovies, object : OnItemViewClickListener{
            override fun onItemClickListener(movie: MovieResultDTO) {
                val manager = activity?.supportFragmentManager
                manager?.let { manager ->
                    val bundle = Bundle().apply{
                        putParcelable(DetailFragment.MOVIE_DATA, movie)
                    }
                    manager.beginTransaction()
                        .replace(R.id.content_main_frame, DetailFragment.newInstance(bundle))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        })
    companion object {
        fun newInstance() = MainFragment()
    }
}


