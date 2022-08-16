package net.harutiro.xclothes.activity.evaluation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import net.harutiro.xclothes.ui.theme.XclothesTheme

@Composable
fun EvaluationScreen(viewModel: EvaluationViewModel) {

    Surface (
        color = MaterialTheme.colorScheme.surface,
    ){
        Box(
            contentAlignment = Alignment.BottomStart,
            modifier = Modifier

        ) {
            PhotoView()

            name()

            GoodButton(Modifier.align(Alignment.BottomEnd).padding(0.dp,0.dp,16.dp,158.dp).size(50.dp))

            NextButton(Modifier.align(Alignment.BottomEnd).padding(0.dp,0.dp,16.dp,228.dp).size(50.dp))


        }
    }
}

@Composable
fun NextButton(align: Modifier) {
    Button(
        shape = CircleShape,
        modifier = align,
        onClick = {},
        contentPadding = PaddingValues(0.dp),
    ){
        Icon(Icons.Filled.Style,"contentDescription")
    }
}

@Composable
fun GoodButton(align: Modifier) {
    Button(
        shape = CircleShape,
        modifier = align,
        onClick = {},
        contentPadding = PaddingValues(0.dp),
    ){
        Icon(Icons.Filled.Style,"contentDescription")
    }
}

@Composable
fun name(url: String = "https://mokelab.com/img/moke_512x512.png",name: String = "テスト太郎"){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0x8000000), Color(0xAA6F6F6F))
                )
            ),
        contentAlignment = Alignment.BottomStart,
    ) {
        Row(
            modifier =  Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(64.dp),
                contentDescription = "My Picture",
                contentScale = ContentScale.Crop,
                model = url,
            )

            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.Start, // 横方向
                verticalArrangement = Arrangement.Center // 縦方向

            ){
                Text(
                    text = name,
                    fontSize = 22.sp,
                )
                Text(
                    text = "男性　20〜25 125cm",
                    fontSize = 16.sp,
                )
            }





        }
    }


}


@Composable
fun SaveButton(){
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ){
        Button(
            onClick = { /*TODO*/ },
        ) {
            Text("保存をする")
        }
    }
}



@Composable
fun PhotoView(url:String = "https://res.cloudinary.com/dlg3xe2l2/image/upload/v1648506804/dkplq1odbhkd4nt2g6ba.jpg"){
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center,
    ){
        AsyncImage(
            contentDescription = "My Picture",
            contentScale = ContentScale.Crop,
            model = url,
            modifier = Modifier
                .fillMaxSize()
        )
    }

}

@Preview(
    showBackground = true,
    widthDp = 730,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun EvaluationScreenPreview() {
    XclothesTheme {
        EvaluationScreen(viewModel())
    }
}