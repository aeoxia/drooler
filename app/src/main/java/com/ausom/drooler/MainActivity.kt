package com.ausom.drooler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ausom.drooler.NavDirection.Companion.FOOD_ID
import com.ausom.drooler.common.Navigator
import com.ausom.drooler.fooddetail.FoodDetailScreen
import com.ausom.drooler.home.HomeScreen
import com.ausom.drooler.ui.theme.DroolerTheme
import com.ausom.drooler.ui.theme.Primary
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

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
                    val store = hiltViewModel<AppStore>()
                    val state by store.state.collectAsState(initial = AppState())
                    Navigation(navigator)
                }
            }
        }
    }

    override fun onDestroy() {
        navigator.unregisterNavController()
        super.onDestroy()
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
fun Navigation(navigator: Navigator) {
    val navController = rememberNavController()
    navigator.registerNavController(navController)
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
