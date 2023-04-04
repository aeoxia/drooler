package com.ausom.drooler

import android.app.Application
import com.ausom.drooler.common.unwrap
import com.ausom.drooler.fooddetail.FoodDetailReducer
import com.ausom.drooler.fooddetail.FoodDetailState
import com.ausom.drooler.home.HomeReducer
import com.ausom.drooler.home.HomeState
import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.architecture.Store
import com.toggl.komposable.extensions.combine
import com.toggl.komposable.extensions.createStore
import com.toggl.komposable.extensions.pullback
import com.toggl.komposable.scope.DispatcherProvider
import com.toggl.komposable.scope.StoreScopeProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
object AppModule {

    @[Singleton Provides]
    fun appStore(
        reducer: Reducer<AppState, AppAction>,
        dispatcherProvider: DispatcherProvider,
        application: Application,
    ): Store<AppState, AppAction> = createStore(
        initialState = AppState(),
        reducer = reducer,
        dispatcherProvider = dispatcherProvider,
        storeScopeProvider = application as StoreScopeProvider,
    )

    @Provides
    fun appReducer(
        homeReducer: HomeReducer,
        foodDetailReducer: FoodDetailReducer
    ): Reducer<AppState, AppAction> = combine(
        homeReducer.pullback(
            mapToLocalState = { it.homeState },
            mapToLocalAction = { it.unwrap() },
            mapToGlobalState = { global: AppState, homeState: HomeState ->
                global.copy(homeState = homeState)
            },
            mapToGlobalAction = { AppAction.Home(it) }
        ),
        foodDetailReducer.pullback(
            mapToLocalState = { it.foodDetailState },
            mapToLocalAction = { it.unwrap() },
            mapToGlobalState = { global: AppState, local: FoodDetailState ->
                global.copy(foodDetailState = local)
            },
            mapToGlobalAction = { AppAction.FoodDetail(it) }
        )
    )

    @[Singleton Provides]
    fun dispatcherProvider(): DispatcherProvider =
        DispatcherProvider(
            io = Dispatchers.IO,
            computation = Dispatchers.Default,
            main = Dispatchers.Main
        )

}