package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.exobe.Model.ProductCategoryModel
import com.exobe.R

class ProductCategoryAdapter(val context: Context, val itemList: ArrayList<ProductCategoryModel>) : RecyclerView.Adapter<ProductCategoryAdapter.ProductCategoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCategoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_product_model_layout, parent, false)
        return ProductCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductCategoryViewHolder, position: Int) {
        val orderData = itemList[position]

        holder.txtItem.text = orderData.itemName

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ProductCategoryViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        var txtItem: TextView

        init {
            txtItem= itemView.findViewById(R.id.Description1)


        }

    }

}