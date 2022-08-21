package net.harutiro.xclothes.models.login.get

import android.text.style.LineHeightSpan
import net.harutiro.xclothes.nav.BottomBarScreen

data class GetLoginResponse(
    var user_find:Boolean,
    var icon:String,
    var name:String,
    var uuid:String,
    var gender:Int,
    var height:Int,
    var age:String,
    var mail:String,
)
