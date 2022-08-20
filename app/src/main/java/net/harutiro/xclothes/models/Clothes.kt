package net.harutiro.xclothes.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.CurrencyYen
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import net.harutiro.xclothes.R
import java.util.*

data class Clothes(
    var id:String = UUID.randomUUID().toString(),
    var category:String = "",
    var categoryIcon:ImageVector = Icons.Filled.Android,
    var brand:String = "",
    var brandIcon:ImageVector = Icons.Filled.Android,
    var price:String = "",
    var priceIcon:ImageVector = Icons.Filled.CurrencyYen,


    )