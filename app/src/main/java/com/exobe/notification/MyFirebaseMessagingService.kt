package com.exobe.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.exobe.R
import com.exobe.hostActivity.MainActivity
import com.exobe.utils.SavedPrefManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson


const val channelId = "notification-channel"
const val channelName = "com.eazyalgo.fcmpushnotification"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            var responce: String = remoteMessage.data.toString().replace("[", "").replace("]", "").replace("=", ":")
            val gson = Gson()
            //    val fcmResponse: FCMResponse = gson.fromJson(responce, FCMResponse::class.java)
//        generateNotification(remoteMessage.notification!!.title!!,remoteMessage.notification!!.body!!)


            if (remoteMessage.data != null) {
                Log.d("","remoteMessage>>>>>>>>>>>>>>>>>> ${remoteMessage.data}")
                var value: Any?=null
                for (key in remoteMessage.data.keys) {
                    if (key.equals( "notificationType")) {
                        value = remoteMessage.data[key]

                        Log.d("NotificationTag", key + "____" + value)
                    }

                }
                getRemoteView(value,remoteMessage.data)
            }
        }
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            SavedPrefManager.saveStringPreferences(
                applicationContext,
                SavedPrefManager.KEY_DEVICE_TOKEN,
                token
            )

        })

    }

    @SuppressLint("WrongConstant")
    fun getRemoteView(value: Any?, data: MutableMap<String, String>) {
        var intent: Intent? = null
        var pendingIntent: PendingIntent? = null




        val notifyIntent = Intent(this, MainActivity::class.java)
        notifyIntent.putExtra("notifyType",data["notifyType"])
        notifyIntent.putExtra("productId",data["productId"])
        notifyIntent.putExtra("serviceId",data["serviceId"])
        notifyIntent.putExtra("campainOn",data["campainOn"])
        notifyIntent.putExtra("serviceProvideId",data["serviceProvideId"])
        notifyIntent.putExtra("categoryId",data["categoryId"])
        pendingIntent = PendingIntent.getActivity(applicationContext, 0, notifyIntent, PendingIntent.FLAG_MUTABLE)





        if (value != null) {
            val notifyImage = BitmapFactory.decodeResource(resources, R.drawable.exobe_login)
            val CHANNEL_ID = "my_channel_01"

            val notification: Notification
            var bitmap:Bitmap?=null
            if(data["thumbnails"]==null|| data["thumbnails"].equals(""))
            {
                val futureTarget = Glide.with(this)
                    .asBitmap()
                    .load(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .submit()
                try {
                    bitmap = futureTarget.get()
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
            else
            {
                val futureTarget = Glide.with(this)
                    .asBitmap()
                    .load(data!!["thumbnails"])
                    .placeholder(R.mipmap.ic_launcher)
                    .submit()
                try {
                    bitmap = futureTarget.get()
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }


            if (Build.VERSION.SDK_INT >= 26) {
                //This only needs to be run on Devices on Android O and above
                val mNotificationManager =
                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                val id = "YOUR_CHANNEL_ID"
                val name: CharSequence = "YOUR_CHANNEL NAME" //user visible
                val description = "YOUR_CHANNEL_DESCRIPTION" //user visible
                val importance = NotificationManager.IMPORTANCE_MAX
                @SuppressLint("WrongConstant") val mChannel = NotificationChannel(id, name, importance)
                mChannel.description = description
                mChannel.enableLights(true)
                mChannel.enableVibration(true)
                mChannel.canShowBadge()

                mChannel.setShowBadge(true)
                mChannel.vibrationPattern = longArrayOf(0, 1000)
                mNotificationManager.createNotificationChannel(mChannel)
                notification = Notification.Builder(applicationContext, "YOUR_CHANNEL_ID")
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle( data!!["title"])
                    .setContentText(data!!["body"])
                    .setTicker(getString(R.string.app_name))
                    .setLargeIcon(bitmap)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setOngoing(false).build()

                val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                val notificationId = System.currentTimeMillis().toInt()
                notificationManager.notify(notificationId, notification)

            }
            else {
                val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle(getString(R.string.app_name))
                    .setLargeIcon(bitmap)
                    .setBadgeIconType(R.drawable.ic_launcher)
                    .setContentTitle( data!!["title"])
                    .setContentText(data!!["body"])
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setAutoCancel(true)
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    notificationBuilder.setSmallIcon(R.drawable.ic_launcher)

                } else {
                    notificationBuilder.setSmallIcon(R.drawable.ic_launcher);
                }
                notificationBuilder.setContentIntent(pendingIntent)

                val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                val notificationId = System.currentTimeMillis().toInt()
                notificationManager.notify(notificationId,  notificationBuilder.build())
            }
        }


    }


}

