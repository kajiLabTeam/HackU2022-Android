package net.harutiro.xclothes.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.harutiro.xclothes.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen() {


    Surface(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ){

        Column(
            modifier = Modifier.padding(24.dp)
        ){

            icon()


        }
    }
}


@Composable
fun icon(){
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)

    ){
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40 dp
                .size(120.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
//                        .fillMaxWidth()

        )
    }
}

@Composable
@Preview
fun ProfileScreenPreview() {
    ProfileScreen()
}