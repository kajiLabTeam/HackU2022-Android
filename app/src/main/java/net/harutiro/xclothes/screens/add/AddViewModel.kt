package net.harutiro.xclothes.screens.add

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddViewModel : ViewModel() {

    var count: Int = 0
        private set

    fun putToastHello(context:Context) {
        Toast.makeText(context, "This is a Sample Toast", Toast.LENGTH_LONG).show()
    }

}

