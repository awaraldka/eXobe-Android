package com.exobe.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.modelClass.SelectDocumentData
import com.exobe.customClicks.SelectDocumentClick
import com.exobe.databinding.SelectDocumentBinding

class SelectDocumentAdapter(val context:Context, private val documentData:ArrayList<SelectDocumentData>, val commonClick:SelectDocumentClick):RecyclerView.Adapter<SelectDocumentAdapter.ViewHolder>() {

    inner class ViewHolder(val binding:SelectDocumentBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SelectDocumentBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectDocumentAdapter.ViewHolder, position: Int) {
       try {
           documentData[position].apply {
               holder.binding.viewName.text = name
               holder.binding.COIChooseFilePath.text = pathName

               holder.binding.COIDeleteIcon.isVisible = showDeleteIcon

               holder.binding.documentId.setSafeOnClickListener {
                   commonClick.selectDocument(position,name)
               }

               holder.binding.COIDeleteIcon.setSafeOnClickListener {
                   commonClick.deleteDocument(position,name)
               }


           }



       }catch (e:Exception){
           e.printStackTrace()
       }
    }

    override fun getItemCount(): Int {
       return documentData.size
    }
}