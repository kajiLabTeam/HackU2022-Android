package net.harutiro.xclothes.screens.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import net.harutiro.xclothes.models.login.ApiLoginMethod
import net.harutiro.xclothes.models.login.post.PostLoginRequestBody


class ProfileViewModel(application: Application): AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    var isNewProfile = false

    var userDataClass = PostLoginRequestBody()

    val apiLoginMethod = ApiLoginMethod()

    var data: SharedPreferences = application.getSharedPreferences("DataSave", Context.MODE_PRIVATE)
    var editor = data.edit()

    fun loginPost(activity: Activity){
        if(isNewProfile){
            apiLoginMethod.loginPost(userDataClass){
                activity.finish()
            }
        }else{

        }

        val gson = Gson()
        val json = gson.toJson(userDataClass)
        editor.putString(json,"")
        editor.apply()
    }



}