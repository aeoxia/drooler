package com.ausom.drooler.home

import androidx.lifecycle.ViewModel
import com.toggl.komposable.architecture.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeStore @Inject constructor(
    private val store: Store<HomeState, HomeAction>
): ViewModel(), Store<HomeState, HomeAction> by store