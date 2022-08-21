package net.harutiro.xclothes.models.login

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.harutiro.xclothes.models.login.post.PostLoginResponse
import okhttp3.*
import java.io.IOException

class ApiLoginMethod {

    val CONNECTION_TIMEOUT_MILLISECONDS = 1000
    val READ_TIMEOUT_MILLISECONDS = 1000

    private val client = OkHttpClient.Builder()
        .connectTimeout(CONNECTION_TIMEOUT_MILLISECONDS.toLong(), java.util.concurrent.TimeUnit.MILLISECONDS
        )
        .readTimeout(READ_TIMEOUT_MILLISECONDS.toLong(), java.util.concurrent.TimeUnit.MILLISECONDS)
        .build()


    fun loginGet(email: String) {
        // Requestを作成
        val request = Request.Builder()
            .url("http://20.168.98.13:8080/login?mail=$email")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                // Responseの読み出し
                val responseBody = response.body?.string().orEmpty()

                Log.d("App", responseBody)

                val gson = Gson()
                val userDataClass = gson.fromJson(responseBody,PostLoginResponse::class.java)

                Log.d("App", userDataClass.toString())


                // 必要に応じてCallback
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error", e.toString())
                // 必要に応じてCallback
            }
        })
    }
}