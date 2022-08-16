package net.harutiro.xclothes.activity.main

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat.startForegroundService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.harutiro.xclothes.service.ForegroundIbeaconOutputServise
import org.altbeacon.beacon.Identifier
import org.altbeacon.beacon.Region
import pub.devrel.easypermissions.EasyPermissions

class MainViewModel : ViewModel(){

    val IBEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"

    val region = Region("unique-id-001", null , Identifier.parse("777"), null)

    //intent用のID指定。Intentから戻ってくる時に使うとかなんとか。
    val PERMISSION_REQUEST_CODE = 1

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

    fun checkPermission(activity:Activity , context: Context){
        //パーミッション確認
        if (!EasyPermissions.hasPermissions(context, *permissions)) {
            // パーミッションが許可されていない時の処理
            EasyPermissions.requestPermissions(activity, "パーミッションに関する説明", PERMISSION_REQUEST_CODE, *permissions)
        }
    }

    fun startService(context: Context){
        //intentのインスタンス化
        val intent = Intent(context, ForegroundIbeaconOutputServise::class.java)
        //値をintentした時に受け渡しをする用
        intent.putExtra("UUID","d02975fb-84ab-4350-8cd7-4d5446240558")
        intent.putExtra("MAJOR","777")
        intent.putExtra("MINOR","0")

        //サービスの開始
        //パーミッションの確認をする。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && EasyPermissions.hasPermissions(context, *permissions)) {
            startForegroundService(context,intent)
        }
    }


}