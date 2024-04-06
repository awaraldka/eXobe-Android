package com.exobe.validations

import android.app.TimePickerDialog
import android.content.Context
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import com.exobe.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object TimeClass {
    var startTimeValid = ""

    fun checkDates(d1: String, d2: String): Boolean {
        val dfDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        var b = false
        try {
            b = if (dfDate.parse(d1)?.before(dfDate.parse(d2)) == true) {
                true // If start date is before end date
            } else dfDate.parse(d1)?.equals(dfDate.parse(d2)) == true
        } catch (e: ParseException) {
            // TODO: Handle the exception
            e.printStackTrace()
        }
        return b
    }

    fun showTimePickerDialog(
        context: Context,
        textView: TextView,
        startDate: TextView,
        endDate: TextView
    ) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val is24HourFormat = false

        val timePickerDialog = TimePickerDialog(
            context,
            R.style.DatePickerTheme,
            { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
                val displayHour = if (selectedHour > 12) selectedHour - 12 else selectedHour
                val amPm = if (selectedHour >= 12) "PM" else "AM"
                val time = String.format(Locale.getDefault(), "%02d:%02d %s", displayHour, selectedMinute, amPm)

                if (textView.id == R.id.start_time || textView.id == R.id.startTimeTv) {
                    startTimeValid = time
                    textView.text = time
                } else if (textView.id == R.id.end_time || textView.id == R.id.endTimeTv) {
                    val openTime = parseTime(startTimeValid)
                    val endTime = parseTime(time)
                    if (openTime != null && endTime != null && checkDates(endDate.text.toString(), startDate.text.toString()) && endTime.before(openTime)) {
                        Toast.makeText(context, "End time cannot be before start time", Toast.LENGTH_SHORT).show()
                        textView.text = ""
                    } else {
                        textView.text = time
                    }
                }
            },
            hour,
            minute,
            is24HourFormat
        )

        timePickerDialog.show()
    }

    private fun parseTime(timeString: String): Date? {
        val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return try {
            format.parse(timeString)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    fun validateFields(
        textstartdate: TextView, start_time: TextView, start_time_ll: RelativeLayout,
        textenddate: TextView, end_time: TextView, end_time_ll: RelativeLayout
    ) {
        if (textstartdate.text.isNotEmpty() && start_time.text.isEmpty()) {
            start_time_ll.setBackgroundResource(R.drawable.input_error)
        } else {
            start_time_ll.setBackgroundResource(R.drawable.delivery_background)
        }

        if (textenddate.text.isNotEmpty() && end_time.text.isEmpty()) {
            end_time_ll.setBackgroundResource(R.drawable.input_error)
        } else {
            end_time_ll.setBackgroundResource(R.drawable.delivery_background)
        }
    }
}
