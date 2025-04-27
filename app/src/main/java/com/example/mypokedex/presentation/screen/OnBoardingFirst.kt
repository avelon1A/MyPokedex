package com.example.mypokedex.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bosch.composewithkotlin20.presentaion.ui.viewModel.event.OnBoardingEvent
import com.example.mypokedex.R
import com.example.mypokedex.data.model.Page
import com.example.mypokedex.data.model.pages
import com.example.mypokedex.presentation.navigation.Screen
import com.example.mypokedex.presentation.ui.theme.Blue
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(
    navController: NavController, event: (OnBoardingEvent) -> Unit
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 0) {
        pages.size
    }
    val buttonsState = remember {
        derivedStateOf {
            when (pagerState.currentPage) {
                0 -> listOf("Continue", "")
                1 -> listOf("Let's get started!", "")
                2 -> listOf("Create account", "I already have an account")
                else -> listOf("", "")
            }
        }
    }



    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {

            HorizontalPager(modifier = Modifier.fillMaxHeight(0.8f), state = pagerState) { index ->
                OnBoarding(page = pages[index], modifier = Modifier)
            }
            if (pagerState.currentPage < 2) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .navigationBarsPadding(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    PageIndicator(
                        modifier = Modifier,
                        pageSize = pages.size - 1,
                        selectedPage = pagerState.currentPage
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {


                    }
                }


                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ButtonOnBoarding(text = buttonsState.value[0], onClick = {
                        scope.launch {
                            if (pagerState.currentPage == 2) {
                                event(OnBoardingEvent.SaveAtEntryPoint)
                            } else {
                                pagerState.animateScrollToPage(
                                    page = pagerState.currentPage + 1
                                )
                            }
                        }
                    })
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ButtonOnBoarding(text = buttonsState.value[0], onClick = {

                    })
                    ButtonOnBoarding(text = buttonsState.value[1], onClick = {})
                }

            }
        }
        if (pagerState.currentPage == 2) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(Screen.HomeScreen)
                            event(OnBoardingEvent.SaveAtEntryPoint)
                        }
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Skip",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.forward_btn),
                        contentDescription = "icon",
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }

    }
}


@Composable
fun ButtonOnBoarding(text: String, onClick: () -> Unit) {
    val ColorText = if (text == "I already have an account") {
        Blue
    } else {
        Color.White
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(58.dp)
            .clip(RoundedCornerShape(size = 50.dp))
            .background(if (ColorText == Color.White) Blue else Color.White)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Text(
            text = text, style = MaterialTheme.typography.bodyLarge, color = ColorText
        )

    }
}

@Composable
fun OnBoarding(
    modifier: Modifier = Modifier, page: Page
) {
    Column(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .padding(top = 100.dp),
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            text = page.title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,

            )
        Spacer(modifier = Modifier.height(21.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 21.dp)
                .fillMaxWidth(),
            text = page.content,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )
    }

}


@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    selectedPage: Int,
    selectedColor: Color = Blue,
    unselectedColor: Color = Color.Gray

) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        repeat(pageSize) {
            Box(
                modifier = modifier
                    .width(if (it == selectedPage) 30.dp else 9.dp)
                    .height(9.dp)
                    .clip(CircleShape)
                    .background(color = if (it == selectedPage) selectedColor else unselectedColor)

            )
        }
    }

}
