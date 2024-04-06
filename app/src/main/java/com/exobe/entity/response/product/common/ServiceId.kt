import com.google.gson.annotations.SerializedName

class ServiceId {

	@SerializedName("serviceLocation")
	val serviceLocation: ServiceLocation = ServiceLocation()
	@SerializedName("serviceImage")
	val serviceImage: ArrayList<String> = ArrayList<String>()
	@SerializedName("slots")
	val slots:  ArrayList<String> = ArrayList<String>()
	@SerializedName("status")
	val status: String = ""
	@SerializedName("_id")
	val _id: String = ""
	@SerializedName("categoryId")
	val categoryId: CategoryId = CategoryId()
	@SerializedName("subCategoryDetails")
	val subCategoryDetails: ArrayList<SubCategoryDetails> = ArrayList<SubCategoryDetails>()
	@SerializedName("userId")
	val userId: String = ""
	@SerializedName("createdAt")
	val createdAt: String = ""
	@SerializedName("updatedAt")
	val updatedAt: String = ""
	@SerializedName("description")
	val description: String = ""
	@SerializedName("duration")
	val duration: String = ""
	@SerializedName("serviceName")
	val serviceName: String = ""
}