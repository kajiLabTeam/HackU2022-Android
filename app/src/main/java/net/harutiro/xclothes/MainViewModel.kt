package net.harutiro.xclothes

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.lifecycle.ViewModel
import pub.devrel.easypermissions.EasyPermissions

class MainViewModel : ViewModel(){
    //intent用のID指定。Intentから戻ってくる時に使うとかなんとか。
    private val PERMISSION_REQUEST_CODE = 1

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
}