package com.ausom.drooler.fooddetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ausom.drooler.data.FoodRepository
import com.ausom.drooler.di.PostExecutionThread
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val postExecutionThread: PostExecutionThread,
    private val repository: FoodRepository
): ViewModel() {

    private val _state = mutableStateOf(FoodDetailState())
    val state: State<FoodDetailState>
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

    fun nextFood() {
        viewModelScope.launch(postExecutionThread.default) {
            val foodDetail = _state.value.foodDetail
            val effect = when {
                foodDetail.isLast || foodDetail.id.toInt() == 0 -> FoodDetailEffect.Close
                else -> FoodDetailEffect.GoToFoodDetail(foodDetail.id.toInt() + 1)
            }
            _effect.emit(effect)
        }
    }
}