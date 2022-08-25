package net.harutiro.test_bottomnavigation_withjetpackcompose.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.harutiro.xclothes.activity.evaluation.EvaluationActivity
import net.harutiro.xclothes.activity.login.LoginActivity
import net.harutiro.xclothes.models.login.ApiLoginMethod
import net.harutiro.xclothes.models.room.BleListDAO
import net.harutiro.xclothes.models.room.BleListDatabase
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
                    apiLoginMethod.loginGet(context,"yada@aich.ac.jp",) { _, _ -> }
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

                    var data: SharedPreferences = activity.getSharedPreferences("DataSave", Context.MODE_PRIVATE)
                    var editor = data.edit()

                    editor.putString("userData","")
                    editor.putString("userId","")
                    editor.putString("ble","")
                    editor.apply()

                }
            ){
                Text("Logout")
            }

            Button(
                onClick = {
                    val db: BleListDatabase = Room.databaseBuilder(
                        context,
                        BleListDatabase::class.java,
                        "memo.db"
                    ).build()
                    val bleListDao: BleListDAO = db.bleListDAO()

                    GlobalScope.launch{
                        bleListDao.removeAll()
                    }

                }
            ){
                Text("bleListDelete")
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                val data: SharedPreferences = activity.getSharedPreferences("DataSave", Context.MODE_PRIVATE)
                var editor = data.edit()

                var isBlePosted by remember { mutableStateOf(data.getBoolean("isBlePosted",false)) }

                Switch(
                    checked = isBlePosted,
                    onCheckedChange = {
                        isBlePosted = it
                        editor.putBoolean("isBlePosted",it)
                        editor.apply()
                    },
                    modifier = Modifier.align(Alignment.CenterVertically)

                )
                Text(
                    text = "BLEを発信する",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(16.dp),
                )
            }
        }

    }
}

@Composable
@Preview
fun SettingsScreenPreview() {
    SettingsScreen()
}