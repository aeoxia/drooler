package com.ausom.drooler.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ausom.drooler.NavDirection
import com.ausom.drooler.R
import com.ausom.drooler.data.FoodEntity
import com.ausom.drooler.ui.theme.Accent

data class HomeState(
    val droolCount: Int = 0,
    val isLoading: Boolean = false,
    val featuredList: List<FoodEntity> = emptyList(),
    val exploreList: List<FoodEntity> = emptyList(),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController) {

    val viewModel = hiltViewModel<HomeViewModel>()
    val state = viewModel.state.value

    val configuration = LocalConfiguration.current
    val coverPhoto = configuration.screenHeightDp.dp.times(0.5f)

    val lazyListState = rememberLazyListState()
    var scrolledY by remember { mutableStateOf(0f) }
    var previousOffset by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.loadFoodList()
    }

    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {

        item {
            Image(
                painter = painterResource(id = R.drawable.pizza),
                contentDescription = null,
                alignment= Alignment.BottomCenter,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .height(coverPhoto)
                    .graphicsLayer {
                        scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                        translationY = scrolledY * 0.5f
                        previousOffset = lazyListState.firstVisibleItemScrollOffset
                    }
                    .fillMaxWidth()
            )
        }
        stickyHeader {
            Canvas(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clipToBounds()) {
                drawArc(
                    color = Color.White,
                    -180f,
                    180f,
                    useCenter = false,
                    size = Size(size.width, size.height * 2),
                )
            }
        }
        stickyHeader {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Spacer(modifier = Modifier.width(24.dp))
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Accent)
                            .padding(vertical = 4.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.FavoriteBorder,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Drools",
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = buildAnnotatedString {
                        append(
                            AnnotatedString(
                                text = "The Fastest in Making \nYou "
                            )
                        )
                        append(
                            AnnotatedString(
                                text = "Drool",
                                spanStyle = SpanStyle(color = Accent)
                            )
                        )
                    },
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    lineHeight = 40.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = "Our job is to fill your tummy with delicious \nfood cravings fast and effectively.",
                    fontFamily = FontFamily.Serif,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Thin,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = "Popular Now",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = "Here are our yummiest foods you wont get to eat.",
                    fontFamily = FontFamily.Serif,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Thin,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow {
                    itemsIndexed(
                        key = { index, _ -> index },
                        items = state.featuredList) { index, item ->
                        if(index == 0) Spacer(modifier = Modifier.width(20.dp))

                        Card(
                            modifier = Modifier
                                .width(150.dp)
                                .height(200.dp)
                                .clickable {
                                    navController.navigate(NavDirection.FoodDetail.with(item.id))
                                },
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 10.dp
                            ),
                        ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                painter = painterResource(id = R.drawable.burger),
                                contentDescription = null)
                            Text(
                                modifier = Modifier
                                    .background(Color.White)
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                text = item.name
                            )
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = "Explore More",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = "Still want to drool more? check these out!",
                    fontFamily = FontFamily.Serif,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Thin,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        items(
            key = { it.id },
            items = state.exploreList) { item ->
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(horizontal = 24.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth().clickable {
                        navController.navigate(NavDirection.FoodDetail.with(item.id))
                    },
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    ),
                ) {
                    Image(painter = painterResource(id = R.drawable.burger), contentDescription = null)
                    Text(
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                            .padding(8.dp),
                        text = item.name
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}