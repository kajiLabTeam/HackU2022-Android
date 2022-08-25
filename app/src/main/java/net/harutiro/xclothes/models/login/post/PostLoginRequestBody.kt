package net.harutiro.xclothes.models.login.post

import java.util.*

data class PostLoginRequestBody(
    var name: String = "",
    var ble: String = UUID.randomUUID().toString(),
    var icon: String = "",
    var gender: Int = 0,
    var age: String = "",
    var height: Int = 0,
    var mail: String = "",
)
