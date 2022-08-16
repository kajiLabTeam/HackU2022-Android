package net.harutiro.xclothes.screens.add

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AddViewModel (application: Application): AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

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

}

