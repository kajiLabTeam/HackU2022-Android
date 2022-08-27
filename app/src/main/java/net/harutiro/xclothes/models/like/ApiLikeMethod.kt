package net.harutiro.xclothes.models.like

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.harutiro.xclothes.R
import net.harutiro.xclothes.models.like.get.GetLikeResponse
import net.harutiro.xclothes.models.like.post.PostLikeRequestBody
import net.harutiro.xclothes.models.like.post.PostLikeResponse
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.lang.reflect.Type


class ApiLikeMethod {

    val CONNECTION_TIMEOUT_MILLISECONDS = 1000
    val READ_TIMEOUT_MILLISECONDS = 1000

    private val client = OkHttpClient.Builder()
        .connectTimeout(CONNECTION_TIMEOUT_MILLISECONDS.toLong(), java.util.concurrent.TimeUnit.MILLISECONDS)
        .readTimeout(READ_TIMEOUT_MILLISECONDS.toLong(), java.util.concurrent.TimeUnit.MILLISECONDS)
        .build()

    fun likeAllGet(context: Context,id: String, nextStepFunc:(List<MutableList<GetLikeResponse>>) -> Unit) {

        val serverUrl = context.getString(R.string.server_url)

        // Requestを作成
        val request = Request.Builder()
            .url( serverUrl + "users/$id/coordinates/likes")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                var coordinateLikes = listOf<MutableList<GetLikeResponse>>()

                if(response.code == 200){

                    // Responseの読み出し
                    val responseBody = response.body?.string().orEmpty()

                    Log.d("App", responseBody)

                    val gson = Gson()
                    val listType: Type = object : TypeToken<List<MutableList<GetLikeResponse?>?>>() {}.type
                    coordinateLikes = gson.fromJson(responseBody, listType)


                    Log.d("App", coordinateLikes.toString())
                    // 必要に応じてCallback

                }
                nextStepFunc(coordinateLikes)

            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error", e.toString())
                // 必要に応じてCallback
            }
        })
    }

    fun likeGet(context: Context,coordinateId: String, nextStepFunc:(MutableList<GetLikeResponse>) -> Unit) {

        val serverUrl = context.getString(R.string.server_url)

        // Requestを作成
        val request = Request.Builder()
            .url( serverUrl + "coordinates/$coordinateId/likes")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                var coordinateLikes = mutableListOf<GetLikeResponse>()

                if(response.code == 200){

                    // Responseの読み出し
                    val responseBody = response.body?.string().orEmpty()

                    Log.d("App", responseBody)

                    val gson = Gson()
                    val listType: Type = object : TypeToken<MutableList<GetLikeResponse?>?>() {}.type
                    coordinateLikes = gson.fromJson(responseBody, listType)


                    Log.d("App", coordinateLikes.toString())
                    // 必要に応じてCallback

                }
                nextStepFunc(coordinateLikes)

            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error", e.toString())
                // 必要に応じてCallback
            }
        })
    }

    fun likePost(context: Context, userId: String, postLikeRequestBody: PostLikeRequestBody, nextStepFunc: () -> Unit){

        val gson = Gson()

        val jsonData = gson.toJson(postLikeRequestBody)

        val JSON_MEDIA = "application/json; charset=utf-8".toMediaType()

        val serverUrl = context.getString(R.string.server_url)

        val request = Request.Builder()
            .url(serverUrl + "coordinates/${userId}/likes")
            .post(jsonData.toRequestBody(JSON_MEDIA))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                // Responseの読み出し
                val responseBody = response.body?.string().orEmpty()
                val code = response.code

                Log.d("App", responseBody)
                Log.d("App", code.toString())

                if(code == 201){

                    val gson = Gson()
                    val apiResponseStatus = gson.fromJson(responseBody, PostLikeResponse::class.java)

                    Log.d("App", apiResponseStatus.toString())
                    // 必要に応じてCallback

                    nextStepFunc()
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error", e.toString())
                // 必要に応じてCallback
            }
        })
    }

}