package com.exobe.fragments.allServices

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.adaptor.RequestInterestServicesAdapter
import com.exobe.androidextention
import com.exobe.customClicks.RequestedServiceClick
import com.exobe.databinding.FragmentServiceRequestInterestedBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.CommonResponseForAll
import com.exobe.entity.response.customerservices.ServicesListNearMeDoc
import com.exobe.entity.response.customerservices.ServicesListNearMeResponse
import com.exobe.entity.response.customerservices.ServicesListNearMeServiceArray
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.extension.diasplay_toast
import com.exobe.modelClass.response_modal_class
import com.exobe.utils.Progresss
import com.exobe.utils.SavedPrefManager
import com.exobe.validations.DialogUtils
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject


class ServiceRequestInterestedFragment : Fragment(),RequestedServiceClick {

    private var _binding: FragmentServiceRequestInterestedBinding? = null
    private val binding get() = _binding!!

    var serviceId = ""
    var userId = ""
    var clickForPopUp = ""
    var participationIn = ""
    var categoryid = ""
    private lateinit var menuClick: LinearLayout
    private lateinit var title: TextView
    private lateinit var cart: ImageView
    private lateinit var filter: ImageView
    private lateinit var back: LinearLayout
    private lateinit var dealsImageView: ImageView
    private lateinit var greyBellImageView: ImageView
    private lateinit var logoutButton: ImageView
    private lateinit var customerServiceAdaptor :RequestInterestServicesAdapter


    private lateinit var dialog: Dialog
    private lateinit var recyclerView: RecyclerView

    private var subCategoryData: ArrayList<ServicesListNearMeServiceArray> = arrayListOf()
    private var docs: ArrayList<ServicesListNearMeDoc>  = arrayListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentServiceRequestInterestedBinding.inflate(layoutInflater, container, false)
        setToolbar()
        arguments?.getString("serviceId")?.let{ serviceId = it }
        arguments?.getString("userId")?.let{ userId = it }

        val data = SavedPrefManager.getStringPreferences(requireContext(),SavedPrefManager.CAMPAIGN_PREFERENCE)
        diasplay_toast(data)

        when(data){
            "AUTOMATIC"->{
                binding.automaticParticipation.isChecked = true
            }
            else->{
                binding.semiParticipation.isChecked = true
            }
        }



        back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.bookingCategory.setSafeOnClickListener {
            clickForPopUp = "Category"
            openPopUpForCategories()
        }
        binding.bookingSubCategory.setSafeOnClickListener {
            if (binding.bookingCategory.text.isEmpty()){
                diasplay_toast("Please select booking category")
                return@setSafeOnClickListener
            }
            clickForPopUp = "Sub Category"
            openPopUpForCategories()

        }


        binding.requestForInterest.setSafeOnClickListener {
            if (binding.bookingCategory.text.isEmpty()){
                diasplay_toast("Please select booking category")
            }else if (binding.bookingSubCategory.text.isEmpty()){
                diasplay_toast("Please select booking sub category")
            }else if (binding.interestedPrice.text.isEmpty()){
                diasplay_toast("Please enter interested price")
            }else{
                addInterestOnServiceApi()
            }
        }



        binding.interestedPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable) {
                val input = editable.toString()
                if (input == "0") {
                    binding.interestedPrice.setText("")
                }
            }
        })




        return  binding.root
    }
    private fun setToolbar() {

        title = activity?.findViewById(R.id.PreLoginTitle_TextView2)!!


        cart = activity?.findViewById(R.id.cart_icon)!!
        filter = activity?.findViewById(R.id.filter_icon)!!
        back = activity?.findViewById(R.id.imageView_back)!!
        menuClick = activity?.findViewById(R.id.MenuClick)!!
        dealsImageView = activity?.findViewById(R.id.Deals_ImageView)!!
        greyBellImageView = activity?.findViewById(R.id.greyBell_ImageView)!!
        logoutButton = activity?.findViewById(R.id.logoutButton)!!
        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
        cartCount.visibility = View.GONE
        cart.visibility = View.GONE
        filter.visibility = View.GONE
        back.visibility = View.VISIBLE

        menuClick.visibility = View.GONE
        dealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        logoutButton.visibility = View.GONE
        title.text = "Bookings Details"
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }


    @SuppressLint("InflateParams", "SetTextI18n")
    fun openPopUpForCategories() {

        try {
            val binding = LayoutInflater.from(requireContext()).inflate(R.layout.pop_lists, null)
            dialog = DialogUtils().createDialog(requireContext(), binding.rootView, 0)!!
            recyclerView = binding.findViewById(R.id.popup_recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            val dialogTitle = binding.findViewById<TextView>(R.id.popupTitle)
            val dialogBackButton = binding.findViewById<ImageView>(R.id.BackButton)
            val searchEditText = binding.findViewById<EditText>(R.id.search_bar_edittext_popuplist)

            searchEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(text: Editable?) {
                    filterData(text.toString(),clickForPopUp)
                }

            })
            dialogBackButton.setSafeOnClickListener { dialog.dismiss() }

            if (clickForPopUp =="Category"){
                listCategoryApi()
            }else{
                setServicesAdapter()
            }

            dialog.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun filterData(searchText: String, flag: String) {
        val filteredList = ArrayList<ServicesListNearMeDoc>()
        val subCategoryFilter = ArrayList<ServicesListNearMeServiceArray>()

        try {
            when (flag) {
                "Category" -> docs.forEach { item ->
                    if (item.subCategory.subCategoryName.lowercase().contains(searchText.lowercase())) {
                        filteredList.add(item)
                    }
                }
                "Sub Category" -> subCategoryData.forEach { item ->
                    if (item.serviceName.lowercase().contains(searchText.lowercase())) {
                        subCategoryFilter.add(item)
                    }
                }

            }

            when (flag) {
                "Category" -> customerServiceAdaptor.filterListCategory(filteredList)
                "Sub Category" -> customerServiceAdaptor.filterListSubCategory(subCategoryFilter)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    private fun listCategoryApi() {
        if (androidextention.isOnline(requireContext())) {
            Progresss.start(requireContext())
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<ServicesListNearMeResponse> =
                ApiCallBack(object :
                    ApiResponseListener<ServicesListNearMeResponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onApiSuccess(
                        response: ServicesListNearMeResponse,
                        apiName: String?
                    ) {
                        Progresss.stop()
                        if (response.responseCode == 200) {
                            try {
                                docs.clear()
                                docs = response.result.docs
                                setServicesAdapter()
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
                            androidextention.alertBox(
                                pojo.responseMessage,
                                requireContext()
                            )

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }

                }, "listCategoryApi", requireContext())

            val jsonObject = JsonObject()
            jsonObject.addProperty("userId", userId)
            jsonObject.addProperty("categoryId", serviceId)

            try {
                serviceManager.ListServiceNearMe(callBack, jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setServicesAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        customerServiceAdaptor = RequestInterestServicesAdapter(requireContext(), docs,subCategoryData,clickForPopUp,this)
        recyclerView.adapter = customerServiceAdaptor

    }

    override fun requestedServiceCategory(subCategoryName: String, id: String, serviceArray: ArrayList<ServicesListNearMeServiceArray>) {
        binding.bookingCategory.text  = subCategoryName
        subCategoryData.clear()
        subCategoryData =  serviceArray
        dialog.dismiss()

    }

    override fun subCategory(serviceName: String, price: String, id: String) {
        binding.bookingSubCategory.text =  serviceName
        binding.actualPrice.text =  price
        categoryid = id
        dialog.dismiss()
    }



    private fun addInterestOnServiceApi() {

        if (androidextention.isOnline(requireContext())) {
            Progresss.start(requireContext())
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<CommonResponseForAll> =
                ApiCallBack(object :
                    ApiResponseListener<CommonResponseForAll> {
                    override fun onApiSuccess(response: CommonResponseForAll, apiName: String?) {
                        Progresss.stop()
                        if (response.responseCode == 200) {
                            try {
                                androidextention.alertBoxFragmentBack(response.responseMessage,requireContext(),parentFragmentManager)

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

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                    }

                }, "addInterestOnProduct", requireContext())


            try {
                participationIn =  if (binding.automaticParticipation.isChecked){
                    "AUTOMATIC"
                }else if (binding.semiParticipation.isChecked){
                    "SEMI_AUTOMATIC"
                }else{
                    "MANUAL"
                }
                serviceManager.addInterestPriceOnService(callBack = callBack,
                    serviceId = categoryid,
                    categoryId = categoryid,
                    interestedPrice = binding.interestedPrice.text.toString(),
                    price = binding.actualPrice.text.toString(),
                    spordicType = participationIn
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }






}