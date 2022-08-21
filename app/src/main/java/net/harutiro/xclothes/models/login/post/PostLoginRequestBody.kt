package net.harutiro.xclothes.models.login.post

data class PostLoginRequestBody(
    var name: String = "",
    var uuid: String = "",
    var icon: String = "",
    var gender: Int = 0,
    var age: String = "",
    var height: Int = 0,
    var mail: String = "",
)
