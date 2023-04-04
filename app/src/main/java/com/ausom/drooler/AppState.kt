package com.ausom.drooler

import com.ausom.drooler.fooddetail.FoodDetailState
import com.ausom.drooler.home.HomeState

data class AppState(
    val homeState: HomeState = HomeState(),
    val foodDetailState: FoodDetailState = FoodDetailState()
)