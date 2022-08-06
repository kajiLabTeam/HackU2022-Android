package net.harutiro.test_bottomnavigation_withjetpackcompose.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import net.harutiro.xclothes.CoordinateEditActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val mContext = LocalContext.current

    Scaffold(
        modifier = Modifier.padding(0.dp,0.dp,0.dp,70.dp),

    ){

        Box(

            modifier = Modifier
                .fillMaxSize()
                .background(Color.Magenta)
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "HOME",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Text(
            text = "HOME",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }



}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}