package com.exobe.activities.retailer

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
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.exobe.permission.RequestPermission
import com.example.validationpractice.utlis.FormValidation
import android.view.LayoutInflater
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.adaptor.CityListPopUp
import com.exobe.adaptor.CountryListPopUp
import com.exobe.adaptor.StateListPopUp
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.activities.terms_conditions
import com.exobe.utils.ImageRotation
import com.exobe.utils.LocationClass
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.bottomSheet.CustomerBottomSheet
import com.exobe.bottomSheet.choosePhotoBottomSheet
import com.exobe.customClicks.AddProductListener
import com.exobe.customClicks.UpdateIsLoginListener
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.SignupRequest
import com.exobe.entity.request.StoreLocation
import com.exobe.entity.response.SignupResponse
import com.exobe.customClicks.popupItemClickListnerCountry
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
import com.google.gson.GsonBuilder
import com.hbb20.CountryCodePicker
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MultipartBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class RegisterRetailerActivity : AppCompatActivity(), ApiResponseListener<SignupResponse>, popupItemClickListnerCountry, AddProductListener,
    UpdateIsLoginListener {
    lateinit var retailer_register: Button
    lateinit var back: ImageView
    lateinit var title: TextView
    lateinit var firstname_et: EditText
    lateinit var first_name: TextView
    lateinit var LastName_et: EditText
    lateinit var surname_tv: TextView
    lateinit var email_et: EditText
    lateinit var email_tv: TextView
    lateinit var mobile_editText: EditText
    lateinit var mobile_tv: TextView
    lateinit var password_et: EditText
    lateinit var password_tv: TextView
    lateinit var confirmpassword_et: EditText
    lateinit var confirmpassword_tv: TextView
    lateinit var country: LinearLayout
    lateinit var address: EditText
    lateinit var address_tv: TextView
    lateinit var city: TextView
    lateinit var city_tv: TextView
    lateinit var zipcode: EditText
    lateinit var zipcode_tv: TextView
    lateinit var country1: TextView
    lateinit var address2: EditText
    lateinit var country_tv: TextView
    lateinit var select_Image: ImageView
    lateinit var profile_image: CircleImageView
    lateinit var check_box: CheckBox
    lateinit var terms_and_conditions: TextView
    lateinit var mContext: Context
    lateinit var cross_eye: ImageView
    lateinit var cross_eye2: ImageView
    lateinit var ConfirmPaswordLL: LinearLayout
    lateinit var phonenumber: EditText
    lateinit var PaswordLL: LinearLayout
    lateinit var RetailerLogIN: LinearLayout
    private var passwordNotVisible = 0
    lateinit var State_ET: TextView
    lateinit var State_TV: TextView
    lateinit var no_notification: LinearLayout
    lateinit var addressLL: LinearLayout
    lateinit var ccp_register_retailer: CountryCodePicker

    lateinit var imageparts: MultipartBody.Part
    var requestImage: String = ""
    private val GALLERY = 1
    private var CAMERA: Int = 2
    lateinit var image: Uri
    lateinit var imageFile: File
    var type = ""
    var imageUploadFlag = false
    lateinit var progressbar: ProgressBar
    lateinit var progressbarpopup: ProgressBar
    var lat: Double = 0.0
    var long: Double = 0.0
    private val LOCATION_PERMISSION_REQ_CODE = 1000;

    private lateinit var dialog: Dialog
    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: CountryListPopUp
    lateinit var adapterState: StateListPopUp
    lateinit var adapterCity: CityListPopUp
    var data: java.util.ArrayList<CountryListResult> = java.util.ArrayList()
    var Statedatadata: java.util.ArrayList<StateList_Result> = java.util.ArrayList()
    var Citydatadata: java.util.ArrayList<CityListResult> = java.util.ArrayList()
    var flag: String = ""

    var isocode: String = ""
    var isoState: String = ""
    lateinit var pop_internet_connection: RelativeLayout
    var pop_lottie: LottieAnimationView? = null
    var PLACE_PICKER_REQUEST = 110

    var profilePic = ""
    private var base64: String? = null


    private var isSocial = false
    private var googleFirstName = ""
    private var googleLastName = ""
    private var googleEmail = ""
    private var googleProfilePicURL = ""
    lateinit var passwordHideVisible:LinearLayout
    lateinit var manualParticipation:RadioButton
    lateinit var semiParticipation:RadioButton
    lateinit var automaticParticipation:RadioButton
    lateinit var locationtrackingClick:ImageView
    var participationIn = "MANUAL"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_retailer)
        window.attributes.windowAnimations = R.style.Fade
        mContext = this.applicationContext
        progressbar = findViewById(R.id.progressbar)
        ConfirmPaswordLL = findViewById(R.id.ConfirmPaswordLL)
        PaswordLL = findViewById(R.id.PaswordLL)
        ccp_register_retailer = findViewById(R.id.ccp_register_retailer)

        retailer_register = findViewById(R.id.retailer_register)
        firstname_et = findViewById(R.id.firstname_et)
        first_name = findViewById(R.id.first_nametv)
        LastName_et = findViewById(R.id.LastName_et)
        surname_tv = findViewById(R.id.surname_tv)
        email_et = findViewById(R.id.email_et)
        email_tv = findViewById(R.id.email_tv)
        mobile_editText = findViewById(R.id.mobile_editText)
        phonenumber = findViewById(R.id.phonenumber)
        mobile_tv = findViewById(R.id.mobile_tv)
        password_et = findViewById(R.id.password_et)
        password_tv = findViewById(R.id.password_tv)
        confirmpassword_et = findViewById(R.id.confirmpassword_et)
        confirmpassword_tv = findViewById(R.id.confirmpassword_tv)

        State_ET = findViewById(R.id.State_ET)
        State_TV = findViewById(R.id.State_TV)
        country = findViewById(R.id.country)
        address = findViewById(R.id.address)
        address_tv = findViewById(R.id.address_tv)
        city = findViewById(R.id.city)
        city_tv = findViewById(R.id.city_tv)
        zipcode_tv = findViewById(R.id.zipcode_tv)
        zipcode = findViewById(R.id.zipcode)
        country1 = findViewById(R.id.country1)
        address2 = findViewById(R.id.address2)
        country_tv = findViewById(R.id.country_tv)
        country = findViewById(R.id.country)
        back = findViewById(R.id.back)
        title = findViewById(R.id.title)
        select_Image = findViewById(R.id.select_Image)
        profile_image = findViewById(R.id.profile_image)
        check_box = findViewById(R.id.CheckBox)
        cross_eye = findViewById(R.id.cross_eye)
        cross_eye2 = findViewById(R.id.cross_eye2)
        terms_and_conditions = findViewById(R.id.terms_and_conditions)
        RetailerLogIN = findViewById(R.id.RetailerLogIN)
        addressLL = findViewById(R.id.addressLL)
        passwordHideVisible = findViewById(R.id.passwordHideVisible)

        manualParticipation = findViewById(R.id.manualParticipation)
        semiParticipation = findViewById(R.id.semiParticipation)
        automaticParticipation = findViewById(R.id.automaticParticipation)
        locationtrackingClick = findViewById(R.id.locationtrackingClick)

        manualParticipation.isChecked = true

        intent?.getBooleanExtra("isSocial",false)?.let { isSocial = it }
        intent?.getStringExtra("googleFirstName")?.let { googleFirstName = it }
        intent?.getStringExtra("googleLastName")?.let { googleLastName = it }
        intent?.getStringExtra("googleEmail")?.let { googleEmail = it }
        intent?.getStringExtra("googleProfilePicURL")?.let { googleProfilePicURL = it }

        if (isSocial){

            Glide.with(this).load(googleProfilePicURL).into(profile_image)
            firstname_et.setText(googleFirstName)
            LastName_et.setText(googleLastName)
            email_et.setText(googleEmail)

            if (googleProfilePicURL.isNotEmpty()){
                profilePic =  googleProfilePicURL
                select_Image.isEnabled = false
            }
            if (googleFirstName.isNotEmpty()){
                firstname_et.isEnabled = false
            }
            if (googleLastName.isNotEmpty()){
                LastName_et.isEnabled = false
            }
            if (googleEmail.isNotEmpty()){
                email_et.isEnabled = false
            }


            passwordHideVisible.isVisible =  false
        }











        INITTILAZIGOOGLEPLACE()
        initializedControl()
        locationpermission()
        type = "RETAILER"










        title.text = "Register"
        retailer_register.setSafeOnClickListener {
            validateForm()
            if (isSocial){
                if (firstname_et.text.isNotEmpty()
                    && LastName_et.text.isNotEmpty()
                    && email_et.text.isNotEmpty()
                    && mobile_editText.text.isNotEmpty()
                    && address.text.isNotEmpty()
                    && country1.text.isNotEmpty()
                    && zipcode.text.isNotEmpty()
                    && zipcode.text.toString().length > 3
                ) {
                    if (!check_box.isChecked) {
                        androidextention.alertBox("Please accept Terms of Conditions.", this)
                    } else {
                        signupRetailer()

                    }
                }
            }else{
                if (firstname_et.text.isNotEmpty()
                    && LastName_et.text.isNotEmpty()
                    && email_et.text.isNotEmpty()
                    && mobile_editText.text.isNotEmpty()
                    && password_et.text.isNotEmpty()
                    && confirmpassword_et.text.isNotEmpty()
                    && confirmpassword_et.text.toString() == password_et.text.toString()
                    && address.text.isNotEmpty()
                    && country1.text.isNotEmpty()
                    && zipcode.text.isNotEmpty()
                    && zipcode.text.toString().length > 3
                ) {
                    if (!check_box.isChecked) {
                        androidextention.alertBox("Please accept Terms of Conditions.", this)
                    } else {
                        signupRetailer()

                    }
                }
            }




        }


        passwordShow()
        terms_and_conditions.setSafeOnClickListener {
            val intent = Intent(this, terms_conditions::class.java)
            startActivity(intent)
        }

        locationtrackingClick.setSafeOnClickListener {
            val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
            // Start the autocomplete intent.
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(applicationContext)
            startActivityForResult(
                intent,
                PLACE_PICKER_REQUEST
            )
        }

        RetailerLogIN.setSafeOnClickListener {
            finish()
        }

        back.setSafeOnClickListener {
            finish()
        }

        select_Image.setSafeOnClickListener {
            RequestPermission.requestMultiplePermissions(this)
            getImages()
        }

        country1.setSafeOnClickListener {
            flag = "Country"
            openPopUp()
            country_tv.text = ""
            country1.setBackgroundResource(R.drawable.backgroundsearch)
        }
        State_ET.setSafeOnClickListener {
            if (country1.text.isEmpty()) {
                country_tv.text = "*Please select country first."
                country1.setBackgroundResource(R.drawable.input_error)
            } else {
                country_tv.text = ""
                State_TV.text = ""
                flag = "State"
                country1.setBackgroundResource(R.drawable.backgroundsearch)
                State_ET.setBackgroundResource(R.drawable.backgroundsearch)
                openPopUp()
            }
        }
        city.setSafeOnClickListener {
            if (State_ET.text.isEmpty()) {
                State_TV.text = "*Please select state first."
                country_tv.text = ""
                State_ET.setBackgroundResource(R.drawable.input_error)
                country1.setBackgroundResource(R.drawable.backgroundsearch)
            } else {
                city_tv.text = ""
                State_TV.text = ""
                flag = "City"
                openPopUp()
                State_ET.setBackgroundResource(R.drawable.backgroundsearch)
                country1.setBackgroundResource(R.drawable.backgroundsearch)
            }
        }
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

                                address.text = Editable.Factory.getInstance().newEditable(addressLine1.joinToString(","))
                                country1.text = country
                                State_ET.text = state
                                city.text = cityName
                                zipcode.setText(zipcodeText)
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
        FormValidation.RetailerRegistrationValidation(
            firstname_et,
            first_name,
            LastName_et,
            surname_tv,
            email_et,
            email_tv,
            mobile_editText,
            mobile_tv, country,
            address,
            address_tv,
            country1,
            country_tv,
            zipcode,
            zipcode_tv,
            password_et,
            password_tv, PaswordLL,
            confirmpassword_et,
            confirmpassword_tv,
            ConfirmPaswordLL,
            addressLL,
            isSocial
        )
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

    @SuppressLint("InflateParams", "SetTextI18n")
    fun openPopUp() {

        try {
            val binding = LayoutInflater.from(this).inflate(R.layout.pop_lists, null)
            dialog = DialogUtils().createDialog(this, binding.rootView, 0)!!
            recyclerView = binding.findViewById(R.id.popup_recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this)
            progressbarpopup = binding.findViewById(R.id.progressbar_pop)
            no_notification = binding.findViewById(R.id.no_notification)
            pop_lottie = binding.findViewById(R.id.pop_lottie)
            pop_internet_connection = binding.findViewById(R.id.pop_internet_connection)
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

            var dialougTitle = binding.findViewById<TextView>(R.id.popupTitle)
            var dialougbackButton = binding.findViewById<ImageView>(R.id.BackButton)
            dialougbackButton.setSafeOnClickListener { dialog.dismiss() }




            if (flag == "Country") {
                dialougTitle.text = flag
                CountryListApi()

            } else if (flag == "State") {

                dialougTitle.text = flag
                StateListApi()
            } else if (flag == "City") {
                dialougTitle.text = flag
                CityListApi()
            }
            dialog.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
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
            } else if (flag == "City") {
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
                            "$failureMessage",
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
        if (flag.equals("Country")) {
            isocode = iso
            country1.text = name
            isoState = ""
            State_ET.text = ""
            dialog.dismiss()
        } else if (flag.equals("State")) {
            isoState = iso
            State_ET.text = name
            city.text = ""
            dialog.dismiss()
        } else if (flag.equals("City")) {
            city.text = name
            dialog.dismiss()
        }

    }


    fun passwordShow() {
        cross_eye.setSafeOnClickListener {
            if (passwordNotVisible == 0) {
                password_et.transformationMethod = HideReturnsTransformationMethod.getInstance()
                cross_eye.setImageDrawable(resources.getDrawable(R.drawable.eye))
                password_et.setSelection(password_et.length())
                passwordNotVisible = 1


            } else if (passwordNotVisible == 1) {
                password_et.transformationMethod = PasswordTransformationMethod.getInstance()
                cross_eye.setImageDrawable(resources.getDrawable(R.drawable.ic_eye_slash_solid))
                password_et.setSelection(password_et.length())
                passwordNotVisible = 0
            } else {
                password_et.transformationMethod = HideReturnsTransformationMethod.getInstance()
                cross_eye.setImageDrawable(resources.getDrawable(R.drawable.eye))
                password_et.setSelection(password_et.length())
                passwordNotVisible = 1
            }
        }
        cross_eye2.setSafeOnClickListener {
            if (passwordNotVisible == 0) {
                confirmpassword_et.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                cross_eye2.setImageDrawable(resources.getDrawable(R.drawable.eye))
                confirmpassword_et.setSelection(confirmpassword_et.length())
                passwordNotVisible = 1


            } else if (passwordNotVisible == 1) {
                confirmpassword_et.transformationMethod = PasswordTransformationMethod.getInstance()
                cross_eye2.setImageDrawable(resources.getDrawable(R.drawable.ic_eye_slash_solid))
                confirmpassword_et.setSelection(confirmpassword_et.length())
                passwordNotVisible = 0
            } else {
                confirmpassword_et.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                cross_eye2.setImageDrawable(resources.getDrawable(R.drawable.eye))
                confirmpassword_et.setSelection(confirmpassword_et.length())
                passwordNotVisible = 1
            }
        }
    }


    private fun getImages() {
        var bottomsheet = choosePhotoBottomSheet("AddProduct", this)
        bottomsheet.show(supportFragmentManager, "bottomsheet")
    }

    fun signupRetailer() {
        if (androidextention.isOnline(this)) {
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<SignupResponse> = ApiCallBack(this, "signupRetailer", mContext)

            if (lat == 0.0 && long == 0.0){
                val fullAddress = address.text.toString() + " " + zipcode.text.toString() + " "  + city.text.toString()+ " " + State_ET.text.toString() + " " + country1.text.toString()
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


            var cor = ArrayList<Double>()
            cor.add(long)
            cor.add(lat)
            var storeLocation = StoreLocation()
            storeLocation.type = "Point"
            storeLocation.coordinates = cor

            val RegisterRequest = SignupRequest()
            RegisterRequest.userType = type
            RegisterRequest.firstName = firstname_et.text.toString()
            RegisterRequest.lastName = LastName_et.text.toString()
            RegisterRequest.email = email_et.text.toString()
            RegisterRequest.countryCode = ccp_register_retailer.selectedCountryCode.toString()
            RegisterRequest.mobileNumber = mobile_editText.text.toString()
            RegisterRequest.phoneNumber = phonenumber.text.toString()
            RegisterRequest.password = password_et.text.toString()
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

            if (imageUploadFlag) {
                RegisterRequest.profilePic = "data:image/png;base64,${profilePic}"
            }else{
                RegisterRequest.profilePic = profilePic
            }


            RegisterRequest.govtDocument?.frontImage = ""
            RegisterRequest.storeLocation = storeLocation

            participationIn =  if (automaticParticipation.isChecked){
                "AUTOMATIC"
            }else if (semiParticipation.isChecked){
                "SEMI_AUTOMATIC"
            }else{
                "MANUAL"
            }
            RegisterRequest.campainPrefrences =  participationIn

            try {
                serviceManager.SignupApi(callBack, RegisterRequest, isSocial)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            androidextention.alertBox("Please check your internet connection.", this)
        }
    }

    override fun onApiSuccess(response: SignupResponse, apiName: String?) {
        progressbar.visibility = View.GONE
        if (response.responseCode == 200) {
            try {
                finish()
                Toast.makeText(this, "Registration stage 1 completed, login and provide business details and documentation.", Toast.LENGTH_SHORT).show()
                supportFragmentManager.let { it1 ->
                    CustomerBottomSheet("Retailer", mContext,this).show(
                        it1,
                        "ModalBottomSheet"
                    )
                }
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
            e.printStackTrace()
        }
    }
    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        progressbar.visibility = View.GONE

    }

    override fun addProduct(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        choosePhotoBottomSheet: choosePhotoBottomSheet,
        imagePath: String
    ) {
        try {
            if (resultCode == Activity.RESULT_CANCELED) {
                choosePhotoBottomSheet.dismiss()
            } else {

                if (requestCode == GALLERY) {
                    if (data != null && data.clipData == null) {
                        try {
                            image = data.data!!

                            val path = getPathFromURI(image)
                            if (path != null) {
                                imageFile = File(path)
                                val maxFileSizeInBytes = 5 * 1024 * 1024
                                if (imageFile.length() <= maxFileSizeInBytes) {
                                    choosePhotoBottomSheet.dismiss()

                                    val finalBitmap = ImageRotation.modifyOrientation(ImageRotation.getBitmap(path)!!, path)
                                    profilePic = finalBitmap?.let { bitmapToString(it) }!!

                                    Glide.with(this).load(imageFile).into(profile_image)
                                    imageUploadFlag = true
                                } else {
                                    androidextention.alertBoxCommon(message = "Please select a image that is 5 MB or smaller.", context = this)
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                } else if (requestCode == CAMERA) {

                    imageFile = File(imagePath)


                    Glide.with(this).load(imageFile).into(profile_image)
                    val finalBitmap = ImageRotation.modifyOrientation(ImageRotation.getBitmap(imagePath)!!, imagePath)
                    profilePic = finalBitmap?.let { bitmapToString(it) }!!

                    imageUploadFlag = true
                }
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

    fun initializedControl() {

        firstname_et.addTextChangedListener(textWatcher)
        LastName_et.addTextChangedListener(textWatcher)
        email_et.addTextChangedListener(textWatcher)
        mobile_editText.addTextChangedListener(textWatcher)
        address.addTextChangedListener(textWatcher)
        country1.addTextChangedListener(textWatcher)
        State_ET.addTextChangedListener(textWatcher)
        city.addTextChangedListener(textWatcher)
        zipcode.addTextChangedListener(textWatcher)
        password_et.addTextChangedListener(textWatcher)
        confirmpassword_et.addTextChangedListener(textWatcher)


    }


    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (!s.toString().equals("")) {
                validateForm()
            }

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

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

    override fun isLoginListener() {
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