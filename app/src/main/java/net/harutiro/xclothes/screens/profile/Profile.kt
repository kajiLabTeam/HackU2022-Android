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

    viewModel.userDataClass = userDataClass

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
                            AddSpinaers("男性",ImageVector.vectorResource(id = R.drawable.man_fill0_wght400_grad0_opsz48)),
                            AddSpinaers("女性",ImageVector.vectorResource(id = R.drawable.woman_fill0_wght400_grad0_opsz48))
                        ),
                        fix = { text , icon ->
                            var genderNumber = if(text == "男性"){ 1 } else { 2 }
                            viewModel.userDataClass.gender = genderNumber
                        },
                        text = viewModel.userDataClass.gender.toString(),
                        comeIcon = if(viewModel.userDataClass.gender == 1){
                            ImageVector.vectorResource(id = R.drawable.man_fill0_wght400_grad0_opsz48)
                        }else{
                            ImageVector.vectorResource(id = R.drawable.woman_fill0_wght400_grad0_opsz48)
                        }
                    )
                    Spacer(modifier = Modifier.padding(top = padding))

                    EditTextField(
                        questionLabel = "年齢",
                        fix = {
                            viewModel.userDataClass.age = it
                        },
                        text = viewModel.userDataClass.age,
                        comeIcon = ImageVector.vectorResource(id = R.drawable.gender_neutral_user_icon_icons_com_55902),
                        keyboardType = KeyboardType.Number,

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

                    saveButton(isNewProfile , viewModel.userDataClass)
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
fun saveButton(isNewProfile: Boolean, userDataClass: PostLoginRequestBody) {
    val activity = LocalContext.current as Activity

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
        ){
        Button(
            onClick = {

                //Todo: Postをおくって新規ユーザー登録をする
                Log.d("Saves", userDataClass.toString())
                if(isNewProfile){
                    activity.finish()
                }

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