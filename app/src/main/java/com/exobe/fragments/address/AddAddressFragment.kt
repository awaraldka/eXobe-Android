package com.exobe.fragments.address

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.callisdairy.extension.setSafeOnClickListener
import com.example.validationpractice.utlis.FormValidation
import com.exobe.adaptor.CityListPopUp
import com.exobe.adaptor.CountryListPopUp
import com.exobe.adaptor.StateListPopUp
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.LocationClass.getAddressCoordinates
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.popupItemClickListnerCountry
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.AddAddressLocation
import com.exobe.entity.request.AddAddressRequest
import com.exobe.entity.response.*
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceConstant
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.validations.DialogUtils
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.hbb20.CountryCodePicker
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class AddAddressFragment(var editflag: String, var id: String, var screenFlag: String) : Fragment(),
    popupItemClickListnerCountry {

    lateinit var backButton: ImageView
    lateinit var save_button: Button
    lateinit var update_button: Button
    lateinit var cancel_button: Button
    lateinit var mContext: Context
    lateinit var et_firstname: EditText
    lateinit var et_lastname: EditText
    lateinit var et_address: TextView
    lateinit var et_address2: EditText
    lateinit var country1: TextView
    lateinit var EmailEt: EditText
    lateinit var CityEt: TextView
    lateinit var et_contactnumber: EditText
    lateinit var ZipCodeET: EditText
    lateinit var ccp_MyProfile: CountryCodePicker
    val Name = "^[A-Za-z]+$"
    lateinit var tv_firstname: TextView
    lateinit var tv_lastname: TextView
    lateinit var tv_contactnumber: TextView
    lateinit var tv_gst: TextView
    lateinit var tv_postcode: TextView
    lateinit var tv_address: TextView
    lateinit var tv_transport: TextView
    lateinit var Address1: TextView
    lateinit var CityTV: TextView
    lateinit var Country: TextView
    lateinit var firstname: String
    lateinit var lastname: String
    lateinit var contactnumber: String
    lateinit var country: LinearLayout

    lateinit var address: String
    lateinit var transport: String

    lateinit var title: TextView
    lateinit var emailTV: TextView
    lateinit var mainHeader: RelativeLayout
    lateinit var cart: ImageView
    lateinit var cartCount: TextView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var locationtrackingClick: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var progressbar: ProgressBar
    lateinit var progressbar_csc: ProgressBar
    lateinit var State_ET: TextView
    lateinit var State_TV: TextView
    lateinit var ZipCodeTV: TextView
    lateinit var nodata: LinearLayout
    lateinit var etAddressLL: LinearLayout


    private lateinit var dialog: Dialog
    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: CountryListPopUp
    lateinit var adapterState: StateListPopUp
    lateinit var adapterCity: CityListPopUp
    var data: ArrayList<CountryListResult> = ArrayList()
    var Statedatadata: ArrayList<StateList_Result> = ArrayList()
    var Citydatadata: ArrayList<CityListResult> = ArrayList()
    var flag: String = ""

    var isocode: String = ""
    var isoState: String = ""
    lateinit var internet_connection: RelativeLayout
    lateinit var pop_internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    var pop_lottie: LottieAnimationView? = null
    var PLACE_PICKER_REQUEST = 110
    var cor = ArrayList<Double>()
    var lat: Double = 0.0
    var long: Double = 0.0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.activity_add_address, container, false)
        setToolbar()

        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        ZipCodeTV = view.findViewById(R.id.ZipCodeTV)
        backButton = view.findViewById(R.id.backButton)
        save_button = view.findViewById(R.id.save_button)
        update_button = view.findViewById(R.id.update_button)
        cancel_button = view.findViewById(R.id.cancel_button)
        tv_firstname = view.findViewById(R.id.tv_firstname)
        et_firstname = view.findViewById(R.id.et_firstname)
        et_lastname = view.findViewById(R.id.et_lastname)
        progressbar = view.findViewById(R.id.add_addressprogressbar)
        ccp_MyProfile = view.findViewById(R.id.ccp_MyProfile)
        mContext = activity?.applicationContext!!
        State_ET = view.findViewById(R.id.State_ET)
        State_TV = view.findViewById(R.id.State_TV)
        emailTV = view.findViewById(R.id.emailTV)
        et_contactnumber = view.findViewById(R.id.et_contactnumber)
        et_address = view.findViewById(R.id.et_address)
        et_address2 = view.findViewById(R.id.et_address2)
        country1 = view.findViewById(R.id.country1)
        tv_lastname = view.findViewById(R.id.tv_lastname)
        tv_contactnumber = view.findViewById(R.id.tv_contactnumber)
        tv_gst = view.findViewById(R.id.tv_gst)
        ZipCodeET = view.findViewById(R.id.ZipCodeET)
        tv_postcode = view.findViewById(R.id.tv_postcode)
        CityEt = view.findViewById(R.id.CityEt)
        Address1 = view.findViewById(R.id.Address1)
        CityTV = view.findViewById(R.id.CityTV)
        Country = view.findViewById(R.id.Country)
        EmailEt = view.findViewById(R.id.EmailEt)
        country = view.findViewById(R.id.country)
        etAddressLL = view.findViewById(R.id.etAddressLL)
        locationtrackingClick = view.findViewById(R.id.locationTrackingClick)
        internet_connection!!.visibility = View.GONE

        title = activity?.findViewById(R.id.PreLoginTitle_TextView2)!!



        firstname = et_firstname.text.toString()
        lastname = et_lastname.text.toString()
        address = et_address.text.toString()
        initializedControl()
        INITTILAZIGOOGLEPLACE()
        if (editflag == "Edit") {
            viewAddressAPI()
        }else{
            val email = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.CUSTOMER_GMAIL_ADDRESS).toString()
            val name = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.USER_NAME).toString()
            val mobile = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.MOBILE).toString()
            et_firstname.setText(name.split(" ")[0])
            et_lastname.setText(name.split(" ")[1])
            et_contactnumber.setText(mobile)
            EmailEt.setText(email)

            etAddressLL.setBackgroundResource(R.drawable.backgroundsearch)
            Address1.isVisible = false

        }

        update_button.setSafeOnClickListener {
            validateForm()
            if (et_firstname.text.isNotEmpty() &&
                et_lastname.text.isNotEmpty() &&
                et_contactnumber.text.isNotEmpty() &&
                EmailEt.text.isNotEmpty() &&
                et_address.text.isNotEmpty() &&
                !ZipCodeET.text.isEmpty() &&
                ZipCodeET.text.toString().length > 3 &&
                country1.text.isNotEmpty()
            ) {
                EditAddressAPI()
            }


        }
        cancel_button.setSafeOnClickListener {
            ChooseDeliveryAddress.apiDeliveryAddressCallFlag = false
            parentFragmentManager.popBackStack()
        }

        locationtrackingClick.setSafeOnClickListener {
            val fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(requireActivity())
            startActivityForResult(intent, PLACE_PICKER_REQUEST)
        }
        
        
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        mainHeader.visibility = View.VISIBLE


        back.setSafeOnClickListener {
            parentFragmentManager.popBackStack()
        }



        save_button.setSafeOnClickListener {
            validateForm()
            if (et_firstname.text.isNotEmpty() &&
                et_lastname.text.isNotEmpty() &&
                et_contactnumber.text.isNotEmpty()
                && EmailEt.text.isNotEmpty() &&
                et_address.text.isNotEmpty() &&
                ZipCodeET.text.isNotEmpty() &&
                ZipCodeET.text.toString().length > 3 &&
                CityEt.text.isNotEmpty() &&
                country1.text.isNotEmpty() &&
                State_ET.text.isNotEmpty()
            ) {
                AddAddressAPI()
            }

        }

        if (editflag == "Edit") {
            title.text = "Update Address"
            update_button.visibility = View.VISIBLE
            save_button.visibility = View.GONE
            viewAddressAPI()

        } else {
            title.text = "Add Address"
            update_button.visibility = View.GONE
            save_button.visibility = View.VISIBLE

            save_button.setSafeOnClickListener {



                validateForm()
                if (et_firstname.text.isNotEmpty() &&
                    et_lastname.text.isNotEmpty()
                    && et_contactnumber.text.isNotEmpty()
                    && EmailEt.text.isNotEmpty() &&
                    et_address.text.isNotEmpty() &&
                    !ZipCodeET.text.isEmpty() &&
                    ZipCodeET.text.toString().length > 3 &&
                    CityEt.text.isNotEmpty() &&
                    country1.text.isNotEmpty()
                ) {

                    val coordinates = getAddressCoordinates(requireContext(), et_address.text.toString())

                    if (coordinates != null) {
                        lat = coordinates.first
                        long = coordinates.second


                    } else {
                        androidextention.alertBox("Invalid address or error occurred",requireContext())
                        return@setSafeOnClickListener
                    }




                    AddAddressAPI()
                }


            }
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




        return view
    }

    private fun INITTILAZIGOOGLEPLACE() {
        Places.initialize(mContext, ServiceConstant.API_KEY)
        val placesClient = Places.createClient(mContext)
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
                            Log.d("tag", "response.toString() => ${response.toString()}")
                            val addressLine1 = ArrayList<String>()
                            var addressLine2 = ""
                            var countryName = ""
                            var state = ""
                            var cityName = ""
                            var zipcodeText = ""

                            val objectData = JSONObject(response.toString())
                            val jsonArrayData: JSONArray = objectData.getJSONArray("results")
                            val jsonObject = jsonArrayData[0] as JSONObject

                            val jsonObjectWorkOrderInfo =
                                JSONObject(jsonObject.getString("geometry"))
                            val locationdata =
                                JSONObject(jsonObjectWorkOrderInfo.getString("location"))
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
                                            countryName = jsonObject.getString("long_name")
                                            isocode = jsonObject.getString("short_name")
                                        }
                                        if (types[j] == "administrative_area_level_1") {
                                            state = jsonObject.getString("long_name")
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

                                et_address.text = addressLine1.joinToString(",")
                                country1.text = countryName
                                State_ET.text = state
                                CityEt.text = cityName
                                ZipCodeET.setText(zipcodeText)
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
        FormValidation.addaddress(
            et_firstname,
            tv_firstname,
            et_lastname,
            tv_lastname,
            et_contactnumber,
            tv_contactnumber,
            country,
            EmailEt,
            emailTV,
            et_address,
            Address1,
            country1,
            Country,
//                State_ET,
//                State_TV,
//                CityEt,
//                CityTV,
            ZipCodeET,
            ZipCodeTV,
            etAddressLL
        )
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    fun openPopUp() {

        try {
            val binding = LayoutInflater.from(requireContext()).inflate(R.layout.pop_lists, null)
            dialog = DialogUtils().createDialog(requireContext(), binding.rootView, 0)!!
            recyclerView = binding.findViewById(R.id.popup_recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            progressbar_csc = binding.findViewById(R.id.progressbar_pop)
            nodata = binding.findViewById(R.id.no_notification)
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
            if (flag.equals("Country")) {
                for (item in data) {
                    try {
                        if (item.name.lowercase().contains(searchText.lowercase())) {
                            filteredList.add(item)
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }

            } else if (flag.equals("State")) {
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
        adapter = this.let { CountryListPopUp(mContext, data, flag, this) }
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
                isoState = ""
                State_ET.text = ""
                CityEt.text = ""
                dialog.dismiss()
                dialog.dismiss()
            }
            "State" -> {
                isoState = iso
                State_ET.text = name
                CityEt.text = ""
                dialog.dismiss()
            }
            "City" -> {
                CityEt.text = name
                dialog.dismiss()
            }
        }

    }


    fun setToolbar() {

        cart = activity?.findViewById(R.id.cart_icon)!!
        filter = activity?.findViewById(R.id.filter_icon)!!
        back = activity?.findViewById(R.id.imageView_back)!!
        MenuClick = activity?.findViewById(R.id.MenuClick)!!
        DealsImageView = activity?.findViewById(R.id.Deals_ImageView)!!
        greyBellImageView = activity?.findViewById(R.id.greyBell_ImageView)!!
        logoutButton = activity?.findViewById(R.id.logoutButton)!!
        cartCount = activity?.findViewById(R.id.cartCount)!!

        cartCount.visibility = View.GONE
        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
        cartCount.visibility = View.GONE
        cart.visibility = View.GONE
        filter.visibility = View.GONE
        back.visibility = View.VISIBLE

        MenuClick.visibility = View.GONE

        DealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        logoutButton.visibility = View.GONE
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }


    fun AddAddressAPI() {
        if (androidextention.isOnline(mContext)) {
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<AddAddressResponse> =
                ApiCallBack<AddAddressResponse>(object :
                    ApiResponseListener<AddAddressResponse> {
                    override fun onApiSuccess(response: AddAddressResponse, apiName: String?) {
                        progressbar.visibility = View.GONE

                        if (response.responseCode == 200) {
                            try {
                                ChooseDeliveryAddress.apiDeliveryAddressCallFlag = true;
                                parentFragmentManager.popBackStack()
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
                            androidextention.alertBox(pojo.responseMessage, requireActivity())

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE
                    }

                }, "AddAddressAPI", mContext)

//            val jsonObject = JsonObject()


            val name1 = et_firstname.text.toString()
            val name2 = et_lastname.text.toString()
            val numberAdd = et_contactnumber.text.toString()
            val emailId = EmailEt.text.toString()
            var addressText = StringBuffer()
            if (et_address.text.isEmpty()) {
                addressText.append(et_address.text.toString())
            }
            if (et_address2.text.isEmpty()) {
                addressText.append(" ${et_address2.text.toString()}")
            }

            val town = CityEt.text.toString()
            val zip = ZipCodeET.text.toString()
            val countryName = country1.text.toString()
            val state = State_ET.text.toString()
            val ccp = ccp_MyProfile.selectedCountryCode.toString()

            cor.clear()
            cor.add(long)
            cor.add(lat)
            var addAddressLocation = AddAddressLocation()
            addAddressLocation.type = "Point"
            addAddressLocation.coordinates = cor


            var addAddressRequest = AddAddressRequest()
            addAddressRequest.firstName = name1
            addAddressRequest.lastName = name2
            addAddressRequest.mobileNumber = numberAdd
            addAddressRequest.email = emailId
            addAddressRequest.countryCode = ccp
            addAddressRequest.address = addressText.toString()
            addAddressRequest.addressLine1 = et_address.text.toString()
            addAddressRequest.addressLine2 = et_address2.text.toString()
            addAddressRequest.zipCode = zip
            addAddressRequest.city = town
            addAddressRequest.country = countryName
            addAddressRequest.state = state
            addAddressRequest.countryIsoCode = isocode
            addAddressRequest.stateIsoCode = isoState
            addAddressRequest.location = addAddressLocation


            try {
                serviceManager.AddAddressapi(callBack, addAddressRequest)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())


        }

    }

    fun viewAddressAPI() {

        if (androidextention.isOnline(mContext)) {
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<ViewAddressResponse> =
                ApiCallBack<ViewAddressResponse>(object :
                    ApiResponseListener<ViewAddressResponse> {
                    override fun onApiSuccess(response: ViewAddressResponse, apiName: String?) {
                        progressbar.visibility = View.GONE

                        if (response.responseCode == 200) {
                            try {
                                et_firstname.setText(response.result.firstName)
                                et_lastname.setText(response.result.lastName)
                                if (response.result.countryCode != "") {
                                    var countryCode = Integer.parseInt(response.result.countryCode)
                                    ccp_MyProfile.setCountryForPhoneCode(countryCode)
                                }
                                isocode = response.result.countryCode
                                et_contactnumber.setText(response.result.mobileNumber)
                                EmailEt.setText(response.result.email)
                                et_address.text = response.result.addressLine1
                                et_address2.setText(response.result.addressLine2)
                                CityEt.text = response.result.city
                                ZipCodeET.setText(response.result.zipCode)
                                country1.text = response.result.country
                                State_ET.text = response.result.state
                                isocode = response.result.countryIsoCode
                                isoState = response.result.stateIsoCode
                                cor.clear()
                                cor.add(response.result.location.coordinates[0])
                                cor.add(response.result.location.coordinates[1])
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
                            androidextention.alertBox(pojo.responseMessage, requireActivity())

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE
                    }

                }, "ViewAddressAPI", mContext)


            try {
                serviceManager.viewAddress(callBack, id)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())

        }

    }

    fun EditAddressAPI() {

        if (androidextention.isOnline(mContext)) {
            progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<EditAddressResponse> =
                ApiCallBack<EditAddressResponse>(object :
                    ApiResponseListener<EditAddressResponse> {
                    override fun onApiSuccess(response: EditAddressResponse, apiName: String?) {
                        progressbar.visibility = View.GONE

                        if (response.responseCode == 200) {
                            try {
                                ChooseDeliveryAddress.apiDeliveryAddressCallFlag =
                                    screenFlag != "service_request"


                                parentFragmentManager.popBackStack()
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
                            androidextention.alertBox(pojo.responseMessage, requireContext())

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE
                    }

                }, "EditAddressAPI", mContext)

            val jsonObject = JsonObject()


            val name1 = et_firstname.text.toString()
            val name2 = et_lastname.text.toString()
            val numberAdd = et_contactnumber.text.toString()
            val emailId = EmailEt.text.toString()
            val addressText = StringBuffer()
            if (et_address.text.isEmpty()) {
                addressText.append(et_address.text.toString())
            }
            if (et_address2.text.isEmpty()) {
                addressText.append(" ${et_address2.text.toString()}")
            }

            val town = CityEt.text.toString()
            val zip = ZipCodeET.text.toString()
            val countryName = country1.text.toString()
            val state = State_ET.text.toString()

            val ccp = ccp_MyProfile.selectedCountryCode.toString()


            cor.clear()
            cor.add(long)
            cor.add(lat)
            val addAddressLocation = AddAddressLocation()
            addAddressLocation.type = "Point"
            addAddressLocation.coordinates = cor


            val addAddressRequest = AddAddressRequest()
            addAddressRequest.addressId = id
            addAddressRequest.firstName = name1
            addAddressRequest.lastName = name2
            addAddressRequest.mobileNumber = numberAdd
            addAddressRequest.email = emailId
            addAddressRequest.countryCode = ccp
            addAddressRequest.address = addressText.toString()
            addAddressRequest.addressLine1 = et_address.text.toString()
            addAddressRequest.addressLine2 = et_address2.text.toString()
            addAddressRequest.zipCode = zip
            addAddressRequest.city = town
            addAddressRequest.country = countryName
            addAddressRequest.state = state
            addAddressRequest.countryIsoCode = isocode
            addAddressRequest.stateIsoCode = isoState
            addAddressRequest.location = addAddressLocation
            try {
                serviceManager.addressEditApi(callBack, addAddressRequest)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            androidextention.alertBox("Please check your internet connection.", requireContext())


        }

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

    fun initializedControl() {
        et_firstname.addTextChangedListener(textWatcher)
        et_lastname.addTextChangedListener(textWatcher)
        et_contactnumber.addTextChangedListener(textWatcher)
        EmailEt.addTextChangedListener(textWatcher)
        et_address.addTextChangedListener(textWatcher)
        country1.addTextChangedListener(textWatcher)
        State_ET.addTextChangedListener(textWatcher)
        CityEt.addTextChangedListener(textWatcher)

    }


}