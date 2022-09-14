package com.ausom.drooler.playground.data

import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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
                isLast = it == FOOD_SIZE - 1
            )
        }
    }

    private var droolCounter = 0

    suspend fun getFoodList() : List<FoodEntity> {
        delay(1000)
        return foodList
    }

    fun getDroolCount(): Int {
        return droolCounter
    }

    fun addDroolCount(count: Int) {
        droolCounter += count
    }


    suspend fun getFoodDetail(id: String) : FoodEntity {
        return foodList.first { it.id == id }
    }
}