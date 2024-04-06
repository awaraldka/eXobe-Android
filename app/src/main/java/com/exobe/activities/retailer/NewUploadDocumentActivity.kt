package com.exobe.activities.retailer

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.modelClass.FileUploadModel
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.SavedPrefManager
import com.exobe.utils.TakePictureUtils
import com.exobe.androidextention
import com.exobe.androidextention.alertBox
import com.exobe.bottomSheet.CustomerBottomSheet
import com.exobe.customClicks.UpdateIsLoginListener
import com.exobe.entity.ApiCallBack
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


class NewUploadDocumentActivity : AppCompatActivity(), UpdateIsLoginListener {

    lateinit var COI_Choose_File: TextView
    lateinit var COI_Choose_File_Path: TextView
    lateinit var COI_Delete_icon: ImageView

    lateinit var VAT_Choose_File: TextView
    lateinit var VAT_Choose_File_Path: TextView
    lateinit var VAT_Delete_icon: ImageView


    lateinit var Director_Choose_File: TextView
    lateinit var Director_Choose_File_Path: TextView
    lateinit var Director_Delete_icon: ImageView


    lateinit var Director_id_Choose_File: TextView
    lateinit var Director_id_Choose_File_Path: TextView
    lateinit var Director_id_Delete_icon: ImageView


    lateinit var bankChooseFile: TextView
    lateinit var bankChooseFilePath: TextView
    lateinit var bankDeleteIcon: ImageView

    var flag: String = ""
    var choosePdf = ""
    lateinit var back: ImageView
    lateinit var title: TextView
    lateinit var progressbarUploadDocument: ProgressBar
    lateinit var uploadDocumentButton: Button
    private var documentFile = ArrayList<FileUploadModel>()
    var imageparts: ArrayList<MultipartBody.Part> = ArrayList()
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_upload_document)
        window.attributes.windowAnimations = R.style.Fade
        COI_Choose_File = findViewById(R.id.COI_Choose_File)
        COI_Choose_File_Path = findViewById(R.id.COI_Choose_File_Path)
        COI_Delete_icon = findViewById(R.id.COI_Delete_icon)

        VAT_Choose_File = findViewById(R.id.VAT_Choose_File)
        VAT_Choose_File_Path = findViewById(R.id.VAT_Choose_File_Path)
        VAT_Delete_icon = findViewById(R.id.VAT_Delete_icon)

        Director_Choose_File = findViewById(R.id.Director_Choose_File)
        Director_Choose_File_Path = findViewById(R.id.Director_Choose_File_Path)
        Director_Delete_icon = findViewById(R.id.Director_Delete_icon)

        Director_id_Choose_File = findViewById(R.id.Director_id_Choose_File)
        Director_id_Choose_File_Path = findViewById(R.id.Director_id_Choose_File_Path)
        Director_id_Delete_icon = findViewById(R.id.Director_id_Delete_icon)

        bankChooseFile = findViewById(R.id.Bank_Choose_File)
        bankChooseFilePath = findViewById(R.id.Bank_Choose_File_Path)
        bankDeleteIcon = findViewById(R.id.Bank_Delete_icon)
        back = findViewById(R.id.back)
        title = findViewById(R.id.title)
        progressbarUploadDocument = findViewById(R.id.progressbar_upload_document)
        uploadDocumentButton = findViewById(R.id.upload_document_button)

        if (intent != null) {
            flag = intent.getStringExtra("flag")!!
        }
        intent?.getStringExtra("rejectReason").let {
           val rejectReason =  it
            if(rejectReason != null) {
                alertBox(rejectReason.toString(), this)
            }
        }

        title.text = "Upload Documentation"

        back.setSafeOnClickListener {
            finish()
        }

        uploadDocumentButton.setSafeOnClickListener {
            if (documentFile.size == 0 || documentFile.size < 5) {
                alertBox("Please select all documents.", this)
            } else {
                uploadDocumentsApi()
            }

        }

        COI_Choose_File.setSafeOnClickListener {
            choosePdf = "COI_Choose_File"
            selectPDF()
        }

        VAT_Choose_File.setSafeOnClickListener {
            choosePdf = "VAT_Choose_File"
            selectPDF()
        }

        Director_Choose_File.setSafeOnClickListener {
            choosePdf = "Director_Choose_File"
            selectPDF()
        }

        Director_id_Choose_File.setSafeOnClickListener {
            choosePdf = "Director_id_Choose_File"
            selectPDF()
        }

        bankChooseFile.setSafeOnClickListener {
            choosePdf = "Bank_Choose_File"
            selectPDF()
        }

        COI_Delete_icon.setSafeOnClickListener {
            val fileName = "cirtificationOfIncorporation_pdf.pdf"
            if (documentFile.size > 0) {
                for(i in 0 until  documentFile.size) {
                    if (documentFile[i].fileName == fileName) {
                        documentFile.remove(documentFile[i])
                        COI_Choose_File_Path.text = ""
                        COI_Choose_File.isEnabled = true
                        COI_Delete_icon.visibility = View.GONE
                        break
                    }
                }

            }
        }

        VAT_Delete_icon.setSafeOnClickListener {
            val fileName = "VatRegConfirmationDocs_pdf.pdf"

            if (documentFile.size > 0) {
                for(i in 0 until  documentFile.size) {
                    if (documentFile[i].fileName == fileName) {
                        documentFile.remove(documentFile[i])
                        VAT_Choose_File_Path.text = ""
                        VAT_Choose_File.isEnabled = true
                        VAT_Delete_icon.visibility = View.GONE
                        break
                    }
                }
            }
        }

        Director_Delete_icon.setSafeOnClickListener {
            val fileName = "directConsentForm_pdf.pdf"
            if (documentFile.size > 0) {
                for(i in 0 until  documentFile.size) {
                    if (documentFile[i].fileName == fileName) {
                        documentFile.remove(documentFile[i])
                        Director_Choose_File_Path.text = ""
                        Director_Choose_File.isEnabled = true
                        Director_Delete_icon.visibility = View.GONE
                        break
                    }
                }
            }
        }

        Director_id_Delete_icon.setSafeOnClickListener {
            val fileName = "directorId_pdf.pdf"
            if (documentFile.size > 0) {
                for(i in 0 until  documentFile.size) {
                    if (documentFile[i].fileName == fileName) {
                        documentFile.remove(documentFile[i])
                        Director_id_Choose_File_Path.text = ""
                        Director_id_Choose_File.isEnabled = true
                        Director_id_Delete_icon.visibility = View.GONE
                        break
                    }
                }

            }
        }

        bankDeleteIcon.setSafeOnClickListener {
            val fileName = "bankConfirmationLetter_pdf.pdf"
            if (documentFile.size > 0) {
                for(i in 0 until  documentFile.size) {
                    if (documentFile[i].fileName == fileName) {
                        documentFile.remove(documentFile[i])
                        bankChooseFilePath.text = ""
                        bankChooseFile.isEnabled = true
                        bankDeleteIcon.visibility = View.GONE
                        break
                    }
                }


            }
        }


    }


    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_CANCELED) {

        } else {
            if (requestCode == 100) {
                if (resultCode == RESULT_OK) {


                    data?.data.let { selectedPdfUri ->

                        val inputStream: InputStream = selectedPdfUri?.let {
                            contentResolver.openInputStream(
                                it
                            )
                        }!!
                        val fileSizeInBytes = inputStream.available()
                        val fileSizeInMB = fileSizeInBytes / (1024 * 1024) // Convert to MB

                        if (fileSizeInMB < 5) {
                            try {
                                if (data != null) {

                                    val sUri: Uri? = data.data
                                    val sPath: String = CommonFunctions.getPDFPath(sUri,this)!!
                                    val file = sUri!!.path?.let { File(it) }

                                    val document = (System.currentTimeMillis() / 1000).toString()
                                    try {
                                        val inputStream: InputStream = contentResolver.openInputStream(data.data!!)!!
                                        val fileOutputStream = FileOutputStream(File(this.getExternalFilesDir("temp"), "$document.pdf"))
                                        TakePictureUtils.copyStream(inputStream, fileOutputStream)
                                        fileOutputStream.close()
                                        inputStream.close()
                                    } catch (e: IOException) {
                                        e.printStackTrace()
                                    }

                                    val imageFile = File(
                                        getExternalFilesDir("temp"),
                                        "$document.pdf"
                                    )


                                    val requestGalleryImageFile: RequestBody = imageFile.asRequestBody("application/pdf".toMediaTypeOrNull())
                                    if (choosePdf == "COI_Choose_File") {
                                        COI_Choose_File.isEnabled = false
                                        COI_Delete_icon.visibility = View.VISIBLE
                                        COI_Choose_File_Path.text = sPath
                                        documentFile.add(FileUploadModel("cirtificationOfIncorporation_pdf.pdf",MultipartBody.Part.createFormData(
                                            "cirtificationOfIncorporation",
                                            "cirtificationOfIncorporation_pdf.pdf",
                                            requestGalleryImageFile
                                        )))

                                    } else if (choosePdf == "VAT_Choose_File") {
                                        VAT_Choose_File.isEnabled = false
                                        VAT_Delete_icon.visibility = View.VISIBLE
                                        VAT_Choose_File_Path.text = sPath
                                        documentFile.add(FileUploadModel("VatRegConfirmationDocs_pdf.pdf", MultipartBody.Part.createFormData(
                                            "VatRegConfirmationDocs",
                                            "VatRegConfirmationDocs_pdf.pdf",
                                            requestGalleryImageFile
                                        )))

                                    } else if (choosePdf == "Director_Choose_File") {
                                        Director_Choose_File.isEnabled = false
                                        Director_Delete_icon.visibility = View.VISIBLE
                                        Director_Choose_File_Path.text = sPath
                                        documentFile.add(FileUploadModel("directConsentForm_pdf.pdf", MultipartBody.Part.createFormData(
                                            "directConsentForm",
                                            "directConsentForm_pdf.pdf",
                                            requestGalleryImageFile
                                        )))
                                    } else if (choosePdf == "Director_id_Choose_File") {
                                        Director_id_Choose_File.isEnabled = false
                                        Director_id_Delete_icon.visibility = View.VISIBLE
                                        Director_id_Choose_File_Path.text = sPath
                                        documentFile.add(FileUploadModel("directorId_pdf.pdf",  MultipartBody.Part.createFormData(
                                            "directorId",
                                            "directorId_pdf.pdf",
                                            requestGalleryImageFile
                                        )))

                                    } else if (choosePdf == "Bank_Choose_File") {
                                        bankChooseFile.isEnabled = false
                                        bankDeleteIcon.visibility = View.VISIBLE
                                        bankChooseFilePath.text = sPath
                                        documentFile.add(FileUploadModel("bankConfirmationLetter_pdf.pdf",  MultipartBody.Part.createFormData(
                                            "bankConfirmationLetter",
                                            "bankConfirmationLetter_pdf.pdf",
                                            requestGalleryImageFile
                                        )))

                                    }
                                    println("AFTERUPLOADSIZE===>" + imageparts.size.toString())

                                }
                            }catch (e : Exception) {
                                e.printStackTrace()
                            }
                        } else {
                            androidextention.alertBoxCommon(message = "Please select a document that is 5 MB or smaller.", context = this)
                        }
                    }





                }

            }
        }
    }

    private fun selectPDF() {
        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
        pdfIntent.type = "application/pdf"
        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
        pickPdf.launch(pdfIntent)
    }

    private val pickPdf = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {


                result.data?.data.let { selectedPdfUri ->

                    val inputStream: InputStream = selectedPdfUri?.let {
                        contentResolver.openInputStream(
                            it
                        )
                    }!!
                    val fileSizeInBytes = inputStream.available()
                    val fileSizeInMB = fileSizeInBytes / (1024 * 1024) // Convert to MB

                    if (fileSizeInMB < 5) {
                        try {
                            if (result.data != null) {

                                val sUri: Uri? = result.data!!.data
                                val sPath: String = CommonFunctions.getPDFPath(sUri,this)!!
                                val file = sUri!!.path?.let { File(it) }

                                val document = (System.currentTimeMillis() / 1000).toString()
                                try {
                                    val inputStream: InputStream = contentResolver.openInputStream(result.data!!.data!!)!!
                                    val fileOutputStream = FileOutputStream(File(this.getExternalFilesDir("temp"), "$document.pdf"))
                                    TakePictureUtils.copyStream(inputStream, fileOutputStream)
                                    fileOutputStream.close()
                                    inputStream.close()
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                }

                                val imageFile = File(
                                    getExternalFilesDir("temp"),
                                    "$document.pdf"
                                )


                                val requestGalleryImageFile: RequestBody = imageFile.asRequestBody("application/pdf".toMediaTypeOrNull())
                                if (choosePdf == "COI_Choose_File") {
                                    COI_Choose_File.isEnabled = false
                                    COI_Delete_icon.visibility = View.VISIBLE
                                    COI_Choose_File_Path.text = sPath
                                    documentFile.add(FileUploadModel("cirtificationOfIncorporation_pdf.pdf",MultipartBody.Part.createFormData(
                                        "cirtificationOfIncorporation",
                                        "cirtificationOfIncorporation_pdf.pdf",
                                        requestGalleryImageFile
                                    )))

                                } else if (choosePdf == "VAT_Choose_File") {
                                    VAT_Choose_File.isEnabled = false
                                    VAT_Delete_icon.visibility = View.VISIBLE
                                    VAT_Choose_File_Path.text = sPath
                                    documentFile.add(FileUploadModel("VatRegConfirmationDocs_pdf.pdf", MultipartBody.Part.createFormData(
                                        "VatRegConfirmationDocs",
                                        "VatRegConfirmationDocs_pdf.pdf",
                                        requestGalleryImageFile
                                    )))

                                } else if (choosePdf == "Director_Choose_File") {
                                    Director_Choose_File.isEnabled = false
                                    Director_Delete_icon.visibility = View.VISIBLE
                                    Director_Choose_File_Path.text = sPath
                                    documentFile.add(FileUploadModel("directConsentForm_pdf.pdf", MultipartBody.Part.createFormData(
                                        "directConsentForm",
                                        "directConsentForm_pdf.pdf",
                                        requestGalleryImageFile
                                    )))
                                } else if (choosePdf == "Director_id_Choose_File") {
                                    Director_id_Choose_File.isEnabled = false
                                    Director_id_Delete_icon.visibility = View.VISIBLE
                                    Director_id_Choose_File_Path.text = sPath
                                    documentFile.add(FileUploadModel("directorId_pdf.pdf",  MultipartBody.Part.createFormData(
                                        "directorId",
                                        "directorId_pdf.pdf",
                                        requestGalleryImageFile
                                    )))

                                } else if (choosePdf == "Bank_Choose_File") {
                                    bankChooseFile.isEnabled = false
                                    bankDeleteIcon.visibility = View.VISIBLE
                                    bankChooseFilePath.text = sPath
                                    documentFile.add(FileUploadModel("bankConfirmationLetter_pdf.pdf",  MultipartBody.Part.createFormData(
                                        "bankConfirmationLetter",
                                        "bankConfirmationLetter_pdf.pdf",
                                        requestGalleryImageFile
                                    )))

                                }
                                println("AFTERUPLOADSIZE===>" + imageparts.size.toString())

                            }
                        }catch (e : Exception) {
                            e.printStackTrace()
                        }
                    } else {
                        androidextention.alertBoxCommon(message = "Please select a document that is 5 MB or smaller.", context = this)
                    }
                }







            } catch (e: Exception) {
                e.printStackTrace()
            }


        }
    }





    private fun uploadDocumentsApi() {

        if (androidextention.isOnline(this)) {
            progressbarUploadDocument.visibility = View.VISIBLE
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<JsonObject> =
                ApiCallBack(object :
                    ApiResponseListener<JsonObject> {
                    override fun onApiSuccess(
                        response: JsonObject,
                        apiName: String?
                    ) {
                        try {
                            progressbarUploadDocument.visibility = View.GONE
                            showPopUp()
                        } catch (e : Exception) {
                            e.printStackTrace()
                        }


                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        progressbarUploadDocument.visibility = View.GONE

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(
                                pojo.responseMessage,
                                this@NewUploadDocumentActivity
                            )

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        progressbarUploadDocument.visibility = View.GONE

                    }

                }, "uploadDocumentsApi", this)

            for(i in 0 until documentFile.size){
                imageparts.add(documentFile[i].documentList!!)
            }
            try {
                if (flag == "Service_Provider") {
                    serviceManager.UploadDocumentsApi(
                        callBack, "user", "documents", SavedPrefManager.getStringPreferences(this, SavedPrefManager.USER_ID).toString(), "PENDING",3, true, imageparts)
                } else {
                    serviceManager.UploadDocumentsApi(callBack, "user", "documents", SavedPrefManager.getStringPreferences(this, SavedPrefManager.USER_ID).toString(),"PENDING", 2, true, imageparts)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            androidextention.alertBox("Please check your internet connection.", this)

        }
    }

    fun showPopUp() {
        if (flag == "Service_Provider") {
            var alertDialog: AlertDialog? = null
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Your documents have been uploaded. You’ll receive an email for further instruction after verification.")
            builder.setPositiveButton("ok") { _, _ ->
                alertDialog!!.dismiss()
                CustomerBottomSheet("Service_Provider", this,this).show(
                    supportFragmentManager,
                    "ModalBottomSheet"
                )
                finish()
            }
            alertDialog = builder.create()
            alertDialog!!.setCancelable(false)
            alertDialog!!.show()


        } else {
            var alertDialog: AlertDialog? = null
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Your documents have been uploaded. You will receive an email for further instruction after verification.")
            builder.setPositiveButton("ok") { _, _ ->
                alertDialog!!.dismiss()
                supportFragmentManager.let { it1 ->
                    CustomerBottomSheet("Retailer", this,this).show(
                        it1,
                        "ModalBottomSheet"
                    )
                }
                finish()
            }
            alertDialog = builder.create()
            alertDialog!!.setCancelable(false)
            alertDialog!!.show()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && (grantResults[0]
                    == PackageManager.PERMISSION_GRANTED)
        ) {
            selectPDF()
        } else {
            selectPDF()
            Toast
                .makeText(
                    applicationContext,
                    "Permission Denied",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
    }



    override fun isLoginListener() {
        CommonFunctions
    }
}



