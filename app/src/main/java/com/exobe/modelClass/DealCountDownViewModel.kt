package com.exobe.modelClass


import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone
import java.util.concurrent.TimeUnit

class DealCountDownViewModel : ViewModel() {

    private val countdownMap: MutableMap<Int, CountDownTimer?> = mutableMapOf()
    val countdownLiveData: MutableLiveData<String> = MutableLiveData()

    fun startCountdown(startTime: String, endTime: String, adapterPosition: Int) {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        format.timeZone = TimeZone.getTimeZone("UTC")

        val startTimeDate: Date = format.parse(startTime)
        val endTimeDate: Date = format.parse(endTime)

        val startMillis: Long = startTimeDate.time // get the start time in milliseconds
        val endMillis: Long = endTimeDate.time // get the end time in milliseconds

        val totalMillis = endMillis - startMillis // total time in milliseconds

        countdownMap[adapterPosition]?.cancel()

        countdownMap[adapterPosition] = object : CountDownTimer(totalMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
                val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60

                val countdownText = String.format("%02dD:%02dH:%02dM:%02dS", days, hours, minutes, seconds)
                countdownLiveData.postValue(countdownText)
            }

            override fun onFinish() {
                countdownLiveData.postValue("Expired!")
            }
        }.start()
    }

    fun stopCountdown(adapterPosition: Int) {
        countdownMap[adapterPosition]?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        for (countdownTimer in countdownMap.values) {
            countdownTimer?.cancel()
        }
        countdownMap.clear()
    }
}
