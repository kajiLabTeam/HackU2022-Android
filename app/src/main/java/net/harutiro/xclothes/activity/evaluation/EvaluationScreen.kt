package net.harutiro.xclothes.activity.evaluation

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import net.harutiro.xclothes.R
import net.harutiro.xclothes.ui.theme.XclothesTheme

@OptIn(ExperimentalPagerApi::class)
@Composable
fun EvaluationScreen(viewModel: EvaluationViewModel) {

    val tabTitles = listOf(
        "https://cdn.discordapp.com/attachments/1003329100649336892/1010179549557964860/IMG_4892.jpg",
        "https://cdn.discordapp.com/attachments/1003329100649336892/1010179549901881395/IMG_4893.jpg",
        "https://cdn.discordapp.com/attachments/1003329100649336892/1010179550224859287/IMG_4882.jpg"
    )
    val pagerState = rememberPagerState() // 2.

    Surface (
        color = MaterialTheme.colorScheme.surface,
    ){
        Box(
            contentAlignment = Alignment.BottomStart,
            modifier = Modifier

        ) {
            VerticalPager(
                count = tabTitles.size,
                state = pagerState,
            ) { tabIndex ->

                PhotoView(tabTitles[tabIndex])
                name(modifier = Modifier.align(Alignment.BottomEnd))
                GoodButton(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(0.dp, 0.dp, 24.dp, 178.dp)
                        .size(60.dp))
            }


        }
    }
}

@Composable
fun GoodButton(align: Modifier) {

    val context = LocalContext.current

    Button(
        shape = CircleShape,
        modifier = align,
        onClick = {
                  Toast.makeText(context, "いいねしました", Toast.LENGTH_SHORT).show()
        },
        contentPadding = PaddingValues(0.dp),
    ){
        Icon(Icons.Filled.ThumbUp,"contentDescription")
    }

}

@Composable
fun name(modifier:Modifier){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 1f),
                shape = RoundedCornerShape(24.dp,24.dp,0.dp,0.dp)
            ),
        contentAlignment = Alignment.BottomStart,
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(84.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.gender_neutral_user_icon_icons_com_55902),
                    contentDescription = "hello",
                    tint = MaterialTheme.colorScheme.onSecondary
                )

                Text(
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 22.sp,
                    text = "男性"
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(84.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.human_male_height_variant_icon_136499),
                    contentDescription = "hello",
                    tint = MaterialTheme.colorScheme.onSecondary
                )

                Text(
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 22.sp,
                    text = "125cm"
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(84.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.calendar_month_fill0_wght400_grad0_opsz48),
                    contentDescription = "hello",
                    tint = MaterialTheme.colorScheme.onSecondary
                )

                Text(
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 22.sp,
                    text = "20~25"
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
            contentScale = ContentScale.Fit,
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