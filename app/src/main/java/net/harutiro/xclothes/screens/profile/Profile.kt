package net.harutiro.xclothes.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import net.harutiro.xclothes.R
import net.harutiro.xclothes.models.AddSpinaers
import net.harutiro.xclothes.models.login.post.PostLoginRequestBody
import net.harutiro.xclothes.screens.add.AddViewModel
import net.harutiro.xclothes.screens.profile.ProfileViewModel
import net.harutiro.xclothes.ui.theme.XclothesTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(userDataClass: PostLoginRequestBody, isNewProfile: Boolean , viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    viewModel.isNewProfile = isNewProfile

    if(isNewProfile) {
        viewModel.userDataClass = userDataClass
    }else{
        viewModel.getUserData()
    }

    val activity = LocalContext.current as Activity

    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.padding(0.dp,0.dp,0.dp,0.dp),
        ) {

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        ) {

            Spacer(modifier = Modifier.padding(top = 64.dp))
            icon(viewModel.userDataClass.icon)

            Card(
                modifier = Modifier
                    .padding(top = 64.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ){
                    val padding = 8.dp

                    EditTextField(
                        questionLabel = "名前",
                        fix = { name ->
                            viewModel.userDataClass.name = name
                        },
                        text = viewModel.userDataClass.name,
                        comeIcon = ImageVector.vectorResource(id = R.drawable.gender_neutral_user_icon_icons_com_55902),
                        keyboardType = KeyboardType.Text,
                    )
                    Spacer(modifier = Modifier.padding(top = padding))

                    Spinner(
                        questionLabel = "性別",
                        suggestions = listOf(
                            AddSpinaers("男性",R.drawable.man_fill0_wght400_grad0_opsz48),
                            AddSpinaers("女性",R.drawable.woman_fill0_wght400_grad0_opsz48)
                        ),
                        fix = { text , icon ->
                            var genderNumber = if(text == "男性"){ 1 } else { 2 }
                            viewModel.userDataClass.gender = genderNumber
                        },
                        text = if(viewModel.userDataClass.gender == 1) {"男性"} else {"女性"},
                        comeIcon = if(viewModel.userDataClass.gender == 1){
                            ImageVector.vectorResource(id = R.drawable.man_fill0_wght400_grad0_opsz48)
                        }else{
                            ImageVector.vectorResource(id = R.drawable.woman_fill0_wght400_grad0_opsz48)
                        }
                    )
                    Spacer(modifier = Modifier.padding(top = padding))

                    Spinner(
                        questionLabel = "年代",
                        suggestions = listOf(
                            AddSpinaers("0~10",R.drawable.gender_neutral_user_icon_icons_com_55902),
                            AddSpinaers("11~20",R.drawable.gender_neutral_user_icon_icons_com_55902),
                            AddSpinaers("21~30",R.drawable.gender_neutral_user_icon_icons_com_55902),
                            AddSpinaers("31~40",R.drawable.gender_neutral_user_icon_icons_com_55902),
                            AddSpinaers("41~50",R.drawable.gender_neutral_user_icon_icons_com_55902),
                            AddSpinaers("51~60",R.drawable.gender_neutral_user_icon_icons_com_55902),
                            AddSpinaers("61~70",R.drawable.gender_neutral_user_icon_icons_com_55902),
                            AddSpinaers("71~80",R.drawable.gender_neutral_user_icon_icons_com_55902),
                            AddSpinaers("81~90",R.drawable.gender_neutral_user_icon_icons_com_55902),
                            AddSpinaers("91~100",R.drawable.gender_neutral_user_icon_icons_com_55902),
                            AddSpinaers("101~",R.drawable.gender_neutral_user_icon_icons_com_55902),

                            ),
                        fix = { text , icon ->
                            viewModel.userDataClass.age = text
                        },
                        text = viewModel.userDataClass.age,
                        comeIcon = ImageVector.vectorResource(id = R.drawable.gender_neutral_user_icon_icons_com_55902),
                    )
                    Spacer(modifier = Modifier.padding(top = padding))

                    EditTextField(
                        questionLabel = "身長",
                        fix = {
                            try{
                                viewModel.userDataClass.height = it.toInt()
                            }catch (e:Exception){
                                // TODO: throw exception
                            }
                        },
                        text = viewModel.userDataClass.height.toString(),
                        comeIcon = ImageVector.vectorResource(id = R.drawable.gender_neutral_user_icon_icons_com_55902),
                        keyboardType = KeyboardType.Number,

                        )
                    Spacer(modifier = Modifier.padding(top = padding))

                    saveButton(){

                        Log.d("test",viewModel.userDataClass.toString())
                        viewModel.loginPost(activity)
                    }
                    Spacer(modifier = Modifier.padding(top = 70.dp))


                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Spinner(
    questionLabel:String,
    suggestions: List<AddSpinaers> =
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
        modifier = Modifier.padding(16.dp,0.dp)
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextField(
    questionLabel:String,
    fix:(String) -> Unit,
    text:String,
    comeIcon: ImageVector,
    keyboardType: KeyboardType,
){
    var selectedText by remember { mutableStateOf(text) }
    var leadingIcon by remember { mutableStateOf(comeIcon) }

    Column(
        modifier = Modifier.padding(16.dp,0.dp)
    ) {
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            value = selectedText,
            onValueChange = {
                selectedText = it
                fix(it)
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = {Text(questionLabel)},
            leadingIcon = {
                Icon(leadingIcon,"contentDescription")
            },
        )
    }
}

@Composable
fun saveButton(saveFunc:() -> Unit){
    val activity = LocalContext.current as Activity

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
        ){
        Button(
            onClick = {
                saveFunc()
            },
        ) {
            Text("保存をする")
        }
    }
}


@Composable
fun icon(icon: String) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)

    ) {
        AsyncImage(
            model = icon,
            contentDescription = "ユーザーアイコン",
            modifier = Modifier
                // Set image size to 40 dp
                .size(120.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
//                        .fillMaxWidth()
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 730,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun ProfileScreenPreview() {
    XclothesTheme {
//        ProfileScreen(viewModel.userDataClass)
    }
}