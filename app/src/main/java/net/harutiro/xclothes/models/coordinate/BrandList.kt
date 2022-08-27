package net.harutiro.xclothes.models.coordinate

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import net.harutiro.xclothes.R
import net.harutiro.xclothes.models.AddSpinaers

class BrandList {
    val brandlist = listOf(
        AddSpinaers("GU",  R.drawable.gu_logo),
        AddSpinaers("ユニクロ", R.drawable.uniqlo),
        AddSpinaers("しまむら", R.drawable.simamura),
        AddSpinaers("BEAMS", R.drawable.beams),
        AddSpinaers("GAP", R.drawable.gap),
        AddSpinaers("H&M", R.drawable.h_m),
        AddSpinaers("PaulSmith", R.drawable.paulsmith),
        AddSpinaers("RAGEBLUE", R.drawable.rageblue),
        AddSpinaers("RifhtOn", R.drawable.right_on),
        AddSpinaers("WEGO", R.drawable.wego),
        AddSpinaers("ZARA", R.drawable.zara),
        AddSpinaers("無印良品", R.drawable.mujirushiryohin),
        AddSpinaers("その他", R.drawable.ic_baseline_radio_button_unchecked_24),

        )

    fun checkBrandIcon(text:String):Int{
        var count = 0

        for (i in brandlist){
            if(text == i.text){
                break
            }
            count++
        }

        return if(count >= brandlist.size) R.drawable.ic_baseline_radio_button_unchecked_24 else brandlist[count].iconDrawableId
    }
}