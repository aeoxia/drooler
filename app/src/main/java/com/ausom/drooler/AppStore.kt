package com.ausom.drooler

import androidx.lifecycle.ViewModel
import com.toggl.komposable.architecture.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppStore @Inject constructor(
    private val store: Store<AppState, AppAction>
): ViewModel(), Store<AppState, AppAction> by store