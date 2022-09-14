package com.ausom.drooler.fooddetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ausom.drooler.data.FoodRepository
import com.ausom.drooler.di.PostExecutionThread
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

    private val _state = MutableStateFlow(FoodDetailState())
    val state: StateFlow<FoodDetailState>
        get() = _state

    private val _effect = MutableSharedFlow<FoodDetailEffect>()
    val effect: Flow<FoodDetailEffect>
        get() = _effect

    fun loadFoodDetail(id: String) {
        viewModelScope.launch(postExecutionThread.io) {
            _state.value = _state.value.copy(
                isLoading = true
            )

            val foodDetail = repository.getFoodDetail(id)
            _state.value = _state.value.copy(
                isLoading = false,
                foodDetail = foodDetail,
            )
        }
    }

    fun drool() {
        viewModelScope.launch(postExecutionThread.io) {
            repository.addDroolCount(1)
            nextFood()
        }
    }

    fun nope() {
        viewModelScope.launch(postExecutionThread.io) {
            nextFood()
        }
    }

    private suspend fun nextFood() {
        val foodDetail = _state.value.foodDetail
        val effect = when {
            foodDetail.isLast -> FoodDetailEffect.Close
            else -> FoodDetailEffect.GoToFoodDetail(foodDetail.id.toInt() + 1)
        }

        _effect.emit(effect)
    }
}