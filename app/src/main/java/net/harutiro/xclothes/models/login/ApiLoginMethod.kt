package net.harutiro.xclothes.models.login

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.harutiro.xclothes.R
import net.harutiro.xclothes.models.login.get.GetLoginResponse
import net.harutiro.xclothes.models.login.post.PostLoginRequestBody
import net.harutiro.xclothes.models.login.post.PostLoginResponse
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class ApiLoginMethod {

    val CONNECTION_TIMEOUT_MILLISECONDS = 1000
    val READ_TIMEOUT_MILLISECONDS = 1000

    private val client = OkHttpClient.Builder()
        .connectTimeout(CONNECTION_TIMEOUT_MILLISECONDS.toLong(), java.util.concurrent.TimeUnit.MILLISECONDS)
        .readTimeout(READ_TIMEOUT_MILLISECONDS.toLong(), java.util.concurrent.TimeUnit.MILLISECONDS)
        .build()


    fun loginGet(context: Context,email: String, nextStepFunc:(GetLoginResponse) -> Unit) {

        val serverUrl = context.getString(R.string.server_url)

        // Requestを作成
        val request = Request.Builder()
            .url( serverUrl + "login?mail=$email")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                // Responseの読み出し
                val responseBody = response.body?.string().orEmpty()

                Log.d("App", responseBody)

                val gson = Gson()
                val userDataClass = gson.fromJson(responseBody, GetLoginResponse::class.java)

                Log.d("App", userDataClass.toString())
                // 必要に応じてCallback

                nextStepFunc(userDataClass)
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error", e.toString())
                // 必要に応じてCallback
            }
        })
    }

    fun loginPost(context: Context, userDataClass:PostLoginRequestBody, nextStepFunc:(PostLoginResponse) -> Unit){
        val gson = Gson()

        val jsonData = gson.toJson(userDataClass)

        val JSON_MEDIA = "application/json; charset=utf-8".toMediaType()

        val serverUrl = context.getString(R.string.server_url)

        // Requestを作成
        val request = Request.Builder()
            .url(serverUrl + "login")
            .post(jsonData.toRequestBody(JSON_MEDIA))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                // Responseの読み出し
                val responseBody = response.body?.string().orEmpty()

                Log.d("App", responseBody)

                val userDataClass = gson.fromJson(responseBody,PostLoginResponse::class.java)

                Log.d("App", userDataClass.toString())
                // 必要に応じてCallback

                nextStepFunc(userDataClass)
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error", e.toString())
                // 必要に応じてCallback
            }
        })


    }
}