package com.ausom.drooler.home

import com.ausom.drooler.AppAction
import com.ausom.drooler.AppState
import com.toggl.komposable.architecture.Store
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
object HomeModule {

    @Provides
    fun store(store: Store<AppState, AppAction>): Store<HomeState, HomeAction> =
        store.view(
            mapToLocalState = { it.homeState },
            mapToGlobalAction = { AppAction.Home(it) }
        )
}