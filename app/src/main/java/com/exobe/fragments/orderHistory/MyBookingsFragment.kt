package com.exobe.fragments.orderHistory

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.Adapter.ViewServiceList
import com.exobe.R
import com.exobe.androidextention
import com.exobe.androidextention.initLoader
import com.exobe.customClicks.BookingDetailListener
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.NewOrderServiceReqDoc
import com.exobe.entity.response.NewOrderServiceReqResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.exobe.util.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyBookingsFragment(var flagSide: String) : Fragment(), BookingDetailListener {

    lateinit var txtFromDate: TextView
    lateinit var fromDateRL: RelativeLayout
    lateinit var toDateRL: RelativeLayout
    lateinit var from_date_ImageView: ImageView
    lateinit var txtToDate: TextView
    lateinit var to_date_ImageView: ImageView
    lateinit var reqServiceListBack: ImageView
    lateinit var adapter: ViewServiceList
    lateinit var RecyclerLayout: RecyclerView
    lateinit var progressbar: ProgressBar
    lateinit var no_Available: LinearLayout

    var yearset = 0
    var monthset = 0
    var day = 0
    val c = Calendar.getInstance()
    var flag = ""
    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var back: LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var mainHeader: RelativeLayout
    lateinit var RV_Services_List: RelativeLayout
    lateinit var search: Button
    lateinit var SR_Swipe: SwipeRefreshLayout

    lateinit var CalenderClickFrom: RelativeLayout
    var fromDate = ""
    var toDate = ""
    var data = ArrayList<NewOrderServiceReqDoc>()
    lateinit var internet_connection: RelativeLayout
    var lottie: LottieAnimationView? = null

    var pages = 0
    var page = 1
    var limit = 20
    var dataLoadFlag = true
    var loaderFlag = true

    lateinit var pbReqServicesPagination: ProgressBar
    lateinit var nestedScrollRequestView: NestedScrollView
    private var apiCallFlag = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.activity_view_service_list, container, false)

        setToolbar()
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        mainHeader.visibility = View.VISIBLE

        nestedScrollRequestView = view.findViewById(R.id.nestedScrollRequestView)
        pbReqServicesPagination = view.findViewById(R.id.pbReqServicesPagination)
        SR_Swipe = view.findViewById(R.id.SR_Swipe)
        RV_Services_List = view.findViewById(R.id.RV_Services_List)
        txtFromDate = view.findViewById(R.id.from_date_Text)
        from_date_ImageView = view.findViewById(R.id.from_date_ImageView)
        txtToDate = view.findViewById(R.id.to_date_Text)
        to_date_ImageView = view.findViewById(R.id.to_date_ImageView)
        toDateRL = view.findViewById(R.id.CalenderClickTo)
        progressbar = view.findViewById(R.id.progressbar)
        search = view.findViewById(R.id.search)
        no_Available = view.findViewById(R.id.no_Available)
        lottie = activity?.findViewById(R.id.lottie)!!
        internet_connection = activity?.findViewById(R.id.internet_connection)!!


        RecyclerLayout = view.findViewById(R.id.RecyclerLayout)

        CalenderClickFrom = view.findViewById(R.id.CalenderClickFrom)


        back.setSafeOnClickListener {
            parentFragmentManager.popBackStack()
        }

        SR_Swipe.setOnRefreshListener {
            resetPagination()
            MyServiceRequestListApi()
            SR_Swipe.isRefreshing = false

        }

        nestedScrollRequestView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                dataLoadFlag = false
                loaderFlag = false
                page++

                pbReqServicesPagination.visibility = View.VISIBLE
                if (page > pages) {
                    pbReqServicesPagination.visibility = View.GONE
                } else {
                    MyServiceRequestListApi()
                }
            }


        })

        val calendar: Calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var date = calendar.get(Calendar.DAY_OF_MONTH)


        CalenderClickFrom.setSafeOnClickListener {

            val datePickerDialog = requireContext().let { it1 ->
                DatePickerDialog(
                    it1, R.style.DatePickerTheme, DatePickerDialog.OnDateSetListener
                    { view, year, monthOfYear, dayOfMonth ->
                        var formattedDate = DateFormat.dealTimeFormatter("$year-${monthOfYear + 1}-$dayOfMonth")
                        if (formattedDate != null) {
                            fromDate = formattedDate
                            txtFromDate.text = formattedDate
                        }


                    }, year, month, date
                )
            }

            datePickerDialog?.show()

        }

        c.set(year, month + 1, date)

        toDateRL.setSafeOnClickListener {
            val datePickerDialog = requireContext().let { it1 ->
                DatePickerDialog(
                    it1, R.style.DatePickerTheme, DatePickerDialog.OnDateSetListener
                    { view, year, monthOfYear, dayOfMonth ->
                        val formattedDate = DateFormat.dealTimeFormatter("$year-${monthOfYear + 1}-$dayOfMonth")
                        if (formattedDate != null) {
                            val selectedDate = DateFormat.dealTimeFormatter("$year-${monthOfYear + 1}-$dayOfMonth")
                            if (selectedDate != null && fromDate <= selectedDate) {
                                toDate = selectedDate
                                txtToDate.text = selectedDate
                            } else {
                                // Show an error message or inform the user that toDate cannot be before fromDate.
                                Toast.makeText(requireContext(), "Invalid toDate selection.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }, year, month, date
                )
            }

            // Set the maximum date for the toDate DatePickerDialog
            datePickerDialog.datePicker.maxDate = c.timeInMillis
            datePickerDialog?.show()
        }

        search.setSafeOnClickListener {
            page = 1
            dataLoadFlag = true
            loaderFlag = true
            MyServiceRequestListApi()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        if (apiCallFlag) {
            MyServiceRequestListApi()
            apiCallFlag = false
        } else {
            if (androidextention.isOnline(requireContext())) {
                internet_connection.visibility = View.GONE
                lottie!!.initLoader(false)
                if (data.size == 0) {
                    no_Available.visibility = View.VISIBLE
                } else {
                    no_Available.visibility = View.GONE
                    setAdaptor(data)
                }
            } else {
                no_Available.visibility = View.GONE
                internet_connection.visibility = View.VISIBLE
                lottie!!.initLoader(true)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    val fm: FragmentManager = requireActivity().supportFragmentManager

                    for (i in 0 until fm.backStackEntryCount) {
                        fm.popBackStack()
                    }


                }
            })

    }


    fun MyServiceRequestListApi() {
        if (androidextention.isOnline(requireContext())) {
            internet_connection.visibility = View.GONE
            lottie!!.initLoader(false)
            if (loaderFlag) {
                progressbar.visibility = View.VISIBLE
            }
            val serviceManager = ServiceManager(requireContext())
            val callBack: ApiCallBack<NewOrderServiceReqResponse> =
                ApiCallBack<NewOrderServiceReqResponse>(object :
                    ApiResponseListener<NewOrderServiceReqResponse> {
                    override fun onApiSuccess(
                        response: NewOrderServiceReqResponse,
                        apiName: String?
                    ) {
                        progressbar.visibility = View.GONE
                        if (response.responseCode == 200) {
                            try {
                                if (dataLoadFlag) {
                                    data.clear()
                                }
                                page = response.result.page
                                pages = response.result.pages
                                data.addAll(response.result.docs)



                                if (data.size == 0) {
                                    no_Available.visibility = View.VISIBLE
                                } else {
                                    no_Available.visibility = View.GONE
                                    setAdaptor(data)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        try {
                            data.clear()
                            setAdaptor(data)
                            progressbar.visibility = View.GONE
                            no_Available.visibility = View.VISIBLE
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbar.visibility = View.GONE
                    }

                }, "MyServiceRequestListApi", requireContext())


            try {
                serviceManager.MyServiceRequestListApi(
                    callBack,
                    fromDate, toDate, page, limit
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            RV_Services_List.visibility = View.GONE
            internet_connection.visibility = View.VISIBLE
            lottie!!.initLoader(true)
        }

    }

    fun setAdaptor(data: ArrayList<NewOrderServiceReqDoc>) {
        RecyclerLayout.layoutManager = LinearLayoutManager(requireContext())
        adapter = ViewServiceList(requireContext(), data, this)
        RecyclerLayout.adapter = adapter
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
        title.text = "List of Bookings"
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

    private fun resetPagination() {
        fromDate = ""
        toDate = ""
        page = 1
        dataLoadFlag = true
        loaderFlag = true
    }

    override fun bookingDetail(flag: String, serviceData: NewOrderServiceReqDoc,serviceStatus:String) {
        val bundle = Bundle()
        bundle.putString("productId", "")
        bundle.putString("flag", flag)
        bundle.putSerializable("serviceData", serviceData)
        bundle.putSerializable("status", serviceStatus)
        val fragObj = OrderHistoryViewFragment()
        fragObj.arguments = bundle
        parentFragmentManager.beginTransaction().replace(R.id.FrameLayout, fragObj, "orderHistory")
            .addToBackStack(null).commit()

    }

}
