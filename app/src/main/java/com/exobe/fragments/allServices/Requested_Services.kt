package com.exobe.fragments.allServices

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.hostActivity.ServicesMainActivity
import com.exobe.adaptor.servicesAdaptor.RequestedAdapter
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.*
import com.exobe.dialogs.confirm_service
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.serviceTab.ServicesListDoc
import com.exobe.entity.response.serviceTab.ServicesListResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject


class Requested_Services(var servicesClick: ServicesMainActivity, var viewserviceclick: viewserviceclick) : Fragment(),serviceselectedclick ,statusselectedclick,
    servicedeleteclick {
    var usertype = ""
    var status = ""
    var receverid = ""
    lateinit var no_notification: LinearLayout
    lateinit var title: TextView
    lateinit var requested_progressbar: ProgressBar
    lateinit var recyclerView: RecyclerView
    var data: ArrayList<ServicesListDoc> = ArrayList()
    var back: ImageView? = null
    var MenuClick: ImageView? = null
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    private var apiCallFlag = true
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_requested__services, container, false)
        title = activity?.findViewById(R.id.title)!!
        back = activity?.findViewById(R.id.back)!!
        MenuClick = activity?.findViewById(R.id.MenuClick)!!
        recyclerView = view.findViewById(R.id.RequestedServices_Recycler)
        no_notification = view.findViewById(R.id.no_notification)
        requested_progressbar = view.findViewById(R.id.RequestedServices_progressbar)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        usertype = "SERVICE_PROVIDER"
        title.text = "Requested Services"
        back?.visibility = View.VISIBLE
        MenuClick?.visibility = View.GONE
        receverid = SavedPrefManager.getStringPreferences(requireContext(), SavedPrefManager.ID).toString()

        back?.setSafeOnClickListener {
            parentFragmentManager.popBackStack()
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        if(apiCallFlag) {
            OrderAPI()
            apiCallFlag = false
        } else {
            setAdaptor()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                val fm: FragmentManager = requireActivity().supportFragmentManager

                for (i in 0 until fm.backStackEntryCount){
                    fm.popBackStack()
                }


            }
        })

    }
    fun setAdaptor() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        var adapter = RequestedAdapter(activity as Context, this,data)
        recyclerView.adapter = adapter
    }

    fun OrderAPI() {
        if (androidextention.isOnline(requireContext())) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
//            androidextention.showProgressDialog(requireContext())
            requested_progressbar.visibility = View.VISIBLE
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<ServicesListResponse> =
                ApiCallBack<ServicesListResponse>(object :
                    ApiResponseListener<ServicesListResponse> {

                    override fun onApiSuccess(
                        response: ServicesListResponse,
                        apiName: String?
                    ) {
//                        androidextention.disMissProgressDialog(requireActivity())
                        requested_progressbar.visibility = View.GONE
                        println("requesting services ${response.toString()}")
                        if (response.responseCode == 200) {
                            try {
                                data = response.result?.docs as ArrayList<ServicesListDoc>
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            setAdaptor()
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
//                        androidextention.disMissProgressDialog(requireContext())
                        requested_progressbar.visibility = View.GONE
                        no_notification.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE


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
                        requested_progressbar.visibility = View.GONE
                        no_notification.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE


                    }
                }, "RequestedOrder", requireContext())

            var jsonObject = JsonObject()
            jsonObject.addProperty("statusType", status)
            jsonObject.addProperty("userType", usertype)
            jsonObject.addProperty("receiverId", receverid)


            try {
                serviceManager.pendingstatusapi(callBack, jsonObject)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }

    override fun pendingclick(position: Int, id: String) {
        parentFragmentManager.let { confirm_service(requireContext(), this, position, viewserviceclick, id, "").show(it, "MyCustomFragment")}
    }

    override fun pendingdeleteclick(position: Int) {

    }

    override fun pendingclick(position: Int, id: String, flag: String) {

            viewserviceclick.viewservice(id,flag)

    }

}