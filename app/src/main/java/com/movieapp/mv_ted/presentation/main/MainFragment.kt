package com.movieapp.mv_ted.presentation.main


import android.content.Context
import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.movieapp.mv_ted.R
import com.movieapp.mv_ted.databinding.MainFragmentBinding
import com.movieapp.mv_ted.domain.AppState
import com.movieapp.mv_ted.domain.models.response.movie.MovieResponse
import com.movieapp.mv_ted.utils.showSnackBar
import com.movieapp.mv_ted.presentation.core.BaseFragment
import com.movieapp.mv_ted.presentation.detail.DetailFragment
import com.movieapp.mv_ted.presentation.main.adapter.MovieCollectionAdapter
import com.movieapp.mv_ted.presentation.main.listeners.OnItemViewClickListener
import org.koin.android.ext.android.getKoin
import org.koin.core.scope.Scope

class MainFragment : BaseFragment<MainFragmentBinding>(R.layout.main_fragment) {
    override val scope: Scope = getKoin().createScope<MainFragment>()
    override val viewBinding: MainFragmentBinding by viewBinding()
    override val viewModel: MainViewModel = scope.get()

    private var listMoviesNow: MutableList<MovieResponse>? = mutableListOf()
    private var isAdult: Boolean = false
    private val adapter: MovieCollectionAdapter by lazy {
        createAdapter(listMoviesNow)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDataSet()
    }


    override fun onStart() {
        viewModel.getData()
        super.onStart()
    }

    private fun initDataSet() {
        activity?.let { preference ->
            isAdult = preference.getSharedPreferences(
                PREFERENCE_NAME,
                Context.MODE_PRIVATE
            ).getBoolean(IS_ADULT_CONTENT_KEY, true)
        }
    }

    override fun renderData(appState: AppState) = with(viewBinding) {
        when (appState) {
            is AppState.Success -> {
                appState.movieDataNow?.let { listMoviesNow?.addAll(it) }
                loadingLayout.visibility = View.GONE
                initListView()
                recycleViewLayout.showSnackBar(
                    getString(R.string.snack_bar_success_msg),
                    Snackbar.LENGTH_SHORT
                )
            }
            is AppState.SuccessPagination -> {
                adapter.addPageData(appState.movieDataNow)
            }
            is AppState.Loading -> {
                loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                loadingLayout.visibility = View.GONE
                recycleViewLayout.showSnackBar(
                    getString(R.string.snack_bar_error_msg),
                    Snackbar.LENGTH_LONG,
                    getString(R.string.Action_Error_Text_SnackBar),
                    viewModel
                )
            }
        }
    }

    private fun initListView() = with(viewBinding) {
        val recyclerViewNow = recycleViewLayout
        recyclerViewNow.adapter = adapter
    }

    private fun createAdapter(listMovies: MutableList<MovieResponse>?) =
        MovieCollectionAdapter(
            listMovies = listMovies,
            onItemViewClickListener = onItemClickListener()
        ) { page: Int -> viewModel.getPageData(page) }

    private fun onItemClickListener(): OnItemViewClickListener =
        object : OnItemViewClickListener {
            override fun onItemClickListener(movie: MovieResponse) {
                val manager = activity?.supportFragmentManager
                manager?.let { manager ->
                    val bundle = Bundle().apply {
                        putInt(DetailFragment.MOVIE_ID, movie.id)
                    }
                    manager.beginTransaction()
                        .replace(R.id.content_main_frame, DetailFragment.newInstance(bundle))
                        .addToBackStack("")
                        .commit()
                }
            }
        }


    companion object {
        const val PREFERENCE_NAME = "Change Adult Content"
        const val IS_ADULT_CONTENT_KEY = "isAdultContentKey"
        fun newInstance() = MainFragment()
    }
}


