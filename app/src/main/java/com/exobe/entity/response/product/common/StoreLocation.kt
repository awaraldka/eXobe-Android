import com.google.gson.annotations.SerializedName

class StoreLocation {

	@SerializedName("type") val type : String=""
	@SerializedName("coordinates") val coordinates : ArrayList<Double> = ArrayList<Double>()
}