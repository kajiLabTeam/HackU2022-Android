package net.harutiro.xclothes.models.coordinate.post

import net.harutiro.xclothes.models.coordinate.CoordinateItems
import net.harutiro.xclothes.nav.BottomBarScreen

data class PostCoordinateResponse(
    var id:String = "",
    var put_flag:Boolean = false,
    var public:Boolean = false,
    var image:String = "",
    var ble:String = "",
    var user_id:String = "",
    var created_at:String = "",
    var updated_at:String = "",
    var wears:MutableList<CoordinateItems> = mutableListOf()

)
