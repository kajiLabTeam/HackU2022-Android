package net.harutiro.xclothes.models.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import net.harutiro.xclothes.models.coordinate.get.CoordinateUsers

class CoordinateUsersTypeConverter {
    @TypeConverter
    fun fromCoordinateUsers(value:String?):CoordinateUsers?{
        val gson = Gson()
        val coordinateUsers = gson.fromJson(value,CoordinateUsers::class.java)

        return if(value == null) null else coordinateUsers
    }

    @TypeConverter
    fun toCoordinateUsers(date:CoordinateUsers?):String?{
        return Gson().toJson(date)
    }
}