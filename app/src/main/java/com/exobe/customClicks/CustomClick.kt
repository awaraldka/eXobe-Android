package com.exobe.customClicks

import android.widget.TextView
import com.exobe.entity.response.NewOrderServiceReqDoc
import com.exobe.entity.response.listNotificationDocs

interface CustomClick {
}

interface deleteCustomClick {
    fun deleteItem(id: String)
}

interface serviceAvailableCategoryClick{
    fun serviceAvailableCategory(_id: String?, categoryName: String?)
}

interface DeleteServiceItem {
    fun deleteServiceItem(count: Int, id: String?, position: Int)
}

interface DeleteCartItem {
    fun deleteCartItem(
        id: String?,
        price: Number?,
        position: Int,
        quantity: String,
        quantity1: TextView,
        count: Int
    )
}
interface wishlistcustomclick2{
    fun wishlist2()
}

interface RemoveService{
    fun removeService(position: Int, _id: String)
    fun amountErrorService(position: Int, _id: String)
}

interface ServiceListing{
    fun serviceListingClick(id: String, description: String, _id: String)
}

interface NotificationType{
    fun notificationClick(position: Int, data: listNotificationDocs)
}

interface selectWholeSalerListener{
    fun selectWholeSalerClick(fullName: String, _id: String)
}

interface RequestServiceListener{
    fun RequestService(
        _id: String,
        subCategoryName: String,
        dealPrice: Double,
        firstName: String,
        lastName: String,
        categoryName: String,
        categoryImage: String,
        subCategoryName1: String,
        _id1: String,
        priceTag:String,
        actualPrice:String,
        discount:String
    )
}

interface DealImageRemoveListener {
    fun deleteImage(position: Int)
}

interface PaymentDoneListener {
    fun paymentDone()
    fun FailedDone()

    fun cancelPayment()
}

interface ServicesAddListener {
    fun addServices(
        id: String,
        price: String,
        isRemove: Boolean,
        isUpdate: Boolean,
        isSelected: Boolean
    )
    fun removeServices(
        position: Int,
        id: String,
        isRemove: Boolean,
        isSelected: Boolean,
        isUpdate: Boolean
    )
}

interface ViewServiceDealListener {
    fun viewServiceDeal(_id: String, categoryName: String)
}

interface UnitListener {
    fun getUnit(unitName: String)
}

interface DeletePackageListener {
    fun isDelete(value: String, unit: String, amount: Number, qty: String)
    fun isEdit(value: String, unit: String, amount: Number, qty: String, id: String)
}

interface DismissListener {
    fun dismissListener(unitName: String)
}

interface ChangePriceListener {
    fun changePrice(price: Number, quantity: String, toString: String)
}

interface PopUpEditPriceDetails {
    fun popupEditDetails(value: String, unit: String, amount: String, qty: String, id: String)
}

interface SizeListener {
    fun getSize(
        name: String,
        id: String,
        price: Number,
        unit: String,
        value: String,
        dealPrice: Number?,
        quantity: String?
    )
}

interface DismissSizeListener {
    fun dismissListener(
        unitName: String,
        id: String,
        price: Number,
        unit: String,
        value: String,
        dealPrice: Number?,
        quantity: String?
    )
}

interface UpdateIsLoginListener {
    fun isLoginListener()
}

interface CustomerDealListener {
    fun dealsOnProduct(flag: String)
    fun dealsOnServices(flag: String)
    fun dealsFromWholesaler()
}
interface BookingDetailListener {
    fun bookingDetail(flag: String, serviceData: NewOrderServiceReqDoc,serviceStatus:String)
}

interface ServiceNameListener{
    fun serviceNameWithPrice(data: String, flag: String, id: String, price : String)
}

interface BannerClickListener{
    fun bannerClick(url : String)
}


interface SubCategoryClick {
    fun subCategoryClick(name: String,subCategoryId:String)
}


interface ClickForDeliveryFees{
    fun deliveryFees(deliveryAmount: String, deliveryType: String)
}



interface serviceNameClickLisntner {
    fun serviceNameClick(
        categoryName: String,
        categoryId: String,
        subCategoryId: String,
        subCategoryName: String,
        price: Int,
        serviceName: String,
        image: String,
        serviceId:String
    )
}


interface SelectDocumentClick {
    fun selectDocument(position: Int, name: String)
    fun deleteDocument(position: Int, pathName: String)
}


interface OrderProcessClick {
    fun orderProcess()

    fun sendOtpForDeliveredItemToFE(retailerId: String)
    fun mapOpen(lat:Double,long:Double)

}

interface NavigationClick {
    fun navigateToMap()
    fun navigateToHome()
}






interface RejectOrderClick {
    fun rejectOrder(reason:String)
}


interface OutForDelivery { fun outForDelivery()
}


interface PaymentForWallet {
    fun ozow(price:String)
    fun payfast(price:String)
}

interface CampaignEdit {
    fun editCampaignClick(productId: String, id: String)
}


interface PaymentForOrderClick {
    fun ozow()
    fun payfast()

    fun walletClick()
}