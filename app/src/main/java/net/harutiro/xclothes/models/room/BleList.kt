package net.harutiro.xclothes.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BleList (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var bleUuid:String,
)