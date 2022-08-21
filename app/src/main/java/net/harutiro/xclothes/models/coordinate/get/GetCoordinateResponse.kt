package net.harutiro.xclothes.models.coordinate.get

import net.harutiro.xclothes.models.coordinate.CoordinateItems

data class GetCoordinateResponse(
    var coordinate_id: String = "",
    var image: String = "",
    var Items:MutableList<CoordinateItems> = mutableListOf(),
    var users:CoordinateUsers = CoordinateUsers(),
    var status:Boolean = false
)
data class CoordinateUsers(
    var id: String = "",
    var name: String = "",
    var gender:Int = 0,
    var age:String = "",
    var height:Int = 0,
    var uuid:String = "",
    var mail: String = "",
    var icon:String = "",
)

