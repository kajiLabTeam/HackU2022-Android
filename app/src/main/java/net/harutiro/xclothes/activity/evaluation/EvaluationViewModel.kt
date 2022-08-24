package net.harutiro.xclothes.activity.evaluation

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.room.Room
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.harutiro.xclothes.models.coordinate.get.GetCoordinateResponse
import net.harutiro.xclothes.models.login.ApiLoginMethod
import net.harutiro.xclothes.models.login.get.GetLoginResponse
import net.harutiro.xclothes.models.room.BleListDAO
import net.harutiro.xclothes.models.room.BleListDatabase
import net.harutiro.xclothes.models.room.GetCoordinateResponseDAO
import net.harutiro.xclothes.models.room.GetLoginResponseDAO

class EvaluationViewModel :ViewModel() {

    var clothePages = listOf<GetCoordinateResponse>( )
    var users = mutableListOf<GetLoginResponse>()

    private lateinit var db:BleListDatabase
    lateinit var bleListDao: BleListDAO
    lateinit var getCoordinateResponseDAO: GetCoordinateResponseDAO
    lateinit var getLoginResponseDAO: GetLoginResponseDAO

    @OptIn(DelicateCoroutinesApi::class)
    fun databaseBuild(context: Context, lifecycle: LifecycleOwner){
        this.db = Room.databaseBuilder(
            context,
            BleListDatabase::class.java,
            "memo.db"
        ).build()

        this.bleListDao = this.db.bleListDAO()
        this.getCoordinateResponseDAO = this.db.getCoordinateResponseDAO()
        this.getLoginResponseDAO = this.db.getLoginResponseDAO()

        GlobalScope.launch{
            clothePages = getCoordinateResponseDAO.getAllNatural()
            for(i in clothePages){
                users.add(getLoginResponseDAO.getUserId(i.user_id))
            }
            getCoordinateResponseDAO.removeAll()
            getLoginResponseDAO.removeAll()
        }
    }


}