package net.harutiro.xclothes.models.coordinate.post

import net.harutiro.xclothes.models.coordinate.CoordinateItems

data class PostCoordinateRequestBody (
        var put_flag:Boolean = true,
        var user_id: String = "",
        var ble: String = "",
        var image: String = "",
        var public: Boolean = false,
        var wears:MutableList<CoordinateItems> = mutableListOf(),

)