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
import com.cloudinary.android.MediaManager
import net.harutiro.xclothes.BuildConfig
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

        viewModel.databaseBuild(this)

        viewModel.cloudinaryBuild(this)

        viewModel.checkPermission(this,this)

        if(EasyPermissions.hasPermissions(this, *viewModel.permissions)){
            viewModel.startService(this )
            createNotificationChannel()
            viewModel.ibeacon(this,this,this)
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
        createNotificationChannel()
        viewModel.didEnterRegion(this)
    }

    override fun didExitRegion(region: Region?) {
    }

    override fun didDetermineStateForRegion(state: Int, region: Region?) {
    }

    override fun didRangeBeaconsInRegion(beacons: MutableCollection<Beacon>?, region: Region?) {
        viewModel.didRangeBeaconsInRegion(beacons)
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    XclothesTheme {
        MainScreen()
    }
}