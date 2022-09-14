package com.ausom.drooler.playground.fooddetail

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
import com.ausom.drooler.playground.NavDirection
import com.ausom.drooler.playground.R
import com.ausom.drooler.playground.common.Loader
import com.ausom.drooler.playground.common.getDrawable
import com.ausom.drooler.playground.data.FoodEntity
import com.ausom.drooler.playground.ui.theme.TransparentBlack20
import com.ausom.drooler.playground.ui.theme.TransparentBlack50
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach



@Composable
fun FoodDetailScreen(navController: NavController, id: String) {

    //TODO 4 add viewmodel


    LaunchedEffect(Unit) {
        //TODO 6.0 load action

        //TODO 7.0 observe effect

    }


    val modifier = Modifier.fillMaxSize()
    Box(
        modifier = modifier,
    ) {
        //TODO 5 add state and UI
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
