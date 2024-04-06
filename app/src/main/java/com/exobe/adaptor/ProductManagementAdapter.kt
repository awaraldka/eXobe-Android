package com.exobe.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.customClicks.*
import com.exobe.entity.response.MyProductDocs


class ProductManagementAdapter(
    val context: Context,
    var productManagementClick: ProductManagementClick,
    var viewProductClick: viewProductClick,
    var data: ArrayList<MyProductDocs>,
    var flag: String
) :
    RecyclerView.Adapter<ProductManagementAdapter.ProductLatestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductLatestViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.productmanagement_modellayout, parent, false)
        return ProductLatestViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductLatestViewHolder, position: Int) {
        try {


            data[position].apply {
                if (flag == "deals") {
                    holder.retailer_details.visibility = View.VISIBLE
                    holder.size_value.visibility = View.GONE

                } else {
                    holder.retailer_details.visibility = View.GONE
                    holder.size_value.visibility = View.VISIBLE
                }

                holder.productname.text = productName.capitalizeFirstLetter()
                holder.product_category.text = "Category: ${categoryId.categoryName}"
                Glide.with(context).load(thumbnail).placeholder(R.drawable.dummyproduct)
                    .into(holder.productimage);
                holder.quantity.text = "Qty: ${priceSizeDetails.getOrNull(0)!!.quantity}"
                holder.size_value.text = "Size: ${priceSizeDetails.getOrNull(0)!!.value} ${priceSizeDetails.getOrNull(0)!!.unit}"
                holder.price.text = "R ${CommonFunctions.currencyFormatter(priceSizeDetails.getOrNull(0)!!.price.toDouble())}"
                holder.viewClick.setSafeOnClickListener {
                    viewProductClick.viewProductClick(_id)
                }

                holder.editProduct.setSafeOnClickListener {
                    productManagementClick.editClick(_id, "own")
                }

            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ProductLatestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var editProduct = view.findViewById<RelativeLayout>(R.id.edit_product)
        var retailer_details = view.findViewById<LinearLayout>(R.id.retailer_details_field)
        var productname = view.findViewById<TextView>(R.id.productname)
        var product_category = view.findViewById<TextView>(R.id.product_category)
        var quantity = view.findViewById<TextView>(R.id.quantity)
        var price = view.findViewById<TextView>(R.id.price)
        var discount = view.findViewById<TextView>(R.id.discount)
        var productimage = view.findViewById<ImageView>(R.id.productimage)
        var size_value = view.findViewById<TextView>(R.id.size_value_tv)
        var viewClick = view.findViewById<LinearLayout>(R.id.viewClick)


    }

}
