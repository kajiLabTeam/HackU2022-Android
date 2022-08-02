package net.harutiro.test_bottomnavigation_withjetpackcompose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import net.harutiro.xclothes.R

sealed class BottomBarScreen(
    val route:String,
    val title:String,
    val icon:ImageVector,

){
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object Profile : BottomBarScreen(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )

    object Settings : BottomBarScreen(
        route = "settings",
        title = "Settings",
        icon = Icons.Default.Settings
    )

    object Map : BottomBarScreen(
        route = "map",
        title = "Map",
        icon = Icons.Default.Map
    )

}
