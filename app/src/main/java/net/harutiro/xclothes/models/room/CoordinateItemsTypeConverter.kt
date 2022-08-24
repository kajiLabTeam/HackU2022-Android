package net.harutiro.xclothes.models.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.harutiro.xclothes.models.coordinate.CoordinateItems

class CoordinateItemsTypeConverter {

    @TypeConverter
    fun fromCoordinateItems(value:String?): MutableList<CoordinateItems>?{
        val gson = Gson()
        val listType = object : TypeToken<MutableList<CoordinateItems>>() {}.type
        val list: MutableList<CoordinateItems> = gson.fromJson(value, listType)

        return if(value == null) null else list
    }

    @TypeConverter
    fun toCoordinateItems(date: MutableList<CoordinateItems>?):String?{
        return Gson().toJson(date)
    }
}