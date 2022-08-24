package net.harutiro.xclothes.models.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import net.harutiro.xclothes.models.coordinate.get.GetCoordinateResponse
import net.harutiro.xclothes.models.login.get.GetLoginResponse

@Database(entities = [BleList::class, GetCoordinateResponse::class, GetLoginResponse::class],version = 1 , exportSchema = false)
@TypeConverters(CoordinateItemsTypeConverter::class)
abstract class BleListDatabase:RoomDatabase() {
    abstract fun bleListDAO():BleListDAO
    abstract fun getCoordinateResponseDAO():GetCoordinateResponseDAO
    abstract fun getLoginResponseDAO():GetLoginResponseDAO

}