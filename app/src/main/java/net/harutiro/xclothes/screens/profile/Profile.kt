package net.harutiro.xclothes.screens

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import net.harutiro.xclothes.R
import net.harutiro.xclothes.ui.theme.XclothesTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen() {


    Surface(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ){

        Column(
            modifier = Modifier.padding(24.dp)
        ){

            Spacer(modifier = Modifier.padding(top = 16.dp))
            icon()
            Spacer(modifier = Modifier.padding(top = 64.dp))
            textInput()
            Spacer(modifier = Modifier.padding(top = 32.dp))
            radioButton()
            Spacer(modifier = Modifier.padding(top = 32.dp))
            ageEditText()
            Spacer(modifier = Modifier.padding(top = 32.dp))
            heightEditText()


        }
    }
}

@Composable
fun heightEditText(){
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .heightIn()
    ){

        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40 dp
                .size(42.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
//                        .fillMaxWidth()

        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)

        ) {
            var text by remember { mutableStateOf("") }
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(text = "身長") },
                placeholder = { Text(text = "身長を入力してください")},
                singleLine = true,
                keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}

@Composable
fun ageEditText(){
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .heightIn()
    ){

        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40 dp
                .size(42.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
//                        .fillMaxWidth()

        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)

        ) {
            var text by remember { mutableStateOf("") }
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(text = "年齢") },
                placeholder = { Text(text = "年齢を入力してください")},
                singleLine = true,
                keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}

@Composable
fun ageSpinner(){
    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("","Item2","Item3")
    var selectedText by remember { mutableStateOf("") }

    var textfieldSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (expanded)
        Icons.Filled.ArrowDropUp //it requires androidx.compose.material:material-icons-extended
    else
        Icons.Filled.ArrowDropDown


    Column() {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                }
                .clickable {
                    expanded = !expanded
                },
            label = {Text("Label")},
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){textfieldSize.width.toDp()})
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = label
                        expanded = false
                              },
                    text = { Text(text = label) }
                )
            }
        }
    }
}

@Composable
fun radioButton() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val selectedGender = remember { mutableStateOf("") }
        Row {
            IconToggleButton(
                checked = selectedGender.value == Gender.male ,
                onCheckedChange = { selectedGender.value = Gender.male }
            ) {
                Icon(
                    painter = painterResource(R.drawable.man_fill0_wght400_grad0_opsz48),
                    contentDescription = "Radio button icon",
                    tint = if(selectedGender.value == Gender.male) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            Text(Gender.male)
            IconToggleButton(
                checked = selectedGender.value == Gender.female ,
                onCheckedChange = { selectedGender.value = Gender.female }
            ) {
                Icon(
                    painter = painterResource(R.drawable.woman_fill0_wght400_grad0_opsz48),
                    contentDescription = "Radio button icon",
                    tint = if(selectedGender.value == Gender.female) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            Text(Gender.female)


        }
    }
}
object Gender {
    const val male = "Male"
    const val female = "Female"
}


@Composable
fun textInput(){

    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .heightIn()
    ){

        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40 dp
                .size(42.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
//                        .fillMaxWidth()

        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)

        ) {
            var text by remember { mutableStateOf("") }
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(text = "名前") },
                placeholder = { Text(text = "名前を入力してください")},
                singleLine = true,
            )
        }
    }


}



@Composable
fun icon(){
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)

    ){
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40 dp
                .size(120.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
//                        .fillMaxWidth()

        )
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun agePreview() {
    XclothesTheme{
        Scaffold(
            modifier = Modifier.heightIn()
        ) {
            ageEditText()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun radioButtonPreview() {
    XclothesTheme{
        Scaffold(
            modifier = Modifier.heightIn()
        ) {
            radioButton()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun textInputPreview() {
    XclothesTheme{
        Scaffold(
            modifier = Modifier.heightIn()
        ) {
            textInput()
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun iconPreview() {
    XclothesTheme{
        Scaffold(
            modifier = Modifier.heightIn()
        ) {
            icon()
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun ProfileScreenPreview() {
    XclothesTheme{
        ProfileScreen()
    }
}