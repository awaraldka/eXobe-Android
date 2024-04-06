package com.exobe.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.customClicks.editDeal
import com.exobe.customClicks.viewProductClick
import com.exobe.entity.response.DealListDoc
import com.exobe.util.DateFormat

class DealsManagement_Adapter(
    val context: Context,
    var customeClick: editDeal,
    var viewProductClick: viewProductClick,
    var data: ArrayList<DealListDoc>,
    var flag: String
) :
    RecyclerView.Adapter<DealsManagement_Adapter.DealsLatestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealsLatestViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.productmanagement_modellayout, parent, false)
        return DealsLatestViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DealsLatestViewHolder, position: Int) {
        data[position].apply {
            if (flag == "deals") {
                holder.retailer_details.visibility = View.VISIBLE
                holder.price.visibility = View.GONE
                holder.quantity.visibility = View.GONE
            } else {
                holder.retailer_details.visibility = View.GONE
            }

            if (productId.size > 0){

                try {
                    holder.productname.text = productId[0].productName.toString().capitalizeFirstLetter()
                    holder.product_category.text = "Category: ${productId[0].categoryId?.categoryName}"
                    Glide.with(context).load(thumbnail).placeholder(R.drawable.dummyproduct).into(holder.productimage)
                    holder.quantity.text = "Qty: ${productId[0].quantity.toString()}"
                    var price = CommonFunctions.currencyFormatter(dealPrice!!.toDouble())
                    holder.price.isVisible = false

                    if (productId[0].quantity!!.isEmpty()){
                        holder.quantity.isVisible = false
                    }

                    if (productId[0].quantity!!.isEmpty()){
                        holder.size_value_tv.isVisible = false
                    }

                    holder.discount.text = "Discount: ${dealDiscount}%"
                    holder.expiresON.text = "Exp. On: ${DateFormat.getDateOfhourminute(dealEndTime.toString())}"

                    holder.viewClick.setSafeOnClickListener {
                        viewProductClick.viewProductClick(Id.toString())
                    }
                    holder.editProduct.setSafeOnClickListener {
                        customeClick.click(Id, productId[0].Id)
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }
            
            
        }

        

    }

    override fun getItemCount(): Int {
        return data.size
    }

    class DealsLatestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var editProduct = view.findViewById<RelativeLayout>(R.id.edit_product)
        var retailer_details = view.findViewById<LinearLayout>(R.id.retailer_details_field)
        var productname = view.findViewById<TextView>(R.id.productname)
        var product_category = view.findViewById<TextView>(R.id.product_category)
        var quantity = view.findViewById<TextView>(R.id.quantity)
        var price = view.findViewById<TextView>(R.id.price)
        var discount = view.findViewById<TextView>(R.id.discount)
        var productimage = view.findViewById<ImageView>(R.id.productimage)
        var expiresON = view.findViewById<TextView>(R.id.expiresON)
        var size_value_tv = view.findViewById<TextView>(R.id.size_value_tv)
        var viewClick = view.findViewById<LinearLayout>(R.id.viewClick)


    }

}