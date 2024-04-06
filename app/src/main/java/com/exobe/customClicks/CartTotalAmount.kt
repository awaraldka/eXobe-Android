package com.exobe.customClicks

import android.widget.RelativeLayout
import android.widget.TextView
import com.exobe.entity.request.PriceSizeDetailsRequest

interface CartTotalAmount {
    fun incrementAmmount(
        count: Int?,
        price: Number?,
        id: String?,
        quantity: String,
        quantity1: TextView,
        price1: TextView,
        increment: RelativeLayout,
        priceSizeDetails: PriceSizeDetailsRequest?
    )
    fun decrementAmmount(
        count: Int?,
        price: Number?,
        id: String?,
        quantity: String,
        quantity1: TextView,
        price1: TextView,
        decrement: RelativeLayout,
        priceSizeDetails: PriceSizeDetailsRequest?
    )

}

interface ServicesTotalAmount {
    fun incrementAmount(count: Int?,id: String?,title: String?, price:Double?,quantity: String)
    fun decrementAmount(count: Int?,id: String?,title: String?,price:Double?,quantity: String)
}