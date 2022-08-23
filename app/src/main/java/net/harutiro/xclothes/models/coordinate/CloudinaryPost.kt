package net.harutiro.xclothes.models.coordinate

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import coil.load
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import net.harutiro.xclothes.BuildConfig
import java.net.URI

class CloudinaryPost {

    private val TAG = "Cloudinary"


    fun postPhoto(context: Context, postUri: Uri, getUrl:(String) -> Unit){

        MediaManager.get().upload(postUri).callback(object : UploadCallback {
            override fun onStart(requestId: String) {
                Log.d(TAG, "onStart: " + "started")
            }

            override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
                Log.d(TAG, "onStart: " + "uploading")
            }

            override fun onSuccess(requestId: String, resultData: Map<*, *>?) {
                Log.d(TAG, "onStart: " + "usuccess" + resultData?.get("secure_url"))
                val url = resultData?.get("secure_url").toString()

                getUrl(url)
            }

            override fun onError(requestId: String?, error: ErrorInfo) {
                Log.d(TAG, "onStart: $error")
            }

            override fun onReschedule(requestId: String?, error: ErrorInfo) {
                Log.d(TAG, "onStart: $error")
            }
        }).dispatch()
    }




}