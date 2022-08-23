package net.harutiro.xclothes.screens.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import net.harutiro.xclothes.R
import net.harutiro.xclothes.models.login.ApiLoginMethod
import net.harutiro.xclothes.models.login.get.GetLoginResponse
import net.harutiro.xclothes.models.login.post.PostLoginRequestBody


class ProfileViewModel(application: Application): AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    var isNewProfile = false
    var userDataClass = PostLoginRequestBody()
    val apiLoginMethod = ApiLoginMethod()

    var data: SharedPreferences = application.getSharedPreferences("DataSave", Context.MODE_PRIVATE)
    var editor = data.edit()

    var userId = ""

    fun loginPost(activity: Activity){
        if(isNewProfile){
            apiLoginMethod.loginPost(context,userDataClass){
                activity.finish()
            }
        }else{

        }

        val gson = Gson()
        val json = gson.toJson(userDataClass)
        editor.putString("userData",json)
        editor.apply()
    }

    fun getUserData(){
        val json = data.getString("userData", "")
        val gson = Gson()
        val userData = gson.fromJson(json, GetLoginResponse::class.java)
        userDataClass.icon = userData.icon
        userDataClass.age = userData.age
        userDataClass.name = userData.name
        userDataClass.gender = userData.gender
        userDataClass.height = userData.height
        userDataClass.uuid = userData.uuid
        userDataClass.mail = userData.mail
        userId = userData.id
    }



}