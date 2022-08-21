package net.harutiro.test_bottomnavigation_withjetpackcompose.screens

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import net.harutiro.xclothes.activity.evaluation.EvaluationActivity
import net.harutiro.xclothes.activity.login.LoginActivity
import net.harutiro.xclothes.models.login.ApiLoginMethod
import net.harutiro.xclothes.service.ForegroundIbeaconOutputServise

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {

    val activity = LocalContext.current as Activity
    val context = LocalContext.current

    Scaffold() {
        Column {
            Button(
                onClick = {
                    val targetIntent = Intent(context, ForegroundIbeaconOutputServise::class.java)
                    activity.stopService(targetIntent)
                },
                modifier = Modifier.padding(it)
            ) {
                Text("サービスを止める")
            }

            Button(
                onClick = {
                    val targetIntent = Intent(context, EvaluationActivity::class.java)
                    startActivity(context,targetIntent,null)
                },
                modifier = Modifier.padding(it)
            ) {
                Text("評価画面の起動")
            }
            Button(
                onClick = {
                    val targetIntent = Intent(context, LoginActivity::class.java)
                    startActivity(context,targetIntent,null)
                },
                modifier = Modifier.padding(it)
            ) {
                Text("ログイン画面の起動")
            }
            Button(
                onClick = {
                    val apiLoginMethod = ApiLoginMethod()
                    apiLoginMethod.loginGet("yada@aich.ac.jp",{})
                },
                modifier = Modifier.padding(it)
            ) {
                Text("postCheck")
            }
            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(context, LoginActivity::class.java)
                    startActivity(context,intent,null)

                }
            ){
                Text("Logout")
            }
        }

    }
}

@Composable
@Preview
fun SettingsScreenPreview() {
    SettingsScreen()
}