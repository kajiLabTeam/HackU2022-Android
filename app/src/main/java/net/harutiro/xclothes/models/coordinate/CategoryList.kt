package net.harutiro.xclothes.models.coordinate

import net.harutiro.xclothes.R
import net.harutiro.xclothes.models.AddSpinaers

class CategoryList {
    val categoryList = listOf(
        AddSpinaers("トップス", R.drawable.currency_yen_fill0_wght400_grad0_opsz48__1_),
        AddSpinaers("Tシャツ", R.drawable.currency_yen_fill0_wght400_grad0_opsz48__1_),
        AddSpinaers("ボトムス", R.drawable.currency_yen_fill0_wght400_grad0_opsz48__1_),
        AddSpinaers("パンツ", R.drawable.currency_yen_fill0_wght400_grad0_opsz48__1_),
        AddSpinaers("靴下", R.drawable.currency_yen_fill0_wght400_grad0_opsz48__1_),
    )

    fun checkCategoryIcon(text:String):Int{
        var count = 0

        for (i in categoryList){
            if(text == i.text){
                break
            }
            count++
        }

        return if(count >= categoryList.size) R.drawable.ic_baseline_radio_button_unchecked_24 else categoryList[count].iconDrawableId
    }
}