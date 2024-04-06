package com.exobe.activities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.adaptor.faqsAdapter
import com.exobe.Model.faqsModel
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.FaqResponse
import com.exobe.entity.response.FaqResult
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder

class FAQs : Fragment(),ApiResponseListener<FaqResponse> {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: faqsAdapter
    lateinit var ivgroup: TextView
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var service_back: ImageView
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var mainHeader: RelativeLayout
    lateinit var faq_scroll_view: NestedScrollView

    lateinit var progressbar: ProgressBar
    var data: ArrayList<FaqResult> = ArrayList()
    var flag = ""
    lateinit var mContext: Context
    lateinit var internet_connection: RelativeLayout
    lateinit var noDataFoundFaqs: TextView
    var lottie: LottieAnimationView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.activity_faqs, container, false)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!
        recyclerView = view.findViewById(R.id.recyclerViewFAQs)
        progressbar = view.findViewById(R.id.progressbar)
        faq_scroll_view = view.findViewById(R.id.faq_scroll_view)
        noDataFoundFaqs = view.findViewById(R.id.noDataFoundFaqs)
        mContext=activity?.applicationContext!!

        faqListApi()

        if (requireArguments().getString("flag") != null) {
            flag = requireArguments().getString("flag").toString()

        }

        if (flag.equals("services")) {

            title = activity?.findViewById(R.id.title)!!
            service_back = activity?.findViewById(R.id.back)!!
            title!!.setText("FAQ")
            service_back!!.visibility = View.VISIBLE
            service_back!!.setSafeOnClickListener {
                fragmentManager?.popBackStack()
            }
        } else {
            setToolbar()
            mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
            mainHeader.visibility = View.VISIBLE


            back.setSafeOnClickListener {
                fragmentManager?.popBackStack()
            }


        }


        return view
    }


    fun setToolbar() {
        title = activity?.findViewById(R.id.PreLoginTitle_TextView2)!!


        cart = activity?.findViewById(R.id.cart_icon)!!
        filter = activity?.findViewById(R.id.filter_icon)!!
        back = activity?.findViewById(R.id.imageView_back)!!
        MenuClick = activity?.findViewById(R.id.MenuClick)!!
        DealsImageView = activity?.findViewById(R.id.Deals_ImageView)!!
        greyBellImageView = activity?.findViewById(R.id.greyBell_ImageView)!!
        logoutButton = activity?.findViewById(R.id.logoutButton)!!

        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
cartCount.visibility = View.GONE
cart.visibility = View.GONE
        filter.visibility = View.GONE
        back.visibility = View.VISIBLE

        MenuClick.visibility = View.GONE
        DealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        logoutButton.visibility = View.GONE
        title.setText("FAQ")
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

    fun faqListApi() {
        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            progressbar.visibility=View.VISIBLE
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack< FaqResponse> =
                ApiCallBack<FaqResponse>(
                    this,
                    "choosedeliveryApi",
                    mContext
                )

            try {
                serviceManager.faqListApi(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            faq_scroll_view.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }

    }

    override fun onApiSuccess(response: FaqResponse, apiName: String?) {
        progressbar.visibility=View.GONE
        if (response.responseCode== 200) {

            try{
                if(response.result?.size!! > 0) {
                    noDataFoundFaqs.visibility = View.GONE
                    var aaqData: ArrayList<faqsModel> = ArrayList()
                    for (i in 0 until response.result?.size!!) {
                        aaqData.add(
                            faqsModel(
                                response.result!!.get(i).question.toString(),
                                response.result!!.get(i).answer.toString(),
                                false
                            )
                        )
                    }
                    setAdapater(aaqData)
                } else {
                    noDataFoundFaqs.visibility = View.VISIBLE

                }
            }catch (e:Exception){
                e.printStackTrace()
            }

        }
    }

    override fun onApiErrorBody(response: String, apiName: String?) {
        progressbar.visibility=View.GONE
        noDataFoundFaqs.visibility = View.VISIBLE


        val gson = GsonBuilder().create()
        var pojo = response_modal_class()

        try {
            pojo = gson.fromJson(response, pojo::class.java)
//            androidextention.alertBox(pojo.responseMessage, requireContext())

        } catch (e: Exception) {
            // handle failure at error parse
        }
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        progressbar.visibility=View.GONE
        noDataFoundFaqs.visibility = View.VISIBLE


    }

    fun setAdapater(data: ArrayList<faqsModel>){
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = faqsAdapter(requireContext(), data)
        recyclerView.adapter = adapter

    }

}