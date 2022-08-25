package net.harutiro.xclothes.models.coordinate

import androidx.room.Entity
import java.util.*

@Entity
data class CoordinateItems(
    var id:String = UUID.randomUUID().toString(),
    var category: String = "",
    var brand: String = "",
    var price: String = "",
)

