package net.harutiro.xclothes

import android.app.Application
import com.cloudinary.android.MediaManager

class CloudinalyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val config = mapOf(
            "cloud_name" to BuildConfig.CLOUD_NAME,
            "api_key" to BuildConfig.API_KEY,
            "api_secret" to BuildConfig.API_SECRET
        )

        MediaManager.init(this, config)
    }
}