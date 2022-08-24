package net.harutiro.xclothes.models.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BleList::class],version = 1 , exportSchema = false)
abstract class BleListDatabase:RoomDatabase() {
    abstract fun bleListDAO():BleListDAO
}