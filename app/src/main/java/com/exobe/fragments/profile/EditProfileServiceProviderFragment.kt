package com.exobe.fragments.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
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
import com.exobe.utils.ImageRotation.getBitmap
import com.exobe.utils.ImageRotation.getImageUri
import com.exobe.utils.ImageRotation.getRealPathFromURI
import com.exobe.utils.ImageRotation.modifyOrientation
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.popupItemClickListnerCountry
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.EditProfileRequest
import com.exobe.entity.request.EditProfileStoreLocation
import com.exobe.entity.response.*
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceConstant
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.extension.diasplay_toast
import com.exobe.utils.LocationClass
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class EditProfileServiceProviderFragment : Fragment(), ApiResponseListener<EditProfile_Response>,
    popupItemClickListnerCountry {

    var datePicker: DatePickerDialog? = null
    lateinit var mContext: Context
    var yearset = 0
    var monthset = 0
    var day = 0
    val c = Calendar.getInstance()

    lateinit var ccp: CountryCodePicker
    lateinit var First_name_ET: EditText
    lateinit var Last_name_ET: EditText
    lateinit var Email_ET: EditText
    lateinit var Mobile_ET: EditText
    lateinit var Address1_ET: EditText
    lateinit var Address2_ET: EditText
    lateinit var Bankname_ET: EditText
    lateinit var A_C_number_ET: EditText
    lateinit var IFSC_ET: EditText
    lateinit var Branchname_ET: EditText

    lateinit var progressbarEditProfile: ProgressBar
    lateinit var updatebutton: Button

    var type = ""
    lateinit var FirstName_TV: TextView
    lateinit var Last_name_TV: TextView
    lateinit var Email_TV: TextView
    lateinit var Mobile_TV: TextView
    lateinit var Address1_TV: TextView
    lateinit var back: ImageView


    lateinit var Bankname_TV: TextView
    lateinit var A_C_number_TV: TextView
    lateinit var IFSC_TV: TextView
    lateinit var Branchname_TV: TextView
    lateinit var profile_image: CircleImageView
    val CAMERA_PERM_CODE = 101
    var imageFile: File? = null
    var imagePath = ""
    var photoURI: Uri? = null
    lateinit var imageparts: MultipartBody.Part
    private val GALLERY = 1
    private var CAMERA: Int = 2
    lateinit var image: Uri
    var requestImage: String = ""

    var USER_IMAGE_UPLOADED = false
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null

    lateinit var country1: TextView
    lateinit var State_ET: TextView
    lateinit var State_TV: TextView
    lateinit var Country: TextView
    lateinit var CityEt: TextView
    lateinit var CityTV: TextView
    lateinit var postalcode: EditText
    lateinit var errorTvEditPostCode: TextView
    lateinit var MobileEtLL: LinearLayout
    lateinit var Address1EtLL: LinearLayout

    var flag: String = ""

    var isocode: String = ""
    var isoState: String = ""
    private lateinit var dialog: Dialog
    private lateinit var recyclerView: RecyclerView
    lateinit var progressbar_csc: ProgressBar
    lateinit var nodata: LinearLayout

    lateinit var adapter: CountryListPopUp
    lateinit var adapterState: StateListPopUp
    lateinit var adapterCity: CityListPopUp
    var data: ArrayList<CountryListResult> = ArrayList()
    var Statedatadata: ArrayList<StateList_Result> = ArrayList()
    var Citydatadata: ArrayList<CityListResult> = ArrayList()
    lateinit var pop_internet_connection: RelativeLayout
    var pop_lottie: LottieAnimationView? = null
    var PLACE_PICKER_REQUEST = 110
    var lat: Double = 0.0
    var long: Double = 0.0
    var cor = ArrayList<Double>()
    lateinit var locationTrackingClick:ImageView

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_serviceprovider, container, false)
        mContext = activity?.applicationContext!!
        back = activity?.findViewById(R.id.back)!!

        val calendar: Calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val date = calendar.get(Calendar.DAY_OF_MONTH)
        datePicker = DatePickerDialog(mContext)

        locationTrackingClick = view.findViewById(R.id.locationTrackingClick)
        errorTvEditPostCode = view.findViewById(R.id.errorTvEditPostCode)
        postalcode = view.findViewById(R.id.postalcode)
        country1 = view.findViewById(R.id.ep_country1)
        Country = view.findViewById(R.id.Country)
        State_ET = view.findViewById(R.id.State_ET)
        State_TV = view.findViewById(R.id.State_TV)
        CityEt = view.findViewById(R.id.CityEt)
        CityTV = view.findViewById(R.id.CityTV)
        ccp = view.findViewById(R.id.ccp)
        Address2_ET = view.findViewById(R.id.Address2_ET)
        progressbarEditProfile = view.findViewById(R.id.progressbarEditProfile)
        First_name_ET = view.findViewById(R.id.First_name_ET)
        Last_name_ET = view.findViewById(R.id.Last_name_ET)
        Email_ET = view.findViewById(R.id.Email_ET)
        Mobile_ET = view.findViewById(R.id.Mobile_ET)
        Address1_ET = view.findViewById(R.id.Address1_ET)
        Bankname_ET = view.findViewById(R.id.Bankname_ET)
        A_C_number_ET = view.findViewById(R.id.A_C_number_ET)
        IFSC_ET = view.findViewById(R.id.IFSC_ET)
        Branchname_ET = view.findViewById(R.id.Branchname_ET)

        FirstName_TV = view.findViewById(R.id.FirstName_TV)
        Last_name_TV = view.findViewById(R.id.Last_name_TV)
        Email_TV = view.findViewById(R.id.Email_TV)
        Mobile_TV = view.findViewById(R.id.Mobile_TV)
        Address1_TV = view.findViewById(R.id.Address1_TV)
        Bankname_TV = view.findViewById(R.id.Bankname_TV)
        A_C_number_TV = view.findViewById(R.id.A_C_number_TV)
        IFSC_TV = view.findViewById(R.id.IFSC_TV)
        Branchname_TV = view.findViewById(R.id.Branchname_TV)
        profile_image = view.findViewById(R.id.profile_image)
        updatebutton = view.findViewById(R.id.updatebutton)
        MobileEtLL = view.findViewById(R.id.MobileEtLL)
        Address1EtLL = view.findViewById(R.id.Address1EtLL)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!


        type = "SERVICE_PROVIDER"
        INITTILAZIGOOGLEPLACE()
        initializedControl()
        back.setSafeOnClickListener {
            parentFragmentManager.popBackStack()
        }
        profile_image.setSafeOnClickListener {

            RequestPermission.requestMultiplePermissions(requireContext())
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERM_CODE
                )
            } else {
                selectImage()
            }

        }


        updatebutton.setSafeOnClickListener {
            callValidation()
            if (First_name_ET.text.isNotEmpty() &&
                Last_name_ET.text.isNotEmpty()&&
                Email_ET.text.isNotEmpty() &&
                Mobile_ET.text.isNotEmpty() &&
                Address1_ET.text.isNotEmpty() &&
                country1.text.isNotEmpty()
            ) {
                if (USER_IMAGE_UPLOADED) {
                    createImageLinkApi()
                } else {
                    EditProfile()
                }
            }

        }

        locationTrackingClick.setSafeOnClickListener {
            val fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
            // Start the autocomplete intent.
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(mContext)
            startActivityForResult(
                intent,
                PLACE_PICKER_REQUEST
            )
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

        getProfileApi()

        return view
    }

    fun initializedControl() {
        First_name_ET.addTextChangedListener(textWatcher)
        Last_name_ET.addTextChangedListener(textWatcher)
        Email_ET.addTextChangedListener(textWatcher)
        Mobile_ET.addTextChangedListener(textWatcher)
        Address1_ET.addTextChangedListener(textWatcher)
        country1.addTextChangedListener(textWatcher)
        State_ET.addTextChangedListener(textWatcher)
        CityEt.addTextChangedListener(textWatcher)
        postalcode.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            if (!s.toString().equals("")) {
                callValidation()
            }
        }
    }

    private fun callValidation() {
        FormValidation.ProfilUpdate(
            First_name_ET,
            FirstName_TV,
            Last_name_ET,
            Last_name_TV,
            Email_ET,
            Email_TV,
            Mobile_ET,
            Mobile_TV,
            Address1_ET,
            Address1_TV,
            country1,
            Country,
//            State_ET,
//            State_TV,
//            CityEt,
//            CityTV,
            postalcode,
            errorTvEditPostCode,
            Address1EtLL,
            MobileEtLL
        )
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

                                Address1_ET.text = Editable.Factory().newEditable(addressLine1.joinToString(","))
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


    private fun getProfileApi() {

        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbarEditProfile.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<EditProfile_Get_Response> =
                ApiCallBack<EditProfile_Get_Response>(object :
                    ApiResponseListener<EditProfile_Get_Response> {
                    override fun onApiSuccess(
                        response: EditProfile_Get_Response,
                        apiName: String?
                    ) {
                        progressbarEditProfile.visibility = View.GONE
                        if (response.responseCode == 200) {
                            var Data = response.result
                            try {
                                cor.clear()
                                cor.add(Data?.storeLocation?.coordinates!![0])
                                cor.add(Data.storeLocation.coordinates[1])
                                First_name_ET.setText(Data?.firstName.toString())
                                Last_name_ET.setText(Data?.lastName.toString())
                                Email_ET.setText(Data?.email.toString())
                                if (!response.result!!.countryCode.equals("")) {
                                    var countryCode = Integer.parseInt(response.result.countryCode)
                                    ccp.setCountryForPhoneCode(countryCode)
                                }
                                Mobile_ET.setText(Data?.mobileNumber.toString())
                                Address1_ET.setText(Data?.addressLine1.toString())
                                Address2_ET.setText(Data?.addressLine2.toString())
                                Bankname_ET.setText(Data?.businessBankingDetails?.bankName.toString())
                                A_C_number_ET.setText(Data?.businessBankingDetails?.accountNumber.toString())
                                postalcode.setText(Data?.zipCode.toString())
                                IFSC_ET.setText(Data?.businessBankingDetails?.swiftCode.toString())
                                Branchname_ET.setText(Data?.businessBankingDetails?.branchName.toString())
//                                    Picasso.get().load(Data?.profilePic).into(profile_image)
                                country1.text = Data?.country
                                State_ET.text = Data?.state
                                CityEt.text = Data?.city

                                Glide.with(requireContext()).load(Data?.profilePic).placeholder(R.drawable.layer_3).into(profile_image)

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbarEditProfile.visibility = View.GONE

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, requireContext())

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbarEditProfile.visibility = View.GONE

                    }

                }, "Editgetprofile", mContext)

            try {
                serviceManager.Edit_Get_Profile(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }

    }

    fun EditProfile() {
        if (androidextention.isOnline(requireContext())) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbarEditProfile.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<EditProfile_Response> =
                ApiCallBack<EditProfile_Response>(this, "signupRetailer", mContext)


            if (cor.isEmpty()){
                val fullAddress = "${Address1_ET.text.toString()} $postalcode ${CityEt.text} ${State_ET.text} ${country1.text}"
                val coordinates = LocationClass.getAddressCoordinates(requireContext(),fullAddress)

                if (coordinates != null) {
                    cor.add(long)
                    cor.add(lat)




                } else {
                    androidextention.alertBox("Invalid address or error occurred,Please try again.",requireContext())
                    Progresss.stop()
                    return
                }
            }




            var storeLocation = EditProfileStoreLocation()
            storeLocation.type = "Point"
            storeLocation.coordinates = cor

            var jsonObject = EditProfileRequest()
            jsonObject.userType = type
            jsonObject.firstName = First_name_ET.text.toString()
            jsonObject.lastName = Last_name_ET.text.toString()
            jsonObject.email = Email_ET.text.toString()
            jsonObject.mobileNumber = Mobile_ET.text.toString()
            jsonObject.zipCode = postalcode.text.toString()
            jsonObject.addressLine1 = Address1_ET.text.toString()
            jsonObject.addressLine2 = Address2_ET.text.toString()
            jsonObject.country = country1.text.toString()
            jsonObject.state = State_ET.text.toString()
            jsonObject.city = CityEt.text.toString()
            jsonObject.countryIsoCode = isocode
            jsonObject.stateIsoCode = isoState
            if (USER_IMAGE_UPLOADED) {
                jsonObject.profilePic = requestImage
            }
            jsonObject.countryCode = ccp.selectedCountryCode.toString()
            jsonObject.storeLocation = storeLocation


            try {
                serviceManager.Edit_Profile(callBack, jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }
    }

    override fun onApiSuccess(response: EditProfile_Response, apiName: String?) {
        progressbarEditProfile.visibility = View.GONE
        if (response!!.responseCode == 200) {
            try {
                diasplay_toast("Success")
                parentFragmentManager.popBackStack()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onApiErrorBody(response: String, apiName: String?) {
        progressbarEditProfile.visibility = View.GONE
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
        progressbarEditProfile.visibility = View.GONE

    }

    fun createImageLinkApi() {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbarEditProfile.visibility = View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<DocumentResponse> =
                ApiCallBack<DocumentResponse>(object :
                    ApiResponseListener<DocumentResponse> {
                    override fun onApiSuccess(response: DocumentResponse, apiName: String?) {
                        if (response.responseCode == 200) {
                            try {
                                requestImage = response.result?.mediaUrl!!

                                if (!requestImage.equals("")) {
                                    EditProfile()
                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbarEditProfile.visibility = View.GONE
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
//                            pojo = gson.fromJson(response, pojo::class.java)
//                            androidextention.alertBox(pojo.responseMessage, mContext)

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbarEditProfile.visibility = View.GONE
//                        Toast.makeText(
//                            mContext,
//                            "${failureMessage}",
//                            Toast.LENGTH_LONG
//                        ).show()

                    }

                }, "createMultiImageLinkApi", mContext)
            try {
                serviceManager.singleUploadImageApiService(callBack, imageparts)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)

        }
    }

    private fun selectImage() {
        // on below line we are creating a new bottom sheet dialog.
        val dialog = BottomSheetDialog(requireContext())

        // on below line we are inflating a layout file which we have created.
        val view = layoutInflater.inflate(R.layout.choose_camera_bottom_sheet, null)

        // below line is use to set cancelable to avoid
        // closing of dialog box when clicking on the screen.
        dialog.setCancelable(true)

        val CameraButton = view.findViewById<ImageView>(R.id.choose_from_camera)
        CameraButton.setSafeOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(requireContext().getPackageManager()) != null) {
                try {
                    imageFile = createImageFile()!!
                } catch (ex: IOException) {
                }
                // Continue only if the File was successfully created
                if (imageFile != null) {
                    photoURI = FileProvider.getUriForFile(
                        requireContext(),
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

    private fun getPathFromURI(contentUri: Uri?): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            requireContext().getContentResolver().query(contentUri!!, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            res = cursor.getString(column_index)
        }
        cursor.close()
        return res
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_CANCELED) {
            return
        } else {
            if (requestCode == GALLERY) {
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    if (data != null) {
                        image = data.data!!
                        val path = getPathFromURI(image)
                        val maxFileSizeInBytes = 5 * 1024 * 1024
                        if (path != null) {
                            imageFile = File(path)
                            if (imageFile!!.length() <= maxFileSizeInBytes) {
                                Glide.with(this).load(imageFile).into(profile_image)
                            }else{
                                androidextention.alertBoxCommon(message = "Please select a image that is 5 MB or smaller.", context = requireContext())
                                return
                            }


                        }
                        val requestGalleryImageFile: RequestBody =
                            imageFile!!.asRequestBody("image/*".toMediaTypeOrNull())
                        imageparts = MultipartBody.Part.createFormData("uploaded_file", imageFile!!.name, requestGalleryImageFile)
                        USER_IMAGE_UPLOADED = true

                    }

                }
            } else if (requestCode == CAMERA) {
                if (resultCode == AppCompatActivity.RESULT_OK) {

                    try {

                        imageFile = File(imagePath)
                        Glide.with(this).load(imageFile).into(profile_image)

                        var finalBitmap = modifyOrientation(getBitmap(imagePath)!!, imagePath)
                        var imageUri = getImageUri(mContext, finalBitmap!!)
                        var getRealPath = getRealPathFromURI(mContext, imageUri)
                        imageFile = File(getRealPath)

//                        ProfileImage.setImageURI(Uri.fromFile(imageFile))
                        var requestGalleryImageFile: RequestBody =
                            RequestBody.create("image/*".toMediaTypeOrNull(), imageFile!!)
                        imageparts =
                            MultipartBody.Part.createFormData(
                                "uploaded_file",
                                imageFile!!.getName(),
                                requestGalleryImageFile
                            )
                        USER_IMAGE_UPLOADED = true
//                    if(USER_IMAGE_UPLOADED) {
//                        uploadUserImageApi()
//                    }
                    } catch (e: java.lang.Exception) {

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
                                    GETLATLONG(latLng)
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
        if (flag.equals("Country")) {
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

        } else if (flag.equals("State")) {
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
        if (flag == "Country") {

            isocode = iso
            country1.text = name
            State_ET.text = ""
            isoState = ""
            CityEt.text = ""
            dialog.dismiss()
            callValidation()
        } else if (flag == "State") {

            isoState = iso
            State_ET.text = name
            CityEt.text = ""
            dialog.dismiss()
            callValidation()
        } else if (flag == "City") {

            CityEt.text = name
            dialog.dismiss()
            callValidation()
        }

    }

}