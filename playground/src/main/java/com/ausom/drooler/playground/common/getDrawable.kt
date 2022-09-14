package com.ausom.drooler.playground.common

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@DrawableRes
@SuppressLint("DiscouragedApi") // just for presentation
@Composable
fun getDrawable(id: String): Int {
    val context = LocalContext.current
    val foodDrawable = "food$id"
    Log.i("FoodDrawable", foodDrawable)
    return remember(foodDrawable) {
        context.resources.getIdentifier(
            foodDrawable,
            "drawable",
            context.packageName
        )
    }
}