package com.movieapp.mv_ted.presentation.detail


import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import by.kirich1409.viewbindingdelegate.viewBinding
import com.movieapp.mv_ted.R
import com.movieapp.mv_ted.databinding.FragmentDetailBinding
import com.movieapp.mv_ted.domain.AppState
import com.movieapp.mv_ted.domain.models.Comment
import com.movieapp.mv_ted.domain.models.Movie
import com.movieapp.mv_ted.domain.models.response.credits.ActorsResponse
import com.movieapp.mv_ted.domain.models.response.movie.Genre
import com.movieapp.mv_ted.domain.models.response.movie.MovieResponse
import com.movieapp.mv_ted.utils.imageUri
import com.movieapp.mv_ted.presentation.core.BaseFragment
import com.movieapp.mv_ted.presentation.detail.adapter.CastAdapter
import com.movieapp.mv_ted.presentation.detail.adapter.DetailFragmentAdapter
import com.movieapp.mv_ted.presentation.maps.MapsFragment
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.getKoin
import org.koin.core.scope.Scope

class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {
    override val scope: Scope = getKoin().createScope<DetailFragment>()
    override val viewBinding: FragmentDetailBinding by viewBinding()
    private val movieId: Int? by lazy {
        arguments?.getInt(MOVIE_ID)
    }
    private var movie: MovieResponse? = null
    private var comments: MutableList<Comment>? = null
    private var likeImg = R.drawable.ic_baseline_tag_faces_24
    private val adapter: DetailFragmentAdapter by lazy { DetailFragmentAdapter() }
    override val viewModel: DetailViewModel = scope.get()

    override fun onStart() {
        viewModel.getMovieById(movieId.toString())
        super.onStart()
    }

    private fun initView(movie: MovieResponse?) = with(viewBinding) {
        movieOverview.text = movie?.overview
        movieIdName.text = movie?.originalTitle
        movieIdDate.text = movie?.releaseDate
        movieTime.text = "${movie?.runtime?.div(60)}h ${movie?.runtime?.rem(60)}m"
        movieGenres.text = genresMap(movie?.genres)
        ratioTextDetail.text = movie?.voteAverage.toString()
        progressBar.progress = (movie?.voteAverage?.times(10))?.toInt() ?: 0
        initImages(movie)
        viewModel.getCredits(movieId.toString())
        initLikesMove(movie)
    }

    private fun genresMap(genres: List<Genre>?): CharSequence? =
        genres?.joinToString(", ") {
            it.name
        }

    private fun initLikesMove(movie: MovieResponse?) {
        viewBinding.like.setOnClickListener {
            likeImg = if (likeImg == R.drawable.ic_baseline_tag_faces_24) {
                R.drawable.ic_baseline_tag_faces_like_on
            } else {
                R.drawable.ic_baseline_tag_faces_24
            }
            viewBinding.like.setImageResource(likeImg)
            viewModel.saveLikesMovie(
                Movie(
                    title = movie?.originalTitle,
                    date = movie?.releaseDate,
                    image = movie?.posterPath,
                    description = movie?.overview
                )
            )
        }
        movie?.id.let {
            if (it != null) {
                viewModel.getAllCommentsByMovie(movieId.toString())
            }
        }
    }

    private fun initImages(movie: MovieResponse?) {
        Picasso.get()
            .load(imageUri + movie?.backdropPath)
            .into(viewBinding.backgroundImage)
        Picasso.get()
            .load(imageUri + movie?.posterPath)
            .fit()
            .into(viewBinding.movieImage)
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessDetailsFrg -> {
                comments = appState.commentsList.toMutableList()
                if (comments != null) {
                    adapter.setData(appState.commentsList)
                    adapter.notifyDataSetChanged()
                }
            }
            is AppState.SuccessMovieId -> {
                movie = appState.movie
                initView(movie)
            }
            is AppState.SuccessCredits -> {
                initCastAdapter(appState.credits)
            }
        }
    }

    private fun initCastAdapter(credits: ActorsResponse) {
        val adapter: CastAdapter = CastAdapter(credits)
        viewBinding.actors.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.to_google_map -> {
                val manger = activity?.supportFragmentManager
                manger?.let {
                    val bundle = Bundle().apply {
                        movie?.id?.let { it1 -> putInt(MapsFragment.MOVIE_ID, it1) }
                    }
                    manger.beginTransaction()
                        .replace(R.id.content_main_frame, MapsFragment.newInstance(bundle))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val MOVIE_ID = "movie_id"
        fun newInstance(bundle: Bundle): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}