package net.harutiro.xclothes.activity.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Location
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.ContextCompat.startForegroundService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.cloudinary.android.MediaManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.harutiro.xclothes.BuildConfig
import net.harutiro.xclothes.R
import net.harutiro.xclothes.activity.evaluation.EvaluationActivity
import net.harutiro.xclothes.activity.login.LoginActivity
import net.harutiro.xclothes.models.coordinate.ApiCoordinateMethod
import net.harutiro.xclothes.models.login.ApiLoginMethod
import net.harutiro.xclothes.models.room.*
import net.harutiro.xclothes.service.ForegroundIbeaconOutputServise
import org.altbeacon.beacon.*
import pub.devrel.easypermissions.EasyPermissions

class MainViewModel : ViewModel(){

    //DB
    private lateinit var db:BleListDatabase
    lateinit var bleListDao:BleListDAO
    lateinit var getCoordinateResponseDAO: GetCoordinateResponseDAO
    lateinit var getLoginResponseDAO: GetLoginResponseDAO




    //gps
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    val IBEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"

    val region = Region("unique-id-001", null , Identifier.parse("777"), null)

    //intent用のID指定。Intentから戻ってくる時に使うとかなんとか。
    val PERMISSION_REQUEST_CODE = 1

    //許可して欲しいパーミッションの記載、
    //Android１２以上ではBlueToothの新しいパーミッションを追加する。
    val permissions = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_ADVERTISE

        )
    }else{
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    }

    fun userCheck(context: Context,activity:Activity){
        val TAG = "login"

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // User is signed in
            Log.d(TAG,user.email.toString())
        } else {
            // No user is signed in
            Log.d(TAG,"NoAccount")

            val intent = Intent(context, LoginActivity::class.java)
            activity.startActivity(intent)
        }
    }

    fun cloudinaryBuild(context: Context){
        val config = mapOf(
            "cloud_name" to BuildConfig.CLOUD_NAME,
            "api_key" to BuildConfig.API_KEY,
            "api_secret" to BuildConfig.API_SECRET
        )

        MediaManager.init(context, config)
    }

    fun databaseBuild(context: Context){
        this.db = Room.databaseBuilder(
            context,
            BleListDatabase::class.java,
            "memo.db"
        ).build()

        this.bleListDao = this.db.bleListDAO()
        this.getCoordinateResponseDAO = this.db.getCoordinateResponseDAO()
        this.getLoginResponseDAO = this.db.getLoginResponseDAO()
    }

    fun checkPermission(activity:Activity , context: Context){
//        //パーミッション確認
//        if (!EasyPermissions.hasPermissions(context, *permissions)) {
//            // パーミッションが許可されていない時の処理
//            EasyPermissions.requestPermissions(activity, "パーミッションに関する説明", PERMISSION_REQUEST_CODE, *permissions)
//        }
    }

    fun startService(context: Context,application: Application,){

        val data: SharedPreferences = application.getSharedPreferences("DataSave", Context.MODE_PRIVATE)

        //intentのインスタンス化
        val intent = Intent(context, ForegroundIbeaconOutputServise::class.java)
        val bleUUID = data.getString("ble", "")

        if(!data.getBoolean("isBlePosted",false)){
            return
        }

        if(bleUUID.isNullOrBlank()){
            Toast.makeText(context,"服情報を発信できませんでした。",Toast.LENGTH_SHORT).show()
            return
        }

        //値をintentした時に受け渡しをする用
        intent.putExtra("UUID",bleUUID)
        intent.putExtra("MAJOR","777")
        intent.putExtra("MINOR","0")

        //サービスの開始
        //パーミッションの確認をする。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && EasyPermissions.hasPermissions(context, *permissions)) {
            startForegroundService(context,intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun ibeacon(context: Context,rangeNotifier: RangeNotifier,monitorNotifier: MonitorNotifier){
        val TAG = "BLE"

        //ここからビーコン関係
        if (EasyPermissions.hasPermissions(context, *permissions)) {
            val beaconManager = BeaconManager.getInstanceForApplication(context)

            // 初期化。これがないと画面を２回以上開いた時に２重でデータを受信してしまう
            beaconManager.removeAllMonitorNotifiers()
            beaconManager.removeAllRangeNotifiers()
            beaconManager.rangedRegions.forEach {region ->
                beaconManager.stopRangingBeacons(region)
                beaconManager.stopMonitoring(region)

            }


            beaconManager.beaconParsers.clear()
            beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout(IBEACON_FORMAT))


            // Uncomment the code below to use a foreground service to scan for beacons. This unlocks
            // the ability to continually scan for long periods of time in the background on Andorid 8+
            // in exchange for showing an icon at the top of the screen and a always-on notification to
            // communicate to users that your app is using resources in the background.
            //
            val builder = Notification.Builder(context)
            builder.setSmallIcon(R.drawable.ic_launcher_foreground)
            builder.setContentTitle("すれ違いをチェック中")
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_MUTABLE
            )
            builder.setContentIntent(pendingIntent)

            builder.setChannelId("search_notify")

            beaconManager.enableForegroundServiceScanning(builder.build(), 456)

            beaconManager.setEnableScheduledScanJobs(false)
            beaconManager.backgroundBetweenScanPeriod = 0
            beaconManager.backgroundScanPeriod = 1100

            Log.d(TAG, "setting up background monitoring in app onCreate")
            beaconManager.addMonitorNotifier(monitorNotifier)


            // If we were monitoring *different* regions on the last run of this app, they will be
            // remembered.  In this case we need to disable them here
            for (region in beaconManager.monitoredRegions) {
                beaconManager.stopMonitoring(region!!)
            }

            beaconManager.startMonitoring(region)

            beaconManager.addRangeNotifier(rangeNotifier)

            beaconManager.startRangingBeacons(region)
        }
    }

    fun didEnterRegion(context: Context){
        val TAG = "beaconDidEnter"
        Log.d(TAG, "did enter region.")

        val intent = Intent(context, EvaluationActivity::class.java)

        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE)

        val builder = NotificationCompat.Builder(context, "room_inside_notify")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("すれ違いました！！")
            .setContentText("タップして服の評価をお願いします。")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            // Set the intent that will fire when the user taps the notification
            .setFullScreenIntent(pendingIntent,true)
            .setAutoCancel(true)


        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(22, builder.build())
        }
    }

    fun didRangeBeaconsInRegion(beacons: MutableCollection<Beacon>?,context: Context,activity: Activity){
        // 検知したBeaconの情報
        Log.d("MainActivity", "beacons.size ${beacons?.size}")
        beacons?.let {
            for (beacon in beacons) {

                GlobalScope.launch{
                    Log.d("database", bleListDao.checkBleList(beacon.id1.toString())?.bleUuid ?: "notting")

                    if(bleListDao.checkBleList(beacon.id1.toString())?.bleUuid.isNullOrBlank()){

                        val apiCoordinateMethod = ApiCoordinateMethod()
                        apiCoordinateMethod.coordinateGet(context,beacon.id1.toString()) { getCoordinateResponse ->
                            getLocation(context, activity){ location ->
                                getCoordinateResponse.lat = location?.latitude?.toFloat()!!
                                getCoordinateResponse.lon = location.longitude.toFloat()

                                GlobalScope.launch{
                                    getCoordinateResponseDAO.insert(getCoordinateResponse)
                                }

                                val apiLoginMethod = ApiLoginMethod()
                                apiLoginMethod.loginUserIdGet(context,getCoordinateResponse.user_id){ user ,code ->
                                    if(code == 200){
                                        GlobalScope.launch{
                                            getLoginResponseDAO.insert(user)
                                        }
                                        didEnterRegion(context)

                                    }
                                }
                            }
                        }


                        val ble = BleList(id = 0, bleUuid = beacon.id1.toString())
                        bleListDao.insert(ble)


                    }
                }

                Log.d("MainActivity", "UUID: ${beacon.id1}, major: ${beacon.id2}, minor: ${beacon.id3}, RSSI: ${beacon.rssi}, TxPower: ${beacon.txPower}, Distance: ${beacon.distance}")
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocation(context: Context , activity: Activity,getLocation:(Location?) -> Unit) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

        if (EasyPermissions.hasPermissions(context, *permissions)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
                Log.d("gps",location.toString())

                getLocation(location)

            }
        }

    }



}