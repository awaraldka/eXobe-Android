package com.exobe.adaptor

import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
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
import com.exobe.customClicks.ViewServiceDealListener
import com.exobe.entity.response.Customerdeals_Result
import com.exobe.entity.response.SubCategoryView_SubCategoryDetails
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class subservice_adaptor(

    val context: Context,
    val itemList: ArrayList<Customerdeals_Result>,
    var flag: String,
    var viewServiceDealListener: ViewServiceDealListener,
) : RecyclerView.Adapter<subservice_adaptor.ViewHolder>() {
    var dataSplit: ArrayList<String> = ArrayList()
    var timeSplit: ArrayList<String> = ArrayList()
    var year: Int = 0
    var month: Int = 0
    var day: Int = 0
    var second: Int = 0
    var minuts: Int = 0
    var hours: Int = 0
    private val handler = Handler(Looper.getMainLooper())

    //    lateinit var EXPTimeIn: TextView
    var data: ArrayList<SubCategoryView_SubCategoryDetails> = ArrayList()

    var flag1 = false
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): subservice_adaptor.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.see_all_deal_customer_layout_new, parent, false)
//        EXPTimeIn = view.findViewById(R.id.EXPTimeIn)

        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: subservice_adaptor.ViewHolder, position: Int) {

        try {
            val userData = itemList[position]

            holder.productName.text = userData.serviceId?.serviceName?.capitalizeFirstLetter()
            Glide.with(context).load(userData.dealImage[0]).placeholder(R.drawable.dummyproduct).into(holder.productImage)
            if(userData.dealPrice != null) {
                holder.originalPrice.text = "R ${userData.dealPrice.toDouble()?.let { CommonFunctions.currencyFormatter(it) }}"
            } else {
                holder.originalPrice.text = ""
            }
            holder.offOnProduct.text = "${userData.serviceId?.price?.toDouble()
                ?.let { CommonFunctions.formatPercentage(calculateDealDiscount(it,userData.dealPrice.toDouble()).toDouble()) }} Off"



            if(userData.serviceId?.price != null) {
                holder.priceTag.text = "R ${userData.serviceId?.price?.let { CommonFunctions.currencyFormatter(it.toDouble()) }}"
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
                    if (flag != "service") {
                        viewServiceDealListener.viewServiceDeal(
                            userData._id,
                            userData.serviceCategoryId.categoryName
                        )
                    }
                }
            }


            holder.priceTag.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG



        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
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
