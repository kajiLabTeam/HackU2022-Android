package net.harutiro.xclothes.screens.add

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import net.harutiro.xclothes.models.Clothes
import net.harutiro.xclothes.models.coordinate.ApiCoordinateMethod
import net.harutiro.xclothes.models.coordinate.CloudinaryPost
import net.harutiro.xclothes.models.coordinate.CoordinateItems
import net.harutiro.xclothes.models.coordinate.post.PostCoordinateRequestBody
import net.harutiro.xclothes.service.ForegroundIbeaconOutputServise
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AddViewModel (application: Application): AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    var data: SharedPreferences = application.getSharedPreferences("DataSave", Context.MODE_PRIVATE)


    private val TAG = "Cloudinary"


    //Composableのデータ
    var checkedState by mutableStateOf(false)
    var clothe = mutableStateListOf(Clothes())

    var imageUrl = ""

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





    fun photoStartUp(): Uri {
        // 保存先のフォルダー
        val cFolder: File? = context.getExternalFilesDir(Environment.DIRECTORY_DCIM)

        //        *名前関係*       //
        //　フォーマット作成
        val fileDate: String = SimpleDateFormat("ddHHmmss", Locale.US).format(Date())
        //　名前作成
        val fileName: String = String.format("CameraIntent_%s.jpg", fileDate)

        //uriの前作成
        val cameraFile: File = File(cFolder, fileName)

        //uri最終作成
        val currentPhotoUri = FileProvider.getUriForFile(context, context.packageName.toString() + ".fileprovider", cameraFile)

        return(currentPhotoUri)

    }

    fun putToastHello() {
        Toast.makeText(context, "This is a Sample Toast", Toast.LENGTH_LONG).show()
    }

    fun addCoordinatePost(uri:Uri,getUrl:(String) -> Unit){
        CloudinaryPost().postPhoto(context,uri){
            imageUrl = it
            getUrl(it)
        }
    }

    fun pushApi(activity: Activity) {

        val coordinateItems = mutableListOf<CoordinateItems>()

        for (i in clothe){
            coordinateItems.add(
                CoordinateItems(
                    category = i.category,
                    brand = i.brand,
                    price = i.price,
                )
            )
        }


        val postCoordinateRequestBody = PostCoordinateRequestBody()
        postCoordinateRequestBody.user_id = data.getString("userId","").toString()
        postCoordinateRequestBody.image = imageUrl
        postCoordinateRequestBody.wears = coordinateItems
        
        val apiCoordinateMethod = ApiCoordinateMethod()
        apiCoordinateMethod.coordinatePost(
            context = context,
            postCoordinateRequestBody = postCoordinateRequestBody,
            nextStepFunc = {
                val data: SharedPreferences = activity.getSharedPreferences("DataSave", Context.MODE_PRIVATE)
                val editor = data.edit()

                val isBlePosted = data.getBoolean("isBlePosted",false)
                if(!isBlePosted){
                    editor.putBoolean("isBlePosted",true)
                    editor.apply()

                    //intentのインスタンス化
                    val intent = Intent(context, ForegroundIbeaconOutputServise::class.java)
                    val bleUUID = data.getString("ble", "")
                    if(bleUUID.isNullOrBlank()){
                        Toast.makeText(context,"服情報を発信できませんでした。",Toast.LENGTH_SHORT).show()
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
                }
            }
        )
    }


}

