package com.exobe.activities.customer

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
import androidx.appcompat.app.AppCompatActivity
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
import com.exobe.adaptor.StateListPopUp
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.activities.OtpVerification
import com.exobe.activities.terms_conditions
import com.exobe.utils.ImageRotation
import com.exobe.utils.LocationClass
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.popupItemClickListnerCountry
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.SignupRequest
import com.exobe.entity.request.StoreLocation
import com.exobe.entity.response.*
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceConstant
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.utils.Progresss
import com.exobe.validations.DialogUtils
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
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RegisterCustomerActivity : AppCompatActivity(), ApiResponseListener<SignupResponse>,
    popupItemClickListnerCountry {

    private lateinit var backBtn : ImageView
    private lateinit var logIn: LinearLayout
    private lateinit var ccpMyprofile: CountryCodePicker
    private lateinit var firstNameET: EditText
    private lateinit var lastNameET: EditText
    private lateinit var phoneET: EditText
    private lateinit var EmailEt: EditText
    private lateinit var NewPasswordEt: EditText
    private lateinit var ConfirmPasswordET: EditText
    private lateinit var FirstNameTV: TextView
    private lateinit var LastNameTV: TextView
    private lateinit var PhoneTV: TextView
    private lateinit var EmailAddressTV: TextView
    private lateinit var PasswordTV: TextView
    private lateinit var ConfirmPasswordTV: TextView
    private lateinit var TermTV: TextView
    private lateinit var termsandconditions: TextView
    private lateinit var SignUp: Button
    private lateinit var checkBox: CheckBox
    private lateinit var FirstNameLL: LinearLayout
    private lateinit var LastNameLL: LinearLayout
    private lateinit var PhoneLL: LinearLayout
    private lateinit var EmailAddress: LinearLayout
    private lateinit var Password: LinearLayout
    private lateinit var ConfirmPassword: LinearLayout
    private lateinit var cross_eye: ImageView
    private lateinit var cross_eye2: ImageView
    private var passwordNotVisible = 0
    private lateinit var select_Image: ImageView
    val CAMERA_PERM_CODE = 101
    var imageFile: File? = null
    var photoURI: Uri? = null
    private var CAMERA: Int = 2
    var imagePath = ""
    private val GALLERY = 1
    private lateinit var image: Uri
    private lateinit var profile_image: CircleImageView
    private lateinit var customer_progress_bar: ProgressBar
    var USER_IMAGE_UPLOADED = false
    var mContext: Context = this
    var type = ""
    private lateinit var email: String
    var lat: Double = 0.0
    var long: Double = 0.0


    var profilePic = ""



    private lateinit var address: EditText
    private lateinit var address_tv: TextView
    private lateinit var city: TextView
    private lateinit var city_tv: TextView
    private lateinit var zipcode: EditText
    private lateinit var zipcode_tv: TextView
    private lateinit var country1: TextView
    private lateinit var address2: EditText
    private lateinit var country_tv: TextView
    private lateinit var State_ET: TextView
    private lateinit var State_TV: TextView
    private lateinit var address_ll: LinearLayout
    private lateinit var country_ll: LinearLayout
    private lateinit var State_ET_ll: LinearLayout
    private lateinit var city_ll: LinearLayout
    private lateinit var zipcode_ll: LinearLayout

    private lateinit var dialog: Dialog
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CountryListPopUp
    private lateinit var adapterState: StateListPopUp
    private lateinit var adapterCity: CityListPopUp
    private lateinit var locationtrackingClick: ImageView
    var data: ArrayList<CountryListResult> = ArrayList()
    var Statedatadata: ArrayList<StateList_Result> = ArrayList()
    var Citydatadata: ArrayList<CityListResult> = ArrayList()
    var flag = ""
    var isocode: String = ""
    var isoState: String = ""

    private lateinit var progressbarpopup: ProgressBar
    private lateinit var no_notification: LinearLayout
    private val LOCATION_PERMISSION_REQ_CODE = 1000;

    private lateinit var pop_internet_connection: RelativeLayout
    var pop_lottie: LottieAnimationView? = null

    var PLACE_PICKER_REQUEST = 110

    private var base64: String? = null


    private var isSocial = false
    private var googleFirstName = ""
    private var googleLastName = ""
    private var googleEmail = ""
    private var googleProfilePicURL = ""
    private lateinit var passwordHideVisible:LinearLayout
    private lateinit var manualParticipation:RadioButton
    private lateinit var semiParticipation:RadioButton
    private lateinit var automaticParticipation:RadioButton
    var participationIn = "MANUAL"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        window.attributes.windowAnimations = R.style.Fade
        mContext = this.applicationContext!!


        backBtn = findViewById(R.id.iv_back)
        ccpMyprofile = findViewById(R.id.ccp_MyProfile)
        zipcode_ll = findViewById(R.id.zipcode_ll)
        city_ll = findViewById(R.id.city_ll)
        State_ET_ll = findViewById(R.id.State_ET_ll)
        country_ll = findViewById(R.id.country_ll)
        address_ll = findViewById(R.id.address_ll)
        State_ET = findViewById(R.id.State_ET)
        State_TV = findViewById(R.id.State_TV)
        address = findViewById(R.id.address)
        address_tv = findViewById(R.id.address_tv)
        city = findViewById(R.id.city)
        city_tv = findViewById(R.id.city_tv)
        zipcode_tv = findViewById(R.id.zipcode_tv)
        zipcode = findViewById(R.id.zipcode)
        country1 = findViewById(R.id.country1)
        address2 = findViewById(R.id.address2)
        country_tv = findViewById(R.id.country_tv)

        customer_progress_bar = findViewById(R.id.customer_progress_bar)
        logIn = findViewById(R.id.LogIN)
        firstNameET = findViewById(R.id.FirstNameET)
        lastNameET = findViewById(R.id.LastNameET)
        phoneET = findViewById(R.id.PhoneET)
        EmailEt = findViewById(R.id.EmailEt)
        NewPasswordEt = findViewById(R.id.NewPasswordEt)
        ConfirmPasswordET = findViewById(R.id.ConfirmPasswordET)
        FirstNameTV = findViewById(R.id.FirstNameTV)
        LastNameTV = findViewById(R.id.LastNameTV)
        PhoneTV = findViewById(R.id.PhoneTV)
        EmailAddressTV = findViewById(R.id.EmailAddressTV)
        PasswordTV = findViewById(R.id.PasswordTV)
        ConfirmPasswordTV = findViewById(R.id.ConfirmPasswordTV)
        TermTV = findViewById(R.id.TermTV)
        SignUp = findViewById(R.id.SignUp)
        checkBox = findViewById(R.id.CheckBox)
        FirstNameLL = findViewById(R.id.FirstNameLL)
        LastNameLL = findViewById(R.id.LastNameLL)
        PhoneLL = findViewById(R.id.PhoneLL)
        EmailAddress = findViewById(R.id.EmailAddress)
        Password = findViewById(R.id.Password)
        ConfirmPassword = findViewById(R.id.ConfirmPassword)
        cross_eye = findViewById(R.id.cross_eye)
        cross_eye2 = findViewById(R.id.cross_eye2)
        select_Image = findViewById(R.id.select_Image)
        profile_image = findViewById(R.id.profile_image)
        termsandconditions = findViewById(R.id.termsandconditions)
        passwordHideVisible = findViewById(R.id.passwordHideVisible)

        manualParticipation = findViewById(R.id.manualParticipation)
        semiParticipation = findViewById(R.id.semiParticipation)
        automaticParticipation = findViewById(R.id.automaticParticipation)
        locationtrackingClick = findViewById(R.id.locationtrackingClick)

        manualParticipation.isChecked = true


        type = "CUSTOMER"
        INITTILAZIGOOGLEPLACE()
        initializedControl()
        locationpermission()

        intent?.getBooleanExtra("isSocial",false)?.let { isSocial = it }
        intent?.getStringExtra("googleFirstName")?.let { googleFirstName = it }
        intent?.getStringExtra("googleLastName")?.let { googleLastName = it }
        intent?.getStringExtra("googleEmail")?.let { googleEmail = it }
        intent?.getStringExtra("googleProfilePicURL")?.let { googleProfilePicURL = it }



        if (isSocial){

            Glide.with(this).load(googleProfilePicURL).into(profile_image)
            firstNameET.setText(googleFirstName)
            lastNameET.setText(googleLastName)
            EmailEt.setText(googleEmail)

            if (googleProfilePicURL.isNotEmpty()){
                profilePic =  googleProfilePicURL
                select_Image.isEnabled = false
            }
            if (googleFirstName.isNotEmpty()){
                firstNameET.isEnabled = false
            }
            if (googleLastName.isNotEmpty()){
                lastNameET.isEnabled = false
            }
            if (googleEmail.isNotEmpty()){
                EmailEt.isEnabled = false
            }

            passwordHideVisible.isVisible =  false

        }



        logIn.setSafeOnClickListener {
            finish()
        }

        backBtn.setSafeOnClickListener {
            finish()
        }


        SignUp.setSafeOnClickListener {

            validateForm()

            if (isSocial){
                if (firstNameET.text.isNotEmpty()
                    && lastNameET.text.isNotEmpty()
                    && EmailEt.text.isNotEmpty()
                    && phoneET.text.isNotEmpty()
                    && address.text.isNotEmpty()
                    && country1.text.isNotEmpty()
                    && zipcode.text.isNotEmpty()
                    && zipcode.text.toString().length > 3
                ) {
                    if (!checkBox.isChecked) {
                        androidextention.alertBox("Please accept Terms of Conditions.", this)
                    } else {
                        signupCustomer()
                    }
                }
            }else{
                if (firstNameET.text.isNotEmpty()
                    && lastNameET.text.isNotEmpty()
                    && EmailEt.text.isNotEmpty()
                    && phoneET.text.isNotEmpty()
                    && NewPasswordEt.text.isNotEmpty()
                    && ConfirmPasswordET.text.isNotEmpty() && ConfirmPasswordET.text.toString() == NewPasswordEt.text.toString()
                    && address.text.isNotEmpty()
                    && country1.text.isNotEmpty()
                    && zipcode.text.isNotEmpty()
                    && zipcode.text.toString().length > 3
                ) {
                    if (!checkBox.isChecked) {
                        androidextention.alertBox("Please accept Terms of Conditions.", this)
                    } else {
                        signupCustomer()
                    }
                }
            }



        }

        locationtrackingClick.setSafeOnClickListener {
            val fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(applicationContext)
            startActivityForResult(intent, PLACE_PICKER_REQUEST)
        }

        country1.setSafeOnClickListener {
            flag = "Country"
            openPopUp()
            country_tv.text = ""
            country_tv.visibility = View.GONE
            country_ll.setBackgroundResource(R.drawable.backgroundsearch)
        }
        State_ET.setSafeOnClickListener {
            if (country1.text.isEmpty()) {
                country_tv.visibility = View.VISIBLE
                country_tv.text = "*Please select country first."
                country_ll.setBackgroundResource(R.drawable.input_error)
            } else {
                country_tv.text = ""
                State_TV.text = ""
                country_tv.visibility = View.GONE
                State_TV.visibility = View.GONE
                flag = "State"
                country_ll.setBackgroundResource(R.drawable.backgroundsearch)
                State_ET_ll.setBackgroundResource(R.drawable.backgroundsearch)
                openPopUp()
            }
        }
        city.setSafeOnClickListener {
            if (State_ET.text.isEmpty()) {
                State_TV.text = "*Please select state first."
                country_tv.text = ""
                country_tv.visibility = View.GONE
                State_TV.visibility = View.VISIBLE
                State_ET_ll.setBackgroundResource(R.drawable.input_error)
                country_ll.setBackgroundResource(R.drawable.white_border)
            } else {
                city_tv.text = ""
                State_TV.text = ""
                city_tv.visibility = View.GONE
                State_TV.visibility = View.GONE
                flag = "City"
                openPopUp()
                State_ET_ll.setBackgroundResource(R.drawable.backgroundsearch)
                country_ll.setBackgroundResource(R.drawable.backgroundsearch)
            }
        }

        termsandconditions.setSafeOnClickListener {
            val intent = Intent(this, terms_conditions::class.java)
            startActivity(intent)
        }
        passwordShow()


        select_Image.setSafeOnClickListener {
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
                    val location: JSONObject
                    try {
                        try {
                            Log.d("tag","response.toString() => ${response.toString()}")
                            val addressLine1 = ArrayList<String>()
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

                                address.text = Editable.Factory.getInstance().newEditable(addressLine1.joinToString(","))
                                country1.text = country
                                State_ET.text = state
                                city.text = cityName
                                zipcode.setText(zipcodeText)
                                validateForm()

                                country1.isEnabled = country1.text.isEmpty()
                                State_ET.isEnabled = State_ET.text.isEmpty()
                                city.isEnabled = city.text.isEmpty()


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
        FormValidation.customerRegistser(
            firstNameET,
            FirstNameTV,
            lastNameET,
            LastNameTV,
            phoneET,
            PhoneTV,
            EmailEt,
            EmailAddressTV,
            NewPasswordEt,
            PasswordTV,
            ConfirmPasswordET,
            ConfirmPasswordTV,
            checkBox,
            TermTV,
            FirstNameLL,
            LastNameLL,
            PhoneLL,
            EmailAddress,
            Password,
            ConfirmPassword,
            country_ll,
            address,
            address_tv,
            country1,
            country_tv,
            zipcode,
            zipcode_tv,
            address_ll,
            zipcode_ll,
            isSocial
        )
    }
    @SuppressLint("InflateParams", "SetTextI18n")
    fun openPopUp() {

//        try {
        val binding = LayoutInflater.from(this).inflate(R.layout.pop_lists, null)
        dialog = DialogUtils().createDialog(this, binding.rootView, 0)!!
        recyclerView = binding.findViewById(R.id.popup_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        progressbarpopup = binding.findViewById(R.id.progressbar_pop)
        no_notification = binding.findViewById(R.id.no_notification)

        pop_lottie = binding.findViewById(R.id.pop_lottie)
        pop_internet_connection = binding.findViewById(R.id.pop_internet_connection)
        var dialougTitle = binding.findViewById<TextView>(R.id.popupTitle)
        var dialougbackButton = binding.findViewById<ImageView>(R.id.BackButton)
        var SearchEditText = binding.findViewById<EditText>(R.id.search_bar_edittext_popuplist)

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


        when (flag) {
            "Country" -> {
                dialougTitle.text = flag
                CountryListApi()

            }
            "State" -> {

                dialougTitle.text = flag
                StateListApi()
            }
            "City" -> {
                dialougTitle.text = flag
                CityListApi()
            }
        }
        dialog.show()

//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }

    private fun filterData(searchText: String, flag: String) {
        var filteredList: ArrayList<CountryListResult> = ArrayList()
        var filteredStateList: ArrayList<StateList_Result> = ArrayList()
        var filteredCityList: ArrayList<CityListResult> = ArrayList()
        try {
            if (flag == "Country") {
                if (data != null) {
                    for (item in data) {
                        try {
                            if (item.name.toLowerCase().contains(searchText.toLowerCase())) {
                                filteredList.add(item)
                            }
                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                        }
                    }
                }

            } else if (flag == "State") {
                if (Statedatadata != null) {
                    for (item in Statedatadata) {
                        try {
                            if (item.name.toLowerCase().contains(searchText.toLowerCase())) {
                                filteredStateList.add(item)
                            }
                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            } else if (flag.equals("City")) {
                if (Citydatadata != null) {
                    for (item in Citydatadata) {
                        try {
                            if (item.name.toLowerCase().contains(searchText.toLowerCase())) {
                                filteredCityList.add(item)
                            }
                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }

            try {
                if (flag == "Country") {
                    adapter.filterList(filteredList)
                } else if (flag == "State") {
                    adapterState.filterList(filteredStateList)
                } else if (flag == "City") {
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
        adapter = this?.let { CountryListPopUp(mContext, data, flag, this) }!!
        recyclerView.adapter = adapter
    }

    fun setStateAdapter() {
        adapterState = this?.let { StateListPopUp(mContext, Statedatadata, flag, this) }!!
        recyclerView.adapter = adapterState
    }

    fun setCityAdapter() {
        adapterCity = this?.let { CityListPopUp(mContext, Citydatadata, flag, this) }!!
        recyclerView.adapter = adapterCity
    }

    fun CountryListApi() {
        if (androidextention.isOnline(mContext)) {
            pop_internet_connection.visibility = View.GONE
            pop_lottie!!.initLoader(false)

            progressbarpopup.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<CountryListResponse> =
                ApiCallBack<CountryListResponse>(object :
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
                        no_notification.visibility = View.VISIBLE
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbarpopup.visibility = View.GONE
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

    fun StateListApi() {
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
                        progressbarpopup.visibility = View.GONE
                        no_notification.visibility = View.VISIBLE

                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbarpopup.visibility = View.GONE
                        Toast.makeText(mContext, "$failureMessage", Toast.LENGTH_LONG).show()

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

    fun CityListApi() {
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
                        no_notification.visibility = View.VISIBLE

                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbarpopup.visibility = View.GONE
                        Toast.makeText(mContext, "$failureMessage", Toast.LENGTH_LONG).show()
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
                country1.text = name
                isoState = ""
                State_ET.text = ""
                city.text = ""
                dialog.dismiss()
            }
            "State" -> {
                isoState = iso
                State_ET.text = name
                State_ET_ll.setBackgroundResource(R.drawable.backgroundsearch)
                city.text = ""
                dialog.dismiss()
            }
            "City" -> {
                city.text = name
                dialog.dismiss()
            }
        }

    }

    fun passwordShow() {
        cross_eye.setSafeOnClickListener {
            when (passwordNotVisible) {
                0 -> {
                    NewPasswordEt.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    cross_eye.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.eye))
                    passwordNotVisible = 1


                }
                1 -> {
                    NewPasswordEt.transformationMethod = PasswordTransformationMethod.getInstance()
                    cross_eye.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_eye_slash_solid))
                    passwordNotVisible = 0
                }
                else -> {
                    NewPasswordEt.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    cross_eye.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.eye))
                    passwordNotVisible = 1
                }
            }
        }
        cross_eye2.setSafeOnClickListener {
            when (passwordNotVisible) {
                0 -> {
                    ConfirmPasswordET.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    cross_eye2.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.eye))
                    passwordNotVisible = 1


                }
                1 -> {
                    ConfirmPasswordET.transformationMethod = PasswordTransformationMethod.getInstance()
                    cross_eye2.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_eye_slash_solid))
                    passwordNotVisible = 0
                }
                else -> {
                    ConfirmPasswordET.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    cross_eye2.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.eye))
                    passwordNotVisible = 1
                }
            }
        }
    }


    private fun selectImage() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.choose_camera_bottom_sheet, null)
        dialog.setCancelable(true)

        val CameraButton = view.findViewById<ImageView>(R.id.choose_from_camera)
        CameraButton.setSafeOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(this.packageManager) != null) {
                try {
                    imageFile = createImageFile()!!
                } catch (ex: IOException) {
                }

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
                                val finalBitmap = ImageRotation.modifyOrientation(ImageRotation.getBitmap(path)!!, path)
                                profilePic = finalBitmap?.let { bitmapToString(it) }!!
                                Glide.with(this).load(imageFile).into(profile_image)
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
                    }catch (e:Exception) {
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

    fun getPathFromURI(contentUri: Uri?): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            this.contentResolver.query(contentUri!!, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            res = cursor.getString(column_index)
        }
        cursor.close()
        return res
    }

    fun signupCustomer() {
        if (androidextention.isOnline(this)) {
            Progresss.start(this)
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<SignupResponse> = ApiCallBack(this, "signupCustomer", mContext)

            if (lat == 0.0 && long == 0.0){
                val fullAddress = address.text.toString() + " " + zipcode.text.toString() + " " + city.text.toString()+ " " + State_ET.text.toString() + " " + country1.text.toString()
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


            val storeLocation = StoreLocation()
            val cor = ArrayList<Double>()
            cor.add(long)
            cor.add(lat)
            storeLocation.type = "Point"
            storeLocation.coordinates = cor
            val RegisterRequest = SignupRequest()
            RegisterRequest.userType = type
            RegisterRequest.firstName = firstNameET.text.toString()
            RegisterRequest.lastName = lastNameET.text.toString()
            RegisterRequest.email = EmailEt.text.toString()
            RegisterRequest.countryCode = ccpMyprofile.selectedCountryCode.toString()
            RegisterRequest.mobileNumber = phoneET.text.toString()
            RegisterRequest.password = NewPasswordEt.text.toString()
            var addressText = StringBuffer()
            if (address.text.isEmpty()) {
                addressText.append(address.text.toString())
            }
            if (address2.text.isEmpty()) {
                addressText.append(" ${address2.text}")
            }
            RegisterRequest.address = addressText.toString()
            RegisterRequest.addressLine1 = address.text.toString()
            RegisterRequest.addressLine2 = address2.text.toString()
            RegisterRequest.city = city.text.toString()
            RegisterRequest.zipCode = zipcode.text.toString()
            RegisterRequest.country = country1.text.toString()
            RegisterRequest.state = State_ET.text.toString()
            RegisterRequest.countryIsoCode = isocode
            RegisterRequest.stateIsoCode = isoState
            RegisterRequest.deviceType = "android"
            RegisterRequest.deviceToken = SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.KEY_DEVICE_TOKEN)
                    .toString()
            RegisterRequest.storeLocation = storeLocation
            if (USER_IMAGE_UPLOADED) {
                RegisterRequest.profilePic = "data:image/png;base64,${profilePic}"
            }else{
                RegisterRequest.profilePic = profilePic
            }

            participationIn =  if (automaticParticipation.isChecked){
                "AUTOMATIC"
            }else if (semiParticipation.isChecked){
                "SEMI_AUTOMATIC"
            }else{
                "MANUAL"
            }
            RegisterRequest.campainPrefrences =  participationIn




            try {
                serviceManager.SignupApi(callBack, RegisterRequest,isSocial)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            androidextention.alertBox("Please check your internet connection.", this)
        }
    }

    override fun onApiSuccess(response: SignupResponse, apiName: String?) {
        Progresss.stop()
        if (response.responseCode == 200) {
            try {
                if (!isSocial){
                    email = response.result?.email!!
                    val intent = Intent(this, OtpVerification::class.java)
                    intent.putExtra("email", email)
                    intent.putExtra("flag1", "customer_login")
                    intent.putExtra("usertype", "CUSTOMER")
                    startActivity(intent)
                }else{
//                    SavedPrefManager.saveStringPreferences(this, SavedPrefManager.SERVICE_ID_Address, response.result.primaryAddressId)
//                    SavedPrefManager.saveStringPreferences(this, SavedPrefManager.TOKEN, response.result.token)

                    SavedPrefManager.saveStringPreferences(this, SavedPrefManager.CUSTOMER_USER_ID, response.result?._id)
                    SavedPrefManager.saveStringPreferences(this, SavedPrefManager.USER_TYPE, response.result?.userType)
                    SavedPrefManager.saveStringPreferences(this, SavedPrefManager.isLogin, "true")

                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("flag", "customer")
                    startActivity(intent)
                }
                finish()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onApiErrorBody(response: String, apiName: String?) {
        Progresss.stop()
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
        Progresss.stop()
    }


    fun initializedControl() {

        firstNameET.addTextChangedListener(textWatcher)
        lastNameET.addTextChangedListener(textWatcher)
        phoneET.addTextChangedListener(textWatcher)
        EmailEt.addTextChangedListener(textWatcher)
        address.addTextChangedListener(textWatcher)
        country1.addTextChangedListener(textWatcher)
        State_ET.addTextChangedListener(textWatcher)
        city.addTextChangedListener(textWatcher)
        zipcode.addTextChangedListener(textWatcher)
        NewPasswordEt.addTextChangedListener(textWatcher)
        ConfirmPasswordET.addTextChangedListener(textWatcher)
    }


    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateForm()
        }
    }



    private fun locationpermission() {
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

