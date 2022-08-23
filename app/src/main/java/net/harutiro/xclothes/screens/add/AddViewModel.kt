package net.harutiro.xclothes.screens.add

import android.annotation.SuppressLint
import android.app.Application
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import net.harutiro.xclothes.models.coordinate.ApiCoordinateMethod
import net.harutiro.xclothes.models.coordinate.CloudinaryPost
import net.harutiro.xclothes.models.coordinate.post.PostCoordinateRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AddViewModel (application: Application): AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val TAG = "Cloudinary"


    //Composableのデータ
    var checkedState by mutableStateOf(false)



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

    fun addCoordinatePost(uri:Uri,getUrl:(String) -> Unit){
        CloudinaryPost().postPhoto(context,uri){
            getUrl(it)
        }
    }

    fun pushApi(postCoordinateRequestBody: PostCoordinateRequestBody) {
        val apiCoordinateMethod = ApiCoordinateMethod()
        apiCoordinateMethod.coordinatePost(
            context = context,
            postCoordinateRequestBody = postCoordinateRequestBody,
            nextStepFunc = {

            }
        )
    }


}

