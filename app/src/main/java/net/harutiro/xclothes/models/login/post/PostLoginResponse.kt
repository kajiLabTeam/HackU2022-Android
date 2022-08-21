package net.harutiro.xclothes.models.login.post

data class PostLoginResponse(
    val id: String = "",
    val name: String = "",
    val uuid: String = "",
    val icon: String = "",
    val gender: String = "",
    val age: String = "",
    val height: Int = 0,
    val mail: String = "",
    val status: Boolean = false,
)
