package com.ausom.drooler.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
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
import com.ausom.drooler.common.Loader
import com.ausom.drooler.common.getDrawable
import com.ausom.drooler.data.FoodEntity
import com.ausom.drooler.ui.theme.Accent

data class HomeState(
    val droolCount: String = "0",
    val isLoading: Boolean = false,
    val featuredList: List<FoodEntity> = emptyList(),
    val exploreList: List<FoodEntity> = emptyList(),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController) {

    val viewModel = hiltViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsState(initial = HomeState())

    val configuration = LocalConfiguration.current
    val coverPhoto = configuration.screenHeightDp.dp.times(0.5f)

    val lazyListState = rememberLazyListState()
    var scrolledY by remember { mutableStateOf(0f) }
    var previousOffset by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.loadDroolCount()
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
                alignment = Alignment.BottomCenter,
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
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .clipToBounds()
            ) {
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
                        Text(
                            text = state.droolCount,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(id = R.string.home_droolCountTitle),
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
            HeaderItem()
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                SectionItem(
                    title = stringResource(id = R.string.home_sectionHeader1),
                    subtitle = stringResource(id = R.string.home_sectionHeader1Subtitle))
                Loader(state.isLoading) {
                    LazyRow {
                        itemsIndexed(
                            key = { index, _ -> index },
                            items = state.featuredList
                        ) { index, item ->
                            if (index == 0) Spacer(modifier = Modifier.width(20.dp))
                            FeaturedItem(
                                id = item.id,
                                name = item.name,
                                onClick = { navController.navigate(NavDirection.FoodDetail.with(item.id)) }
                            )
                        }
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
                SectionItem(
                    title = stringResource(id = R.string.home_sectionHeader2),
                    subtitle = stringResource(id = R.string.home_sectionHeader2Subtitle))
            }
        }

        if(state.isLoading) {
            item {
                Column(
                    modifier = Modifier.background(Color.White)
                ) {
                    Spacer(modifier = Modifier.height(100.dp))
                    Loader(state.isLoading)
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        } else {
            items(
                key = { it.id },
                items = state.exploreList
            ) { item ->
                ExploreItem(
                    id = item.id,
                    name = item.name,
                    onClick = { navController.navigate(NavDirection.FoodDetail.with(item.id)) }
                )
            }
        }
    }
}

@Composable
fun HeaderItem() {
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
                        text = stringResource(id = R.string.home_titleLine1)
                    )
                )
                append(
                    AnnotatedString(
                        text = stringResource(id = R.string.home_titleLine2),
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
            text = stringResource(id = R.string.home_description),
            fontFamily = FontFamily.Serif,
            fontSize = 14.sp,
            fontWeight = FontWeight.Thin,
            lineHeight = 24.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun SectionItem(title: String, subtitle: String) {
    Text(
        modifier = Modifier.padding(horizontal = 24.dp),
        text = title,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        modifier = Modifier.padding(horizontal = 24.dp),
        text = subtitle,
        fontFamily = FontFamily.Serif,
        fontSize = 14.sp,
        fontWeight = FontWeight.Thin,
        lineHeight = 24.sp,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun FeaturedItem(id: String, name: String, onClick: () -> Unit) {
    val modifier = Modifier.fillMaxWidth()
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(200.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
    ) {
        Image(
            modifier = modifier.weight(1f),
            painter = painterResource(id = getDrawable(id = id)),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Text(
            modifier = modifier.padding(8.dp),
            text = name
        )
    }
    Spacer(modifier = Modifier.width(20.dp))
}

@Composable
fun ExploreItem(id: String, name: String, onClick: () -> Unit) {
    val modifier = Modifier.fillMaxWidth()
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        Card(
            modifier = modifier.clickable(onClick = onClick),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
        ) {
            Image(
                painter = painterResource(id = getDrawable(id)),
                contentDescription = null
            )
            Text(
                modifier = modifier.padding(8.dp),
                text = name
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}