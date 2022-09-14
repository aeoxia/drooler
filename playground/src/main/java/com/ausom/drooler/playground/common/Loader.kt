package com.ausom.drooler.playground.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ausom.drooler.playground.theme.Accent

@Composable
fun Loader(visible: Boolean, modifier: Modifier = Modifier, block: @Composable () -> Unit = {}) {
    Box {
        AnimatedVisibility(!visible) {
            block.invoke()
        }
        AnimatedVisibility(visible, enter = fadeIn(), exit = fadeOut()) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            )  {
                CircularProgressIndicator(color = Accent)
            }
        }
    }
}