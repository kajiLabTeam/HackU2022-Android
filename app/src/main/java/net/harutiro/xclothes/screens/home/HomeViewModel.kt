package net.harutiro.xclothes.screens.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import net.harutiro.xclothes.models.like.ApiLikeMethod
import net.harutiro.xclothes.models.like.get.GetLikeResponse
import net.harutiro.xclothes.models.login.ApiLoginMethod
import net.harutiro.xclothes.models.login.get.GetLoginResponse
import net.harutiro.xclothes.models.map.ApiMapMethod
import net.harutiro.xclothes.models.map.get.GetMapResponse
import pub.devrel.easypermissions.EasyPermissions

class HomeViewModel (application: Application): AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    val myApplication = application

    var nowLocation = LatLng(0.0, 0.0)

    //gps
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    var data: SharedPreferences = application.getSharedPreferences("DataSave", Context.MODE_PRIVATE)

    val coordinates = mutableStateOf(mutableListOf<GetMapResponse>())

    var likes = mutableStateOf(listOf<MutableList<GetLikeResponse>>())


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

    fun myCoordinates(){
        val apiMapMethod = ApiMapMethod()
        apiMapMethod.mapGet(context,data.getString("userId","").toString()){
            coordinates.value = it
        }
    }

    fun myLikesGet(){
        val apiLikeMethod = ApiLikeMethod()
        apiLikeMethod.likeAllGet(context,data.getString("userId","").toString()){
            likes.value = it
        }

    }

    fun userGet(userId:String,getFunc:(GetLoginResponse) -> Unit){
        val apiLoginMethod = ApiLoginMethod()
        apiLoginMethod.loginUserIdGet(context,userId){ userDate , code ->

            getFunc(userDate)

        }
    }

    fun likeGet(coordinateId:String,getFunc:(MutableList<GetLikeResponse>) -> Unit){
        val apiLikeMethod = ApiLikeMethod()
        apiLikeMethod.likeGet(context,coordinateId){
            getFunc(it)
        }
    }


    @SuppressLint("MissingPermission")
    fun getLocation( getLocation:(Location?) -> Unit) {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(myApplication)

        if (EasyPermissions.hasPermissions(context, *permissions)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
                Log.d("gps",location.toString())

                getLocation(location)

            }
        }

    }

}