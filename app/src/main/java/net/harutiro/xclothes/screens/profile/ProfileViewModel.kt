package net.harutiro.xclothes.screens.profile

import android.annotation.SuppressLint
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

    fun loginPost(){
        if(isNewProfile){
        }else{
        }
    }



}