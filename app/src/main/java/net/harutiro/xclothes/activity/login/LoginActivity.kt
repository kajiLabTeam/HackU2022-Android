package net.harutiro.xclothes.activity.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import net.harutiro.xclothes.ui.theme.XclothesTheme

class LoginActivity : ComponentActivity() {

    private val viewModel : LoginViewModel by lazy { ViewModelProvider.NewInstanceFactory().create(LoginViewModel::class.java) }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            XclothesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(viewModel)

//                    AsyncImage(
//                        contentDescription = "My Picture",
//                        contentScale = ContentScale.Fit,
//                        model = auth.currentUser?.photoUrl,
//                        modifier = Modifier
//                            .size(32.dp)
//                    )
                }
            }
        }
    }

    //戻るボタンの無効化
    override fun onBackPressed() {}


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, resultCode, data ,this , this)
    }

//
//
//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if(currentUser != null){
////            reload();
//        }
//    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    XclothesTheme {
    }
}