package com.ausom.drooler.home

import com.ausom.drooler.data.FoodRepository
import com.toggl.komposable.architecture.Effect
import com.toggl.komposable.architecture.Mutable
import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.extensions.mutateWithoutEffects
import com.toggl.komposable.extensions.returnEffect
import com.toggl.komposable.scope.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeReducer @Inject constructor(
    private val getDroolCount: GetDroolCount,
    private val getFoodList: GetFoodList,
) : Reducer<HomeState, HomeAction> {

    override fun reduce(state: Mutable<HomeState>, action: HomeAction): List<Effect<HomeAction>> {
        return when(action) {
            HomeAction.LoadDroolCount -> returnEffect(getDroolCount)
            is HomeAction.DisplayDroolCount -> {
                val droolCount = action.count.toString()

                state.mutateWithoutEffects {
                    copy(
                        droolCount = droolCount
                    )
                }
            }
            HomeAction.LoadFoodList -> {
                state.mutate {
                    copy(
                        isLoading = true
                    )
                } returnEffect getFoodList
            }
            is HomeAction.DisplayFoodList -> {
                val foodList = action.foodList
                val featuredCap = 5
                state.mutateWithoutEffects {
                    copy(
                        isLoading = false,
                        featuredList = foodList.slice(0..featuredCap),
                        exploreList = foodList.slice((featuredCap + 1)..foodList.lastIndex)
                    )
                }
            }
        }
    }
}

class GetDroolCount @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val repository: FoodRepository
): Effect<HomeAction> {
    override suspend fun execute(): HomeAction = withContext(dispatcherProvider.io) {
        val droolCount = repository.getDroolCount()
        return@withContext HomeAction.DisplayDroolCount(droolCount)
    }
}

class GetFoodList @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val repository: FoodRepository
): Effect<HomeAction> {
    override suspend fun execute(): HomeAction = withContext(dispatcherProvider.io) {
        val foodList = repository.getFoodList()
        return@withContext HomeAction.DisplayFoodList(foodList)
    }
}
