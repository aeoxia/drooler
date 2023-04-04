package com.ausom.drooler.fooddetail

import com.ausom.drooler.NavDirection
import com.ausom.drooler.common.Navigator
import com.ausom.drooler.data.FoodRepository
import com.toggl.komposable.architecture.Effect
import com.toggl.komposable.architecture.Mutable
import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.extensions.effectOf
import com.toggl.komposable.extensions.mutateWithoutEffects
import com.toggl.komposable.extensions.noEffect
import com.toggl.komposable.extensions.returnEffect
import com.toggl.komposable.scope.DispatcherProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodDetailReducer @Inject constructor(
    private val getFoodDetail: GetFoodDetail.Factory,
    private val addDroolCount: AddDroolCount.Factory,
    private val navigator: Navigator
): Reducer<FoodDetailState, FoodDetailAction> {
    override fun reduce(
        state: Mutable<FoodDetailState>,
        action: FoodDetailAction
    ): List<Effect<FoodDetailAction>> {
        return when(action) {
            is FoodDetailAction.LoadFoodDetail -> {
                val id = action.id
                state.mutate {
                    copy(
                        isLoading = true
                    )
                } returnEffect getFoodDetail(id)
            }
            is FoodDetailAction.DisplayFoodDetail -> {
                val foodDetail = action.foodDetail
                state.mutateWithoutEffects {
                    copy(
                        isLoading = false,
                        foodDetail = foodDetail,
                    )
                }
            }
            FoodDetailAction.Drool -> returnEffect(addDroolCount(1))
            FoodDetailAction.Nope -> effectOf(FoodDetailAction.NextFood)
            FoodDetailAction.NextFood -> {
                val foodDetail = state().foodDetail
                when {
                    foodDetail.isLast -> navigator.pop(
                        NavDirection.Home.name,
                        inclusive = false
                    )
                    else -> {
                        val id = foodDetail.id.toInt() + 1
                        val route = NavDirection.FoodDetail.with(id)
                        navigator.navigate(route)
                    }
                }
                noEffect()
            }

        }
    }
}

class GetFoodDetail @AssistedInject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val repository: FoodRepository,
    @Assisted private val id: String
): Effect<FoodDetailAction> {
    override suspend fun execute(): FoodDetailAction = withContext(dispatcherProvider.io) {
        val foodDetail = repository.getFoodDetail(id)
        return@withContext FoodDetailAction.DisplayFoodDetail(foodDetail)
    }

    @AssistedFactory
    interface Factory {
        operator fun invoke(id: String): GetFoodDetail
    }
}

class AddDroolCount @AssistedInject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val repository: FoodRepository,
    @Assisted private val count: Int
): Effect<FoodDetailAction> {
    override suspend fun execute(): FoodDetailAction = withContext(dispatcherProvider.io) {
        repository.addDroolCount(count)
        return@withContext FoodDetailAction.NextFood
    }

    @AssistedFactory
    interface Factory {
        operator fun invoke(count: Int): AddDroolCount
    }
}