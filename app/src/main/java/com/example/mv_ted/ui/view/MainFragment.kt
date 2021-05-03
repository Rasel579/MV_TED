package com.example.mv_ted.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mv_ted.databinding.MainFragmentBinding
import com.example.mv_ted.models.data.model.RepositoryImpl
import com.example.mv_ted.view_model.AppState
import com.example.mv_ted.view_model.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private var _binding : MainFragmentBinding? = null



    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var listMovies: RepositoryImpl

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getData().observe(viewLifecycleOwner, Observer {
            renderData(it as AppState)
        })
        viewModel.getMovieData()
        return _binding!!.root
    }

    override  fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       // listMovies = RepositoryImpl()
        // listMovies.init()
    }

    private fun renderData(appState: AppState) {
       when(appState){
           is AppState.Success -> {
               listMovies = appState.movieData
               listMovies.init()
               _binding?.loadingLayout?.visibility = View.GONE
               initListView(view)
               Snackbar.make(_binding?.recycleViewLayout!!, "Success", Snackbar.LENGTH_LONG).show()
           }
           is AppState.Loading ->{
               _binding?.loadingLayout?.visibility = View.VISIBLE
           }
           is AppState.Error -> {
               _binding?.loadingLayout?.visibility = View.GONE
               Snackbar.make(_binding?.recycleViewLayout!!, "Error", Snackbar.LENGTH_LONG).setAction("Reload"){
                   viewModel.getMovieData()
               }.show()
           }
       }
    }

    private fun initListView(view: View?) {
        val recyclerViewNow : RecyclerView = _binding?.recycleViewLayout!!
        val movieCollectionAdapter = MovieCollectionAdapter(listMovies)
        recyclerViewNow.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewNow.adapter = movieCollectionAdapter
        val recyclerViewComingSoon: RecyclerView = _binding?.recycleViewLayoutComingSoon!!
        recyclerViewComingSoon.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewComingSoon.adapter = movieCollectionAdapter

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}