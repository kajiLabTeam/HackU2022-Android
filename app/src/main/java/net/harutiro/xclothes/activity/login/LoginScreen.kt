package net.harutiro.xclothes.activity.login

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import net.harutiro.test_bottomnavigation_withjetpackcompose.screens.HomeScreen
import net.harutiro.xclothes.screens.ProfileScreen

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    AccountIcon(viewModel)
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun AccountIcon(viewModel: LoginViewModel) {
    val activity = LocalContext.current as Activity

    val pagerState = rememberPagerState() // 2.

    viewModel.navController = rememberNavController()

    DotsIndicator(
        totalDots = 2,
        selectedIndex = pagerState.currentPage,
        selectedColor = Color(0xFF070707),
        unSelectedColor = Color(0xFF636363)
    )

    NavHost(navController = viewModel.navController!!, startDestination = "main") {
        composable("main") {
            WelcomeScreen(
                login = {viewModel.login(activity)},
                logout = {viewModel.logout()},
            )
        }
        // ここを追加
        composable("second") {
            ProfileScreen()
        }
    }
}

@Composable
fun DotsIndicator(
    totalDots : Int,
    selectedIndex : Int,
    selectedColor: Color,
    unSelectedColor: Color,
){

    LazyRow(
        modifier = Modifier
            .padding(16.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center

    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier. padding (horizontal = 2.dp ))
            }
        }
    }
}