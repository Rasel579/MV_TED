package com.example.mv_ted.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.transition.Transition
import android.view.Gravity.apply
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat.apply
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mv_ted.R
import com.example.mv_ted.databinding.MainFragmentBinding
import com.example.mv_ted.models.data.model.Movie
import com.example.mv_ted.models.data.model.interfaces.OnItemViewClickListener
import com.example.mv_ted.view_model.AppState
import com.example.mv_ted.view_model.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.content_main.*

@Suppress("NAME_SHADOWING")
class MainFragment : Fragment() {

    private lateinit var _binding: MainFragmentBinding


    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var listMovies: MutableList<Movie>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getData().observe(viewLifecycleOwner, {
            renderData(it as AppState)
        })
        viewModel.getMovieData()
        return _binding.root
    }


    private fun renderData(appState: AppState)= with(_binding) {
        when (appState) {
            is AppState.Success -> {
                listMovies = appState.movieData
                loadingLayout?.visibility = View.GONE
                initListView()
                recycleViewLayout.showSnackBar("success", Snackbar.LENGTH_SHORT)
            }
            is AppState.Loading -> {
                loadingLayout?.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                loadingLayout?.visibility = View.GONE
                recycleViewLayout.showSnackBar("Error", Snackbar.LENGTH_LONG, "reload", viewModel)
            }
        }
    }

    private fun initListView()= with(_binding) {
        val recyclerViewNow: RecyclerView = _binding.recycleViewLayout
        val movieCollectionAdapter = MovieCollectionAdapter(listMovies, object : OnItemViewClickListener{
            override fun onItemClickListener(movie: Movie) {
                val manager = activity?.supportFragmentManager
                manager?.let { manager ->
                    val bundle = Bundle().apply{
                             putParcelable(DetailFragment.MOVIE_DATA, movie)
                    }
                    manager.beginTransaction()
                        .replace(R.id.content_main_frame, DetailFragment.newInstance(bundle))
                        .commitAllowingStateLoss()
                }
            }

        })
        recyclerViewNow.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewNow.adapter = movieCollectionAdapter
        val recyclerViewComingSoon: RecyclerView? = recycleViewLayoutComingSoon
        recyclerViewComingSoon?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewComingSoon?.adapter = movieCollectionAdapter

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding
    }

}


fun View.showSnackBar(message: String,  length : Int){
    Snackbar.make(this, message, length).show()
}

fun View.showSnackBar(message: String, length: Int, actionText: String, viewModel : MainViewModel){
    Snackbar.make(this, message, length)
        .setAction(actionText) {
            viewModel.getMovieData()
        }.show()
}