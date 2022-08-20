package net.harutiro.xclothes.models.login.post

data class PostLoginRequestBody(
    var name: String,
    var uuid: String,
    var icon: String,
    var gender: String,
    var age: String,
    var height: String,
    var mail: String,
)
