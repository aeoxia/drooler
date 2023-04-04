package com.ausom.drooler.fooddetail

import com.ausom.drooler.AppAction
import com.ausom.drooler.AppState
import com.toggl.komposable.architecture.Store
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
object FoodDetailModule {

    @Provides
    fun store(store: Store<AppState, AppAction>): Store<FoodDetailState, FoodDetailAction> =
        store.view(
            mapToLocalState = { it.foodDetailState },
            mapToGlobalAction = { AppAction.FoodDetail(it) }
        )
}