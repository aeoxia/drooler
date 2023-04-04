package com.ausom.drooler.home

import com.ausom.drooler.data.FoodEntity


sealed class HomeAction {
    object LoadDroolCount: HomeAction()
    data class DisplayDroolCount(val count: Int): HomeAction()

    object LoadFoodList: HomeAction()
    data class DisplayFoodList(val foodList: List<FoodEntity>): HomeAction()
}
