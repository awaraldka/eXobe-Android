package com.exobe.activities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R

class Payment : Fragment() {

    lateinit var title : TextView
    lateinit var cart : ImageView
    lateinit var filter : ImageView
    lateinit var back : LinearLayout
    lateinit var DealsImageView: ImageView
    lateinit var greyBellImageView: ImageView
    lateinit var logoutButton: ImageView
    lateinit var MenuClick: LinearLayout
    lateinit var mainHeader: RelativeLayout
    lateinit var services: LinearLayout
    lateinit var PaymentButton:Button
    var flag:String=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.activity_payment, container, false)
        setToolbar()
        if(requireArguments().getString("flag") != null) {
            flag = requireArguments().getString("flag").toString()

        }
        mainHeader = activity?.findViewById(R.id.RelativeLayout)!!
        mainHeader!!.visibility = View.VISIBLE

        PaymentButton= view.findViewById(R.id.PaymentButton)

        back.setSafeOnClickListener{
            fragmentManager?.popBackStack()
        }

        PaymentButton.setSafeOnClickListener{
            if(flag.equals("servicerequest")){

            }
            else{
//                fragmentManager?.let { DialogBoxPayment().show(it, "MyCustomFragment") }
            }
//            Toast.makeText(requireContext(),"Payment Done Successfully",Toast.LENGTH_SHORT)


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
        logoutButton = activity?.findViewById(R.id.greyBell_ImageView)!!
        val cartCount = activity?.findViewById<TextView>(R.id.cartCount)!!
cartCount.visibility = View.GONE
cart.visibility = View.GONE
        filter.visibility = View.GONE
        back.visibility = View.VISIBLE

        MenuClick.visibility = View.GONE
        DealsImageView.visibility = View.GONE
        greyBellImageView.visibility = View.GONE
        logoutButton.visibility = View.GONE
        title.setText("Payment Details")
        val shareIcon = activity?.findViewById<ImageView>(R.id.shareIcon)!!
        shareIcon.visibility = View.GONE
    }

}


