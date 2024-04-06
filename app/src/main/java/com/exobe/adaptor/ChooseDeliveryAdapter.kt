package com.exobe.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.fragments.address.AddAddressFragment
import com.exobe.fragments.cart.OrderReview
import com.exobe.R
import com.exobe.utils.SavedPrefManager
import com.exobe.customClicks.delete
import com.exobe.entity.response.AddresslistDocs

class ChooseDeliveryAdapter(
    var context: Context,
    var itemList: ArrayList<AddresslistDocs>,
    var delete: delete,
    var fragmentManager: FragmentManager?,
    var flag: String
) : RecyclerView.Adapter<ChooseDeliveryAdapter.ViewHolder>() {
    var count = 0
    var mSelectedItem = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.delivery_address_modallayout, parent, false)
        return ViewHolder(view)

    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       try {
           itemList[position].apply {
               holder.radioButton.isVisible = flag != "setting_address"

               if (isPrimary == true) {
                   SavedPrefManager.saveStringPreferences(context, SavedPrefManager.SERVICE_ID_Address , _id)
                   holder.delete.visibility = View.GONE
                   holder.tick.visibility = View.VISIBLE
               } else {
                   holder.delete.visibility = View.VISIBLE
                   holder.tick.visibility = View.GONE
               }

               holder.firstname.text = "${firstName} ${lastName}"
               val finalAddress = StringBuffer()
               if (addressLine1 != null && !addressLine1.equals("")) {
                   finalAddress.append("${addressLine1},")
               }

               if (city != null && city != "") {
                   finalAddress.append("${city},")
               }
               if (state != null && state != "") {
                   finalAddress.append("${state},")
               }
               if (country != null && country != "") {
                   finalAddress.append("${country}")
               }
               holder.address.text = Html.fromHtml("<b>${"Address:"}</b> $finalAddress")
               holder.phonenumber.text =
                   Html.fromHtml("<b>${"Ph. no:"}</b> ${countryCode}-${mobileNumber.toString()}")
               holder.pincode.text = Html.fromHtml("<b>${"Zipcode:"}</b> ${zipCode.toString()}")

               holder.edit.setSafeOnClickListener {
                   fragmentManager?.beginTransaction()?.replace(
                       R.id.FrameLayout,
                       AddAddressFragment("Edit", _id.toString(), ""),
                       "addAddress"
                   )?.addToBackStack(null)?.commit()

               }


               holder.radioButton.isChecked = isSelected



               holder.chooseDelivery.setOnClickListener {
                   if (flag == "setting_address") {
                       if(isPrimary == false) {
                           delete.updatePrimaryAddress(_id)
                       }
                   } else {
                       if (flag == "service_request") {
                           SavedPrefManager.saveStringPreferences(context, SavedPrefManager.SERVICE_ID_Address, _id.toString())
                           fragmentManager?.popBackStack()
                           sendDataToPreviousFragment(_id.toString())
                       }else{
                           if (!isSelected) {
                               val previouslySelectedItem = getSelectedItem()
                               previouslySelectedItem?.isSelected = false
                               isSelected = true
                               notifyDataSetChanged()
                           } else {
                               val previouslySelectedItem = getSelectedItem()
                               previouslySelectedItem?.isSelected = true
                               isSelected = false
                               notifyDataSetChanged()
                           }
//                           delete.selectedAddress(position,_id!!)
                       }


                   }

                   delete.selectedAddress(position,_id!!)


               }



               holder.delete.setSafeOnClickListener {
                   delete.delete(_id)

               }
           }

        }catch (e:Exception){
            e.printStackTrace()
        }


    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var firstname: TextView
        var edit: ImageView
        var chooseDelivery: LinearLayout
        var radioButton: RadioButton
        var delete: ImageView
        var tick: ImageView
        var address: TextView
        var pincode: TextView
        var phonenumber: TextView

        init {
            firstname = itemView.findViewById(R.id.firstname)
            address = itemView.findViewById(R.id.address)
            pincode = itemView.findViewById(R.id.pincode)
            phonenumber = itemView.findViewById(R.id.phonenumber)
            edit = itemView.findViewById(R.id.editaddress)
            chooseDelivery = itemView.findViewById(R.id.chooseDelivery)
            radioButton = itemView.findViewById(R.id.radioButton)
            delete = itemView.findViewById(R.id.delete)
            tick = itemView.findViewById(R.id.tick)

        }
    }


    override fun getItemCount(): Int {
        return itemList.size
    }


    private fun sendDataToPreviousFragment(data: String) {
        fragmentManager?.setFragmentResult("requestKey", Bundle().apply {
            putString("addressId", data)
            putBoolean("isAddressChanged",true)
        })
    }


    private fun getSelectedItem(): AddresslistDocs? {
        return itemList.find { it.isSelected }
    }




}