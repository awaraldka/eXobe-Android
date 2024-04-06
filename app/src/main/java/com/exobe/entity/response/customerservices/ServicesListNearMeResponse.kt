package com.exobe.entity.response.customerservices


import com.google.gson.annotations.SerializedName


class ServicesListNearMeResponse (
    @SerializedName("result")
    val result: ServicesListNearMeResult = ServicesListNearMeResult(),
    @SerializedName("responseMessage")
    val responseMessage: String = "",
    @SerializedName("responseCode")
    val responseCode: Int = 0)
class ServicesListNearMeResult {
    @SerializedName("docs")
    val docs: ArrayList<ServicesListNearMeDoc> = ArrayList()
    @SerializedName("userDeails") val userDetails : OwnerDetails = OwnerDetails()
}

class ServicesListNearMeDoc {
    @SerializedName("subCategory")
    val subCategory: ServicesListNearMeSubCategory = ServicesListNearMeSubCategory()
    @SerializedName("serviceArray")
    val serviceArray: ArrayList<ServicesListNearMeServiceArray> = ArrayList()

    var expanded: Boolean = false

}
class userDetails {

    @SerializedName("_id")
    val _id: String= ""
    @SerializedName("profilePic") val profilePic : String=""
    @SerializedName("userType")
    val userType: String= ""
    @SerializedName("firstName")
    val firstName: String= ""
    @SerializedName("lastName")
    val lastName: String= ""
}

class ServicesListNearMeSubCategory {
    @SerializedName("thumbnail")
    val thumbnail: String = ""
    @SerializedName("serviceImage")
    val serviceImage: ArrayList<String> = ArrayList()
    @SerializedName("status")
    val status: String = ""
    @SerializedName("_id")
    val id: String = ""
    @SerializedName("categoryId")
    val categoryId: ServicesListNearMeCategoryId = ServicesListNearMeCategoryId()
    @SerializedName("subCategoryName")
    val subCategoryName: String = ""
    @SerializedName("__v")
    val v: Int = 0
    @SerializedName("createdAt")
    val createdAt: String = ""
    @SerializedName("updatedAt")
    val updatedAt: String = ""

}

class ServicesListNearMeCategoryId {
    @SerializedName("thumbnail")
    val thumbnail: String = ""
    @SerializedName("status")
    val status: String = ""
    @SerializedName("_id")
    val id: String = ""
    @SerializedName("categoryName")
    val categoryName: String = ""
    @SerializedName("categoryImage")
    val categoryImage: String = ""
    @SerializedName("description")
    val description: String = ""
    @SerializedName("createdAt")
    val createdAt: String = ""
    @SerializedName("updatedAt")
    val updatedAt: String = ""
    @SerializedName("__v")
    val v: Int = 0
}

class ServicesListNearMeServiceArray {
    @SerializedName("serviceLocation")
    val serviceLocation: ServicesListNearMeServiceLocation = ServicesListNearMeServiceLocation()
    @SerializedName("serviceImage")
    val serviceImage: ArrayList<String> = ArrayList()
    @SerializedName("slots")
    val slots: ArrayList<String> = ArrayList()
    @SerializedName("status")
    val status: String = ""
    @SerializedName("thumbnail")
    val thumbnail: String = ""
    @SerializedName("_id")
    val id: String = ""
    @SerializedName("description")
    val description: String = ""
    @SerializedName("categoryId")
    val categoryId: String = ""
    @SerializedName("subCategoryId")
    val subCategoryId: String = ""
    @SerializedName("serviceName")
    val serviceName: String = ""
    @SerializedName("__v")
    val v: Int = 0
    @SerializedName("serviceReferenceId")
    val serviceReferenceId: String = ""
    @SerializedName("price")
    val price: Int = 0
    @SerializedName("userId")
    val userId: String = ""
    @SerializedName("createdAt")
    val createdAt: String = ""
    @SerializedName("updatedAt")
    val updatedAt: String = ""
    @SerializedName("dealPrice")
    val dealPrice: String = ""
    @SerializedName("dealDiscount")
    val dealDiscount: String = ""
    @SerializedName("dealId")
    val dealId: String = ""
    var isSelected : Boolean = false
    @SerializedName("isDealActive")
    var isDealActive : Boolean = false
}
class ServicesListNearMeServiceLocation {
    @SerializedName("type")
    val type: String = ""
    @SerializedName("coordinates")
    val coordinates: ArrayList<Double> = ArrayList()
}
