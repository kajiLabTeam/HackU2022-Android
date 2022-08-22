package net.harutiro.xclothes.activity.login

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import net.harutiro.test_bottomnavigation_withjetpackcompose.screens.AddScreen
import net.harutiro.xclothes.R
import net.harutiro.xclothes.ui.theme.XclothesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    login:() -> Unit,
    logout:() -> Unit,
){

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, // 横方向
        verticalArrangement = Arrangement.SpaceEvenly // 縦方向
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // 横方向
        ) {
            Text(
                fontSize = 24.sp,
                text = "ようこそ",
            )
            Text(
                fontSize = 24.sp,
                text = "Xclothesへ",
            )
        }


        Image(
            modifier = Modifier
                .size(200.dp),
            painter = painterResource(R.drawable.icon_image),
            contentDescription = "test"
        )

        Button(
            modifier = Modifier,
            onClick = {login()},
        ){
            Text("ログイン / 新規登録")
        }
    }


}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun AddScreenPreview() {

    XclothesTheme {
        WelcomeScreen({},{})
    }
}