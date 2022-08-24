package net.harutiro.xclothes.models.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BleListDAO {

    @Insert
    fun insert(memo:BleList)

    @Query("select * from blelist where bleUuid = :bleUuid LIMIT 1")
    fun checkBleList(bleUuid: String):BleList?

    //クエリの中身
    @Query("select * from blelist")
    fun getAll(): LiveData<List<BleList>>
    //変更があったときに自動で取得をしてくれる。
}