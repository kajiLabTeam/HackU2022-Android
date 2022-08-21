package net.harutiro.xclothes.screens.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import net.harutiro.xclothes.models.login.ApiLoginMethod
import net.harutiro.xclothes.models.login.post.PostLoginRequestBody

class ProfileViewModel(application: Application): AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    var isNewProfile = false

    var userDataClass = PostLoginRequestBody()

    val apiLoginMethod = ApiLoginMethod()

    fun loginPost(activity: Activity){
        if(isNewProfile){
            apiLoginMethod.loginPost(userDataClass){
                activity.finish()
            }
        }else{
        }
    }



}