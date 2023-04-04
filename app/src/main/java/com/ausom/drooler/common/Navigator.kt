package com.ausom.drooler.common

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {
    private var navController: NavHostController? = null

    val startDestination: Int
        get() = navController?.graph?.findStartDestination()?.id?: 0

    fun navigate(route: String, builder: NavOptionsBuilder.() -> Unit = {}): NavBackStackEntry? {
        navController?.navigate(route, navOptions(builder))
        return navController?.currentBackStackEntry
    }

    fun pop(): NavBackStackEntry?  {
        navController?.popBackStack()
        return navController?.currentBackStackEntry
    }

    fun pop(route: String, inclusive: Boolean): NavBackStackEntry?  {
        navController?.popBackStack(route, inclusive)
        return navController?.currentBackStackEntry
    }

    fun registerNavController(navHostController: NavHostController) {
        navController = navHostController
    }

    fun unregisterNavController() {
        navController = null
    }
}