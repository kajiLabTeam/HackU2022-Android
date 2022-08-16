package net.harutiro.xclothes.activity.evaluation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import net.harutiro.xclothes.ui.theme.XclothesTheme

class EvaluationActivity : ComponentActivity() {

    private val viewModel : EvaluationViewModel by lazy { ViewModelProvider.NewInstanceFactory().create(EvaluationViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XclothesTheme {
                // A surface container using the 'background' color from the theme
                EvaluationScreen(viewModel)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    XclothesTheme {
        EvaluationScreen(viewModel())
    }
}