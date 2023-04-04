package com.ausom.drooler.fooddetail

import androidx.lifecycle.ViewModel
import com.toggl.komposable.architecture.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodDetailStore @Inject constructor(
    private val store: Store<FoodDetailState, FoodDetailAction>
): ViewModel() , Store<FoodDetailState, FoodDetailAction> by store