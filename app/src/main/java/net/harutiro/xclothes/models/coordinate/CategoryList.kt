package net.harutiro.xclothes.models.coordinate

import net.harutiro.xclothes.R
import net.harutiro.xclothes.models.AddSpinaers

class CategoryList {
    val categoryList = listOf(
        AddSpinaers("ジャケット", R.drawable.jacket),
        AddSpinaers("スカート", R.drawable.skirt),
        AddSpinaers("ズボン", R.drawable.pants),
        AddSpinaers("ワンピース", R.drawable.one_piece),
        AddSpinaers("Tシャツ", R.drawable.s_thirt),
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