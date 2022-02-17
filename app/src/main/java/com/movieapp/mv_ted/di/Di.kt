package com.movieapp.mv_ted.di

import com.movieapp.mv_ted.data.datasource.cloudsource.CloudSource
import com.movieapp.mv_ted.data.datasource.cloudsource.CloudSourceImpl
import com.movieapp.mv_ted.data.datasource.cloudsource.api.BackendRepo
import com.movieapp.mv_ted.data.datasource.localstore.DataStore
import com.movieapp.mv_ted.data.datasource.localstore.DataStoreImpl
import com.movieapp.mv_ted.data.datasource.localstore.commentdb.CommentDataBase
import com.movieapp.mv_ted.data.datasource.localstore.likesmoviesdb.LikesMoviesDatabase
import com.movieapp.mv_ted.data.repository.RepositoryImpl
import com.movieapp.mv_ted.domain.repository.Repository
import com.movieapp.mv_ted.presentation.contactsreader.ContactProviderFragment
import com.movieapp.mv_ted.presentation.detail.DetailFragment
import com.movieapp.mv_ted.presentation.detail.DetailViewModel
import com.movieapp.mv_ted.presentation.favorite.LikedMovieViewModel
import com.movieapp.mv_ted.presentation.favorite.LikedMoviesFragment
import com.movieapp.mv_ted.presentation.main.MainFragment
import com.movieapp.mv_ted.presentation.main.MainViewModel
import com.movieapp.mv_ted.presentation.maps.MapsFragment
import com.movieapp.mv_ted.presentation.maps.MapsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Di {
    fun sourceModules() = module {
        single<DataStore> {
            DataStoreImpl(
                commentDb = CommentDataBase.create(get()).commentDao(),
                likesDb = LikesMoviesDatabase.create(get()).likesMoviesDao()
            )
        }
        single<CloudSource> {
            CloudSourceImpl(BackendRepo.api)
        }
    }

    fun repositoryModules() = module {
        single<Repository> {
            RepositoryImpl(get(), get())
        }
    }

    fun viewModelsModules() = module {
        scope<MainFragment> {
            viewModel { MainViewModel(get()) }
        }
        scope<DetailFragment> {
            viewModel { DetailViewModel(get()) }
        }
        scope<LikedMoviesFragment> {
            viewModel { LikedMovieViewModel(get()) }
        }
        scope<MapsFragment> {
            viewModel { MapsViewModel(get()) }
        }
        scope<ContactProviderFragment> {
            viewModel { MainViewModel(get()) }
        }
    }
}