package net.harutiro.xclothes.screens.add

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import net.harutiro.xclothes.BuildConfig
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AddViewModel (application: Application): AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val TAG = "Cloudinary"


    fun photoStartUp(): Uri {
        // 保存先のフォルダー
        val cFolder: File? = context.getExternalFilesDir(Environment.DIRECTORY_DCIM)

        //        *名前関係*       //
        //　フォーマット作成
        val fileDate: String = SimpleDateFormat("ddHHmmss", Locale.US).format(Date())
        //　名前作成
        val fileName: String = String.format("CameraIntent_%s.jpg", fileDate)

        //uriの前作成
        val cameraFile: File = File(cFolder, fileName)

        //uri最終作成
        val currentPhotoUri = FileProvider.getUriForFile(context, context.packageName.toString() + ".fileprovider", cameraFile)

        return(currentPhotoUri)

    }

    fun putToastHello() {
        Toast.makeText(context, "This is a Sample Toast", Toast.LENGTH_LONG).show()
    }

    fun photoPost(filePath: String){
        val config = mapOf(
            "cloud_name" to BuildConfig.CLOUD_NAME,
            "api_key" to BuildConfig.API_KEY,
            "api_secret" to BuildConfig.API_SECRET
        )

        MediaManager.init(context, config);

        MediaManager.get().upload(filePath).callback(object : UploadCallback {
            override fun onStart(requestId: String) {
                Log.d(TAG, "onStart: " + "started")
            }

            override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
                Log.d(TAG, "onStart: " + "uploading")
            }

            override fun onSuccess(requestId: String, resultData: Map<*, *>?) {
                Log.d(TAG, "onStart: " + "usuccess" + resultData?.get("secure_url"))
            }

            override fun onError(requestId: String?, error: ErrorInfo) {
                Log.d(TAG, "onStart: $error")
            }

            override fun onReschedule(requestId: String?, error: ErrorInfo) {
                Log.d(TAG, "onStart: $error")
            }
        }).dispatch()
    }


    fun addCoordinatePost(){

    }



}

