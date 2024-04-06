package com.exobe.fragments.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.permission.RequestPermission
import com.example.validationpractice.utlis.FormValidation
import com.exobe.adaptor.CityListPopUp
import com.exobe.adaptor.CountryListPopUp
import com.exobe.adaptor.StateListPopUp
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.ImageRotation
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.bottomSheet.choosePhotoBottomSheet
import com.exobe.customClicks.AddProductListener
import com.exobe.customClicks.popupItemClickListnerCountry
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.EditProfileRequest
import com.exobe.entity.request.EditProfileStoreLocation
import com.exobe.entity.response.*
import com.exobe.entity.response.imageupload.SingleImageUpload
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceConstant
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.utils.LocationClass
import com.exobe.utils.Progresss
import com.exobe.validations.DialogUtils
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.gson.GsonBuilder
import com.hbb20.CountryCodePicker
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import kotlin.collections.ArrayList

class EditProfileActivity : AppCompatActivity(), ApiResponseListener<MyProfileResponse>,
    AddProductListener, popupItemClickListnerCountry {

    lateinit var backButtonEditProfile: ImageView
    lateinit var locationTrackingClick: ImageView
    lateinit var btnSave: Button
    lateinit var mContext: Context
    lateinit var ccp: CountryCodePicker
    lateinit var errorTvEditFirstName: TextView
    lateinit var errorTvEditLastName: TextView
    lateinit var errorTvEditContactNumber: TextView
    lateinit var errorTvEditPostCode: TextView
    lateinit var errorTvEditAddress: TextView
    lateinit var select_Image: ImageView
    lateinit var firstname: EditText
    lateinit var lastname: EditText
    lateinit var MobileNumber_EditText: EditText
    lateinit var address_line2: EditText
    lateinit var postalcode: EditText
    lateinit var address: EditText
    val CAMERA_PERM_CODE = 101
    var photoURI: Uri? = null
    var imagePath = ""
    lateinit var profile_image: CircleImageView
    var USER_IMAGE_UPLOADED = false
    lateinit var imageparts: MultipartBody.Part
    var requestImage: String = ""
    private val GALLERY = 1
    private var CAMERA: Int = 2
    lateinit var image: Uri
    lateinit var imageFile: File
    lateinit var editprofile_progressbar: ProgressBar
    var imageUploadFlag = false
    lateinit var country1: TextView
    lateinit var State_ET: TextView
    lateinit var State_TV: TextView
    lateinit var Country: TextView
    lateinit var CityEt: TextView
    lateinit var CityTV: TextView
    private lateinit var dialog: Dialog
    private lateinit var recyclerView: RecyclerView
    lateinit var progressbar_csc: ProgressBar
    lateinit var nodata: LinearLayout
    lateinit var addressLine1LL: LinearLayout
    lateinit var Email_ET: EditText
    lateinit var Email_TV: TextView


    lateinit var adapter: CountryListPopUp
    lateinit var adapterState: StateListPopUp
    lateinit var adapterCity: CityListPopUp
    var data: java.util.ArrayList<CountryListResult> = java.util.ArrayList()
    var Statedatadata: java.util.ArrayList<StateList_Result> = java.util.ArrayList()
    var Citydatadata: java.util.ArrayList<CityListResult> = java.util.ArrayList()
    var flag: String = ""

    var isocode: String = ""
    var isoState: String = ""
    lateinit var internet_connection: RelativeLayout
    lateinit var pop_internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    var pop_lottie: LottieAnimationView? = null
    var PLACE_PICKER_REQUEST = 110
    var lat: Double = 0.0
    var long: Double = 0.0
    var cor = ArrayList<Double>()
    var userType = ""
    var participationIn = ""

    private lateinit var manualParticipation:RadioButton
    private lateinit var semiParticipation:RadioButton
    private lateinit var automaticParticipation:RadioButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        mContext = this.applicationContext!!
        window.attributes.windowAnimations = R.style.Fade
        Email_TV = findViewById(R.id.Email_TV)
        Email_ET = findViewById(R.id.Email_ET)
        backButtonEditProfile = findViewById(R.id.backButtonEditProfile)
        editprofile_progressbar = findViewById(R.id.editprofile_progressbar)
        btnSave = findViewById(R.id.btnSave)
        country1 = findViewById(R.id.ep_country1)
        Country = findViewById(R.id.Country)
        State_ET = findViewById(R.id.State_ET)
        State_TV = findViewById(R.id.State_TV)
        CityEt = findViewById(R.id.CityEt)
        CityTV = findViewById(R.id.CityTV)
        address_line2 = findViewById(R.id.address_line2)
        ccp = findViewById(R.id.ccp)
        errorTvEditFirstName = findViewById(R.id.errorTvEditFirstName)
        errorTvEditLastName = findViewById(R.id.errorTvEditLastName)
        errorTvEditContactNumber = findViewById(R.id.errorTvEditContactNumber)
        errorTvEditPostCode = findViewById(R.id.errorTvEditPostCode)
        errorTvEditAddress = findViewById(R.id.errorTvEditAddress)
        select_Image = findViewById(R.id.select_Image)
        profile_image = findViewById(R.id.profile_image)
        firstname = findViewById(R.id.firstname)
        lastname = findViewById(R.id.lastname)
        MobileNumber_EditText = findViewById(R.id.MobileNumber_EditText)
        postalcode = findViewById(R.id.postalcode)
        address = findViewById(R.id.address_line1)
        addressLine1LL = findViewById(R.id.addressLine1LL)

        manualParticipation = findViewById(R.id.manualParticipation)
        semiParticipation = findViewById(R.id.semiParticipation)
        automaticParticipation = findViewById(R.id.automaticParticipation)
        locationTrackingClick = findViewById(R.id.locationTrackingClick)


        INITTILAZIGOOGLEPLACE()
        initializedControl()
        userType =
            SavedPrefManager.getStringPreferences(this, SavedPrefManager.USER_TYPE).toString()
        backButtonEditProfile.setSafeOnClickListener {
            onBackPressed()
        }

        locationTrackingClick.setSafeOnClickListener {
            val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(applicationContext)
            startActivityForResult(
                intent,
                PLACE_PICKER_REQUEST
            )
        }



        address.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable) {
                val input = editable.toString()
                if (input == "") {
                    cor.clear()
                }
            }
        })

        btnSave.setSafeOnClickListener {
            callValidation()
            if (
                firstname.text.isNotEmpty()
                && lastname.text.isNotEmpty()
                && MobileNumber_EditText.text.isNotEmpty()
                && postalcode.text.isNotEmpty()
                && address.text.isNotEmpty()
                && country1.text.isNotEmpty()
            ) {
                if (imageUploadFlag) {
                    createImageLinkApi()
                } else {
                    val firstname = firstname.text.toString()
                    val lastname = lastname.text.toString()
                    val mobilenumber = MobileNumber_EditText.text.toString()
                    val postalcode = postalcode.text.toString()
                    val address = address.text.toString()
                    val address2 = address_line2.text.toString()
                    val countyCode = ccp.selectedCountryCode.toString()
                    val country = country1.text.toString()
                    val state = State_ET.text.toString()
                    val city = CityEt.text.toString()
                    val email = Email_ET.text.toString()
                    updateProfileAPI(
                        firstname,
                        lastname,
                        mobilenumber,
                        postalcode,
                        address,
                        countyCode,
                        address2,
                        country,
                        state,
                        city,
                        email
                    )
                }

            }
        }

        select_Image.setSafeOnClickListener {
            RequestPermission.requestMultiplePermissions(this)
            getImages()
        }

        country1.setSafeOnClickListener {
            flag = "Country"
            openPopUp()
            Country.text = ""
            country1.setBackgroundResource(R.drawable.backgroundsearch)
        }
        State_ET.setSafeOnClickListener {
            if (country1.text.isEmpty()) {
                Country.text = "*Please select country first."
                country1.setBackgroundResource(R.drawable.input_error)
            } else {
                Country.text = ""
                State_TV.text = ""
                flag = "State"
                country1.setBackgroundResource(R.drawable.backgroundsearch)
                State_ET.setBackgroundResource(R.drawable.backgroundsearch)
                openPopUp()
            }
        }
        CityEt.setSafeOnClickListener {
            if (State_ET.text.isEmpty()) {
                State_TV.text = "*Please select state first."
                Country.text = ""
                State_ET.setBackgroundResource(R.drawable.input_error)
                country1.setBackgroundResource(R.drawable.backgroundsearch)
            } else {
                CityTV.text = ""
                State_TV.text = ""
                flag = "City"
                openPopUp()
                State_ET.setBackgroundResource(R.drawable.backgroundsearch)
                country1.setBackgroundResource(R.drawable.backgroundsearch)
            }
        }

        myGetProfileApi()

    }

    private fun INITTILAZIGOOGLEPLACE() {
        Places.initialize(mContext, ServiceConstant.API_KEY)
        val placesClient = Places.createClient(mContext)
    }


    private fun GETLATLONG(latLng: LatLng) {
        val sb = StringBuffer()
        sb.append(latLng.latitude)
        sb.append(",")
        sb.append(latLng.longitude)
        val url = ("https://maps.googleapis.com/maps/api/geocode/json?latlng="
                + sb.toString() + "&key=${ServiceConstant.API_KEY}")
        val queue = Volley.newRequestQueue(mContext)
        val stateReq =
            JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                { response ->
                    val location: JSONObject
                    try {
                        try {
                            Log.d("tag","response.toString() => ${response.toString()}")
                            val addressLine1 = ArrayList<String>()
                            val addressLine2 = ""
                            var country = ""
                            var state = ""
                            var cityName = ""
                            var zipcodeText = ""

                            val objectData = JSONObject(response.toString())
                            val jsonArrayData: JSONArray = objectData.getJSONArray("results")
                            val jsonObject = jsonArrayData[0] as JSONObject

                            val jsonObjectWorkOrderInfo = JSONObject(jsonObject.getString("geometry"))
                            val locationdata = JSONObject(jsonObjectWorkOrderInfo.getString("location"))
                            lat = locationdata.getString("lat").toDouble()
                            long = locationdata.getString("lng").toDouble()
                            cor.clear()
                            cor.add(long)
                            cor.add(lat)
                            if (jsonArrayData.length() > 0) {
                                val data = jsonArrayData[0] as JSONObject
                                val address_components = data.getJSONArray("address_components")
                                for (i in 0 until address_components.length()) {
                                    val jsonObject = address_components[i] as JSONObject
                                    val types = jsonObject.getJSONArray("types")
                                    for (j in 0 until types.length()) {
                                        if (types[j] == "route") {
                                            addressLine1.add(jsonObject.getString("long_name"))
                                        }
                                        if (types[j] == "sublocality_level_3") {
                                            addressLine1.add(jsonObject.getString("long_name"))
                                        }
                                        if (types[j] == "sublocality_level_2") {
                                            addressLine1.add(jsonObject.getString("long_name"))
                                        }
                                        if (types[j] == "sublocality_level_1") {
                                            addressLine1.add(jsonObject.getString("long_name"))
                                        }
                                        if (types[j] == "country") {
                                            country = jsonObject.getString("long_name")
                                            isocode = jsonObject.getString("short_name")
                                        }
                                        if (types[j] == "administrative_area_level_1") {
                                            state= jsonObject.getString("long_name")
                                            isoState = jsonObject.getString("short_name")
                                        }
                                        if (types[j] == "administrative_area_level_3") {
                                            cityName = jsonObject.getString("long_name")
                                        }
                                        if (types[j] == "postal_code") {
                                            zipcodeText = jsonObject.getString("long_name")
                                        }
                                    }
                                }

                                address.text = Editable.Factory.getInstance().newEditable(addressLine1.joinToString(","))
                                country1.text = country
                                State_ET.text = state
                                CityEt.text = cityName
                                postalcode.setText(zipcodeText)
                                callValidation()
                            }

                        } catch (e: java.lang.Exception) {

                        }

                        location = response!!.getJSONArray("results").getJSONObject(0)
                            .getJSONObject("geometry").getJSONObject("location")
                        if (location.getDouble("lat") != 0.0 && location.getDouble("lng") != 0.0) {
                            val latLng =
                                LatLng(location.getDouble("lat"), location.getDouble("lng"))

                            //Do what you want
                        }
                    } catch (e1: JSONException) {
                        e1.printStackTrace()
                    }
                }
            ) { error -> Log.d("Error.Response", error.toString()) }

        queue.add(stateReq)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == 110) {
            if (resultCode == Activity.RESULT_OK) {
                if (intent != null) {
                    if (requestCode == PLACE_PICKER_REQUEST) {
                        when (resultCode) {
                            Activity.RESULT_OK -> {
                                val place = Autocomplete.getPlaceFromIntent(intent)
                                val latLng = place.latLng
                                GETLATLONG(latLng)
                            }
                            AutocompleteActivity.RESULT_ERROR -> {
                                val status = Autocomplete.getStatusFromIntent(intent)
                            }
                            Activity.RESULT_CANCELED -> {
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getImages() {
        val bottomsheet = choosePhotoBottomSheet("AddProduct", this)
        bottomsheet.show(supportFragmentManager, "bottomsheet")
    }

    // this method user for uploading uer image
    override fun addProduct(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        choosePhotoBottomSheet: choosePhotoBottomSheet,
        imagePath: String
    ) {
        try {
            if (requestCode == GALLERY) {
                if (data != null && data!!.clipData == null) {
                    try {
                        image = data.data!!

                        val path = getPathFromURI(image)
                        val maxFileSizeInBytes = 5 * 1024 * 1024
                        if (path != null) {
                            imageFile = File(path)

                        }
                        if (imageFile.length() <= maxFileSizeInBytes) {
                            choosePhotoBottomSheet.dismiss()
                            val requestGalleryImageFile: RequestBody =
                                RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
                            imageparts =
                                MultipartBody.Part.createFormData(
                                    "uploaded_file",
                                    imageFile.getName(),
                                    requestGalleryImageFile
                                )
                            Glide.with(this).load(imageFile).into(profile_image)
                            imageUploadFlag = true
                        }else{
                            androidextention.alertBoxCommon(message = "Please select a image that is 5 MB or smaller.", context = this)
                            return
                        }



                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }

            } else if (requestCode == CAMERA) {

                imageFile = File(imagePath)
                Glide.with(this).load(imageFile).into(profile_image)

                val finalBitmap =
                    ImageRotation.modifyOrientation(ImageRotation.getBitmap(imagePath)!!, imagePath)
                val imageUri = ImageRotation.getImageUri(mContext, finalBitmap!!)
                var getRealPath = ImageRotation.getRealPathFromURI(mContext, imageUri)
                imageFile = File(getRealPath)

                choosePhotoBottomSheet.dismiss()
                var requestGalleryImageFile: RequestBody =
                    RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
                imageparts =
                    MultipartBody.Part.createFormData(
                        "uploaded_file",
                        imageFile.getName(),
                        requestGalleryImageFile
                    )

                imageUploadFlag = true
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getPathFromURI(contentUri: Uri?): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = contentResolver.query(contentUri!!, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            res = cursor.getString(column_index)
        }
        cursor.close()
        return res
    }


    override fun onBackPressed() {
        finish()
    }

    private fun myGetProfileApi() {
        if (androidextention.isOnline(mContext)) {
            Progresss.start(this)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<MyProfileResponse> =
                ApiCallBack(this, "mygetprofileApi", mContext)
            try {
                serviceManager.myProfileApi(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            androidextention.alertBox("Please check your internet connection.", this)

        }

    }

    override fun onApiSuccess(response: MyProfileResponse, apiName: String?) {
        Progresss.stop()
        if (response.responseCode == 200) {
            try {
                cor.clear()
                cor.add(response.result.storeLocation?.coordinates!![0])
                cor.add(response.result.storeLocation.coordinates[1])
                firstname.setText(response.result.firstName)
                lastname.setText(response.result.lastName)
                if (!response.result.countryCode.equals("")) {
                    var countryCode = Integer.parseInt(response.result.countryCode)
                    ccp.setCountryForPhoneCode(countryCode)
                }
                MobileNumber_EditText.setText(response.result.mobileNumber)
                Email_ET.setText(response.result.email)
                response.result.zipCode?.let { postalcode.setText(it.toString()) }
                address.setText(response.result.addressLine1)
                address_line2.setText(response.result.addressLine2)
                country1.text = response.result.country
                State_ET.text = response.result.state
                CityEt.text = response.result.city
                isocode = response.result.countryIsoCode.toString()
                isoState = response.result.stateIsoCode.toString()
                Glide.with(mContext).load(response.result.profilePic).placeholder(R.drawable.layer_3).into(profile_image)


                when(response.result.campainPrefrences){
                    "AUTOMATIC"->{


                        automaticParticipation.isChecked =  true
                    }
                    "SEMI_AUTOMATIC"->{
                        semiParticipation.isChecked = true
                    }
                    else->{
                        manualParticipation.isChecked = true
                    }
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onApiErrorBody(response: String, apiName: String?) {
        Progresss.stop()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        Progresss.stop()

    }

    fun updateProfileAPI(
        firstname: String,
        lastname: String,
        mobilenumber: String,
        postalcode: String,
        address: String,
        countryCode: String,
        address2: String,
        country: String,
        state: String,
        city: String,
        email: String
    ) {
        if (androidextention.isOnline(this)) {
            editprofile_progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<CommonResponseForAll> =
                ApiCallBack<CommonResponseForAll>(object :
                    ApiResponseListener<CommonResponseForAll> {
                    override fun onApiSuccess(response: CommonResponseForAll, apiName: String?) {
                        editprofile_progressbar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {

                                finish()
                                MyProfileActivity.apiProfileCallFlag = true
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        editprofile_progressbar.visibility = View.GONE

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, this@EditProfileActivity)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        editprofile_progressbar.visibility = View.GONE
                    }

                }, "UpdateProfileAPI", mContext)

            var storeLocation = EditProfileStoreLocation()


            if (cor.isEmpty()){
                val fullAddress = "$address $postalcode $city $state $country"
                val coordinates = LocationClass.getAddressCoordinates(this,fullAddress)

                if (coordinates != null) {
                    cor.add(long)
                    cor.add(lat)




                } else {
                    androidextention.alertBox("Invalid address or error occurred,Please try again.",this)
                    Progresss.stop()
                    return
                }
            }

            storeLocation.type = "Point"
            storeLocation.coordinates = cor

            var jsonObject = EditProfileRequest()
            jsonObject.userType = userType
            jsonObject.firstName = firstname
            jsonObject.lastName = lastname
            jsonObject.mobileNumber = mobilenumber
            jsonObject.email = email
            jsonObject.zipCode = postalcode
            jsonObject.addressLine1 = address
            jsonObject.addressLine2 = address2
            jsonObject.country = country
            jsonObject.state = state
            jsonObject.city = city
            jsonObject.countryIsoCode = isocode
            jsonObject.stateIsoCode = isoState
            if (imageUploadFlag) {
                jsonObject.profilePic = requestImage
            }
            jsonObject.countryCode = countryCode
            jsonObject.storeLocation = storeLocation

            participationIn =  if (automaticParticipation.isChecked){
                "AUTOMATIC"
            }else if (semiParticipation.isChecked){
                "SEMI_AUTOMATIC"
            }else{
                "MANUAL"
            }

            jsonObject.campainPrefrences = participationIn


            try {
                serviceManager.Updateprofileapi(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            androidextention.alertBox("Please check your internet connection.", this)

        }
    }

    fun createImageLinkApi() {
        if (androidextention.isOnline(mContext)) {
            editprofile_progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<SingleImageUpload> =
                ApiCallBack<SingleImageUpload>(object :
                    ApiResponseListener<SingleImageUpload> {
                    override fun onApiSuccess(response: SingleImageUpload, apiName: String?) {
                        editprofile_progressbar.visibility = View.GONE

                        if (response.responseCode == 200) {

                            try {
                                requestImage = response.result?.mediaUrl!!

                                if (!requestImage.equals("")) {
                                    val firstname = firstname.text.toString()
                                    val lastname = lastname.text.toString()
                                    val mobilenumber = MobileNumber_EditText.text.toString()
                                    val postalcode = postalcode.text.toString()
                                    val address = address.text.toString()
                                    val address2 = address_line2.text.toString()
                                    val countryCode = ccp.selectedCountryCode.toString()
                                    val country = country1.text.toString()
                                    val state = State_ET.text.toString()
                                    val city = CityEt.text.toString()
                                    val email = Email_ET.text.toString()
                                    updateProfileAPI(
                                        firstname,
                                        lastname,
                                        mobilenumber,
                                        postalcode,
                                        address,
                                        countryCode,
                                        address2,
                                        country,
                                        state,
                                        city,
                                        email
                                    )
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        editprofile_progressbar.visibility = View.GONE
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(
                                pojo.responseMessage,
                                this@EditProfileActivity
                            )

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        editprofile_progressbar.visibility = View.GONE
                        Toast.makeText(
                            this@EditProfileActivity,
                            "${failureMessage}",
                            Toast.LENGTH_LONG
                        ).show()

                    }

                }, "createMultiImageLinkApi", mContext)
            try {
                serviceManager.singleUploadImageApi(callBack, imageparts)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            androidextention.alertBox("Please check your internet connection.", this)

        }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            callValidation()
        }
    }

    private fun callValidation() {
        FormValidation.editprofile(
            firstname, errorTvEditFirstName,
            lastname, errorTvEditLastName,
            MobileNumber_EditText, errorTvEditContactNumber,
            postalcode, errorTvEditPostCode,
            address, errorTvEditAddress,
            country1, Country,
//            State_ET, State_TV,
//            CityEt, CityTV,
            Email_ET, Email_TV,
            addressLine1LL
        )
    }

    fun initializedControl() {
        firstname.addTextChangedListener(textWatcher)
        lastname.addTextChangedListener(textWatcher)
        Email_ET.addTextChangedListener(textWatcher)
        MobileNumber_EditText.addTextChangedListener(textWatcher)
        address.addTextChangedListener(textWatcher)
        country1.addTextChangedListener(textWatcher)
        State_ET.addTextChangedListener(textWatcher)
        CityEt.addTextChangedListener(textWatcher)
        postalcode.addTextChangedListener(textWatcher)
    }


    @SuppressLint("InflateParams", "SetTextI18n")
    fun openPopUp() {

        try {
            val binding = LayoutInflater.from(this).inflate(R.layout.pop_lists, null)
            dialog = DialogUtils().createDialog(this, binding.rootView, 0)!!
            recyclerView = binding.findViewById(R.id.popup_recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this)
            progressbar_csc = binding.findViewById(R.id.progressbar_pop)
            nodata = binding.findViewById(R.id.no_notification)
            pop_lottie = binding.findViewById(R.id.pop_lottie)
            pop_internet_connection = binding.findViewById(R.id.pop_internet_connection)
            val dialougTitle = binding.findViewById<TextView>(R.id.popupTitle)
            val dialougbackButton = binding.findViewById<ImageView>(R.id.BackButton)
            val SearchEditText = binding.findViewById<EditText>(R.id.search_bar_edittext_popuplist)

            SearchEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(text: Editable?) {
                    filterData(text.toString(), flag)
                }

            })
            dialougbackButton.setSafeOnClickListener { dialog.dismiss() }


//            var SearchEditText = binding.findViewById<EditText>(R.id.search_bar_edittext_popuplist)


            if (flag.equals("Country")) {
                dialougTitle.setText(flag)
                CountryListApi()

            } else if (flag.equals("State")) {
                dialougTitle.setText(flag)
                StateListApi()
            } else if (flag.equals("City")) {
                dialougTitle.setText(flag)
                CityListApi()
            }
            dialog.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun filterData(searchText: String, flag: String) {
        val filteredList: ArrayList<CountryListResult> = ArrayList()
        val filteredStateList: ArrayList<StateList_Result> = ArrayList()
        val filteredCityList: ArrayList<CityListResult> = ArrayList()
try {
        if (flag == "Country") {
            for (item in data) {
                try {
                    if (item.name.lowercase().contains(searchText.lowercase())) {
                        filteredList.add(item)
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }

        } else if (flag == "State") {
            for (item in Statedatadata) {
                try {
                    if (item.name.lowercase().contains(searchText.lowercase())) {
                        filteredStateList.add(item)
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        } else if (flag.equals("City")) {
            for (item in Citydatadata) {
                try {
                    if (item.name.lowercase().contains(searchText.lowercase())) {
                        filteredCityList.add(item)
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }

        try {
            if (flag.equals("Country")) {
                adapter.filterList(filteredList)
            } else if (flag.equals("State")) {
                adapterState.filterList(filteredStateList)
            } else if (flag.equals("City")) {
                adapterCity.filterList(filteredCityList)
            }

        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
} catch (e: Exception) {
    e.printStackTrace()
}
    }


    fun setAdapter() {
        adapter = this.let { CountryListPopUp(mContext, data, flag, this) }!!
        recyclerView.adapter = adapter
    }

    fun setStateAdapter() {
        adapterState = this.let { StateListPopUp(mContext, Statedatadata, flag, this) }!!
        recyclerView.adapter = adapterState
    }

    fun setCityAdapter() {
        adapterCity = this.let { CityListPopUp(mContext, Citydatadata, flag, this) }!!
        recyclerView.adapter = adapterCity
    }

    fun CountryListApi() {
        if (androidextention.isOnline(mContext)) {
            pop_internet_connection.visibility = View.GONE
            pop_lottie!!.initLoader(false)
            progressbar_csc.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<CountryListResponse> =
                ApiCallBack<CountryListResponse>(object :
                    ApiResponseListener<CountryListResponse> {
                    override fun onApiSuccess(response: CountryListResponse, apiName: String?) {
                        progressbar_csc.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {
                                data = response.result
                                setAdapter()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar_csc.visibility = View.GONE
                        nodata.visibility = View.VISIBLE
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar_csc.visibility = View.GONE

                        Toast.makeText(
                            mContext,
                            "${failureMessage}",
                            Toast.LENGTH_LONG
                        ).show()

                    }

                }, "CountryListApi", mContext)
            try {
                serviceManager.CountryList(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            nodata.visibility = View.GONE
            pop_internet_connection.visibility = View.VISIBLE
            pop_lottie!!.initLoader(true)
        }
    }

    fun StateListApi() {
        if (androidextention.isOnline(mContext)) {
            pop_internet_connection.visibility = View.GONE
            pop_lottie!!.initLoader(false)

            progressbar_csc.visibility = View.VISIBLE

            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<StateListResponse> =
                ApiCallBack<StateListResponse>(object :
                    ApiResponseListener<StateListResponse> {
                    override fun onApiSuccess(response: StateListResponse, apiName: String?) {
                        progressbar_csc.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {
                                Statedatadata = response.result
                                setStateAdapter()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar_csc.visibility = View.GONE
                        nodata.visibility = View.VISIBLE


                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar_csc.visibility = View.GONE
                        Toast.makeText(
                            mContext,
                            "${failureMessage}",
                            Toast.LENGTH_LONG
                        ).show()

                    }

                }, "StateListApi", mContext)
            try {
                serviceManager.StateList(callBack, isocode)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            nodata.visibility = View.GONE
            pop_internet_connection.visibility = View.VISIBLE
            pop_lottie!!.initLoader(true)

        }
    }

    fun CityListApi() {
        if (androidextention.isOnline(mContext)) {
            pop_internet_connection.visibility = View.GONE
            pop_lottie!!.initLoader(false)
            progressbar_csc.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<CityListResponse> =
                ApiCallBack<CityListResponse>(object :
                    ApiResponseListener<CityListResponse> {
                    override fun onApiSuccess(response: CityListResponse, apiName: String?) {
                        progressbar_csc.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {
                                Citydatadata = response.result
                                setCityAdapter()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbar_csc.visibility = View.GONE
                        nodata.visibility = View.VISIBLE

                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar_csc.visibility = View.GONE
                        Toast.makeText(
                            mContext,
                            "${failureMessage}",
                            Toast.LENGTH_LONG
                        ).show()

                    }

                }, "CityListApi", mContext)
            try {
                serviceManager.CityList(callBack, isocode, isoState)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            nodata.visibility = View.GONE
            pop_internet_connection.visibility = View.VISIBLE
            pop_lottie!!.initLoader(true)

        }
    }

    override fun getCountry(name: String, flag: String, iso: String) {
        when (flag) {
            "Country" -> {

                isocode = iso
                country1.text = name
                State_ET.text = ""
                isoState = ""
                CityEt.text = ""
                dialog.dismiss()
                callValidation()
            }
            "State" -> {

                isoState = iso
                State_ET.text = name
                CityEt.text = ""
                dialog.dismiss()
                callValidation()
            }
            "City" -> {

                CityEt.text = name
                dialog.dismiss()
                callValidation()
            }
        }

    }




}