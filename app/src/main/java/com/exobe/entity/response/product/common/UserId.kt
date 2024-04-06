import com.google.gson.annotations.SerializedName
import java.net.SocketImpl

class UserId {

	@SerializedName("storeLocation")
	val storeLocation: StoreLocation = StoreLocation()
	@SerializedName("govtDocument")
	val govtDocument: GovtDocument= GovtDocument()
	@SerializedName("socialLink")
	val socialLink: SocialLink = SocialLink()
	@SerializedName("businessDetails")
	val businessDetails: BusinessDetails = BusinessDetails()
	@SerializedName("businessBankingDetails")
	val businessBankingDetails: BusinessBankingDetails = BusinessBankingDetails()
	@SerializedName("serviceDetails")
	val serviceDetails: ServiceDetails = ServiceDetails()
	@SerializedName("address")
	val address: String=""
	@SerializedName("otpVerification")
	val otpVerification: Boolean= false
	@SerializedName("userVerification")
	val userVerification: Boolean= false
	@SerializedName("profilePic")
	val profilePic: String=""
	@SerializedName("websiteUrl")
	val websiteUrl: String=""
	@SerializedName("duration")
	val duration: String=""
	@SerializedName("userRequestStatus")
	val userRequestStatus: String=""
	@SerializedName("zipCode")
	val zipCode: Int=0
	@SerializedName("eoriNumber")
	val eoriNumber: Int=0
	@SerializedName("additionalDocName")
	val additionalDocName: String=""
	@SerializedName("additionalDocument")
	val additionalDocument: String=""
	@SerializedName("ownerFirstName")
	val ownerFirstName: String=""
	@SerializedName("ownerLastName")
	val ownerLastName: String=""
	@SerializedName("ownerEmail")
	val ownerEmail: String=""
	@SerializedName("noOfUniqueProducts")
	val noOfUniqueProducts: Int=0
	@SerializedName("listOfBrandOrProducts")
	val listOfBrandOrProducts: ArrayList<String> = ArrayList<String>()
	@SerializedName("keepStock")
	val keepStock: Boolean= false
	@SerializedName("isPhysicalStore")
	val isPhysicalStore: Boolean= false
	@SerializedName("howManyStoresDoYouHave")
	val howManyStoresDoYouHave: Int= 0
	@SerializedName("preferredSupplierOrWholeSalerId")
	val preferredSupplierOrWholeSalerId: ArrayList<String> = ArrayList<String>()
	@SerializedName("comments")
	val comments: String=""
	@SerializedName("completeProfile")
	val completeProfile: Boolean= false
	@SerializedName("flag")
	val flag: Int=0
	@SerializedName("placeOrderCount")
	val placeOrderCount: Int=0
	@SerializedName("serviceOrderCount")
	val serviceOrderCount: Int=0
	@SerializedName("receiveOrderCount")
	val receiveOrderCount: Int=0
	@SerializedName("status")
	val status: String=""
	@SerializedName("serviceOtpVerification")
	val serviceOtpVerification: Boolean= false
	@SerializedName("_id")
	val _id: String=""
	@SerializedName("userType")
	val userType: String=""
	@SerializedName("firstName")
	val firstName: String=""
	@SerializedName("lastName")
	val lastName: String=""
	@SerializedName("countryCode")
	val countryCode: Int=0
	@SerializedName("mobileNumber")
	val mobileNumber: Int=0
	@SerializedName("email")
	val email: String=""
	@SerializedName("phoneNumber")
	val phoneNumber: Int=0
	@SerializedName("storeAddress")
	val storeAddress: String=""
	@SerializedName("storeName")
	val storeName: String=""
	@SerializedName("storeContactNo")
	val storeContactNo: Int=0
	@SerializedName("storeBrand")
	val storeBrand: String=""
	@SerializedName("password")
	val password: String=""
	@SerializedName("city")
	val city: String=""
	@SerializedName("state")
	val state: String=""
	@SerializedName("country")
	val country: String=""
	@SerializedName("addressLine1")
	val addressLine1: String=""
	@SerializedName("createdAt")
	val createdAt: String=""
	@SerializedName("updatedAt")
	val updatedAt: String=""
	@SerializedName("__v")
	val __v: Int=0
	@SerializedName("userUniqueId")
	val userUniqueId: String=""
}