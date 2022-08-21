package net.harutiro.xclothes.screens.profile

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import net.harutiro.xclothes.models.login.post.PostLoginRequestBody

class ProfileViewModel(application: Application): AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    var isNewProfile = false

    var userDataClass = PostLoginRequestBody()

    fun loginPost(){
        if(isNewProfile){
            postNewUser()
        }else{
            postFixUser()
        }
    }

    fun postNewUser(){

    }

    fun postFixUser(){

    }


}