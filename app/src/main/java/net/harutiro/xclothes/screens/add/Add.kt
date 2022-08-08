package net.harutiro.test_bottomnavigation_withjetpackcompose.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import net.harutiro.xclothes.screens.ProfileScreen
import net.harutiro.xclothes.screens.add.AddViewModel
import net.harutiro.xclothes.ui.theme.XclothesTheme

@Composable
fun AddScreen(viewModel: AddViewModel) {

    val context = LocalContext.current

    Surface (
        modifier = Modifier
            ){
        Button(
            onClick = {
                viewModel.putToastHello(context)
            }
        ) {

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
fun ProfileScreenPreview() {
    val viewModel :AddViewModel = viewModel()

    XclothesTheme {
        AddScreen(viewModel)
    }
}