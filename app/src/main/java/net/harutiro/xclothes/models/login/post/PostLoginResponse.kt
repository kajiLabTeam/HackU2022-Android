package net.harutiro.xclothes.models.login.post

data class PostLoginResponse(
    val id: String,
    val name: String,
    val uuid: String,
    val icon: String,
    val gender: String,
    val age: String,
    val height: Int,
    val mail: String,
    val status: String,
)
