package net.harutiro.xclothes.models.coordinate

import androidx.room.Entity

@Entity
data class CoordinateItems(
    var category: String = "",
    var brand: String = "",
    var price: String = "",
)

