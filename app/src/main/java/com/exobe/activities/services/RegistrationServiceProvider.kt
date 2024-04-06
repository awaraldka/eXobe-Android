package com.exobe.activities.services


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
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
import com.exobe.adaptor.FeesConfigurationAdapter
import com.exobe.adaptor.FeesConfigurationFieldEntityAdapter
import com.exobe.adaptor.StateListPopUp
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.activities.terms_conditions
import com.exobe.utils.ImageRotation
import com.exobe.utils.LocationClass
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.bottomSheet.CustomerBottomSheet
import com.exobe.bottomSheet.choosePhotoBottomSheet
import com.exobe.customClicks.AddProductListener
import com.exobe.customClicks.UpdateIsLoginListener
import com.exobe.customClicks.popupItemClickListnerCountry
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.PickupFeeData
import com.exobe.entity.request.ServiceProviderSignup_Request
import com.exobe.entity.request.ServiceProvider_StoreLocation
import com.exobe.entity.request.SizeTypeRequest
import com.exobe.entity.response.*
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceConstant
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.utils.Progresss
import com.exobe.validations.DialogUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.GsonBuilder
import com.hbb20.CountryCodePicker
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RegistrationServiceProvider : AppCompatActivity(), ApiResponseListener<ServiceProviderSignup_Response>,popupItemClickListnerCountry, AddProductListener, UpdateIsLoginListener {
    lateinit var title: TextView
    lateinit var back: ImageView
    lateinit var cross_eye2: ImageView
    lateinit var cross_eye: ImageView
    lateinit var ServiceProvider_register: Button
    lateinit var mContext: Context
    lateinit var FirstName_ET: EditText
    lateinit var FirstName_TV: TextView
    lateinit var LastName_ET: EditText
    lateinit var LastName_TV: TextView
    lateinit var Email_ET: EditText
    lateinit var Email_TV: TextView

    lateinit var Phone_TV: TextView
    lateinit var Mobile_ET: EditText
    lateinit var Phone_ET: EditText
    lateinit var Mobile_TV: TextView
    lateinit var Address1_ET: EditText
    lateinit var Address1_TV: TextView
    lateinit var Address2_ET: EditText
    lateinit var Address2_TV: TextView
    lateinit var Country_ET: TextView
    lateinit var Country_TV: TextView
    lateinit var State_ET: TextView
    lateinit var State_TV: TextView
    lateinit var City_ET: TextView
    lateinit var City_TV: TextView
    lateinit var ZipCode_ET: EditText
    lateinit var ZipCode_TV: TextView
    lateinit var Password_ET: EditText
    lateinit var Password_TV: TextView
    lateinit var Confirm_ET: EditText
    lateinit var Confirm_TV: TextView
    lateinit var checkBox: CheckBox
    lateinit var termsandconditions: TextView
    lateinit var PaswordLL: LinearLayout
    lateinit var ConfirmPaswordLL: LinearLayout
    lateinit var Address1eEtLL: LinearLayout

    lateinit var progressbar: ProgressBar
    lateinit var no_notification: LinearLayout
    lateinit var progressbarpopup: ProgressBar
    private var passwordNotVisible = 0
    lateinit var ccp_MyProfile: CountryCodePicker
    var type = ""
    private var client: FusedLocationProviderClient? = null
    lateinit var imageparts: MultipartBody.Part
    lateinit var Countrycode: LinearLayout
    lateinit var login: TextView
    var day = 0
    val CAMERA_PERM_CODE = 101
    val c = Calendar.getInstance()
    var imageFile: File? = null
    var imagePath = ""
    var photoURI: Uri? = null
    private val GALLERY = 1
    private var CAMERA: Int = 2
    lateinit var image: Uri
    lateinit var profile_image: CircleImageView
    var requestImage: String = ""
    var USER_IMAGE_UPLOADED = false
    var imageUploadFlag = false
    var flag: String = ""

    private lateinit var dialog: Dialog
    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: CountryListPopUp
    lateinit var adapterState: StateListPopUp
    lateinit var adapterCity: CityListPopUp
    var data: ArrayList<CountryListResult> = ArrayList()
    var Statedatadata: ArrayList<StateList_Result> = ArrayList()
    var Citydatadata: ArrayList<CityListResult> = ArrayList()

    var isocode: String = ""
    var isoState: String = ""
    var City: String = ""
    lateinit var pop_internet_connection: RelativeLayout
    var pop_lottie: LottieAnimationView? = null
    private val LOCATION_PERMISSION_REQ_CODE = 1000;
    var PLACE_PICKER_REQUEST = 110
    var lat: Double = 0.0
    var long: Double = 0.0



    var userRole = ""
    lateinit var feeCustomize:TextView
    private lateinit var dialogFeeCustomize: Dialog
    private lateinit var recyclerViewFeeCustomize: RecyclerView
    private lateinit var feesView: LinearLayout
    private lateinit var feesConfig: LinearLayout
    private lateinit var tickFeeCustomize: TextView
    private lateinit var feesViewTv: TextView
    private lateinit var passwordLL: LinearLayout
    private lateinit var select_Image: ImageView
    var dataFeeCustomize : List<DeliveryFeesResult> = listOf()
    var FeeCustomizeRequest: List<DeliveryFeesResult> = listOf()
    lateinit var adapterFeeCustomize: FeesConfigurationAdapter

    var profilePic = ""
    private var base64: String? = null

    private var isSocial = false
    private var googleFirstName = ""
    private var googleLastName = ""
    private var googleEmail = ""
    private var googleProfilePicURL = ""
    lateinit var locationTrackingClick : ImageView




    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_serviceprovider)
        window.attributes.windowAnimations = R.style.Fade
        client = LocationServices.getFusedLocationProviderClient(this)
        mContext = this.applicationContext
        ServiceProvider_register = findViewById(R.id.serviceProvider_register)
        login = findViewById(R.id.login)
        title = findViewById(R.id.title)
        back = findViewById(R.id.back)

        FirstName_ET = findViewById(R.id.FirstName_ET)
        progressbar = findViewById(R.id.service_provider_progress_bar)
        FirstName_TV = findViewById(R.id.FirstName_TV)
        LastName_ET = findViewById(R.id.LastName_ET)
        LastName_TV = findViewById(R.id.LastName_TV)
        Email_ET = findViewById(R.id.Email_ET)
        Email_TV = findViewById(R.id.Email_TV)
        Phone_ET = findViewById(R.id.Phone_ET)
        Phone_TV = findViewById(R.id.Phone_TV)
        Mobile_ET = findViewById(R.id.Mobile_ET)
        Mobile_TV = findViewById(R.id.Mobile_TV)
        Address1_ET = findViewById(R.id.Address1_ET)
        Address1_TV = findViewById(R.id.Address1_TV)
        Address2_ET = findViewById(R.id.Address2_ET)
        Address2_TV = findViewById(R.id.Address2_TV)
        Country_ET = findViewById(R.id.Country_ET)
        Country_TV = findViewById(R.id.Country_TV)
        State_ET = findViewById(R.id.State_ET)
        State_TV = findViewById(R.id.State_TV)
        City_ET = findViewById(R.id.City_ET)
        City_TV = findViewById(R.id.City_TV)
        ZipCode_ET = findViewById(R.id.ZipCode_ET)
        ZipCode_TV = findViewById(R.id.ZipCode_TV)
        Password_ET = findViewById(R.id.Password_ET)
        Password_TV = findViewById(R.id.Password_TV)
        Confirm_ET = findViewById(R.id.Confirm_ET)
        Confirm_TV = findViewById(R.id.Confirm_TV)
        Countrycode = findViewById(R.id.country)
        ccp_MyProfile = findViewById(R.id.ccp_MyProfile)
        profile_image = findViewById(R.id.profile_image)
        cross_eye2 = findViewById(R.id.cross_eye2)
        cross_eye = findViewById(R.id.cross_eye)
        checkBox = findViewById(R.id.CheckBox)
        PaswordLL = findViewById(R.id.PaswordLL)
        ConfirmPaswordLL = findViewById(R.id.ConfirmPaswordLL)
        termsandconditions = findViewById(R.id.termsandconditions)
        Address1eEtLL = findViewById(R.id.Address1eEtLL)
        feeCustomize = findViewById(R.id.feeCustomize)
        feesViewTv = findViewById(R.id.feesViewtv)
        feesView = findViewById(R.id.feesView)
        feesConfig = findViewById(R.id.feesConfig)
        passwordLL = findViewById(R.id.passwordLL)
        select_Image = findViewById(R.id.select_Image)
        locationTrackingClick = findViewById(R.id.locationTrackingClick)

        intent?.getStringExtra("userRole")?.let{ userRole = it }


        intent?.getBooleanExtra("isSocial",false)?.let { isSocial = it }
        intent?.getStringExtra("googleFirstName")?.let { googleFirstName = it }
        intent?.getStringExtra("googleLastName")?.let { googleLastName = it }
        intent?.getStringExtra("googleEmail")?.let { googleEmail = it }
        intent?.getStringExtra("googleProfilePicURL")?.let { googleProfilePicURL = it }


        if (isSocial){

            Glide.with(this).load(googleProfilePicURL).into(profile_image)
            FirstName_ET.setText(googleFirstName)
            LastName_ET.setText(googleLastName)
            Email_ET.setText(googleEmail)

            if (googleProfilePicURL.isNotEmpty()){
                profilePic =  googleProfilePicURL
                select_Image.isEnabled = false
            }
            if (googleFirstName.isNotEmpty()){
                FirstName_ET.isEnabled = false
            }
            if (googleLastName.isNotEmpty()){
                LastName_ET.isEnabled = false
            }
            if (googleEmail.isNotEmpty()){
                Email_ET.isEnabled = false
            }


            passwordLL.isVisible =  false
        }






//        progressbar = findViewById(R.id.progressbar)
        INITTILAZIGOOGLEPLACE()
        initializedControl()
        locationPermission()





        type = when(userRole){ // Here we creating Request parameter of roles based on userRole
            "Pickup Driver" -> {
                 "PICKUP_PARTNER"
            }

            "Field Entity" -> {
                 "FIELD_ENTITY"
            }

            "Delivery Driver" -> {
                 "DELIVERY_PARTNER"
            }

            else -> {
                 "SERVICE_PROVIDER"
            }
        }

        if (userRole != "Service Provider"){
            servicesListApi()
            feeCustomize.isVisible = false
            feesViewTv.isVisible = false
            feesView.isVisible = false
        }else{
            feeCustomize.isVisible = false
            feesViewTv.isVisible = false
            feesView.isVisible = false
            feesConfig.isVisible = false
        }


        feesConfig.setOnClickListener {
            openPopUpForFeeCustomize()
        }



        back.visibility = View.VISIBLE
        title.text = "Register"
        back.setSafeOnClickListener {
            finish()
        }

        passwordShow()
        profile_image.setSafeOnClickListener {

            RequestPermission.requestMultiplePermissions(this)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERM_CODE
                )
            } else {
                selectImage()

            }
        }

        login.setSafeOnClickListener{
            finish()
        }

        termsandconditions.setSafeOnClickListener {
            val intent = Intent(this, terms_conditions::class.java)
            startActivity(intent)
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

        ServiceProvider_register.setSafeOnClickListener {
            validateForm()
            if (isSocial){
                if (FirstName_ET.text.isNotEmpty() &&
                    LastName_ET.text.isNotEmpty() &&
                    Email_ET.text.isNotEmpty() &&
                    Mobile_ET.text.isNotEmpty() &&
                    Address1_ET.text.isNotEmpty() &&
                    Country_ET.text.isNotEmpty() &&
                    ZipCode_ET.text.isNotEmpty() &&
                    ZipCode_ET.text.toString().length > 3) {
                    if (!checkBox.isChecked) {
                        androidextention.alertBox("Please accept Terms of Conditions.", this)
                    } else {
                        signupServiceProvider()
                    }
                }
            }else{

                if (FirstName_ET.text.isNotEmpty() &&
                    LastName_ET.text.isNotEmpty() &&
                    Email_ET.text.isNotEmpty() &&
                    Mobile_ET.text.isNotEmpty() &&
                    Address1_ET.text.isNotEmpty() &&
                    Country_ET.text.isNotEmpty() &&
                    ZipCode_ET.text.isNotEmpty() &&
                    ZipCode_ET.text.toString().length > 3 &&
                    Password_ET.text.isNotEmpty() &&
                    Confirm_ET.text.isNotEmpty() &&
                    Confirm_ET.text.toString() == Password_ET.text.toString()
                ) {
                    if (!checkBox.isChecked) {
                        androidextention.alertBox("Please accept Terms of Conditions.", this)
                    } else {
                        signupServiceProvider()
                    }
                }
            }




        }
        locationPermission()

        Country_ET.setSafeOnClickListener {
            flag = "Country"
            openPopUp()
            Country_TV.text = ""
            Country_ET.setBackgroundResource(R.drawable.backgroundsearch)
        }
        State_ET.setSafeOnClickListener {
            if (Country_ET.text.isEmpty()) {
                Country_TV.text = "*Please select country first."
                Country_ET.setBackgroundResource(R.drawable.input_error)
            } else {
                Country_TV.text = ""
                State_TV.text = ""
                flag = "State"
                Country_ET.setBackgroundResource(R.drawable.backgroundsearch)
                State_ET.setBackgroundResource(R.drawable.backgroundsearch)
                openPopUp()
            }
        }
        City_ET.setSafeOnClickListener {
            if (State_ET.text.isEmpty()) {
                State_TV.text = "*Please select state first."
                Country_TV.text = ""
                State_ET.setBackgroundResource(R.drawable.input_error)
                Country_ET.setBackgroundResource(R.drawable.backgroundsearch)
            } else {
                City_TV.text = ""
                State_TV.text = ""
                flag = "City"
                openPopUp()
                State_ET.setBackgroundResource(R.drawable.backgroundsearch)
                Country_ET.setBackgroundResource(R.drawable.backgroundsearch)
            }
        }
    }

    private fun INITTILAZIGOOGLEPLACE() {
        Places.initialize(mContext, ServiceConstant.API_KEY)
        val placesClient = Places.createClient(mContext)
    }


    private fun getLatLong(latLng: LatLng) {
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
                    var location: JSONObject
                    try {
                        try {
                            Log.d("tag","response.toString() => ${response.toString()}")
                            var addressLine1 = ArrayList<String>()
                            var addressLine2 = ""
                            var country = ""
                            var state = ""
                            var cityName = ""
                            var zipcodeText = ""

                            var objectData = JSONObject(response.toString())
                            val jsonArrayData: JSONArray = objectData.getJSONArray("results")
                            val jsonObject = jsonArrayData[0] as JSONObject

                            val jsonObjectWorkOrderInfo = JSONObject(jsonObject.getString("geometry"))
                            val locationdata = JSONObject(jsonObjectWorkOrderInfo.getString("location"))
                            lat = locationdata.getString("lat").toDouble()
                            long = locationdata.getString("lng").toDouble()

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

                                Address1_ET.text = Editable.Factory.getInstance().newEditable(addressLine1.joinToString(","))
                                Country_ET.text = country
                                State_ET.text = state
                                City_ET.text = cityName
                                ZipCode_ET.setText(zipcodeText)

                                validateForm()
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

    private fun validateForm() {
        FormValidation.ServiceProviderRegistration(
            FirstName_ET,
            FirstName_TV,
            LastName_ET,
            LastName_TV,
            Email_ET,
            Email_TV,
            Mobile_ET,
            Mobile_TV,
            Countrycode,
            Address1_ET,
            Address1_TV,
            Country_ET,
            Country_TV,
            ZipCode_ET,
            ZipCode_TV,
            Password_ET,
            Password_TV,
            PaswordLL,
            Confirm_ET,
            Confirm_TV,
            ConfirmPaswordLL,
            Address1eEtLL,
            isSocial
        )
    }

    private fun passwordShow() {
        cross_eye.setSafeOnClickListener {
            when (passwordNotVisible) {
                0 -> {
                    Password_ET.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    cross_eye.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.eye))
                    Password_ET.setSelection(Password_ET.length())
                    passwordNotVisible = 1


                }
                1 -> {
                    Password_ET.transformationMethod = PasswordTransformationMethod.getInstance()
                    cross_eye.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_eye_slash_solid))
                    Password_ET.setSelection(Password_ET.length())
                    passwordNotVisible = 0
                }
                else -> {
                    Password_ET.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    cross_eye.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.eye))
                    Password_ET.setSelection(Password_ET.length())
                    passwordNotVisible = 1
                }
            }
        }
        cross_eye2.setSafeOnClickListener {
            if (passwordNotVisible == 0) {
                Confirm_ET.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                cross_eye2.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.eye))
                Confirm_ET.setSelection(Confirm_ET.length())
                passwordNotVisible = 1


            } else if (passwordNotVisible == 1) {
                Confirm_ET.transformationMethod = PasswordTransformationMethod.getInstance()
                cross_eye2.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_eye_slash_solid))
                Confirm_ET.setSelection(Confirm_ET.length())
                passwordNotVisible = 0
            } else {
                Confirm_ET.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                cross_eye2.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.eye))
                Confirm_ET.setSelection(Confirm_ET.length())
                passwordNotVisible = 1
            }
        }
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    fun openPopUp() {

        try {
            val binding = LayoutInflater.from(this).inflate(R.layout.pop_lists, null)
            dialog = DialogUtils().createDialog(this, binding.rootView, 0)!!
            recyclerView = binding.findViewById(R.id.popup_recyclerView)
            no_notification = binding.findViewById(R.id.no_notification)
            recyclerView.layoutManager = LinearLayoutManager(this)
            progressbarpopup = binding.findViewById(R.id.progressbar_pop)
            pop_lottie = binding.findViewById(R.id.pop_lottie)
            pop_internet_connection = binding.findViewById(R.id.pop_internet_connection)
            val dialogTitle = binding.findViewById<TextView>(R.id.popupTitle)
            val dialogBackButton = binding.findViewById<ImageView>(R.id.BackButton)
            val searchEditText = binding.findViewById<EditText>(R.id.search_bar_edittext_popuplist)

            searchEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(text: Editable?) {
                    filterData(text.toString(), flag)
                }

            })
            dialogBackButton.setSafeOnClickListener { dialog.dismiss() }




            when (flag) {
                "Country" -> {
                    dialogTitle.text = flag
                    countryListApi()

                }
                "State" -> {

                    dialogTitle.text = flag
                    stateListApi()
                }
                "City" -> {
                    dialogTitle.text = flag
                    cityListApi()

                }
            }
            dialog.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun filterData(searchText: String, flag: String) {
        val filteredList = ArrayList<CountryListResult>()
        val filteredStateList = ArrayList<StateList_Result>()
        val filteredCityList = ArrayList<CityListResult>()

        try {
            when (flag) {
                "Country" -> data.forEach { item ->
                    if (item.name.lowercase().contains(searchText.lowercase())) {
                        filteredList.add(item)
                    }
                }
                "State" -> Statedatadata.forEach { item ->
                    if (item.name.lowercase().contains(searchText.lowercase())) {
                        filteredStateList.add(item)
                    }
                }
                "City" -> Citydatadata.forEach { item ->
                    if (item.name.lowercase().contains(searchText.lowercase())) {
                        filteredCityList.add(item)
                    }
                }
            }

            when (flag) {
                "Country" -> adapter.filterList(filteredList)
                "State" -> adapterState.filterList(filteredStateList)
                "City" -> adapterCity.filterList(filteredCityList)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun setAdapter() {
        adapter = CountryListPopUp(mContext, data, flag, this)
        recyclerView.adapter = adapter
    }

    fun setStateAdapter() {
        adapterState = StateListPopUp(mContext, Statedatadata, flag, this)
        recyclerView.adapter = adapterState
    }

    fun setCityAdapter() {
        adapterCity = CityListPopUp(mContext, Citydatadata, flag, this)
        recyclerView.adapter = adapterCity
    }

    fun countryListApi() {
        if (androidextention.isOnline(mContext)) {
            pop_internet_connection.visibility = View.GONE
            pop_lottie!!.initLoader(false)
            progressbarpopup.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<CountryListResponse> =
                ApiCallBack(object :
                    ApiResponseListener<CountryListResponse> {
                    override fun onApiSuccess(response: CountryListResponse, apiName: String?) {
                        progressbarpopup.visibility = View.GONE
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
                        progressbarpopup.visibility = View.GONE
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
//                            pojo = gson.fromJson(response, pojo::class.java)
//                            androidextention.alertBox(pojo.responseMessage, mContext)
                            no_notification.visibility = View.VISIBLE

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbarpopup.visibility = View.GONE
                        no_notification.visibility = View.VISIBLE

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
            no_notification.visibility = View.GONE
            pop_internet_connection.visibility = View.VISIBLE
            pop_lottie!!.initLoader(true)
        }
    }

    fun stateListApi() {
        if (androidextention.isOnline(mContext)) {
            pop_internet_connection.visibility = View.GONE
            pop_lottie!!.initLoader(false)
            progressbarpopup.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<StateListResponse> =
                ApiCallBack<StateListResponse>(object :
                    ApiResponseListener<StateListResponse> {
                    override fun onApiSuccess(response: StateListResponse, apiName: String?) {
                        progressbarpopup.visibility = View.GONE
//                        androidextention.disMissProgressDialog(mContext)
                        if (response.responseCode == 200) {
                            try {

                                Statedatadata = response.result
                                setStateAdapter()
//                            requestImage = response.result?.mediaUrl!!
//
//                            if (!requestImage.equals("")) {
//                                signupServiceProvider()
//                            }

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbarpopup.visibility = View.GONE
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
//                            pojo = gson.fromJson(response, pojo::class.java)
//                            androidextention.alertBox(pojo.responseMessage, mContext)
                            no_notification.visibility = View.VISIBLE


                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbarpopup.visibility = View.GONE
                        no_notification.visibility = View.VISIBLE
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
            no_notification.visibility = View.GONE
            pop_internet_connection.visibility = View.VISIBLE
            pop_lottie!!.initLoader(true)
        }
    }

    fun cityListApi() {
        if (androidextention.isOnline(mContext)) {
            pop_internet_connection.visibility = View.GONE
            pop_lottie!!.initLoader(false)
            progressbarpopup.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<CityListResponse> =
                ApiCallBack<CityListResponse>(object :
                    ApiResponseListener<CityListResponse> {
                    override fun onApiSuccess(response: CityListResponse, apiName: String?) {
                        progressbarpopup.visibility = View.GONE
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
                        progressbarpopup.visibility = View.GONE
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
//                            pojo = gson.fromJson(response, pojo::class.java)
//                            androidextention.alertBox(pojo.responseMessage, mContext)
                            no_notification.visibility = View.VISIBLE

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbarpopup.visibility = View.GONE
                        no_notification.visibility = View.VISIBLE

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
            no_notification.visibility = View.GONE
            pop_internet_connection.visibility = View.VISIBLE
            pop_lottie!!.initLoader(true)
        }
    }

    override fun getCountry(name: String, flag: String, iso: String) {
        when (flag) {
            "Country" -> {
                isocode = iso
                Country_ET.text = name
                isoState = ""
                State_ET.text = ""
                City_ET.text = ""
                dialog.dismiss()
            }
            "State" -> {
                isoState = iso
                State_ET.text = name
                City_ET.text = ""
                dialog.dismiss()
            }
            "City" -> {
                City_ET.text = name
                dialog.dismiss()
            }
        }

    }

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
                        if (path != null) {
                            imageFile = File(path)
                        }


                        choosePhotoBottomSheet.dismiss()
                        var requestGalleryImageFile: RequestBody =
                            RequestBody.create("image/*".toMediaTypeOrNull(), imageFile!!)
                        imageparts =
                            MultipartBody.Part.createFormData(
                                "uploaded_file",
                                imageFile?.getName(),
                                requestGalleryImageFile
                            )
                        Glide.with(this).load(imageFile).into(profile_image)
                        imageUploadFlag = true

                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }

            } else if (requestCode == CAMERA) {

                imageFile = File(imagePath)

                choosePhotoBottomSheet.dismiss()
                var requestGalleryImageFile: RequestBody =
                    RequestBody.create("image/*".toMediaTypeOrNull(), imageFile!!)
                imageparts =
                    MultipartBody.Part.createFormData(
                        "uploaded_file",
                        imageFile!!.getName(),
                        requestGalleryImageFile
                    )
                Glide.with(this).load(imageFile).into(profile_image)
                imageUploadFlag = true
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun getPathFromURI(contentUri: Uri?): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            this.getContentResolver().query(contentUri!!, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            res = cursor.getString(column_index)
        }
        cursor.close()
        return res
    }

    private fun selectImage() {
        // on below line we are creating a new bottom sheet dialog.
        val dialog = BottomSheetDialog(this)

        // on below line we are inflating a layout file which we have created.
        val view = layoutInflater.inflate(R.layout.choose_camera_bottom_sheet, null)

        // below line is use to set cancelable to avoid
        // closing of dialog box when clicking on the screen.
        dialog.setCancelable(true)

        val CameraButton = view.findViewById<ImageView>(R.id.choose_from_camera)
        CameraButton.setSafeOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
                try {
                    imageFile = createImageFile()!!
                } catch (ex: IOException) {
                }
                // Continue only if the File was successfully created
                if (imageFile != null) {
                    photoURI = FileProvider.getUriForFile(
                        this,
                        "com.exobe.fileprovider",
                        imageFile!!
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, CAMERA)
                    dialog.dismiss()
                }
            }
        }

        val GalleryButton = view.findViewById<ImageView>(R.id.choose_from_gallery)
        GalleryButton.setSafeOnClickListener {
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY)
            dialog.dismiss()
        }

        // on below line we are setting
        // content view to our view.
        dialog.setContentView(view)

        // on below line we are calling
        // a show method to display a dialog.
        dialog.show()
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )

        imagePath = image.absolutePath
        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_CANCELED) {
            return
        } else {
            if (requestCode == GALLERY) {
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        image = data.data!!
                        val path = getPathFromURI(image)
                        val maxFileSizeInBytes = 5 * 1024 * 1024
                        if (path != null) {
                            imageFile = File(path)
                            if (imageFile!!.length() <= maxFileSizeInBytes) {
                                Glide.with(this).load(imageFile).into(profile_image)

                                val finalBitmap = ImageRotation.modifyOrientation(ImageRotation.getBitmap(path)!!, path)
                                profilePic = finalBitmap?.let { bitmapToString(it) }!!

                            }else{
                                androidextention.alertBoxCommon(message = "Please select a image that is 5 MB or smaller.", context = this)
                                return
                            }





                        }

                        USER_IMAGE_UPLOADED = true

                    }

                }
            } else if (requestCode == CAMERA) {
                if (resultCode == RESULT_OK) {

                    try {

                        imageFile = File(imagePath)
                        Glide.with(this).load(imageFile).into(profile_image)


                        val finalBitmap = ImageRotation.modifyOrientation(ImageRotation.getBitmap(imagePath)!!, imagePath)
                        profilePic = finalBitmap?.let { bitmapToString(it) }!!

                        USER_IMAGE_UPLOADED = true

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                }
            } else if (requestCode == 110) {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        if (requestCode == PLACE_PICKER_REQUEST) {
                            when (resultCode) {
                                Activity.RESULT_OK -> {
                                    val place = Autocomplete.getPlaceFromIntent(data)
                                    val latLng = place.latLng
                                    getLatLong(latLng)
                                }
                                AutocompleteActivity.RESULT_ERROR -> {
                                    val status = Autocomplete.getStatusFromIntent(data)
                                }
                                Activity.RESULT_CANCELED -> {
                                }
                            }
                        }
                    }
                }
            }
        }

    }



    fun signupServiceProvider() {
        if (androidextention.isOnline(this)) {
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<ServiceProviderSignup_Response> =
                ApiCallBack<ServiceProviderSignup_Response>(this, "signupRetailer", mContext)


            if (lat == 0.0 && long == 0.0){
                val fullAddress = Address1_ET.text.toString() + " " + ZipCode_ET.text.toString() + " " + City_ET.text.toString()+ " " + State_ET.text.toString() + " " + Country_ET.text.toString()
                val coordinates = LocationClass.getAddressCoordinates(this,fullAddress)

                if (coordinates != null) {
                    lat = coordinates.first
                    long = coordinates.second


                } else {
                    androidextention.alertBox("Invalid address or error occurred,Please try again.",this)
                    Progresss.stop()
                    return
                }
            }


            val storeLocation = ServiceProvider_StoreLocation()
            val cor = ArrayList<Double>()
            cor.add(long)
            cor.add(lat)
            storeLocation.type = "Point"
            storeLocation.coordinates = cor

            val serviceProvidersRequest = ServiceProviderSignup_Request()

            val pickupFeeArray = ArrayList<PickupFeeData>()

            for (request in dataFeeCustomize.indices){



                when (userRole) {
                    "Field Entity" -> {
                        if(dataFeeCustomize[request].dailyFee == null) dataFeeCustomize[request].dailyFee = dataFeeCustomize[request].storageFee!!.daily.toString()
                        if(dataFeeCustomize[request].weeklyFee == null) dataFeeCustomize[request].weeklyFee = dataFeeCustomize[request].storageFee!!.weekly.toString()
                        if(dataFeeCustomize[request].quarterlyFee == null) dataFeeCustomize[request].quarterlyFee = dataFeeCustomize[request].storageFee!!.quarterly.toString()
                        if(dataFeeCustomize[request].monthlyFee == null) dataFeeCustomize[request].monthlyFee = dataFeeCustomize[request].storageFee!!.monthly.toString()


                        val sizeType = SizeTypeRequest()
                        sizeType.maximumSize = dataFeeCustomize[request].sizeType!!.maximumSize
                        sizeType.minimumSize = dataFeeCustomize[request].sizeType!!.minimumSize

                        pickupFeeArray.add(PickupFeeData(deliveryType = "",
                            feeName = dataFeeCustomize[request].feeName, pickupFee = "",
                            dailyFee = dataFeeCustomize[request].dailyFee,
                            weeklyFee = dataFeeCustomize[request].weeklyFee,
                            monthlyFee = dataFeeCustomize[request].monthlyFee,
                            quarterlyFee = dataFeeCustomize[request].quarterlyFee,
                            deliveryFee = "",standardFee= dataFeeCustomize[request].standardFee.toString(),type = "",
                            sizeType = sizeType))
                    }
                    "Delivery Driver" -> {
                        if(dataFeeCustomize[request].fees == null) dataFeeCustomize[request].fees =
                            dataFeeCustomize[request].deliveryAmount.toString()


                        pickupFeeArray.add(PickupFeeData(deliveryType = dataFeeCustomize[request].deliveryType,
                            feeName = dataFeeCustomize[request].feeName, pickupFee = "", dailyFee = "", weeklyFee = "",
                            monthlyFee = "", quarterlyFee = "",deliveryFee = dataFeeCustomize[request].fees,
                            standardFee= dataFeeCustomize[request].standardFee.toString(),
                            type = dataFeeCustomize[request].type!!))
                    }
                    else -> {
                        if(dataFeeCustomize[request].fees == null) dataFeeCustomize[request].fees =
                            dataFeeCustomize[request].pickupFee.toString()

                        pickupFeeArray.add(PickupFeeData(deliveryType = dataFeeCustomize[request].deliveryType,
                            feeName = dataFeeCustomize[request].feeName,
                            pickupFee = dataFeeCustomize[request].fees , dailyFee = "", weeklyFee = "",
                            monthlyFee = "", quarterlyFee = "",deliveryFee = "",
                            standardFee= dataFeeCustomize[request].standardFee.toString(),
                            type = ""))
                    }
                }



            }




            serviceProvidersRequest.pickupFeeArray  = pickupFeeArray
            serviceProvidersRequest.userType = type
            serviceProvidersRequest.profilePic = "data:image/png;base64,${profilePic}"
            serviceProvidersRequest.firstName = FirstName_ET.text.toString()
            serviceProvidersRequest.lastName = LastName_ET.text.toString()
            serviceProvidersRequest.email = Email_ET.text.toString()
            serviceProvidersRequest.countryCode = ccp_MyProfile.selectedCountryCode.toString()
            serviceProvidersRequest.phoneNumber = Phone_ET.text.toString()
            serviceProvidersRequest.storeLocation = storeLocation
            serviceProvidersRequest.mobileNumber = Mobile_ET.text.toString()
            serviceProvidersRequest.password = Password_ET.text.toString()


            val addressText = StringBuffer()
            if (Address1_ET.text.isEmpty()) {
                addressText.append(Address1_ET.text.toString())
            }
            if (Address2_ET.text.isEmpty()) {
                addressText.append(" ${Address2_ET.text}")
            }
            serviceProvidersRequest.address = addressText.toString()
            serviceProvidersRequest.addressLine1 = Address1_ET.text.toString()
            serviceProvidersRequest.addressLine2 = Address2_ET.text.toString()
            serviceProvidersRequest.country = Country_ET.text.toString()
            serviceProvidersRequest.countryIsoCode = isocode
            serviceProvidersRequest.stateIsoCode = isoState
            serviceProvidersRequest.state = State_ET.text.toString()
            serviceProvidersRequest.city = City_ET.text.toString()
            serviceProvidersRequest.zipCode = ZipCode_ET.text.toString()
            serviceProvidersRequest.deviceType = "android"
            serviceProvidersRequest.deviceToken =
                SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.KEY_DEVICE_TOKEN)
                    .toString()

            try {
                serviceManager.ServiceProviderSignupApi(callBack, serviceProvidersRequest)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            androidextention.alertBox("Please check your internet connection.", this)

        }
    }

    override fun onApiSuccess(response: ServiceProviderSignup_Response, apiName: String?) {
        progressbar.visibility = View.GONE
        if (response!!.responseCode == 200) {
            try {
                Toast.makeText(this, "Registration stage 1 completed, login and provide business details and documentation.", Toast.LENGTH_SHORT).show()

                CustomerBottomSheet("Service_Provider", this, this).show(
                    supportFragmentManager,
                    "ModalBottomSheet"
                )
                finish()

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override fun onApiErrorBody(response: String, apiName: String?) {
        progressbar.visibility = View.GONE
        val gson = GsonBuilder().create()
        var pojo = response_modal_class()

        try {
            pojo = gson.fromJson(response, pojo::class.java)
            androidextention.alertBox(pojo.responseMessage, this)

        } catch (e: Exception) {
            // handle failure at error parse
        }
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        progressbar.visibility = View.GONE

    }


    //Profile Image

    fun initializedControl() {
        FirstName_ET.addTextChangedListener(textWatcher)
        LastName_ET.addTextChangedListener(textWatcher)
        Email_ET.addTextChangedListener(textWatcher)
        Mobile_ET.addTextChangedListener(textWatcher)
        Address1_ET.addTextChangedListener(textWatcher)
        Country_ET.addTextChangedListener(textWatcher)
        State_ET.addTextChangedListener(textWatcher)
        City_ET.addTextChangedListener(textWatcher)
        ZipCode_ET.addTextChangedListener(textWatcher)
        Password_ET.addTextChangedListener(textWatcher)
        Confirm_ET.addTextChangedListener(textWatcher)

    }


    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s.toString() != "") {
                validateForm()

            }

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }


    }

    private fun locationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQ_CODE
            )
        } else {
            LocationClass.getCurrentLocation(this)
            LocationClass.displayLocationSettingsRequest(this)
        }

    }

    override fun isLoginListener() {

    }



    private fun openPopUpForFeeCustomize() {
        try {
            val bindingPopup = LayoutInflater.from(this).inflate(R.layout.pop_lists, null)
            dialogFeeCustomize = DialogUtils().createDialog(this, bindingPopup.rootView, 0)!!
            recyclerViewFeeCustomize = bindingPopup.findViewById(R.id.popup_recyclerView)
            tickFeeCustomize = bindingPopup.findViewById(R.id.tick)
            recyclerViewFeeCustomize.layoutManager = LinearLayoutManager(this)


            tickFeeCustomize.isVisible = true


            // Iterate through the data and set the selected state for each  object


            if (userRole == "Field Entity"){

                for (fees in FeeCustomizeRequest) {

                    val selectedPrice = FeeCustomizeRequest.find {
                        it.isSelected
                    }
                    if (selectedPrice != null) {
                        fees.isSelected = selectedPrice.isSelected
                        fees.dailyFee = selectedPrice.dailyFee
                        fees.weeklyFee = selectedPrice.weeklyFee
                        fees.monthlyFee = selectedPrice.monthlyFee
                        fees.quarterlyFee = selectedPrice.quarterlyFee
                    }
                }

                setAdapterFeeConfigFieldEntity(dataFeeCustomize)
            }else{
                for (fees in FeeCustomizeRequest) {

                    val selectedPrice = FeeCustomizeRequest.find {
                        it.isSelected
                    }
                    if (selectedPrice != null) {
                        fees.isSelected = selectedPrice.isSelected
                        fees.fees = selectedPrice.fees
                    }
                }

                setAdapterFeeConfig(dataFeeCustomize)
            }




            tickFeeCustomize.isVisible = true

            val title = bindingPopup.findViewById<TextView>(R.id.popupTitle)
            val searchArea = bindingPopup.findViewById<RelativeLayout>(R.id.searchArea)
            title.text = "Fees Configuration"
            searchArea.isVisible = false
            val backButton = bindingPopup.findViewById<ImageView>(R.id.BackButton)
            backButton.setOnClickListener { dialogFeeCustomize.dismiss() }


            tickFeeCustomize.setOnClickListener {
//                 Get Data Based On Role

                FeeCustomizeRequest = if (userRole == "Field Entity"){
                    dataFeeCustomize.filter { it.isSelected && it.dailyFee.isNotBlank() && it.weeklyFee.isNotBlank()
                            && it.monthlyFee.isNotBlank() && it.quarterlyFee.isNotBlank() }
                }else{
                    dataFeeCustomize.filter { it.isSelected && it.fees.isNotBlank() }
                }

//                 Set Data Based On Role

                val formattedData = if (userRole == "Field Entity"){
                    FeeCustomizeRequest.joinToString(", ") { "${it.feeName} Daily-R (${it.dailyFee}) Weekly-R (${it.weeklyFee}) Monthly-R (${it.monthlyFee}) Quarterly-R (${it.quarterlyFee})" }
                }else{
                    FeeCustomizeRequest.joinToString(", ") { "${it.feeName}(${it.fees})" }
                }
                feeCustomize.text = formattedData
                dialogFeeCustomize.dismiss()
            }

            dialogFeeCustomize.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setAdapterFeeConfig(result: List<DeliveryFeesResult>) {
        recyclerViewFeeCustomize.layoutManager = LinearLayoutManager(this)
        adapterFeeCustomize = FeesConfigurationAdapter(this, result, userRole)
        recyclerViewFeeCustomize.adapter = adapterFeeCustomize
    }

    private fun setAdapterFeeConfigFieldEntity(result: List<DeliveryFeesResult>) {
        recyclerViewFeeCustomize.layoutManager = LinearLayoutManager(this)
        val adapterFeeCustomizeFieldEntity = FeesConfigurationFieldEntityAdapter(this, result)
        recyclerViewFeeCustomize.adapter = adapterFeeCustomizeFieldEntity
    }


    private fun servicesListApi() {
        if (androidextention.isOnline(this)) {
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<DeliveryFessResponse> =
                ApiCallBack(object :
                    ApiResponseListener<DeliveryFessResponse> {

                    override fun onApiSuccess(
                        response: DeliveryFessResponse,
                        apiName: String?
                    ) {
                        if (response.responseCode == 200) {
                            try {
                                dataFeeCustomize = response.result
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
//                            androidextention.alertBox(pojo.responseMessage, requireContext())

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {

                    }

                }, "pickupFeeListApi", this)
            try {
                serviceManager.pickupFeeListOption(callBack,type)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun bitmapToString(`in`: Bitmap): String {
        var options = 50
        var base64Value = ""
        val bytes = ByteArrayOutputStream()
        `in`.compress(Bitmap.CompressFormat.JPEG, 40, bytes)
        while (bytes.toByteArray().size / 1024 > 400) {
            bytes.reset()
            `in`.compress(Bitmap.CompressFormat.JPEG, options, bytes)
            options -= 10
        }
        base64 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getEncoder().encodeToString(bytes.toByteArray())
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        base64Value = base64Value.replace("\n", "") + base64
        return base64Value
    }

}