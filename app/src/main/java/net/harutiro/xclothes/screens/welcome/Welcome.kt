package net.harutiro.xclothes.activity.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    login:() -> Unit,
    logout:() -> Unit,
){

    Column(
    ){

        Button(
            modifier = Modifier,
            onClick = {login()},
        ){
            Text("ログイン")
        }

        Button(
            modifier = Modifier,
            onClick = {logout()},
        ){
            Text("ログアウト")
        }

    }


}