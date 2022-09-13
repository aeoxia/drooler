package com.ausom.drooler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ausom.drooler.NavDirection.Companion.FOOD_ID
import com.ausom.drooler.fooddetail.FoodDetailScreen
import com.ausom.drooler.home.HomeScreen
import com.ausom.drooler.ui.theme.DroolerTheme
import com.ausom.drooler.ui.theme.Primary
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DroolerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Primary
                ) {
                    Navigation()
                }
            }
        }
    }
}

sealed class NavDirection(
    val name: String,
    val args: List<NamedNavArgument> = emptyList()
) {
    companion object {
        const val FOOD_ID = "id"
    }
    object Home: NavDirection(
        name = "Home"
    )
    object FoodDetail: NavDirection(
        name = "Home/{$FOOD_ID}",
        args = listOf(
            navArgument(FOOD_ID) { NavType.StringType }
        )
    )

    fun with(vararg args: Any): String = "${name.split("/").first()}/${args.joinToString(separator = "/")}"
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavDirection.Home.name,
    ) {
        composable(
            route = NavDirection.Home.name,
        ) {
            HomeScreen(navController)
        }
        composable(
            route = NavDirection.FoodDetail.name,
            arguments = NavDirection.FoodDetail.args
        ) {
            val id = it.arguments?.getString(FOOD_ID)?: ""
            BackHandler {
                navController.popBackStack(NavDirection.Home.name, inclusive = false)
            }
            FoodDetailScreen(navController, id)
        }
    }
}
