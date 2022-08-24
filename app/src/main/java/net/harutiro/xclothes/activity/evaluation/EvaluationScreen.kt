package net.harutiro.xclothes.activity.evaluation

import android.app.Activity
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberLazyListSnapperLayoutInfo
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import net.harutiro.xclothes.R
import net.harutiro.xclothes.models.Clothes
import net.harutiro.xclothes.models.coordinate.get.GetCoordinateResponse
import net.harutiro.xclothes.models.login.get.GetLoginResponse
import net.harutiro.xclothes.ui.theme.XclothesTheme

@OptIn(ExperimentalPagerApi::class)
@Composable
fun EvaluationScreen(viewModel: EvaluationViewModel) {

    val pagerState = rememberPagerState() // 2.

    val openDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    SimpleAlertDialog(openDialog.value){
        openDialog.value = false
    }

    Surface (
        color = MaterialTheme.colorScheme.surface,
    ){
        Box(
            contentAlignment = Alignment.BottomStart,
            modifier = Modifier

        ) {
            VerticalPager(
                count = viewModel.clothePages.size,
                state = pagerState,
            ) { tabIndex ->

                PhotoView(viewModel.clothePages[tabIndex])
                PershonInformation(modifier = Modifier.align(Alignment.BottomEnd),viewModel.clothePages[tabIndex],viewModel.users[tabIndex])

                GoodButton(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(0.dp, 0.dp, 24.dp, 178.dp)
                        .size(60.dp),

                    onClick = {
                        openDialog.value = true
                        viewModel.postLike(context ,activity,viewModel.clothePages[tabIndex])
                    }

                )
            }




        }
    }
}

@Composable
fun SimpleAlertDialog(openDialog: Boolean , fix:() -> Unit) {

    val clotheIndex = remember { mutableStateOf(0) }


    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
                fix()
            },
            title = {
                Text("服の詳細だよ")
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ClothesList(){
                        clotheIndex.value = it
                    }

                    DotsIndicator(
                        totalDots = 5,
                        selectedIndex = clotheIndex.value,
                        selectedColor = MaterialTheme.colorScheme.onPrimary,
                        unSelectedColor = MaterialTheme.colorScheme.primary
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { // confirmをタップしたとき
                        fix()
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = null
        )
    }
}

@Composable
fun DotsIndicator(
    totalDots : Int,
    selectedIndex : Int,
    selectedColor: Color,
    unSelectedColor: Color,
){

    val newSelectedIndex = if(selectedIndex == 0) 0 else selectedIndex - 1

    LazyRow(
        modifier = Modifier
            .padding(16.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center

    ) {

        items(totalDots) { index ->
            if (index == newSelectedIndex) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier. padding (horizontal = 2.dp ))
            }
        }
    }
}

@OptIn(ExperimentalSnapperApi::class, ExperimentalFoundationApi::class)
@Composable
fun ClothesList(indexChanged:(Int)-> Unit){
    val lazyListState: LazyListState = rememberLazyListState()
    val layoutInfo = rememberLazyListSnapperLayoutInfo(lazyListState)

    var screenWidth = 0.dp

    BoxWithConstraints {
        screenWidth = with(LocalDensity.current) { constraints.maxWidth.toDp() }
        val screenHeight = with(LocalDensity.current) { constraints.maxHeight.toDp() }
    }
    val itemWidthDp = 250f.dp
    val xForCenteredItemDp = ((screenWidth - itemWidthDp) / 2)

    val huku1 = Clothes(
        category = "トップス",
        categoryIcon = Icons.Filled.CurrencyYen,
        brand = "GU",
        brandIcon = ImageVector.vectorResource(id = R.drawable.gu_logo),
        price = "10001~",
        priceIcon = Icons.Filled.CurrencyYen

    )

    val huku2 = Clothes(
        category = "ボトムス",
        categoryIcon = Icons.Filled.CurrencyYen,
        brand = "しまむら",
        brandIcon = ImageVector.vectorResource(id = R.drawable.simamura),
        price = "10001~",
        priceIcon = Icons.Filled.CurrencyYen

    )
    val huku3 = Clothes(
        category = "ボトムス",
        categoryIcon = Icons.Filled.CurrencyYen,
        brand = "しまむら",
        brandIcon = ImageVector.vectorResource(id = R.drawable.simamura),
        price = "10001~",
        priceIcon = Icons.Filled.CurrencyYen

    )
    val huku4 = Clothes(
        category = "ボトムス",
        categoryIcon = Icons.Filled.CurrencyYen,
        brand = "しまむら",
        brandIcon = ImageVector.vectorResource(id = R.drawable.simamura),
        price = "10001~",
        priceIcon = Icons.Filled.CurrencyYen

    )
    val huku5 = Clothes(
        category = "ボトムス",
        categoryIcon = Icons.Filled.CurrencyYen,
        brand = "しまむら",
        brandIcon = ImageVector.vectorResource(id = R.drawable.simamura),
        price = "10001~",
        priceIcon = Icons.Filled.CurrencyYen

    )

    val names = remember { mutableStateListOf(
        huku1,
        huku2,
        huku3,
        huku4,
        huku5,

        ) }

    LaunchedEffect(lazyListState.isScrollInProgress) {
        if (!lazyListState.isScrollInProgress) {
            // The scroll (fling) has finished, get the current item and
            // do something with it!
            val snappedItem = layoutInfo.currentItem
            Log.d("index",snappedItem.toString())

            indexChanged(snappedItem?.index ?: 0)
        }
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        state = lazyListState,
        flingBehavior = rememberSnapperFlingBehavior(layoutInfo),
    ) {

        item {
            Box(
                modifier = Modifier.width(xForCenteredItemDp)
            )
        }

        itemsIndexed(
            items = names,
            key = {index ,i ->
                i.id
            }

        ) { index , name ->

            AnimatedVisibility(
                modifier = Modifier.animateItemPlacement(),
                visible = names.contains(name),
                enter = fadeIn(),
                exit = fadeOut(),
            ) {

                Card(Modifier.width(itemWidthDp)) {
                    TextBox(
                        questionLabel = "ブランド" ,
                        text = name.brand,
                        comeIcon = name.brandIcon
                    )

                    TextBox(
                        questionLabel = "カテゴリ",
                        text = name.category,
                        comeIcon = name.categoryIcon
                    )

                    TextBox(
                        questionLabel = "価格帯",
                        text = name.price,
                        comeIcon = name.priceIcon
                    )

                }
            }

        }

        item {
            Box(
                modifier = Modifier.width(xForCenteredItemDp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextBox(
    questionLabel:String,
    text:String,
    comeIcon:ImageVector
){
    var selectedText by remember { mutableStateOf(text) }

    var leadingIcon by remember { mutableStateOf(comeIcon) }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {
                selectedText = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    focusManager.clearFocus()
                },
            label = {Text(questionLabel)},
            leadingIcon = {
                Icon(leadingIcon,"contentDescription")
            },
        )
    }
}

@Composable
fun GoodButton(align: Modifier , onClick: () -> Unit) {

    val context = LocalContext.current



    Button(
        shape = CircleShape,
        modifier = align,
        onClick = {
            Toast.makeText(context, "いいねしました", Toast.LENGTH_SHORT).show()
            onClick()
        },
        contentPadding = PaddingValues(0.dp),
    ){
        Icon(Icons.Filled.ThumbUp,"contentDescription")
    }


}

@Composable
fun PershonInformation(
    modifier: Modifier,
    coordinate: GetCoordinateResponse,
    user: GetLoginResponse
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 1f),
                shape = RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp)
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
                    text = if(user.gender == 1) "男性" else "女性"
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
                    text = user.height.toString() + "cm"
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
                    text = user.age
                )
            }





        }
    }


}


@Composable
fun PhotoView(clothe:GetCoordinateResponse){
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center,
    ){
        AsyncImage(
            contentDescription = "My Picture",
            contentScale = ContentScale.Fit,
            model = clothe.image,
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