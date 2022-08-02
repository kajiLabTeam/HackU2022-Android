package net.harutiro.test_bottomnavigation_withjetpackcompose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.harutiro.test_bottomnavigation_withjetpackcompose.screens.HomeScreen
import net.harutiro.test_bottomnavigation_withjetpackcompose.screens.MapScreen
import net.harutiro.test_bottomnavigation_withjetpackcompose.screens.ProfileScreen
import net.harutiro.test_bottomnavigation_withjetpackcompose.screens.SettingsScreen

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
            ProfileScreen()
        }
        composable(route = BottomBarScreen.Settings.route){
            SettingsScreen()
        }
        composable(route = BottomBarScreen.Map.route){
            MapScreen()
        }
    }
}