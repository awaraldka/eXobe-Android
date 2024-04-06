package com.exobe.customClicks

import android.content.Intent
import com.exobe.bottomSheet.choosePhotoBottomSheet

interface AddProductListener {
    fun addProduct(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        choosePhotoBottomSheet: choosePhotoBottomSheet,
        imagePath: String
    )
}

interface AddProfile {
    fun addImage(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        choosePhotoBottomSheet: choosePhotoBottomSheet,
        imagePath: String
    )
}
interface popupItemClickListnerDeals {
    fun getDatas(data: String, flag: String, id: String)
}
interface popupItemClickListnerCountry {
    fun getCountry(name: String, flag: String, isocode: String)
}


interface ViewImages {
    fun viewImage(imageList: ArrayList<String>)

}


interface DeleteAccount {
    fun deleteAccount()
}
