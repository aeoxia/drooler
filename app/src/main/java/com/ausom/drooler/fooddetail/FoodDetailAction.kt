package com.ausom.drooler.fooddetail

import com.ausom.drooler.data.FoodEntity

sealed class FoodDetailAction {
    data class LoadFoodDetail(val id: String): FoodDetailAction()
    data class DisplayFoodDetail(val foodDetail: FoodEntity): FoodDetailAction()

    object Drool: FoodDetailAction()
    object Nope: FoodDetailAction()
    object NextFood: FoodDetailAction()

}