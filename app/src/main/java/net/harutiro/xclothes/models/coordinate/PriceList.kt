package net.harutiro.xclothes.models.coordinate

import net.harutiro.xclothes.R
import net.harutiro.xclothes.models.AddSpinaers

class PriceList {
    val priceList = listOf(
        AddSpinaers("0~1000", R.drawable.currency_yen_fill0_wght400_grad0_opsz48__1_),
        AddSpinaers("1001~3000", R.drawable.currency_yen_fill0_wght400_grad0_opsz48__1_),
        AddSpinaers("3001~5000", R.drawable.currency_yen_fill0_wght400_grad0_opsz48__1_),
        AddSpinaers("5001~10000", R.drawable.currency_yen_fill0_wght400_grad0_opsz48__1_),
        AddSpinaers("10001~", R.drawable.currency_yen_fill0_wght400_grad0_opsz48__1_),
    )


    fun checkPriceIcon(text:String):Int{
        var count = 0

        for (i in priceList){
            if(text == i.text){
                break
            }
            count++
        }

        return if(count >= priceList.size) R.drawable.ic_baseline_radio_button_unchecked_24 else priceList[count].iconDrawableId
    }
}