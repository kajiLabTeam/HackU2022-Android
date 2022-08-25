package net.harutiro.xclothes.models.like.get

data class GetLikeResponse (
    var id:String = "",
    var lat:Float = 0f,
    var lon:Float = 0f,
    var send_user_id:String = "",
    var receive_user_id:String = "",
    var coordinate_id:String = ""
)