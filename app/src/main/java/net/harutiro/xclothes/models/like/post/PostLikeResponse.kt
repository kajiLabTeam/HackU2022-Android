package net.harutiro.xclothes.models.like.post

data class PostLikeResponse(
    var id:String = "",
    var lat:Float = 0f,
    var lon:Float = 0f,
    var send_user_id:String = "",
    var receive_user_id:String = "",
    var coordinate_id:String = "",
    var created_at:String = "",
    var update_at:String = "",
)
