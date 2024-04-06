import com.google.gson.annotations.SerializedName


class SubCategoryDetails {

    @SerializedName("price")
    val price: Number = 0

    @SerializedName("subCategoryId")
    val subCategoryId: String = ""

    @SerializedName("serviceSubCategoriesDetails")
    val serviceSubCategoriesDetails: ServiceSubCategoriesDetails = ServiceSubCategoriesDetails()
}


class  ServiceSubCategoriesDetails {

    @SerializedName("_id")
    val _id: String = ""
    @SerializedName("status")
    val status: String= ""
    @SerializedName("categoryId")
    val categoryId: String= ""
    @SerializedName("serviceName")
    val serviceName: String= ""
    @SerializedName("description")
    val description: String= ""
    @SerializedName("serviceImage")
    val serviceImage: ArrayList<String> = ArrayList()
    @SerializedName("__v")
    val __v: Int=0
    @SerializedName("createdAt")
    val createdAt: String= ""
    @SerializedName("updatedAt")
    val updatedAt: String= ""
}