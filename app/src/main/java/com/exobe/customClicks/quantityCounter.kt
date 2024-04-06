package com.exobe.customClicks

import android.widget.TextView
import com.exobe.entity.response.customerservices.ServicesListNearMeServiceArray
import com.exobe.entity.response.serviceTab.NewServicesResponseServiceArray

interface quantityCounter {
    fun decrement(position: Int, price: String?)
    fun increment(position: Int, price: String?)
}

interface CustomeClick {
    fun click(_id: String?)

}

interface editDeal {
    fun click(deal_id: String?, product_id: String?)

}
interface CustomeClick2 {
    fun click2(_id: String?,servicename:String?)

}
interface CategoryClick {
    fun categoryClick(_id: String?, categoryName: String?)

}
interface CustomeClick4 {
    fun click4(_id: String, _id1: String)

}

interface DealsForCustomer {
    fun click(flag: String, flag1: Boolean, _id: String, productid: String)

}

interface ServiceDealsForCustomer {
    fun serviceDealClick(
        _id: String,
        serviceName: String,
        toDouble: Double,
        firstName: String,
        lastName: String,
        categoryName: String,
        categoryImage: String,
        subCategoryName: String,
        _id1: String,
        dealId:String,
        priceTag:String,
        actualPrice:String,
        discount:String
    )

}
interface DealsFromwholesaler {
    fun click(flag: String, flag1: Boolean, _id: String, productid:String)

}

interface ServicesClick {
    fun click(flag: String, _id: String?)

}

interface popupItemClickListner {
    fun getData(data: String, flag: String)
}
interface servicedeleteclick{
    fun pendingdeleteclick(position: Int)
}
interface viewserviceclick{
    fun viewservice(id: String?, s: String)
}
interface serviceselectedclick{
    fun pendingclick(position: Int, id : String)
}
interface subserviceClick{
    fun subservice(flag: String, CategoryId: String, categoryName: String)
}
interface wishlistcustomclick{
    fun wishlist(_id: String?)
}

interface categorynameclick {
    fun categoryname(_id: String?,categoryname:String)
}
interface viewProductClick{
    fun viewProductClick(itemId: String)
}
interface deleteCartApi {
    fun deleteCartList(
        itemId: String,
        position: Int,
        price: Number?,
        quantity: String,
        quantity1: TextView,
        i: Int
    )
}
interface categoryserviceClick{
    fun viewsubcategoryservice(id :String,  title:String,description:String)

}

interface chooseServicesListener {
    fun isCheckedChooseServices(
        mainId: String,
        id: String,
        title: String,
        price: Number,
        subCategoryName: String,
        subCategoryId: String
    )
    fun deleteChooseItem(
        id: String,
        title: String,
        price: Number,
        subCategoryName: String,
        subCategoryId: String
    )
}

interface CustomClick5 {
    fun click5(itemId: String, position: Int)
}

interface CustomClick6 {
    fun click5(
        itemId: String,
        position: Int,
        price: Number?,
        quantity: String,
        quantity1: TextView,
        i: Int
    )
}

interface ProductManagementClick {
    fun editClick( id : String, flag : String)
}

interface productmanagementadddeals {
    fun adddealsclick( id : String)
}


interface statusselectedclick{
    fun pendingclick(position: Int, id : String,flag: String)
}


interface OrderManagementListener {
    fun orderManagementClick(_id: String?, flag: String?)

}

interface CommonListenerServices {
    fun serviceProvidersPendingClick(_id:String,orderId: String, userType:String)
    fun serviceProvidersCompletedClick(_id:String,orderId: String,userType:String)

}

interface ProductViewListener {
    fun viewProduct(productId: String, dealId: String)

}


interface LikeUnlikeClick {
    fun wishlistClickListener(id: String,position: Int)
}


interface UpdateFeeConfig {
    fun updateFeePickUpDriver(id:String, fee:String)
    fun updateFeeDeliveryDriver(id:String, fee:String, standard:String)
    fun updateFeeFieldEntity(id:String, dailyFee:String, monthlyFee:String, weeklyFee:String, quarterlyFee:String, minimumFee:String, maximumFee:String)
}


interface ClickSelectedServices {
    fun getSelectedService(name:String, categoryId: String,categoryType:String)
}


interface RequestedServiceClick {
    fun requestedServiceCategory(
        subCategoryName: String,
        id: String,
        serviceArray: ArrayList<ServicesListNearMeServiceArray>
    )


    fun subCategory(serviceName: String, price: String, id: String)
}


interface CampaignOnServiceClick {
    fun categoryClick(
        subCategoryName: String,
        id: String,
        serviceArray: List<NewServicesResponseServiceArray>
    )
    fun subCategoryClick(
        serviceName: String,
        id: String,
        priceDaily: String
    )
}
