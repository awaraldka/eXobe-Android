package com.exobe.fragments

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
import com.exobe.Adapter.PaymentStatusAdapter
import com.exobe.modelClass.PaymentStatusModel
import com.exobe.R

class PaymentDescription(var flagSide: String) : Fragment() {

    lateinit var My_RecyclerView: RecyclerView
    lateinit var Adapter : PaymentStatusAdapter
    lateinit var back : LinearLayout
    var array: ArrayList<PaymentStatusModel> = ArrayList()
    lateinit var mContext : Context
    lateinit var MenuClick: LinearLayout

    lateinit var title: TextView
    lateinit var cart: ImageView
    lateinit var filter: ImageView
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var mainHeader: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_payment_description, container, false)
        mContext = activity?.applicationContext!!
        setToolbar()
        My_RecyclerView = view.findViewById(R.id.payment_recycler)

        if (flagSide.equals("SideMenu")) {
            back.visibility = View.GONE
            MenuClick.visibility = View.VISIBLE
        } else {
            MenuClick.visibility = View.GONE
            back.visibility = View.VISIBLE
        }
        var data1 = PaymentStatusModel(
            "May 1st 2019",
            "Hi Max, $ 300 received from Customer name for Order ID TOY3535."
        )
        var data2 = PaymentStatusModel(
            "May 1st 2019",
            "Hi Max, $ 300 received from Customer name for Order ID TOY3535."
        )
        var data3 = PaymentStatusModel(
            "May 1st 2019",
            "Hi Max, $ 300 received from Customer name for Order ID TOY3535."
        )
        var data4 = PaymentStatusModel(
            "May 1st 2019",
            "Hi Max, $ 300 received from Customer name for Order ID TOY3535."
        )
        var data5 = PaymentStatusModel(
            "May 1st 2019",
            "Hi Max, $ 300 received from Customer name for Order ID TOY3535."
        )
        var data6 = PaymentStatusModel(
            "May 1st 2019",
            "Hi Max, $ 300 received from Customer name for Order ID TOY3535."
        )
        var data7 = PaymentStatusModel(
            "May 1st 2019",
            "Hi Max, $ 300 received from Customer name for Order ID TOY3535."
        )
        var data8 = PaymentStatusModel(
            "May 1st 2019",
            "Hi Max, $ 300 received from Customer name for Order ID TOY3535."
        )
        var data9 = PaymentStatusModel(
            "May 1st 2019",
            "Hi Max, $ 300 received from Customer name for Order ID TOY3535."
        )
        var data10 = PaymentStatusModel(
            "May 1st 2019",
            "Hi Max, $ 300 received from Customer name for Order ID TOY3535."
        )
        var data11 = PaymentStatusModel(
            "May 1st 2019",
            "Hi Max, $ 300 received from Customer name for Order ID TOY3535."
        )
        var data12 = PaymentStatusModel(
            "May 1st 2019",
            "Hi Max, $ 300 received from Customer name for Order ID TOY3535."
        )
        var data13 = PaymentStatusModel(
            "May 1st 2019",
            "Hi Max, $ 300 received from Customer name for Order ID TOY3535."
        )
        var data14 = PaymentStatusModel(
            "May 1st 2019",
            "Hi Max, $ 300 received from Customer name for Order ID TOY3535."
        )
        var data15 = PaymentStatusModel(
            "May 1st 2019",
            "Hi Max, $ 300 received from Customer name for Order ID TOY3535."
        )



        array.add(data1)
        array.add(data2)
        array.add(data3)
        array.add(data4)
        array.add(data5)
        array.add(data6)
        array.add(data7)
        array.add(data8)
        array.add(data9)
        array.add(data10)
        array.add(data11)
        array.add(data12)
        array.add(data13)
        array.add(data14)
        array.add(data15)



        My_RecyclerView.layoutManager = LinearLayoutManager(activity)
//        Adapter = PaymentStatusAdapter(mContext, array)
        My_RecyclerView.adapter = Adapter




        return view



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
        back.visibility = View.GONE

        MenuClick.visibility = View.VISIBLE
        DealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        logoutButton.visibility = View.GONE
        title.setText("Payment History")
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE

    }
}