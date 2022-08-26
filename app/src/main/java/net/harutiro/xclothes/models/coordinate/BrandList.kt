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