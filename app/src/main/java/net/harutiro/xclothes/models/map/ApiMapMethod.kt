package net.harutiro.xclothes.models.map

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import net.harutiro.xclothes.R
import net.harutiro.xclothes.models.login.get.GetLoginResponse
import net.harutiro.xclothes.models.map.get.GetMapResponse
import okhttp3.*
import java.io.IOException

class ApiMapMethod {
    val CONNECTION_TIMEOUT_MILLISECONDS = 1000
    val READ_TIMEOUT_MILLISECONDS = 1000

    private val client = OkHttpClient.Builder()
        .connectTimeout(CONNECTION_TIMEOUT_MILLISECONDS.toLong(), java.util.concurrent.TimeUnit.MILLISECONDS)
        .readTimeout(READ_TIMEOUT_MILLISECONDS.toLong(), java.util.concurrent.TimeUnit.MILLISECONDS)
        .build()


    fun mapGet(context: Context, userId: String, nextStepFunc:(GetMapResponse) -> Unit) {

        val serverUrl = context.getString(R.string.server_url)

        // Requestを作成
        val request = Request.Builder()
            .url( serverUrl + "users/$userId/coordinates")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                var userCoordinateDates = GetMapResponse()

                if(response.code == 200){

                    // Responseの読み出し
                    val responseBody = response.body?.string().orEmpty()

                    Log.d("App", responseBody)

                    val gson = Gson()
//                    userCoordinateDates = gson.fromJson(responseBody, GetMapResponse::class.java)

                    Log.d("App", userCoordinateDates.toString())
                    // 必要に応じてCallback

                }
                nextStepFunc(userCoordinateDates)

            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error", e.toString())
                // 必要に応じてCallback
            }
        })
    }
}