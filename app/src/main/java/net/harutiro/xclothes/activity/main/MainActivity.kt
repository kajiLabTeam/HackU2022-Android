package net.harutiro.xclothes.activity.main

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.harutiro.xclothes.MainScreen
import net.harutiro.xclothes.R
import net.harutiro.xclothes.activity.evaluation.EvaluationActivity
import net.harutiro.xclothes.ui.theme.XclothesTheme
import org.altbeacon.beacon.*
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : ComponentActivity(), RangeNotifier ,MonitorNotifier{

    private val viewModel :MainViewModel by lazy { ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java) }
    val TAG = "mainActivity"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.checkPermission(this,this)

        if(EasyPermissions.hasPermissions(this, *viewModel.permissions)){
            viewModel.startService(this )
            createNotificationChannel()
            ibeacon()
        }

        setContent {
            XclothesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun ibeacon(){
        //ここからビーコン関係
        if (EasyPermissions.hasPermissions(this, *viewModel.permissions)) {
            val beaconManager = BeaconManager.getInstanceForApplication(this)

            // 初期化。これがないと画面を２回以上開いた時に２重でデータを受信してしまう
            beaconManager.removeAllMonitorNotifiers()
            beaconManager.removeAllRangeNotifiers()
            beaconManager.rangedRegions.forEach {region ->
                beaconManager.stopRangingBeacons(region)
                beaconManager.stopMonitoring(region)

            }


            beaconManager.beaconParsers.clear()
            beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout(viewModel.IBEACON_FORMAT))


            // Uncomment the code below to use a foreground service to scan for beacons. This unlocks
            // the ability to continually scan for long periods of time in the background on Andorid 8+
            // in exchange for showing an icon at the top of the screen and a always-on notification to
            // communicate to users that your app is using resources in the background.
            //
            val builder = Notification.Builder(this)
            builder.setSmallIcon(R.drawable.ic_launcher_foreground)
            builder.setContentTitle("すれ違いをチェック中")
            val intent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_MUTABLE
            )
            builder.setContentIntent(pendingIntent)

            builder.setChannelId("search_notify")

            beaconManager.enableForegroundServiceScanning(builder.build(), 456)

            beaconManager.setEnableScheduledScanJobs(false)
            beaconManager.backgroundBetweenScanPeriod = 0
            beaconManager.backgroundScanPeriod = 1100

            Log.d(TAG, "setting up background monitoring in app onCreate")
            beaconManager.addMonitorNotifier(this)


            // If we were monitoring *different* regions on the last run of this app, they will be
            // remembered.  In this case we need to disable them here
            for (region in beaconManager.monitoredRegions) {
                beaconManager.stopMonitoring(region!!)
            }

            beaconManager.startMonitoring(viewModel.region)

            beaconManager.addRangeNotifier(this)

            beaconManager.startRangingBeacons(viewModel.region)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {

        val channelId = "search_notify"
        val channelName = "すれ違いのときのサービスを起動通知をするちゃんねる"
        val channel2 = NotificationChannel(
            channelId, channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel2)

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = "すれ違い検出通知"
        val descriptionText = "すれ違いを検知した時に通知するチャンネルです。"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("room_inside_notify", name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun didEnterRegion(arg0: Region?) {
        Log.d(TAG, "did enter region.")

        createNotificationChannel()

        val intent = Intent(this, EvaluationActivity::class.java)

        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)

        val builder = NotificationCompat.Builder(this, "room_inside_notify")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("すれ違いました！！")
            .setContentText("タップして服の評価をお願いします。")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            // Set the intent that will fire when the user taps the notification
            .setFullScreenIntent(pendingIntent,true)
            .setAutoCancel(true)


                with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(22, builder.build())
        }
    }

    override fun didExitRegion(region: Region?) {
    }

    override fun didDetermineStateForRegion(state: Int, region: Region?) {
    }

    override fun didRangeBeaconsInRegion(beacons: MutableCollection<Beacon>?, region: Region?) {
        // 検知したBeaconの情報
        Log.d("MainActivity", "beacons.size ${beacons?.size}")
        beacons?.let {
            for (beacon in beacons) {
                Log.d("MainActivity", "UUID: ${beacon.id1}, major: ${beacon.id2}, minor: ${beacon.id3}, RSSI: ${beacon.rssi}, TxPower: ${beacon.txPower}, Distance: ${beacon.distance}")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    XclothesTheme {
        MainScreen()
    }
}