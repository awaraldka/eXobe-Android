package com.exobe.adaptor

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
import com.exobe.customClicks.ServiceListing
import com.exobe.entity.response.customerservices.ListOfServiceProviderDoc
import kotlin.math.roundToInt

class ServicesAvaliableAdapter_SP(
    var itemList: ArrayList<ListOfServiceProviderDoc>,
    var context: Context,
    var serviceListing: ServiceListing,
    var categoryName: String,
    var id: String


) : RecyclerView.Adapter<ServicesAvaliableAdapter_SP.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ServicesAvaliableAdapter_SP.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.services_avaliable, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServicesAvaliableAdapter_SP.ViewHolder, position: Int) {
        val serviceData = itemList[position]

        try {
            Glide.with(context).load(serviceData.profilePic).placeholder(R.drawable.dummyproduct).into(holder.itemImage)
            holder.servicename.text = "${serviceData.firstName} ${serviceData.lastName}"
            holder.location.text = "${serviceData.addressLine1}"

            var finalAddress = StringBuffer()
            if(serviceData.addressLine1 != null && serviceData.addressLine1 != "") {
                finalAddress.append("${serviceData.addressLine1},")
            }
//            if(serviceData.addressLine2 != null && !serviceData.addressLine2.equals("")) {
//                finalAddress.append("${serviceData.addressLine2},")
//            }
            if(serviceData.city != null && serviceData.city != "") {
                finalAddress.append("${serviceData.city},")
            }
            if(serviceData.state != null && serviceData.state != "") {
                finalAddress.append("${serviceData.state},")
            }
            if(serviceData.country != null && serviceData.country != "") {
                finalAddress.append("${serviceData.country}, ")
            }
            if(serviceData.zipCode != null && serviceData.zipCode != "") {
                finalAddress.append("Zipcode: ${serviceData.zipCode}")
            }
            holder.location.text = "$finalAddress"

            var random = serviceData.distance
            val roundoff = (random * 100.0).roundToInt() / 100.0
            holder.locationtracking.text = "${roundoff.toString()} M"
            if (serviceData.comments.equals("")){
                holder.comment.visibility=View.GONE
            }
            else{
                holder.comment.text = serviceData.comments
            }
//            holder.comment.text = serviceData.comments
            holder.services.text = "${categoryName} Service"

            holder.ServicesAvaliableAdapter_LL.setSafeOnClickListener {
//                if (serviceData.serviceDetails.listOfServices.size > 0) {
                    serviceListing.serviceListingClick(id,"", serviceData.id)
//                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }


    }


    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var ServicesAvaliableAdapter_LL: LinearLayout
        var servicename: TextView
        var location: TextView
        var services: TextView
        var locationtracking: TextView
        var comment: TextView


        init {
            itemImage = itemView.findViewById(R.id.image)
            servicename = itemView.findViewById(R.id.servicename)
            services = itemView.findViewById(R.id.services)
            ServicesAvaliableAdapter_LL =
                itemView.findViewById(R.id.ServicesAvaliableAdapter_LinearLayout)
            location = itemView.findViewById(R.id.location)
            locationtracking = itemView.findViewById(R.id.locationtracking)
            comment = itemView.findViewById(R.id.comment)
        }
    }

}