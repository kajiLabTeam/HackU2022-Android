package net.harutiro.xclothes.models.login.get

import android.text.style.LineHeightSpan
import net.harutiro.xclothes.nav.BottomBarScreen

data class GetLoginResponse(
    var user_find:Boolean = false,
    var icon:String = "",
    var name:String = "",
    var uuid:String = "",
    var gender:Int = 0,
    var height:Int = 0,
    var age:String = "",
    var mail:String = "",
    var status:Boolean = false,
    var message:String = "",
)
