package com.exobe.adaptor

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.exobe.R
import com.exobe.databinding.OrderTrackingLayoutBinding
import com.exobe.entity.response.OrderTracking
import kotlinx.coroutines.*


class OrderTrackingAdapter(val context: Context, val itemList: ArrayList<OrderTracking>, val status:String) :RecyclerView.Adapter<OrderTrackingAdapter.LastOrderViewHolder>() {

    private var job: Job? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastOrderViewHolder {
        val binding = OrderTrackingLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return LastOrderViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor", "ResourceType")
    override fun onBindViewHolder(holder: LastOrderViewHolder, position: Int) {
        try {
            itemList[position].apply {




                val itemListLastTrue =  itemList.filter { it.orderStatus }

                if (orderStatus){
                    holder.binding.progressLine.setProgress(progressValue)

                    if (itemListLastTrue.size == position + 1 && status != "DELIVERED"){
                        startAnimationOn(holder.binding.img)
                        Glide.with(context).load(R.drawable.paused_icon_tracking).into(holder.binding.img)
                    }else{
                        Glide.with(context).load(R.drawable.filled_icon_tracking).into(holder.binding.img)

                    }


                }else{
                    Glide.with(context).load(R.drawable.ic_baseline_greycircle_24).into(holder.binding.img)
                }



                if (itemList.size -1 == position){
                    holder.binding.progressLine.isVisible = false

                }



                holder.binding.itemHeader.text = orderType
                holder.binding.itemDesc.text = orderDescription
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class LastOrderViewHolder(val binding : OrderTrackingLayoutBinding) : RecyclerView.ViewHolder(binding.root)


    private fun startAnimationOn(img: ImageView) {
        val pulseAnimator = ValueAnimator.ofFloat(0.8f, 1f, 0.8f)
        pulseAnimator.duration = 1000 // Duration for one complete pulse cycle
        pulseAnimator.interpolator = AccelerateDecelerateInterpolator()
        pulseAnimator.repeatCount = ValueAnimator.INFINITE // Infinite repeat
        pulseAnimator.addUpdateListener { animation ->
            val scale = animation.animatedValue as Float
            img.scaleX = scale
            img.scaleY = scale
        }
        pulseAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
                // Animation ended
            }
        })
        pulseAnimator.start()
    }


}

