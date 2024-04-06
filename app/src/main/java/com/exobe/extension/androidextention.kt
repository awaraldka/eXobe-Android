package com.exobe

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.FragmentManager
import com.airbnb.lottie.LottieAnimationView
import com.example.validationpractice.utlis.FormValidation.runOnUiThread
import com.exobe.activities.GenericKeyEvent
import com.exobe.activities.GenericTextWatcher
import com.exobe.utils.CommonFunctions
import com.exobe.utils.SavedPrefManager
import com.exobe.customClicks.DeleteAccount
import com.exobe.customClicks.NavigationClick


object androidextention {

    var dialogProgress: ProgressDialog? = null
    var toast: Toast? = null

    fun deleteAccount(context: Context,click:DeleteAccount) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.delete_account_layout)
        val btnSave = dialog.findViewById<TextView>(R.id.btnSave)
        val btnCancel = dialog.findViewById<TextView>(R.id.btnCancel)

        btnSave.setOnClickListener {
            click.deleteAccount()
            dialog.dismiss()
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
        val window = dialog.window
        window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    fun showDialog(title: String, context: Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.product_enquiry_alert_box)
        val yesBtn = dialog.findViewById<ImageView>(R.id.imgCancel)
        val bodyTitle = dialog.findViewById<TextView>(R.id.txtAlertTitle)
        bodyTitle.text = title
        yesBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
        val window = dialog.window
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun logOutDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.log_out_alert_box)
        val yesBtn = dialog.findViewById<Button>(R.id.btnSave)
        yesBtn.setOnClickListener {
            SavedPrefManager.saveStringPreferences(context, SavedPrefManager.isLogin, "false")
            SavedPrefManager.saveStringPreferences(context, SavedPrefManager.USER_NAME,"Guest")
            SavedPrefManager.saveStringPreferences(context, SavedPrefManager.USER_PROFILE,"")
            CommonFunctions.logoutApi(context)
            dialog.dismiss()


        }
        val noBtn = dialog.findViewById<Button>(R.id.btnCancel)
        noBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
        val window = dialog.window
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

    fun switchRoleDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.switch_alert_box)
        val yesBtn = dialog.findViewById<Button>(R.id.btnSave)
        yesBtn.setOnClickListener {
            CommonFunctions.logoutApi(context)
            dialog.dismiss()


        }
        val noBtn = dialog.findViewById<Button>(R.id.btnCancel)
        noBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
        val window = dialog.window
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

    fun serviceRequestSuccessful(context: Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.service_req_successfully_alert_box)
        val yesBtn = dialog.findViewById<ImageView>(R.id.imageSerReqCancel)
        yesBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
        val window = dialog.window
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun notificationDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_acceptservises)
        val yesBtn = dialog.findViewById<Button>(R.id.ForgotSubmit)
//        val bodyTitle = dialog.findViewById<TextView>(R.id.txtAlertTitle)
//        bodyTitle.text = title

        val ResendCode = dialog.findViewById(R.id.ResendCode) as TextView
        val textView = dialog.findViewById(R.id.textView) as TextView
        val et1 = dialog.findViewById(R.id.et_1) as EditText
        val et2 = dialog.findViewById(R.id.et_2) as EditText
        val et3 = dialog.findViewById(R.id.et_3) as EditText
        val et4 = dialog.findViewById(R.id.et_4) as EditText

        et1.addTextChangedListener(GenericTextWatcher(et1, et2))
        et2.addTextChangedListener(GenericTextWatcher(et2, et3))
        et3.addTextChangedListener(GenericTextWatcher(et3, et4))
        et4.addTextChangedListener(GenericTextWatcher(et4, null))


        et1.setOnKeyListener(GenericKeyEvent(et1, null))
        et2.setOnKeyListener(GenericKeyEvent(et2, et1))
        et3.setOnKeyListener(GenericKeyEvent(et3, et2))
        et4.setOnKeyListener(GenericKeyEvent(et4, et3))

        val countdownTimeInMillis = 1 * 60 * 1000 // 1 minute in milliseconds

        object : CountDownTimer(countdownTimeInMillis.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                val formattedTime = String.format("%02d:%02d", minutes, seconds)
                // Update your UI with the formattedTime
                textView.text = formattedTime
            }

            override fun onFinish() {
                // Timer has finished, implement your actions here
                textView.text = "00:00"
                // Do something when the timer finishes, like enabling a button or showing a message.
            }
        }.start()


        yesBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
        val window = dialog.window
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        )
//        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun notificationserviceproviderDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_after_accepting)
        val et1 = dialog.findViewById(R.id.namepopup) as TextView
        val et2 = dialog.findViewById(R.id.Contactpopup) as TextView
        val et3 = dialog.findViewById(R.id.mentionchargespopup) as TextView
        val et4 = dialog.findViewById(R.id.descriptionpopup) as EditText
        val SendBtn = dialog.findViewById<Button>(R.id.btnSend)


        et1.addTextChangedListener(GenericTextWatcher(et1, et2))
        et2.addTextChangedListener(GenericTextWatcher(et2, et3))
        et3.addTextChangedListener(GenericTextWatcher(et3, et4))
        et4.addTextChangedListener(GenericTextWatcher(et4, null))

        SendBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
        val window = dialog.window
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        )
//        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    fun rateThisApp(context: Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.rate_this_app)
        val yesBtn = dialog.findViewById<Button>(R.id.btnSave)
//            val bodyTitle = dialog.findViewById<TextView>(R.id.txtAlertTitle)
//            bodyTitle.text = title
        yesBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
        val window = dialog.window
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun showProgressDialog(context: Context?) {
        if (context != null) {
            try {
                dialogProgress?.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        dialogProgress = ProgressDialog(context, R.style.ProgressDialogStyle)
        if (dialogProgress!!.getWindow() != null) {
            dialogProgress!!.getWindow()!!.setLayout(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        dialogProgress!!.setMessage("Searching for delivery driver...")
        if (!dialogProgress!!.isShowing) {
            try {
                dialogProgress?.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        dialogProgress!!.setCancelable(false)
    }

    fun isOnline(context: Context): Boolean {
        val conMgr = context!!
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        return !(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable)
    }

    fun disMissProgressDialog(mContext: Context?) {
        try {
            if (dialogProgress != null) {
                dialogProgress?.dismiss()
                dialogProgress = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun alertBox(message: String, context: Context) {
        runOnUiThread{
            var alertDialog: AlertDialog? = null
            var builder = AlertDialog.Builder(context)
            builder.setIcon(R.mipmap.ic_launcher)
            builder.setTitle("eXobe")
            builder.setMessage(message)
            builder.setPositiveButton("ok") { dialogInterface, which ->
                alertDialog!!.dismiss()
                builder = AlertDialog.Builder(context, R.style.ProgressDialogStyle)

            }
            alertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

    }

    fun alertBoxCommon(message: String, context: Context) {
        runOnUiThread {
            val alertDialogBuilder = AlertDialog.Builder(context)
            val dialogView = LayoutInflater.from(context).inflate(R.layout.common_alert_box, null)
            val messageTextView = dialogView.findViewById<TextView>(R.id.message)
            val okButton = dialogView.findViewById<TextView>(R.id.CLickOk)

            messageTextView.text = message

            val alertDialog = alertDialogBuilder.setView(dialogView).create()
            alertDialog.setCancelable(false)

            okButton.setOnClickListener {
                alertDialog.dismiss()
            }

            alertDialog.show()
            val window = alertDialog.window
            window?.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }


    fun LottieAnimationView.initLoader(isLoading: Boolean) {
        runOnUiThread {
            if (isLoading) {
                playAnimation()
                visibility = View.VISIBLE
            } else {
                pauseAnimation()
                animation?.reset()
                visibility = View.GONE
            }
        }
    }



    fun alertBoxNavigation(message: String, context: Context, click:NavigationClick) {
        runOnUiThread{
            var alertDialog: AlertDialog? = null
            var builder = AlertDialog.Builder(context)
            builder.setMessage(message)
            builder.setIcon(R.mipmap.ic_launcher)
            builder.setTitle("eXobe")
            builder.setPositiveButton("Navigate to map") { _, _ ->
                alertDialog!!.dismiss()
                click.navigateToMap()

            }
            builder.setNegativeButton("Cancel") { _, which ->
                alertDialog!!.dismiss()
                click.navigateToHome()
                builder = AlertDialog.Builder(context, R.style.ProgressDialogStyle)

            }
            alertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

    }



    fun alertBoxUploadDocument(message: String, context: Context, click:NavigationClick) {
        runOnUiThread{
            var alertDialog: AlertDialog? = null
            var builder = AlertDialog.Builder(context)
            builder.setMessage(message)
            builder.setIcon(R.mipmap.ic_launcher)
            builder.setTitle("eXobe")
            builder.setPositiveButton("Upload Document") { _, _ ->
                alertDialog!!.dismiss()
                click.navigateToMap()

            }
            builder.setNegativeButton("Later") { _, which ->
                alertDialog!!.dismiss()
                click.navigateToHome()
                builder = AlertDialog.Builder(context, R.style.ProgressDialogStyle)

            }
            alertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

    }



    fun alertBoxFinish(
        message: String?,
        context: Context,
        activity: Activity
    ) {
        runOnUiThread{
            var alertDialog: AlertDialog? = null
            var builder = AlertDialog.Builder(context)
            builder.setMessage(message)
            builder.setIcon(R.mipmap.ic_launcher)
            builder.setTitle("eXobe")
            builder.setPositiveButton("ok") { dialogInterface, which ->
                alertDialog!!.dismiss()
                activity.finishAfterTransition()
                builder = AlertDialog.Builder(context, R.style.ProgressDialogStyle)

            }
            alertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

    }
    fun alertBoxFragmentBack(
        message: String?,
        context: Context,
        parentFragmentManager: FragmentManager
    ) {
        runOnUiThread{
            var alertDialog: AlertDialog? = null
            var builder = AlertDialog.Builder(context)
            builder.setMessage(message)
            builder.setIcon(R.mipmap.ic_launcher)
            builder.setTitle("eXobe")
            builder.setPositiveButton("ok") { dialogInterface, which ->
                alertDialog!!.dismiss()
                parentFragmentManager.popBackStack()
                builder = AlertDialog.Builder(context, R.style.ProgressDialogStyle)

            }
            alertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

    }






}