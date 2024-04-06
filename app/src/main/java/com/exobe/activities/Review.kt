package com.exobe.activities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.adaptor.ReviewAdaptor
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.Progresss
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.customer.ReviewOfProducts
import com.exobe.entity.response.customer.ReviewResult
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder

class Review : Fragment() {

    lateinit var backButton: ImageView
    lateinit var reviewAdaptor: ReviewAdaptor
    lateinit var recyclerView: RecyclerView
    var data = ArrayList<ReviewResult>()
    lateinit var mContext: Context
    lateinit var title : TextView
    lateinit var cart : ImageView
    lateinit var filter : ImageView
    lateinit var back : LinearLayout

    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var NoReview: LinearLayout
    lateinit var mainHeader: RelativeLayout
    var productId2= ""
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.activity_review, container, false)
        mContext = activity?.applicationContext!!
        setToolbar()
        backButton = view.findViewById(R.id.back)
        recyclerView = view.findViewById(R.id.review_rv)
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        NoReview = view.findViewById(R.id.NoReview)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!


        mainHeader.visibility = View.VISIBLE

        back.setSafeOnClickListener {
            fragmentManager?.popBackStack()
        }

        if (requireArguments().getString("productId2") != null) {
            productId2 = requireArguments().getString("productId2").toString()
        }

        back.setSafeOnClickListener {
            fragmentManager?.popBackStack()
        }

        viewReviews()


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
        title.setText("Reviews")
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }



    fun viewReviews() {

        if (androidextention.isOnline(mContext)) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            Progresss.start(requireContext())
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<ReviewOfProducts> =
                ApiCallBack<ReviewOfProducts>(object :
                    ApiResponseListener<ReviewOfProducts> {
                    override fun onApiSuccess(response: ReviewOfProducts, apiName: String?) {
                        Progresss.stop()
                        if (response.responseCode == 200) {
                            try {
                                data = response.result as ArrayList<ReviewResult>
                                if (data.size > 0) {
                                    NoReview.visibility = View.GONE
                                    setAdaptor()
                                }else{
                                    NoReview.visibility = View.VISIBLE
                                    androidextention.alertBox(response.responseMessage.toString(), requireContext())
                            }

                            }catch (e:Exception){
                                e.printStackTrace()
                            }

                        }else{
                            androidextention.alertBox(response.responseMessage.toString(),requireContext())
                        }

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        Progresss.stop()

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(pojo.responseMessage, mContext)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()
                        Toast.makeText(requireActivity(), "onApiFailure", Toast.LENGTH_SHORT).show()

                    }

                }, "viewReviews", mContext)


            try {
                serviceManager.viewReview(callBack, "",productId2, 1, 10)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            NoReview.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }
    }


    fun setAdaptor() {
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        reviewAdaptor = ReviewAdaptor(mContext, data)
        recyclerView.adapter = reviewAdaptor
    }




}