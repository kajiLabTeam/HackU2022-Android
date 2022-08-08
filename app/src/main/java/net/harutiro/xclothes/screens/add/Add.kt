package net.harutiro.test_bottomnavigation_withjetpackcompose.screens

import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import net.harutiro.xclothes.screens.ProfileScreen
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
        Button(onClick = { launcher.launch(uri) }) {

        }

        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(data = uriRemember)
                    .build()
            ),
            contentDescription = "My Picture",
            modifier = Modifier.fillMaxSize()
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