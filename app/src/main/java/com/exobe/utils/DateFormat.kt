package com.exobe.util

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

class DateFormat {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SimpleDateFormat")
        fun getDateOfhourminute(date: String?): String? {
            var result = ""

            val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            sourceFormat.timeZone = TimeZone.getTimeZone("UTC")
            var parsed: Date? = null // => Date is in UTC now
            parsed = try {
                sourceFormat.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }
            val destFormat = SimpleDateFormat("yyyy-MM-dd, h:mm aa")
            destFormat.timeZone = TimeZone.getDefault()
            result = destFormat.format(parsed)
            return result
        }


        fun getDealDate(date: String?): String? {
            var result = ""

            val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            sourceFormat.timeZone = TimeZone.getTimeZone("UTC")
            var parsed: Date? = null // => Date is in UTC now
            parsed = try {
                sourceFormat.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }
            val destFormat = SimpleDateFormat("yyyy-MM-dd")
            destFormat.timeZone = TimeZone.getDefault()
            result = destFormat.format(parsed)
            return result
        }


        fun exobeAllScreenDateFormat(date: String?): String? {
            var result = ""

            val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            sourceFormat.timeZone = TimeZone.getTimeZone("UTC")
            var parsed: Date? = null // => Date is in UTC now
            parsed = try {
                sourceFormat.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }
            val destFormat = SimpleDateFormat("MMM dd, yyyy")
            destFormat.timeZone = TimeZone.getDefault()
            result = destFormat.format(parsed)
            return result
        }


        fun dealsdate(date: String?): String? {
            var result = ""

            val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            sourceFormat.timeZone = TimeZone.getTimeZone("UTC")
            var parsed: Date? = null // => Date is in UTC now
            parsed = try {
                sourceFormat.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }
            val destFormat = SimpleDateFormat("yyyy-MM-dd")
            destFormat.timeZone = TimeZone.getDefault()
            result = destFormat.format(parsed)
            return result
        }


        fun NotificationDate(date: String?): String? {
            var result = ""
            val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            sourceFormat.timeZone = TimeZone.getTimeZone("UTC")
            var parsed: Date? = null // => Date is in UTC now
            parsed = try {
                sourceFormat.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }
            val destFormat = SimpleDateFormat("yyyy-MM-dd, hh:mm aa")
            destFormat.timeZone = TimeZone.getDefault()
            result = destFormat.format(parsed)
            return result
        }

        fun deliveryDateFormat(date: String?): String? {
            var result = ""
            val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            sourceFormat.timeZone = TimeZone.getTimeZone("UTC")
            var parsed: Date? = null // => Date is in UTC now
            parsed = try {
                sourceFormat.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }
            val destFormat = SimpleDateFormat("yyyy-MM-dd")
            destFormat.timeZone = TimeZone.getDefault()
            result = destFormat.format(parsed)
            return result
        }


        fun dealstime(date: String?): String? {
            var result = ""

            val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            sourceFormat.timeZone = TimeZone.getTimeZone("UTC")
            var parsed: Date? = null // => Date is in UTC now
            parsed = try {
                sourceFormat.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }
            val destFormat = SimpleDateFormat("hh:mm:ss aa")
            destFormat.timeZone = TimeZone.getDefault()
            result = destFormat.format(parsed)
            return result
        }

        fun showDealTime(date: String?): String? {
            var result = ""

            val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            sourceFormat.timeZone = TimeZone.getTimeZone("UTC")
            var parsed: Date? = null // => Date is in UTC now
            parsed = try {
                sourceFormat.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }

            val destFormat = SimpleDateFormat("hh:mm a")
            destFormat.timeZone = TimeZone.getDefault()
            result = destFormat.format(parsed)
            return result
        }


        @RequiresApi(Build.VERSION_CODES.O)
        fun covertTimeOtherFormat(dataDate: String?): String? {
            val yyyy_MM_dd_HH_mm = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()
            )
            val fullDateTime = SimpleDateFormat(
                "yyyy-MM-dd, hh:mm aa", Locale.getDefault()
            )
            val HHmm = SimpleDateFormat(
                "hh:mm aa",
                Locale.getDefault()
            ) /*from w  w  w .  j a  v  a  2s .  co m*/
            val MM_dd_HHmm = SimpleDateFormat(
                "MM-dd HH:mm", Locale.getDefault()
            )
            yyyy_MM_dd_HH_mm.timeZone = TimeZone.getTimeZone("UTC")
            var convertDate = yyyy_MM_dd_HH_mm.parse(dataDate) as Date
            val newTime = Date()
            try {
                val cal = Calendar.getInstance()
                cal.time = newTime
                val oldCal = Calendar.getInstance()
                oldCal.time = convertDate
                val oldYear = oldCal[Calendar.YEAR]
                val year = cal[Calendar.YEAR]
                val oldDay = oldCal[Calendar.DAY_OF_YEAR]
                val day = cal[Calendar.DAY_OF_YEAR]
                if (oldYear == year) {
                    val value = oldDay - day
                    return if (value == -1) {
                        "yesterday, " + HHmm.format(convertDate)
                    } else if (value == 0) {
                        "today, " + HHmm.format(convertDate)
                    } else if (value == 1) {
                        "tomorrow, " + HHmm.format(convertDate)
                    } else {
                        fullDateTime.format(convertDate)
                    }
                }
            } catch (e: java.lang.Exception) {
            }
            return fullDateTime.format(convertDate)
        }


        fun dealTimeFormatter(date: String?): String? {
            var result = ""

            val sourceFormat = SimpleDateFormat("yyyy-MM-dd")
            sourceFormat.timeZone = TimeZone.getTimeZone("UTC")
            var parsed: Date? = null // => Date is in UTC now
            parsed = try {
                sourceFormat.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }
            val destFormat = SimpleDateFormat("yyyy-MM-dd")
            destFormat.timeZone = TimeZone.getDefault()
            result = destFormat.format(parsed)
            return result
        }


        fun expectedDate(date: String, days: Int): String {

            var finalDate = ""
            var sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val c = Calendar.getInstance()
            c.time = sdf.parse(date)
            c.add(Calendar.DATE, days)
            sdf = SimpleDateFormat("yyyy-MM-dd")
            val resultdate = Date(c.timeInMillis)
            finalDate = sdf.format(resultdate)
            return finalDate
        }


        fun createDealTimeToIos(time: String?): String? {
            var result = ""

            val sourceFormat = SimpleDateFormat("yyyy-MM-dd hh:mm a")
//            sourceFormat.timeZone = TimeZone.getTimeZone("UTC")
            var parsed: Date? = null // => Date is in UTC now
            parsed = try {
                sourceFormat.parse(time)
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }
            val destFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            destFormat.timeZone = TimeZone.getTimeZone("UTC")
            result = destFormat.format(parsed)
            return result
        }


        fun serviceDateFormatter(date: String): String {
            var result = ""
            val sourceFormat = SimpleDateFormat("dd-MM-yyyy")
            var parsed: Date? = null // => Date is in UTC now
            parsed = try {
                sourceFormat.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }
            val destFormat = SimpleDateFormat("dd-MM-yyyy")
            result = destFormat.format(parsed)
            return result
        }


        @SuppressLint("SimpleDateFormat")
        fun deliveryExpectedDateNew(dateRequest: String, days: Int): String {

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val outputFormat = SimpleDateFormat("yyyy-MM-dd")
            outputFormat.timeZone = TimeZone.getDefault()

            val date = inputFormat.parse(dateRequest)!!

            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.DAY_OF_YEAR, days)

            val futureDate = calendar.time
            val formattedDate: String = outputFormat.format(futureDate)

            println(formattedDate)


            return formattedDate
        }


        @SuppressLint("SimpleDateFormat")
        fun getDateAndTime(inputDateString: String): String {

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")

            val outputFormat = SimpleDateFormat("yyyy-MM-dd,hh:mm a")

            val date: Date = inputFormat.parse(inputDateString)!!

            return outputFormat.format(date)

        }


        @RequiresApi(Build.VERSION_CODES.O)
        fun formatToISOString(dateString: String, timeString: String): String? {
            try {
                val inputDateFormat = DateTimeFormatter.ofPattern("yyyy-M-d", Locale.US)
                val inputTimeFormat = DateTimeFormatter.ofPattern("hh:mm a", Locale.US)
                val outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")

                val date = LocalDate.parse(dateString, inputDateFormat)
                val time = LocalTime.parse(timeString, inputTimeFormat)

                val dateTime = LocalDateTime.of(date, time)
                val offsetDateTime = OffsetDateTime.of(dateTime, ZoneOffset.UTC)

                return outputFormat.format(offsetDateTime)
            } catch (e: DateTimeParseException) {
                e.printStackTrace()
                return null
            }
        }


        @RequiresApi(Build.VERSION_CODES.O)
        fun formatDateTime(isoTimestamp: String): Pair<String, String>? {
            return try {
                val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                val outputDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val outputTimeFormat = DateTimeFormatter.ofPattern("hh:mm a")

                val dateTime = LocalDateTime.parse(isoTimestamp, inputFormat)
                val formattedDate = outputDateFormat.format(dateTime)
                val formattedTime = outputTimeFormat.format(dateTime)

                Pair(formattedDate, formattedTime.uppercase())
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }


        @RequiresApi(Build.VERSION_CODES.O)
        fun convertAndSubtractTime(utcTimestamp: String): String {
            val instant = Instant.parse(utcTimestamp)

            // Convert to ZonedDateTime in UTC
            val originalDateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"))

            // Subtract 5 hours and 30 minutes
            val modifiedDateTime = originalDateTime.minusHours(5).minusMinutes(30)

            // Format the result
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            return formatter.format(modifiedDateTime)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun convertAndAddTime(utcTimestamp: String): String {
            val instant = Instant.parse(utcTimestamp)

            // Convert to ZonedDateTime in UTC
            val originalDateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"))

            // Subtract 5 hours and 30 minutes
            val modifiedDateTime = originalDateTime.plusHours(5).plusMinutes(30)

            // Format the result
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            return formatter.format(modifiedDateTime)
        }


        fun calculateRemainingTime(targetTime: String): Long {
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


        @SuppressLint("SimpleDateFormat")
        @RequiresApi(Build.VERSION_CODES.O)
        fun dateForCountdown(date: String): Pair<String, String> {
            try {
                val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                df.timeZone = TimeZone.getTimeZone("UTC")

                val dateObj = df.parse(date)!!

                val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                val timeFormatter = SimpleDateFormat("hh:mm:ss a", Locale.ENGLISH)

                val formattedDate = dateFormatter.format(dateObj)
                val formattedTime = timeFormatter.format(dateObj)

                return Pair(formattedDate, formattedTime)
            }catch (e:Exception){
                e.printStackTrace()
            }
            return  Pair("","")
        }


        fun convert12HourTo24Hour(time12Hour: String): String {
            val inputFormat = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())
            val outputFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

            try {
                val date = inputFormat.parse(time12Hour)
                return outputFormat.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return "" // Return an empty string or handle the error as needed
        }



    }


}