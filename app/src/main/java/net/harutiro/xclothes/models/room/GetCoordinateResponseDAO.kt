package net.harutiro.xclothes.models.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.harutiro.xclothes.models.coordinate.get.GetCoordinateRequestBody
import net.harutiro.xclothes.models.coordinate.get.GetCoordinateResponse

@Dao
interface GetCoordinateResponseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(get:GetCoordinateResponse)

    //クエリの中身
    @Query("select * from getcoordinateresponse")
    fun getAll(): LiveData<List<GetCoordinateResponse>>
    //変更があったときに自動で取得をしてくれる。

    //クエリの中身
    @Query("select * from getcoordinateresponse")
    fun getAllNatural(): List<GetCoordinateResponse>

    @Query("delete from getcoordinateresponse")
    fun removeAll()
}