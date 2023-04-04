package com.ausom.drooler.fooddetail

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ausom.drooler.NavDirection
import com.ausom.drooler.R
import com.ausom.drooler.common.Loader
import com.ausom.drooler.common.getDrawable
import com.ausom.drooler.data.FoodEntity
import com.ausom.drooler.ui.theme.TransparentBlack20
import com.ausom.drooler.ui.theme.TransparentBlack50

data class FoodDetailState(
    val isLoading: Boolean = false,
    val droolCount: Int = 0,
    val foodDetail: FoodEntity = FoodEntity()
)

@Composable
fun FoodDetailScreen(navController: NavController, id: String) {

    val store = hiltViewModel<FoodDetailStore>()
    val state by store.state.collectAsState(initial = FoodDetailState())

    LaunchedEffect(Unit) {
        store.send(FoodDetailAction.LoadFoodDetail(id))
    }

    val modifier = Modifier.fillMaxSize()
    Box(
        modifier = modifier,
    ) {
        AnimatedVisibility(state.foodDetail.id.isNotEmpty(), enter = fadeIn(), exit = fadeOut() ) {
            Image(
                modifier = modifier,
                contentScale = ContentScale.Crop,
                painter = painterResource(id = getDrawable(state.foodDetail.id)),
                contentDescription = null
            )
        }
        Column(
            modifier = modifier
                .statusBarsPadding()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(TransparentBlack20)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        navController.popBackStack(
                            NavDirection.Home.name,
                            inclusive = false
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Text(
                        text = state.foodDetail.name,
                        fontSize = 24.sp,
                        color = Color.White
                    )
                }
            }
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    EmojiItem(
                        drawable = R.drawable.nope,
                        onClick = { store.send(FoodDetailAction.Nope) }
                    )
                    EmojiItem(
                        drawable = R.drawable.drool,
                        onClick = { store.send(FoodDetailAction.Drool) }
                    )
                }
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
        Loader(state.isLoading, Modifier.statusBarsPadding())
    }
}

@Composable
fun EmojiItem(@DrawableRes drawable: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = TransparentBlack50
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Inside,
            painter = painterResource(id = drawable),
            contentDescription = null
        )
    }
}
