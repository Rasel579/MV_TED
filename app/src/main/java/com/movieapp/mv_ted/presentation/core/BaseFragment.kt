package com.movieapp.mv_ted.presentation.core

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.movieapp.mv_ted.domain.AppState

abstract class BaseFragment<T>(@LayoutRes id: Int): Fragment(id) {
    abstract val viewBinding: T
    abstract val viewModel: BaseViewModel
    override fun onStart() {
        viewModel.getLiveData().observe(viewLifecycleOwner){renderData(it as AppState)}
        super.onStart()
    }
    abstract fun renderData(appState: AppState)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        setMenuVisibility(true)
    }
}