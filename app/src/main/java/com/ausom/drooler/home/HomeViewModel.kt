package com.ausom.drooler.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ausom.drooler.data.FoodRepository
import com.ausom.drooler.di.PostExecutionThread
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
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState>
        get() = _state

    fun loadDroolCount() {
        viewModelScope.launch(postExecutionThread.io) {
            val droolCount = repository.getDroolCount()
            _state.value = _state.value.copy(
                droolCount = droolCount.toString()
            )
        }
    }

    fun loadFoodList() {
        viewModelScope.launch(postExecutionThread.io) {
            _state.value = _state.value.copy(
                isLoading = true
            )

            val foodList = repository.getFoodList()
            val featuredCap = 5
            _state.value = _state.value.copy(
                isLoading = false,
                featuredList = foodList.slice(0..featuredCap),
                exploreList = foodList.slice((featuredCap + 1)..foodList.lastIndex)
            )
        }
    }
}