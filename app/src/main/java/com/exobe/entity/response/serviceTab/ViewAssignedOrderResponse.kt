package com.exobe.entity.response.serviceTab

import com.exobe.entity.response.MyProfileStoreLocation
import com.exobe.entity.response.PriceSizeDetails
import com.google.gson.annotations.SerializedName

class ViewAssignedOrderResponse {

    @SerializedName("responseCode") val responseCode :Int = 0
    @SerializedName("responseMessage") val responseMessage :String = ""
    @SerializedName("result") val result : ViewAssignedOrderResult =  ViewAssignedOrderResult()

}


class ViewAssignedOrderResult{
    @SerializedName("requestStatus") val requestStatus :String = ""
    @SerializedName("isDeliverToFieldEntity") val isDeliverToFieldEntity :Boolean = false
    @SerializedName("isOrderPickedUpFromFieldEntity") val isOrderPickedUpFromFieldEntity :Boolean = false
    @SerializedName("isOutForDelivery") val isOutForDelivery :Boolean = false
    @SerializedName("_id") val id :String = ""
    @SerializedName("userType") val userType :String = ""
    @SerializedName("createdAt") val createdAt :String = ""
    @SerializedName("filedEntity") val filedEntity :FiledEntity = FiledEntity()
    @SerializedName("orderId") val orderIdRes :orderIdRes = orderIdRes()
    @SerializedName("deliveryDriver") val deliveryDriver : DeliveryDriver = DeliveryDriver()
    @SerializedName("retailers") val retailers : ArrayList<Retailers> = arrayListOf()
}

class FiledEntity{
    @SerializedName("assignedUser") val assignedUser: AssignedUser = AssignedUser()
}


class DeliveryDriver{
    @SerializedName("assignedUser") val assignedUser: AssignedUser = AssignedUser()

}


class orderIdRes{
    @SerializedName("userId") val userId: AssignedUser = AssignedUser()
    @SerializedName("shippingFixedAddress") val shippingFixedAddress: ShippingFixedAddress =ShippingFixedAddress()


}

class ShippingFixedAddress{
    @SerializedName("location") val storeLocation: MyProfileStoreLocation? = null
    @SerializedName("_id") val id:String = ""
    @SerializedName("firstName") val firstName:String = ""
    @SerializedName("lastName") val lastName:String = ""
    @SerializedName("mobileNumber") val mobileNumber:String = ""
    @SerializedName("addressLine1") val addressLine1:String = ""
    @SerializedName("zipCode") val zipCode:String = ""
    @SerializedName("email") val email:String = ""
    @SerializedName("city") val city:String = ""
    @SerializedName("state") val state:String = ""
    @SerializedName("country") val country:String = ""
}





class Retailers{
    @SerializedName("userId") val userId: AssignedUser = AssignedUser()
    @SerializedName("products") val products : ArrayList<AllProducts> = arrayListOf()
    @SerializedName("isOrderPickedUp") val isOrderPickedUp:Boolean = false
    @SerializedName("isOrderDelivered") val isOrderDelivered:Boolean = false
}


class AllProducts{
    @SerializedName("quantity") val quantity:Int = 0
    @SerializedName("price") val price:Double = 0.0
    @SerializedName("priceSizeDetails") val priceSizeDetails: PriceSizeDetails = PriceSizeDetails()
    @SerializedName("productId") val productId : ProductIdViewAssignedOrder = ProductIdViewAssignedOrder()
    var isSelected =  false
}


class ProductIdViewAssignedOrder{
    @SerializedName("thumbnail") val thumbnail:String = ""
    @SerializedName("productName") val productName:String = ""
    @SerializedName("_id") val id:String = ""
    @SerializedName("dealReferenceId") val dealReferenceId: DealRefrenceId = DealRefrenceId()
}

class DealRefrenceId{
    @SerializedName("dealDiscount") val dealDiscount:String = ""
    @SerializedName("dealPrice") val dealPrice:Double = 0.0

}