package net.harutiro.test_bottomnavigation_withjetpackcompose.screens

import android.content.res.Configuration
import android.net.Uri
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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import net.harutiro.xclothes.R
import net.harutiro.xclothes.models.AddSpinaers
import net.harutiro.xclothes.models.Clothes
import net.harutiro.xclothes.screens.add.AddViewModel
import net.harutiro.xclothes.ui.theme.XclothesTheme

@Composable
fun AddScreen(viewModel: AddViewModel) {

    var urlRemember by remember { mutableStateOf("") }
    val uri = viewModel.photoStartUp()

    //写真を取ったあとのURIを受け取る
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { _ ->
        viewModel.addCoordinatePost(uri){
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

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                val checkedState = remember { mutableStateOf(true) }
                Switch(
                    checked = checkedState.value,
                    onCheckedChange = { checkedState.value = it },
                    modifier = Modifier.align(Alignment.CenterVertically)

                )
                Text(
                    text = "公開範囲",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(16.dp),
                )
            }

            ClothesList()
            Spacer(modifier = Modifier.padding(16.dp))
            SaveButton()
            Spacer(modifier = Modifier.padding(16.dp))


        }
    }
}



@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalSnapperApi::class
)
@Composable
fun ClothesList(){
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxWidth()

    ) {
        val names = remember { mutableStateListOf(Clothes()) }

        Column() {
            OutlinedButton(
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp),
                shape = RoundedCornerShape(20),
                onClick = {
                    names.add(Clothes())
                }
            ) {
                Text("服を追加")
            }

//            Button(
//                onClick = {
//                    for(i in names){
//                        Log.d("test",i.id)
//                        Log.d("test",i.category)
//                        Log.d("test",i.brand)
//                        Log.d("test",i.price)
//                    }
//                }
//            ) {
//                Text("呼び出し")
//            }

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

                        Card(Modifier.width(itemWidthDp.dp)) {
                            IconButton(
                                modifier = Modifier.padding(16.dp).align(Alignment.End),
                                onClick = {
                                    names.remove(name)
                                }
                            ){
                                Icon(Icons.Filled.Delete,"contentDescription")
                            }

                            Spinner(
                                questionLabel = "ブランド" ,
                                suggestions = listOf(
                                    AddSpinaers("GU",ImageVector.vectorResource(id = R.drawable.gu_logo)),
                                    AddSpinaers("ユニクロ",ImageVector.vectorResource(id = R.drawable.uniqlo)),
                                    AddSpinaers("しまむら",ImageVector.vectorResource(id = R.drawable.simamura)),
                                ),
                                fix = { text , icon ->
                                    names[index] = Clothes(
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
                                suggestions = listOf(
                                    AddSpinaers("トップス",Icons.Filled.CurrencyYen),
                                    AddSpinaers("Tシャツ",Icons.Filled.CurrencyYen),
                                    AddSpinaers("ボトムス",Icons.Filled.CurrencyYen),
                                    AddSpinaers("パンツ",Icons.Filled.CurrencyYen),
                                    AddSpinaers("靴下",Icons.Filled.CurrencyYen),
                                ),
                                fix = { text , icon ->
                                    names[index] = Clothes(
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
                                suggestions = listOf(
                                    AddSpinaers("0~1000",Icons.Filled.CurrencyYen),
                                    AddSpinaers("1001~3000",Icons.Filled.CurrencyYen),
                                    AddSpinaers("3001~5000",Icons.Filled.CurrencyYen),
                                    AddSpinaers("5001~10000",Icons.Filled.CurrencyYen),
                                    AddSpinaers("10001~",Icons.Filled.CurrencyYen),
                                ),
                                fix = { text , icon ->
                                    names[index] = Clothes(
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
fun SaveButton(){

    val context = LocalContext.current

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ){
        Button(
            onClick = {
                Toast.makeText(context, "服を登録しました", Toast.LENGTH_SHORT).show()
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
                DropdownMenuItem(
                    onClick = {
                        selectedText = label.text
                        leadingIcon = label.icon
                        expanded = false
                        fix(selectedText , leadingIcon)
                    },
                    text = {
                        Row{
                            Icon(label.icon,"contentDescription")
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