package net.harutiro.xclothes.models

import java.util.*

data class Clothes(
    var id:String = UUID.randomUUID().toString(),
    var category:String = "",
    var brand:String = "",
    var price:String = "",
)