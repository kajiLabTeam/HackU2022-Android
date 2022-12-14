package net.harutiro.xclothes.activity.login

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.startIntentSenderForResult
import androidx.core.app.AppLaunchChecker
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import net.harutiro.xclothes.models.login.ApiLoginMethod
import net.harutiro.xclothes.models.login.post.PostLoginRequestBody
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import pub.devrel.easypermissions.EasyPermissions
import java.io.IOException

class LoginViewModel: ViewModel() {

    private lateinit var auth: FirebaseAuth

    val TAG = "AuthDebug"

    private lateinit var oneTapClient: SignInClient

    val default_web_client_id_auth = "930686882818-acl9s28jpbmmcuvumuv6nrol9ifqfg17.apps.googleusercontent.com"

    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity

    var navController: NavHostController? = null

    var userDataClass: PostLoginRequestBody = PostLoginRequestBody()

    val apiLoginMethod = ApiLoginMethod()

    val PERMISSION_REQUEST_CODE = 1


    //許可して欲しいパーミッションの記載、
    //Android１２以上ではBlueToothの新しいパーミッションを追加する。
    val permissions = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_ADVERTISE

        )
    }else{
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    }

    fun login(activity: Activity) {
        // Initialize Firebase Auth
        auth = Firebase.auth

        oneTapClient = Identity.getSignInClient(activity)

        Log.d(TAG, auth.currentUser?.photoUrl.toString())


        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(default_web_client_id_auth)
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(false)
                    .build())
            .build();



        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(activity) { result ->
                try {
                    startIntentSenderForResult(activity,
                        result.pendingIntent.intentSender, REQ_ONE_TAP,
                        null, 0, 0, 0, null)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e("Auth2", "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(activity) { e ->
                // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
                e.localizedMessage?.let { Log.d("Auth3", it) }
            }
    }

    fun checkFirstStart(context: Context,activity:Activity){
        if(AppLaunchChecker.hasStartedFromLauncher(context)){
            Log.d("AppLaunchChecker","2回目以降");
        } else {
            AlertDialog.Builder(context) // FragmentではActivityを取得して生成
                .setTitle("位置情報の取り扱い")
                .setMessage("このアプリでは位置情報によるアラート機能を可能にするために、現在地のデータが収集されます。\n" +
                        "アプリを閉じている時や、使用していないときにも収集されます。\n" +
                        "位置情報は、個人を特定できない統計的な情報として、\n" +
                        "お知らせの配信、位置情報の利用を許可しない場合は、\n" +
                        "この後表示されるダイアログで「許可しない」を選択してください。")
                .setPositiveButton("OK") { dialog, which ->
                    if (!EasyPermissions.hasPermissions(context, *permissions)) {
                        // パーミッションが許可されていない時の処理
                        EasyPermissions.requestPermissions(activity, "パーミッションに関する説明", PERMISSION_REQUEST_CODE, *permissions)
                    }
                }
                .show()
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent? , activity: Activity , context: Context){
        when (requestCode) {
            REQ_ONE_TAP -> {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
                    val idToken = credential.googleIdToken
                    when {
                        idToken != null -> {
                            // Got an ID token from Google. Use it to authenticate
                            // with Firebase.
                            Log.d(TAG, "Got ID token.")

                            firebaseCertification(data,activity,context)
                        }
                        else -> {
                            // Shouldn't happen.
                            Log.d(TAG, "No ID token!")
                        }
                    }
                } catch (e: ApiException) {

                    Log.d(TAG,e.toString())
                    // ...
                }
            }
        }
    }

    fun firebaseCertification (data: Intent?, activity: Activity , context:Context){
        val googleCredential = oneTapClient.getSignInCredentialFromIntent(data)
        val idToken = googleCredential.googleIdToken


        when {
            idToken != null -> {
                // Got an ID token from Google. Use it to authenticate
                // with Firebase.
                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener(activity) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success")
                            val user = auth.currentUser

                            Log.d(TAG, user?.email.toString())

                            userDataClass.icon = user?.photoUrl.toString()
                            userDataClass.name = user?.displayName.toString()
                            userDataClass.mail = user?.email.toString()

                            apiLoginMethod.loginGet(context , userDataClass.mail){ userDataClass , code ->
                                if(code == 200){
                                    //TODO:ユーザーデータを保存する

                                    val data: SharedPreferences = activity.getSharedPreferences("DataSave", Context.MODE_PRIVATE)
                                    val editor = data.edit()
                                    val gson = Gson()
                                    val json = gson.toJson(userDataClass)
                                    editor.putString("userData",json)
                                    editor.putString("userId",userDataClass.id.toString())
                                    editor.putString("ble",userDataClass.ble.toString())
                                    editor.apply()

                                    activity.finish()
                                }else{
                                    //メインスレッドで行うようにしてい
                                    activity.runOnUiThread(){
                                        navController?.navigate("second")
                                    }
                                }
                            }


//                                updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.exception)
//                                updateUI(null)
                        }
                    }
            }
            else -> {
                // Shouldn't happen.
                Log.d(TAG, "No ID token!")
            }
        }
    }

    fun logout(){
        FirebaseAuth.getInstance().signOut()
    }
}