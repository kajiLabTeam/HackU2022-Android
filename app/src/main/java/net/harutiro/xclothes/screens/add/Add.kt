package net.harutiro.test_bottomnavigation_withjetpackcompose.screens

import android.app.Activity
import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import net.harutiro.xclothes.R
import net.harutiro.xclothes.models.AddSpinaers
import net.harutiro.xclothes.models.Clothes
import net.harutiro.xclothes.models.coordinate.BrandList
import net.harutiro.xclothes.models.coordinate.CategoryList
import net.harutiro.xclothes.models.coordinate.PriceList
import net.harutiro.xclothes.models.coordinate.post.PostCoordinateRequestBody
import net.harutiro.xclothes.screens.add.AddViewModel
import net.harutiro.xclothes.ui.theme.XclothesTheme
import java.util.*

@Composable
fun AddScreen(viewModel: AddViewModel) {

    var urlRemember by remember { mutableStateOf("") }
    val uri = viewModel.photoStartUp()

    val activity = LocalContext.current as Activity
    val context = LocalContext.current

    //写真を取ったあとのURIを受け取る
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { _ ->
        viewModel.addCoordinatePost(uri){
            //TODO ロード中画面ぐるぐるにする
            urlRemember = it
        }
//        uriRemember = uri.toString()
    }

    Surface (
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.padding(0.dp,0.dp,0.dp,70.dp),
    ){
        Column (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())

        ){
            if(urlRemember.isEmpty()){
                NottingPhotoIcon(launcher,uri)
            }else{
                PhotoView(urlRemember)
            }

            IsReleaseSwitch(viewModel)

            ClothesList(viewModel)
            Spacer(modifier = Modifier.padding(16.dp))
            SaveButton(){
                Log.d("checkValue", viewModel.checkedState.toString())

                for (i in viewModel.clothe){
                    Log.d("checkValue", i.toString())
                }

                viewModel.pushApi(activity)

            }
            Spacer(modifier = Modifier.padding(16.dp))


        }
    }
}

@Composable
fun IsReleaseSwitch(viewModel: AddViewModel) {

//    val checkedState = remember { mutableStateOf(true) }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {

        Switch(
            checked = viewModel.checkedState,
            onCheckedChange = { viewModel.checkedState = it },
            modifier = Modifier.align(Alignment.CenterVertically)

        )
        Text(
            text = "公開範囲",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(16.dp),
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalSnapperApi::class
)
@Composable
fun ClothesList(viewModel: AddViewModel){


    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxWidth()

    ) {

        Column() {
            OutlinedButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                shape = RoundedCornerShape(20),
                onClick = {
                    viewModel.clothe.add(Clothes())
                }
            ) {
                Text("服を追加")
            }

            val lazyListState: LazyListState = rememberLazyListState()

            val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp.value
            val itemWidthDp = 300f
            val xForCenteredItemDp = ((screenWidthDp - itemWidthDp) / 2) - 12

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                state = lazyListState,
                flingBehavior = rememberSnapperFlingBehavior(lazyListState),
            ) {

                item {
                    Box(
                        modifier = Modifier.width(xForCenteredItemDp.dp)
                    )
                }

                itemsIndexed(
                    items = viewModel.clothe,
                    key = {index ,i ->
                        i.id
                    }

                ) { index , name ->

                    AnimatedVisibility(
                        modifier = Modifier.animateItemPlacement(),
                        visible = viewModel.clothe.contains(name),
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {

                        Card(Modifier.width(itemWidthDp.dp)) {
                            IconButton(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.End),
                                onClick = {
                                    viewModel.clothe.remove(name)
                                }
                            ){
                                Icon(Icons.Filled.Delete,"contentDescription")
                            }

                            Spinner(
                                questionLabel = "ブランド" ,
                                suggestions = BrandList().brandlist,
                                fix = { text , icon ->
                                    viewModel.clothe[index] = Clothes(
                                        id = name.id,
                                        category = name.category,
                                        categoryIcon = name.categoryIcon,
                                        brand = text,
                                        brandIcon = icon,
                                        price = name.price,
                                        priceIcon = name.priceIcon
                                    )
                                },
                                text = name.brand,
                                comeIcon = name.brandIcon
                            )

                            Spinner(
                                questionLabel = "カテゴリ",
                                suggestions = CategoryList().categoryList,
                                fix = { text , icon ->
                                    viewModel.clothe[index] = Clothes(
                                        id = name.id,
                                        category = text,
                                        categoryIcon = icon,
                                        brand = name.brand,
                                        brandIcon = name.brandIcon,
                                        price = name.price,
                                        priceIcon = name.priceIcon
                                    )
                                },
                                text = name.category,
                                comeIcon = name.categoryIcon
                            )

                            Spinner(
                                questionLabel = "価格帯",
                                suggestions = PriceList().priceList,
                                fix = { text , icon ->
                                    viewModel.clothe[index] = Clothes(
                                        id = name.id,
                                        category = name.category,
                                        categoryIcon = name.categoryIcon,
                                        brand = name.brand,
                                        brandIcon = name.brandIcon,
                                        price = text,
                                        priceIcon = icon
                                    )
                                },
                                text = name.price,
                                comeIcon = name.priceIcon
                            )

                        }
                    }

                }

                item {
                    Box(
                        modifier = Modifier.width(xForCenteredItemDp.dp)
                    )
                }
            }
        }
    }
}



@Composable
fun SaveButton(onClick: () -> Unit){

    val context = LocalContext.current

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ){
        Button(
            onClick = {
                Toast.makeText(context, "服を登録しました", Toast.LENGTH_SHORT).show()
                onClick()
            },
        ) {
            Text("保存をする")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Spinner(
    questionLabel:String,suggestions: List<AddSpinaers> =
    listOf(
        AddSpinaers("Item1",R.drawable.ic_launcher_foreground),
        AddSpinaers("Item1",R.drawable.ic_launcher_foreground),
        AddSpinaers("Item1",R.drawable.ic_launcher_foreground),
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
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){dropDownWidth.toDp()})
        ) {
            suggestions.forEach { label ->

                val icon = ImageVector.vectorResource(label.iconDrawableId)

                DropdownMenuItem(
                    onClick = {
                        selectedText = label.text
                        leadingIcon = icon
                        expanded = false
                        fix(selectedText , leadingIcon)
                    },
                    text = {
                        Row{
                            Icon(icon,"contentDescription")
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text(text = label.text)
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun NottingPhotoIcon(launcher: ManagedActivityResultLauncher<Uri, Boolean>, uri: Uri) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary)
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
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary),
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
        )
    }
}

@Composable
fun PhotoView(urlRemember:String){
    Box(){
        AsyncImage(
            model = urlRemember,
            contentDescription = "My Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        )

//        Text(
//            text = "This is a dog.",
//            color = Color.White,
//            modifier = Modifier
//                .padding(32.dp)
//                .align(alignment = Alignment.BottomStart)
//        )
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
        AddScreen(viewModel())
    }
}