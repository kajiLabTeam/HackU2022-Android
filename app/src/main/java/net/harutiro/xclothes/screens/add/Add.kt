package net.harutiro.test_bottomnavigation_withjetpackcompose.screens

import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import net.harutiro.xclothes.R
import net.harutiro.xclothes.screens.Gender
import net.harutiro.xclothes.screens.add.AddViewModel
import net.harutiro.xclothes.ui.theme.XclothesTheme
import java.util.*

@Composable
fun AddScreen(viewModel: AddViewModel) {

    var uriRemember by remember { mutableStateOf("") }
    val uri = viewModel.photoStartUp()
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { _ ->
        uriRemember = uri.toString()
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
            if(uriRemember.isEmpty()){
                NottingPhotoIcon(launcher,uri)
            }else{
                PhotoView(uriRemember)
            }

            Spinner("公開範囲",listOf("する","しない"))
            SaveButton()
            Spacer(modifier = Modifier.padding(16.dp))

            ClothesList()


        }
    }
}

data class Clothes(
    var id:String = UUID.randomUUID().toString(),
    var category:String = "",
    var brand:String = "",
    var price:String = "",
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalSnapperApi::class
)
@Composable
fun ClothesList(){
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        val names = remember { mutableStateListOf<Clothes>() }

        Column() {
            Button(
                onClick = {
                    if(names.size > 0){
                        names.add(names.size - 1,Clothes())
                    }else {
                        names.add(Clothes())
                    }
                }
            ) {
                Text("追加")
            }

            Button(
                onClick = {
                    for(i in names){
                        Log.d("test",i.id)
                        Log.d("test",i.category)
                        Log.d("test",i.brand)
                        Log.d("test",i.price)
                    }
                }
            ) {
                Text("呼び出し")
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
//                            Column(
//                                horizontalAlignment = Alignment.CenterHorizontally
//                            ){
//
//                                var text by remember { mutableStateOf("") }
//
//                                OutlinedTextField(
//                                    value = text,
//                                    onValueChange = {
//                                        text = it
//                                        names[index] = Clothes(name.id, it)
//                                    },
//                                    label = { Text(text = "名前") },
//                                    placeholder = { Text(text = "名前を入力してください") },
//                                    singleLine = true,
//                                )
//                            }

                            Button(
                                modifier = Modifier.padding(16.dp),
                                onClick = {
                                    names.remove(name)
                                }
                            ){
                                Text("delete")
                            }

                            Spinner("服の種類")
                            Spinner("体の部位")
                            Spinner("価格帯",listOf("0~1000","1001~3000","3001~5000","5001~10000","10001~"))

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
fun Spinner(questionLabel:String,suggestions: List<String> = listOf("Item1","Item2","Item3")){
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    var dropDownWidth by remember { mutableStateOf(0) }

    val icon = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown


    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
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
                Icon(Icons.Filled.Style,"contentDescription")
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
                        selectedText = label
                        expanded = false
                    },
                    text = {
                        Row{
                            Icon(Icons.Filled.Style,"contentDescription")
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text(text = label)
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
fun PhotoView(uriRemember:String){
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
        AddScreen(viewModel())
    }
}