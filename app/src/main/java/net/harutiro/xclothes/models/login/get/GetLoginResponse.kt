package net.harutiro.xclothes.models.login.get

import android.text.style.LineHeightSpan
import net.harutiro.xclothes.nav.BottomBarScreen

data class GetLoginResponse(
    var id:String = "",
    var name:String = "",
    var gender:Int = 0,
    var age:String = "",
    var height:Int = 0,
    var uuid:String = "",
    var mail:String = "",
    var icon:String = "",
    var message:String = "",
)
