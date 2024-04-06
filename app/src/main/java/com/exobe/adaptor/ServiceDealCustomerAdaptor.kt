package com.exobe.adaptor


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.CommonFunctions.capitalizeFirstLetter
import com.exobe.customClicks.ServiceDealsForCustomer
import com.exobe.entity.response.Customerdeals_Result
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class ServiceDealCustomerAdaptor(
    val context: Context,
    val itemList: ArrayList<Customerdeals_Result>,
    var flag: String,
    var serviceDealsForCustomer: ServiceDealsForCustomer,
) : RecyclerView.Adapter<ServiceDealCustomerAdaptor.ViewHolder>() {

    var flag1 = false
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {  //deal_for_customer_modallayout
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.deal_customer_layout_new, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val userData = itemList[position]

            holder.productName.text = userData.serviceId!!.serviceName.capitalizeFirstLetter()
            Glide.with(context).load(userData.dealImage[0]).thumbnail(0.25f).placeholder(R.drawable.dummyproduct).into(holder.productImage)
            holder.originalPrice.text = "R ${userData.dealPrice.toDouble().let { CommonFunctions.currencyFormatter(it) }}"

            holder.offOnProduct.text = "${CommonFunctions.formatPercentage(calculateDealDiscount(userData.serviceId.price.toDouble(),userData.dealPrice.toDouble()).toDouble())} Off"
//            holder.offOnProduct.text = "${userData.dealDiscount}% Off"


            if(userData.serviceId.price != null) {
                holder.priceTag.text = "R ${userData.serviceId.price.let { CommonFunctions.currencyFormatter(it.toDouble()) }}"
            } else {
                holder.priceTag.text = ""
            }
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            format.timeZone = TimeZone.getTimeZone("UTC")

            val dealStartTimeDate: Date = format.parse(userData.serverTime,)
            val dealEndTimeDate: Date = format.parse(userData.dealEndTime)

            val startMillis: Long = dealStartTimeDate.time //get the start time in milliseconds
            val endMillis: Long = dealEndTimeDate.time  //get the end time in milliseconds
            val totalMillis = endMillis - startMillis //total time in milliseconds

            holder.countDownTimer?.cancel()

            holder.countDownTimer = object : CountDownTimer(totalMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    var millisUntilFinished = millisUntilFinished
                    val days: Long = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
                    millisUntilFinished -= TimeUnit.DAYS.toMillis(days)
                    val hours: Long = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                    millisUntilFinished -= TimeUnit.HOURS.toMillis(hours)
                    val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                    millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes)
                    val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                    holder.expTime.text = "${days}D:${hours}H:${minutes}M:${seconds}S"
                }

                override fun onFinish() {
                    holder.expTime.text = "Expired!"
                }
            }.start()


            holder.viewProductView.setSafeOnClickListener {
                if (userData.expired) {
                    Toast.makeText(context, "Deal Expired", Toast.LENGTH_SHORT).show()
                } else {
                    serviceDealsForCustomer.serviceDealClick(
                        userData.dealUserId._id,
                        userData.serviceId?.serviceName!!,
                        userData.dealPrice.toDouble(),
                        userData.dealUserId.firstName,
                        userData.dealUserId.lastName,
                        userData.serviceCategoryId.categoryName,
                        userData.serviceCategoryId.categoryImage,
                        userData.serviceSubCategoryId.subCategoryName,
                        userData.serviceId._id,
                        userData._id,
                        holder.priceTag.text.toString(),
                        holder.originalPrice.text.toString(),
                        holder.offOnProduct.text.toString()
                    )
                }
            }


            holder.priceTag.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun getItemCount(): Int {
        return if (itemList.size > 10) {
            10
        } else {
            itemList.size
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var productName:TextView = itemView.findViewById(R.id.productName)
        var priceTag:TextView = itemView.findViewById(R.id.priceTag)
        var originalPrice:TextView = itemView.findViewById(R.id.originalPrice)
        var expTime:TextView = itemView.findViewById(R.id.expTime)
        var offOnProduct:TextView = itemView.findViewById(R.id.offOnProduct)
        var productImage:ImageView = itemView.findViewById(R.id.productImage)
        var viewProductView:LinearLayout = itemView.findViewById(R.id.viewProductView)
        var countDownTimer: CountDownTimer? = null

    }


    fun calculateDealDiscount(actualPrice: Double, dealPrice: Double): String {
        val discount = actualPrice - dealPrice
        val discountPercentage = (discount / actualPrice) * 100

        // Format the discount percentage to two decimal places
        val decimalFormat = DecimalFormat("0")
        val formattedDiscountPercentage = decimalFormat.format(discountPercentage)

        return "$formattedDiscountPercentage"
    }
}