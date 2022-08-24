package net.harutiro.xclothes.activity.evaluation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.room.Room
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.harutiro.xclothes.models.coordinate.get.GetCoordinateResponse
import net.harutiro.xclothes.models.room.BleListDAO
import net.harutiro.xclothes.models.room.BleListDatabase
import net.harutiro.xclothes.models.room.GetCoordinateResponseDAO

class EvaluationViewModel :ViewModel() {

    var clothePages = listOf<GetCoordinateResponse>( )

    private lateinit var db:BleListDatabase
    lateinit var bleListDao: BleListDAO
    lateinit var getCoordinateResponseDAO: GetCoordinateResponseDAO

    @OptIn(DelicateCoroutinesApi::class)
    fun databaseBuild(context: Context, lifecycle: LifecycleOwner){
        this.db = Room.databaseBuilder(
            context,
            BleListDatabase::class.java,
            "memo.db"
        ).build()

        this.bleListDao = this.db.bleListDAO()
        this.getCoordinateResponseDAO = this.db.getCoordinateResponseDAO()

        GlobalScope.launch{
            clothePages = getCoordinateResponseDAO.getAllNatural()
        }
    }


}