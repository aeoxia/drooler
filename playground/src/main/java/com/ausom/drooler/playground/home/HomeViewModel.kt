package com.ausom.drooler.playground.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ausom.drooler.playground.data.FoodRepository
import com.ausom.drooler.playground.di.PostExecutionThread
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postExecutionThread: PostExecutionThread,
    private val repository: FoodRepository
): ViewModel() {

    //TODO 1. add state

    fun loadDroolCount() {
        viewModelScope.launch(postExecutionThread.io) {
            //TODO 2.2 load drool count
        }
    }

    fun loadFoodList() {
        viewModelScope.launch(postExecutionThread.io) {
            //TODO 2.1 load food list
        }
    }
}