package net.harutiro.xclothes

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.harutiro.test_bottomnavigation_withjetpackcompose.BottomBarScreen
import net.harutiro.test_bottomnavigation_withjetpackcompose.BottomNavGraph
import net.harutiro.xclothes.ui.theme.XclothesTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val navController = rememberNavController()

    Scaffold (
        topBar = { TopBar()},
        bottomBar = { BottomBar(navController = navController) } ,
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {
//
//                },
//            ) {
//                Icon(Icons.Filled.Add, contentDescription = "追加")
//            }
//        },
//        floatingActionButtonPosition = FabPosition.Center,
    ){

        BottomNavGraph(navController = navController)
    }
}


@Composable
fun TopBar(){
    SmallTopAppBar (
        title = {
            Text(stringResource(id = R.string.app_name ))
        }
    )
}


@Composable
fun BottomBar(navController: NavHostController){
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Profile,
        BottomBarScreen.Settings,
        BottomBarScreen.Map,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomAppBar(
        modifier = Modifier
            .height(80.dp)
            .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
//        cutoutShape = CircleShape,
        tonalElevation = 22.dp
    ){
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}



@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
//        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    XclothesTheme {
        MainScreen()
    }
}
