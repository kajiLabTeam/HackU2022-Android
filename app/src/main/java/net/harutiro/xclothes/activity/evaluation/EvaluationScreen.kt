package net.harutiro.xclothes.activity.evaluation

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
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
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import net.harutiro.test_bottomnavigation_withjetpackcompose.screens.Spinner
import net.harutiro.xclothes.R
import net.harutiro.xclothes.models.AddSpinaers
import net.harutiro.xclothes.models.Clothes
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


    val openDialog = remember { mutableStateOf(false) }

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
                count = tabTitles.size,
                state = pagerState,
            ) { tabIndex ->

                PhotoView(tabTitles[tabIndex])
                name(modifier = Modifier.align(Alignment.BottomEnd))

                GoodButton(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(0.dp, 0.dp, 24.dp, 178.dp)
                        .size(60.dp),

                    onClick = {
                        openDialog.value = true
                    }

                )
            }




        }
    }
}

@Composable
fun SimpleAlertDialog(openDialog: Boolean , fix:() -> Unit) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
                fix()
            },
            title = {
                Text("服の詳細だよ")
            },
            text = {
                ClothesList()
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

@OptIn(ExperimentalSnapperApi::class, ExperimentalFoundationApi::class)
@Composable
fun ClothesList(){
    val lazyListState: LazyListState = rememberLazyListState()

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

    val names = remember { mutableStateListOf(
        huku1,
        huku2
    ) }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        state = lazyListState,
        flingBehavior = rememberSnapperFlingBehavior(lazyListState),
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
                    Spinner(
                        questionLabel = "ブランド" ,
                        suggestions = listOf(
                            AddSpinaers("GU",ImageVector.vectorResource(id = R.drawable.gu_logo)),
                            AddSpinaers("ユニクロ",ImageVector.vectorResource(id = R.drawable.uniqlo)),
                            AddSpinaers("しまむら",ImageVector.vectorResource(id = R.drawable.simamura)),
                        ),
                        fix = { text , icon ->
                        },
                        text = name.brand,
                        comeIcon = name.brandIcon
                    )

                    Spinner(
                        questionLabel = "カテゴリ",
                        suggestions = listOf(
                            AddSpinaers("トップス",Icons.Filled.CurrencyYen),
                            AddSpinaers("Tシャツ",Icons.Filled.CurrencyYen),
                            AddSpinaers("ボトムス",Icons.Filled.CurrencyYen),
                            AddSpinaers("パンツ",Icons.Filled.CurrencyYen),
                            AddSpinaers("靴下",Icons.Filled.CurrencyYen),
                        ),
                        fix = { text , icon ->
                        },
                        text = name.category,
                        comeIcon = name.categoryIcon
                    )

                    Spinner(
                        questionLabel = "価格帯",
                        suggestions = listOf(
                            AddSpinaers("0~1000",Icons.Filled.CurrencyYen),
                            AddSpinaers("1001~3000",Icons.Filled.CurrencyYen),
                            AddSpinaers("3001~5000",Icons.Filled.CurrencyYen),
                            AddSpinaers("5001~10000",Icons.Filled.CurrencyYen),
                            AddSpinaers("10001~",Icons.Filled.CurrencyYen),
                        ),
                        fix = { text , icon ->
                        },
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
    questionLabel:String,suggestions: List<AddSpinaers> =
        listOf(
            AddSpinaers("Item1",Icons.Filled.Style),
            AddSpinaers("Item1",Icons.Filled.Delete),
            AddSpinaers("Item1",Icons.Filled.AddAPhoto),
        ),
    fix:(String,ImageVector) -> Unit,
    text:String,
    comeIcon:ImageVector
){
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(text) }

    var dropDownWidth by remember { mutableStateOf(0) }

    val icon = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
    var leadingIcon by remember { mutableStateOf(comeIcon) }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {
                selectedText = it
                fix(it , leadingIcon)
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    //フォーカスを外すようにする。
                    focusManager.clearFocus()
                }
                .onSizeChanged {
                    dropDownWidth = it.width
                },
            label = {Text(questionLabel)},
            trailingIcon = {
                Icon(icon,"contentDescription", Modifier.clickable { expanded = !expanded })
            },
            leadingIcon = {
                Icon(leadingIcon,"contentDescription")
            },
            enabled = false
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
fun name(modifier:Modifier){
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