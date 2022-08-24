package net.harutiro.xclothes.models.coordinate.get

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import net.harutiro.xclothes.models.coordinate.CoordinateItems

@Entity
data class GetCoordinateResponse(
    @PrimaryKey
    var coordinate_id: String = "",
    var image: String = "",
    var items:MutableList<CoordinateItems> = mutableListOf(),
    var users:CoordinateUsers = CoordinateUsers(),
    var status:Boolean = false
)

@Entity
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

