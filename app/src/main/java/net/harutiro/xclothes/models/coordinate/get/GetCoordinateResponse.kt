package net.harutiro.xclothes.models.coordinate.get

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import net.harutiro.xclothes.models.coordinate.CoordinateItems

@Entity
data class GetCoordinateResponse(
    @PrimaryKey
    var id: String = "",
    var put_flag:String = "",
    var public:String = "",
    var image: String = "",
    var wears:MutableList<CoordinateItems> = mutableListOf(),
    var user_id:String = "",
    var lat:Float = 0f,
    var lon:Float = 0f,
)