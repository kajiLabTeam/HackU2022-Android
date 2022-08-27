package net.harutiro.xclothes.models.map.get

import net.harutiro.xclothes.models.coordinate.CoordinateItems

data class GetMapResponse (
    var id: String = "",
    var put_flag:String = "",
    var public:String = "",
    var image: String = "",
    var wears:MutableList<CoordinateItems> = mutableListOf(),
    var user_id:String = "",
    var lat:Float = 0f,
    var lon:Float = 0f,
)