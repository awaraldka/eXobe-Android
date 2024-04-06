package com.exobe.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.customClicks.CustomeClick
import com.exobe.modalClass.ProductDescModelClass

class ProductDescAdapter (var context: Context,
                          var data: ArrayList<ProductDescModelClass>,var click: CustomeClick
): RecyclerView.Adapter<ProductDescAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // Define click listener for the ViewHolder's View.
        var background = view.findViewById<ImageView>(R.id.backGround)
        var image = view.findViewById<ImageView>(R.id.product_image)
        var name=view.findViewById<TextView>(R.id.productName)
//        var priceTag = view.findViewById<TextView>(R.id.Price)
        var heartline = view.findViewById<ImageView>(R.id.heartOutline)
        var heartfull = view.findViewById<ImageView>(R.id.heartFill)

        var price=view.findViewById<TextView>(R.id.productPrice)
        var ProductDetails=view.findViewById<LinearLayout>(R.id.ProductDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDescAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_description_recycler, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductDescAdapter.ViewHolder, position: Int) {
        var userData = data[position]
        holder.name.text = userData.name
        holder.price.text = "R ${CommonFunctions.currencyFormatter(userData.price.toDouble())}"

//        if(SavedPrefManager.getStringPreferences(context, SavedPrefManager.priceTag).equals("Customer")) {
//            holder.priceTag.setText("M.R.P: ")
//        } else {
//            holder.priceTag.setText("W.S.P: ")
//        }
        Glide.with(context).load(userData.background).into(holder.background);
        Glide.with(context).load(userData.image).into(holder.image);

        holder.heartline.setSafeOnClickListener {
            holder.heartfull.visibility=View.VISIBLE
            holder.heartline.visibility=View.GONE
        }
        holder.heartfull.setSafeOnClickListener {
            holder.heartline.visibility=View.VISIBLE
            holder.heartfull.visibility=View.GONE
        }

        holder.ProductDetails.setSafeOnClickListener {
            click.click("")
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}