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
import com.exobe.customClicks.DealsForCustomer
import com.exobe.entity.response.product.DealBannerDocs
import com.exobe.util.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class HomeProductDealsAdaptor(
    val context: Context,
    val itemList: ArrayList<DealBannerDocs>,
    var flag: String,
    var dealsForCustomer: DealsForCustomer,
) : RecyclerView.Adapter<HomeProductDealsAdaptor.ViewHolder>() {

    var flag1 = false

    var countDownTimer: CountDownTimer? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {  //deal_for_customer_modallayout
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.see_all_deal_customer_layout_new, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val userData = itemList[position]

            holder.productName.text = userData.productId.getOrNull(0)!!.productName.capitalizeFirstLetter()
            Glide.with(context).load(userData.productId.getOrNull(0)!!.productImage[0]).thumbnail(0.25f)
                .placeholder(R.drawable.dummyproduct).into(holder.productImage)
            if(userData.dealDetails?.get(0)?.dealPrice != null) {
                holder.originalPrice.text = "R ${userData.dealDetails[0].dealPrice?.toDouble()?.let { CommonFunctions.currencyFormatter(it) }}"
            } else {
                holder.originalPrice.text = ""
            }


            holder.offOnProduct.text = "${CommonFunctions.formatPercentage(userData.dealDiscount.toDouble())} Off"


            if(userData.dealDetails?.get(0)?.price != null) {
                holder.priceTag.text = "R ${userData.dealDetails[0].price?.let { CommonFunctions.currencyFormatter(it.toDouble()) }}"
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

            /*val (formattedStartDate, formattedStartTime) = DateFormat.dateForCountdown(userData.serverTime)
            val (formattedEndDate, formattedEndTime) = DateFormat.dateForCountdown(userData.dealEndTime)

            val currentDate = LocalDate.now()
            val formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val compareCurrentDate =  currentDate.format(formatterDate)

            val currentTime = LocalTime.now()
            val formatter = DateTimeFormatter.ofPattern("hh:mm:ss a")
            val finalFormattedTime = currentTime.format(formatter)
            val formatterTime = DateTimeFormatter.ofPattern("hh:mm:ss a")
            val compareCurrentTime = currentTime.format(formatterTime).uppercase()

            mangeTimeForDeal(formattedStartTime,compareCurrentTime,formattedEndDate,formattedEndTime,holder)*/




            holder.viewProductView.setSafeOnClickListener {
                if (userData.expired) {
                    Toast.makeText(context, "Deal Expired", Toast.LENGTH_SHORT).show()
                } else {
                    dealsForCustomer.click("", false, userData._id!!, userData.productId.get(0)._id)
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



    fun mangeTimeForDeal(
        formattedStartTime: String,
        compareCurrentTime: String,
        formattedEndDate: String,
        formattedEndTime: String,
        holder: ViewHolder
    ) {
        val sdf = SimpleDateFormat("hh:mm:ss a", Locale.US)

        try {
            val startTime = sdf.parse(formattedStartTime)
            val currentTime = sdf.parse(compareCurrentTime)
            if (startTime != null && currentTime != null) {
                val comparisonResult = currentTime.compareTo(startTime)
                if (comparisonResult >= 0) {
                    val convert12HourTo24Hour = DateFormat.convert12HourTo24Hour(formattedEndTime)
                    val remainingTimeMillis = calculateRemainingTime(convert12HourTo24Hour)

                    val days = TimeUnit.MILLISECONDS.toDays(remainingTimeMillis)
                    val hours = TimeUnit.MILLISECONDS.toHours(remainingTimeMillis) % 24
                    val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTimeMillis) % 60
                    val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTimeMillis) % 60

                    val formattedTime = String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds)
                    holder.expTime.text = formattedTime
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    private fun calculateRemainingTime(targetTime: String): Long {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        // Get the current time
        val currentTime = Calendar.getInstance()
        val currentDateString = sdf.format(currentTime.time)
        val currentDate = sdf.parse(currentDateString)

        // Parse the target time
        val targetDate = sdf.parse(targetTime)

        // Calculate the remaining time
        val remainingTime = targetDate!!.time - currentDate!!.time

        return if (remainingTime >= 0) remainingTime else 0
    }







}