package net.harutiro.xclothes.models.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.harutiro.xclothes.models.login.get.GetLoginResponse

@Dao
interface GetLoginResponseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(get: GetLoginResponse)

    //クエリの中身
    @Query("select * from getloginresponse")
    fun getAll(): LiveData<List<GetLoginResponse>>
    //変更があったときに自動で取得をしてくれる。

    //クエリの中身
    @Query("select * from getloginresponse")
    fun getAllNatural(): List<GetLoginResponse>

    @Query("delete from getloginresponse")
    fun removeAll()

    @Query("select * from getloginresponse where id = :userId")
    fun getUserId(userId:String):GetLoginResponse
}