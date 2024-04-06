package com.exobe.Adapter

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.*
import com.exobe.activities.GenericKeyEvent
import com.exobe.activities.GenericTextWatcher
import com.exobe.Model.AcceptedModelClass

class AcceptedAdapter (var context: Context, var data: ArrayList<AcceptedModelClass>)
    : RecyclerView.Adapter<AcceptedAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var client = view.findViewById<TextView>(R.id.client)
        var service = view.findViewById<TextView>(R.id.service)
        var address = view.findViewById<TextView>(R.id.address)
        var completebtn = view.findViewById<Button>(R.id.completebtn)

        }

//    override fun alertbox(context: Context) {
//        super.alertbox(context)
//    }
//    fun alertbox() {
//        val builder = AlertDialog.Builder(context).create()
//        val dialogLayout = LayoutInflater.from(context).inflate(R.layout.dialog_acceptservises, null)
//        builder.setView(dialogLayout)
//
//        builder.show()
//
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.accepted_recycler, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val userData = data[position]

            holder.client.text = userData.client
            holder.service.text = userData.service
            holder.address.text = userData.address

            holder.completebtn.setSafeOnClickListener {

                androidextention.notificationDialog(context)
            }

        } catch (e : Exception) {
            e.printStackTrace()
        }


    }

    override fun getItemCount(): Int {
        return data.size
    }



    private fun ForgotPassword() {
        val dialog = this.let { Dialog(context) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_acceptservises)

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


        ResendCode.setSafeOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }


}