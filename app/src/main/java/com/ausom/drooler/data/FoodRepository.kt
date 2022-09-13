package com.ausom.drooler.data

import kotlinx.coroutines.delay
import javax.inject.Inject

class FoodRepository @Inject constructor() {

    companion object {
        const val FOOD_SIZE = 20
    }
    private val foodList by lazy {
        List(FOOD_SIZE) {
            FoodEntity(
                id = "$it",
                name = "Food $it",
                thumbnail = it,
                isLast = it == FOOD_SIZE
            )
        }
    }

    suspend fun getFoodList() : List<FoodEntity> {
        delay(3000)
        return foodList
    }

    suspend fun getFoodDetail(id: String) : FoodEntity {
        delay(1500)
        return foodList.first { it.id == id }
    }
}