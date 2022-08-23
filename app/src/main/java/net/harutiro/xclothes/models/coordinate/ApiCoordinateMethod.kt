package net.harutiro.xclothes.models.coordinate

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import net.harutiro.xclothes.R
import net.harutiro.xclothes.models.coordinate.get.GetCoordinateResponse
import net.harutiro.xclothes.models.coordinate.post.PostCoordinateRequestBody
import net.harutiro.xclothes.models.coordinate.post.PostCoordinateResponse
import net.harutiro.xclothes.models.login.get.GetLoginResponse
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class ApiCoordinateMethod {

    val CONNECTION_TIMEOUT_MILLISECONDS = 1000
    val READ_TIMEOUT_MILLISECONDS = 1000

    private val client = OkHttpClient.Builder()
        .connectTimeout(CONNECTION_TIMEOUT_MILLISECONDS.toLong(), java.util.concurrent.TimeUnit.MILLISECONDS)
        .readTimeout(READ_TIMEOUT_MILLISECONDS.toLong(), java.util.concurrent.TimeUnit.MILLISECONDS)
        .build()

    fun coordinateGet(context: Context, ble:String, nextStepFunc:() -> Unit){

        val serverUrl = context.getString(R.string.server_url)

        val request = Request.Builder()
            .url(serverUrl + "cordinate:?ble=$ble")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                // Responseの読み出し
                val responseBody = response.body?.string().orEmpty()

                Log.d("App", responseBody)

                val gson = Gson()
                val coordinateDate = gson.fromJson(responseBody, GetCoordinateResponse::class.java)

                Log.d("App", coordinateDate.toString())
                // 必要に応じてCallback

                nextStepFunc()
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error", e.toString())
                // 必要に応じてCallback
            }
        })
    }

    fun coordinatePost(context:Context,postCoordinateRequestBody: PostCoordinateRequestBody,nextStepFunc: () -> Unit){

        val gson = Gson()

        val jsonData = gson.toJson(postCoordinateRequestBody)

        val JSON_MEDIA = "application/json; charset=utf-8".toMediaType()

        val serverUrl = context.getString(R.string.server_url)

        val request = Request.Builder()
            .url(serverUrl + "cordinate")
            .post(jsonData.toRequestBody(JSON_MEDIA))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                // Responseの読み出し
                val responseBody = response.body?.string().orEmpty()

                Log.d("App", responseBody)

                val gson = Gson()
                val apiResponseStatus = gson.fromJson(responseBody, PostCoordinateResponse::class.java)

                Log.d("App", apiResponseStatus.toString())
                // 必要に応じてCallback

                nextStepFunc()
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error", e.toString())
                // 必要に応じてCallback
            }
        })
    }
}