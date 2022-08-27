package net.harutiro.test_bottomnavigation_withjetpackcompose.screens

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.widget.Toast
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
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.harutiro.xclothes.activity.evaluation.EvaluationActivity
import net.harutiro.xclothes.activity.login.LoginActivity
import net.harutiro.xclothes.models.like.ApiLikeMethod
import net.harutiro.xclothes.models.login.ApiLoginMethod
import net.harutiro.xclothes.models.login.get.GetLoginResponse
import net.harutiro.xclothes.models.map.ApiMapMethod
import net.harutiro.xclothes.models.room.BleListDAO
import net.harutiro.xclothes.models.room.BleListDatabase
import net.harutiro.xclothes.service.ForegroundIbeaconOutputServise
import pub.devrel.easypermissions.EasyPermissions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {

    val activity = LocalContext.current as Activity
    val context = LocalContext.current

    //許可して欲しいパーミッションの記載、
    //Android１２以上ではBlueToothの新しいパーミッションを追加する。
    val permissions = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_ADVERTISE

        )
    }else{
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    }

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
                    editor.putBoolean("isBlePosted",false)
                    editor.apply()

                    val targetIntent = Intent(context, ForegroundIbeaconOutputServise::class.java)
                    activity.stopService(targetIntent)

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

            Button(
                onClick = {
                    val responseBody = "{\n" +
                            "    \"id\": \"-0MlNSjap\",\n" +
                            "    \"ble\": \"aaa\",\n" +
                            "    \"mail\": \"fuma@aitech.ac.jp\",\n" +
                            "    \"name\": \"fuma kkk\",\n" +
                            "    \"gender\": 1,\n" +
                            "    \"age\": \"16~20\",\n" +
                            "    \"height\": 170,\n" +
                            "    \"icon\": \"https://res.cloudinary.com/dhbnknlos/image/upload/v1661434595/My%20Uploads/lfurynpqtv6iahehcgnw.jpg\",\n" +
                            "    \"created_at\": \"2022-08-25T15:41:00+09:00\",\n" +
                            "    \"update_at\": \"2022-08-25T22:36:41+09:00\"\n" +
                            "}"

                    val gson = Gson()
                    val userDataClass = gson.fromJson(responseBody, GetLoginResponse::class.java)

                    Log.d("gson",userDataClass.toString())

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

                        if(it){
                            //intentのインスタンス化
                            val intent = Intent(context, ForegroundIbeaconOutputServise::class.java)
                            val bleUUID = data.getString("ble", "")
                            if(bleUUID.isNullOrBlank()){
                                Toast.makeText(context,"服情報を発信できませんでした。", Toast.LENGTH_SHORT).show()
                            }else{
                                //値をintentした時に受け渡しをする用
                                intent.putExtra("UUID",bleUUID)
                                intent.putExtra("MAJOR","777")
                                intent.putExtra("MINOR","0")

                                //サービスの開始
                                //パーミッションの確認をする。
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && EasyPermissions.hasPermissions(context, *permissions)) {
                                    ContextCompat.startForegroundService(context, intent)
                                }
                            }
                        }else{
                            val targetIntent = Intent(context, ForegroundIbeaconOutputServise::class.java)
                            activity.stopService(targetIntent)
                        }
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

            Button(
                onClick = {
                    val data: SharedPreferences = activity.getSharedPreferences("DataSave", Context.MODE_PRIVATE)

                    val apiMapMethod = ApiMapMethod()
                    apiMapMethod.mapGet(context,data.getString("userId","").toString()){
                        Log.d("apiget",it.toString())
                    }
                }
            ) {
                Text("Mapを受け取る")
            }

            Button(
                onClick = {
                    val data: SharedPreferences = activity.getSharedPreferences("DataSave", Context.MODE_PRIVATE)

                    val apiLikeMethod = ApiLikeMethod()
                    apiLikeMethod.likeAllGet(context,data.getString("userId","").toString()){
                        Log.d("apiget",it.toString())
                    }
                }
            ) {
                Text("自分のIDから評価をすべて受け取る")
            }

            Button(
                onClick = {
                    val data: SharedPreferences = activity.getSharedPreferences("DataSave", Context.MODE_PRIVATE)

                    val apiLikeMethod = ApiLikeMethod()
                    apiLikeMethod.likeGet(context,"0or28F0aM"){
                        Log.d("apiget",it.toString())
                    }
                }
            ) {
                Text("一つの服についての評価をもらえる")
            }

            Button(
                onClick = {
                    val data: SharedPreferences = activity.getSharedPreferences("DataSave", Context.MODE_PRIVATE)

                    val apiMapMethod = ApiMapMethod()
                    apiMapMethod.mapPublicGet(context){
                        Log.d("apiget",it.toString())
                    }
                }
            ) {
                Text("すべてのパブリックのマップを取得")
            }

            Button(
                onClick = {
                    val data: SharedPreferences = activity.getSharedPreferences("DataSave", Context.MODE_PRIVATE)

                    val apiLikeMethod = ApiLikeMethod()
                    apiLikeMethod.likeAllGet(context,data.getString("userId","").toString()){
                        Log.d("apiget",it.toString())
                    }
                }
            ) {
                Text("すべてのパブリックから評価をすべて受け取る")
            }

        }

    }
}

@Composable
@Preview
fun SettingsScreenPreview() {
    SettingsScreen()
}