package net.harutiro.xclothes.models.login.get

import android.text.style.LineHeightSpan
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Entity
import androidx.room.PrimaryKey
import net.harutiro.xclothes.nav.BottomBarScreen

@Entity
data class GetLoginResponse(
    @PrimaryKey
    var id:String = "",
    var name:String = "",
    var gender:Int = 0,
    var age:String = "",
    var height:Int = 0,
    var ble:String = "",
    var mail:String = "",
    var icon:String = "",
    var message:String = "",
)
