package com.ausom.drooler.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Test()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Test() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = { Text(text = "Scroll Behavior Test") },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(it)) {
            items((1..50).toList()) { item ->
                Text(modifier = Modifier.padding(8.dp), text = "Item $item")
            }
        }
    }
}