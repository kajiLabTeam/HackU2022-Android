package net.harutiro.test_bottomnavigation_withjetpackcompose.screens

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import net.harutiro.xclothes.R
import net.harutiro.xclothes.screens.Gender
import net.harutiro.xclothes.screens.add.AddViewModel
import net.harutiro.xclothes.ui.theme.XclothesTheme

@Composable
fun AddScreen(viewModel: AddViewModel) {

    val context = LocalContext.current

    var uriRemember by remember { mutableStateOf("") }
    val uri = viewModel.photoStartUp(context)
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { _ ->
        uriRemember = uri.toString()
    }

    Surface (
        modifier = Modifier
    ){
        Column {
            if(uriRemember.isEmpty()){
                nottingPhotoIcon(launcher,uri)
            }else{
                photoView(uriRemember)
            }



        }
    }
}

@Composable
fun nottingPhotoIcon(launcher: ManagedActivityResultLauncher<Uri, Boolean>, uri: Uri) {
    Box(
        modifier = Modifier
            .height(320.dp)
            .fillMaxWidth()
            .clickable {
                launcher.launch(uri)
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_baseline_add_a_photo_24),
            contentDescription = "My Picture",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
        )
    }
}

@Composable
fun photoView(uriRemember:String){
    Box(){
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(data = uriRemember)
                    .build()
            ),
            contentDescription = "My Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        )

        Text(
            text = "This is a dog.",
            color = Color.White,
            modifier = Modifier
                .padding(32.dp)
                .align(alignment = Alignment.BottomStart)
        )
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
        AddScreen(AddViewModel())
    }
}