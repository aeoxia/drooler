package com.ausom.drooler

import com.ausom.drooler.common.ActionWrapper
import com.ausom.drooler.fooddetail.FoodDetailAction
import com.ausom.drooler.home.HomeAction

sealed class AppAction {

    data class Home(override val action: HomeAction): AppAction(), ActionWrapper<HomeAction>
    data class FoodDetail(override val action: FoodDetailAction): AppAction(), ActionWrapper<FoodDetailAction>

}