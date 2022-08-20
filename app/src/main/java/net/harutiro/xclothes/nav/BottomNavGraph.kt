package net.harutiro.test_bottomnavigation_withjetpackcompose

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.harutiro.test_bottomnavigation_withjetpackcompose.screens.AddScreen
import net.harutiro.test_bottomnavigation_withjetpackcompose.screens.HomeScreen
import net.harutiro.test_bottomnavigation_withjetpackcompose.screens.MapScreen
import net.harutiro.xclothes.screens.ProfileScreen
import net.harutiro.test_bottomnavigation_withjetpackcompose.screens.SettingsScreen
import net.harutiro.xclothes.models.login.post.PostLoginRequestBody
import net.harutiro.xclothes.nav.BottomBarScreen
import net.harutiro.xclothes.screens.add.AddViewModel

@Composable
fun BottomNavGraph(navController:NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ){

        composable(route = BottomBarScreen.Home.route){
            HomeScreen()
        }
        composable(route = BottomBarScreen.Profile.route){
            ProfileScreen(PostLoginRequestBody())
        }
        composable(route = BottomBarScreen.Settings.route){
            SettingsScreen()
        }
        composable(route = BottomBarScreen.Map.route){
            MapScreen()
        }
        composable(route = BottomBarScreen.Add.route){
            val addViewModel:AddViewModel = viewModel()
            AddScreen(addViewModel)
        }
    }
}