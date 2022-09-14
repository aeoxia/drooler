package com.ausom.drooler.playground.fooddetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ausom.drooler.playground.data.FoodRepository
import com.ausom.drooler.playground.di.PostExecutionThread
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val postExecutionThread: PostExecutionThread,
    private val repository: FoodRepository
): ViewModel() {

    //TODO 1 add state


    //TODO 2 add state



    fun loadFoodDetail(id: String) {
        viewModelScope.launch(postExecutionThread.io) {
            //TODO 3.1 load food detail
        }
    }

    fun drool() {
        viewModelScope.launch(postExecutionThread.io) {
            //TODO 3.3 do drool action
        }
    }

    fun nope() {
        viewModelScope.launch(postExecutionThread.io) {
            //TODO 3.4 do nope action
        }
    }

    private suspend fun nextFood() {
        //TODO 3.2 do drool action
    }
}