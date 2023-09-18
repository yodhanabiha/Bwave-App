package com.nabiha.myapplication.screens.search.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailSearchViewModelFactory (private val id: Int) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = DetailSearchViewModel(id) as T
}