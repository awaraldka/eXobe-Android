package com.exobe.activities.services

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.exobe.permission.RequestPermission
import com.exobe.activities.LoginPresent
import com.exobe.activities.Services
import com.exobe.adaptor.SelectDocumentAdapter
import com.exobe.modelClass.SelectDocumentData
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.CommonFunctions
import com.exobe.utils.ImageRotation
import com.exobe.utils.Progresss
import com.exobe.utils.SavedPrefManager
import com.exobe.utils.TakePictureUtils
import com.exobe.androidextention
import com.exobe.customClicks.SelectDocumentClick
import com.exobe.customClicks.UpdateIsLoginListener
import com.exobe.databinding.ActivityUploadDocumentForServiceBinding
import com.exobe.entity.ApiCallBack
import com.exobe.entity.response.DocumentListResponse
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import id.zelory.compressor.Compressor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date

class UploadDocumentForServiceActivity : AppCompatActivity(), SelectDocumentClick,
    UpdateIsLoginListener {

    private lateinit var binding: ActivityUploadDocumentForServiceBinding

    var documentData = ArrayList<SelectDocumentData>()
    var imageparts: ArrayList<MultipartBody.Part> = ArrayList()

    var fulfillment =  false
    var transportation =  false
    var standard =  false

    var imageFile: File? = null

    var photoURI: Uri? = null
    var mimeType = ""
    var imagePath = ""



    private var documentPosition = 0
    var documentViewName = ""
    var isFrom = ""
    lateinit var adapterSelectDocument: SelectDocumentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadDocumentForServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.attributes.windowAnimations = R.style.Fade


        intent?.getStringExtra("isFrom")?.let { isFrom = it }

        intent.getBooleanExtra("standard",false).let { standard = it }
        intent.getBooleanExtra("transportation", false).let { transportation = it }
        intent.getBooleanExtra("fulfillment",false).let { fulfillment = it }



        binding.backButtonClick.setOnClickListener {
            if (isFrom == "Profile") {
                alertBoxFinish()
            }else{
                finishAfterTransition()
            }
        }


        uploadDocumentsListApi()


        binding.ProceedButton.setOnClickListener {

            for (i in documentData.indices) {
                val documentName = documentData[i].name

                if (documentName.contains("if applicable", ignoreCase = true)) {
                    continue
                }

                if (!documentData[i].showDeleteIcon) {
                    androidextention.alertBox("Please upload $documentName document", this)
                    return@setOnClickListener
                }
            }

            uploadDocumentsApi()
        }


        this.onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isFrom == "Profile") {
                    alertBoxFinish()
                }else{
                    finishAfterTransition()
                }
            }

        })




    }


    private fun selectDocumentAdapter() {
        binding.documentReycler.layoutManager = LinearLayoutManager(this)
        adapterSelectDocument = SelectDocumentAdapter(this, documentData, this)
        binding.documentReycler.adapter = adapterSelectDocument
    }


    private fun selectPdf() {
        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
        pdfIntent.type = "application/pdf"
        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
        pickPdf.launch(pdfIntent)
    }

    private val pickPdf = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {

                    result?.data!!.data.let { selectedPdfUri ->
                        val inputStream: InputStream = selectedPdfUri?.let { contentResolver.openInputStream(it) }!!
                        val fileSizeInBytes = inputStream.available()
                        val fileSizeInMB = fileSizeInBytes / (1024 * 1024) // Convert to MB

                        if (fileSizeInMB < 5) {

                            val fileUris = result.data!!.data
                            val pathName = CommonFunctions.getPDFPath(fileUris, this)!!
                            val document = (System.currentTimeMillis() / 1000).toString()
                            try {
                                val inputStream: InputStream = contentResolver.openInputStream(fileUris!!)!!
                                val fileOutputStream = FileOutputStream(
                                    File(this.getExternalFilesDir("temp"), "$document.pdf"))
                                TakePictureUtils.copyStream(inputStream, fileOutputStream)
                                fileOutputStream.close()
                                inputStream.close()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                            val imageFile = File(getExternalFilesDir("temp"), "$document.pdf")


                            val requestGalleryImageFile: RequestBody = imageFile.asRequestBody("application/pdf".toMediaTypeOrNull())


                            documentData[documentPosition].pathName = pathName
                            documentData[documentPosition].showDeleteIcon = true
                            documentData[documentPosition].documentList = requestGalleryImageFile
                            adapterSelectDocument.notifyItemChanged(documentPosition)


                        }else{
                            androidextention.alertBoxCommon(message = "Please select a document that is 5 MB or smaller.", context = this)

                        }

                    }




                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }
        }

    override fun selectDocument(position: Int, name: String) {
        documentPosition = position
        documentViewName = name

        selectImage()


    }

    private fun selectImage() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_drawer, null)
        dialog.setCancelable(true)

        val camButton = view.findViewById<TextView>(R.id.camera_open)
        val cancel = view.findViewById<TextView>(R.id.cancel)

        cancel.setOnClickListener {
            dialog.dismiss()
        }

        camButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                RequestPermission.requestMultiplePermissions(this)
            }else{
                dialog.dismiss()
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent.resolveActivity(this.packageManager) != null) {
                    try {
                        imageFile = createImageFile()!!
                    } catch (ex: IOException) {
                        ex.printStackTrace()
                    }
                    if (imageFile != null) {
                        photoURI = FileProvider.getUriForFile(this, "com.exobe.fileprovider", imageFile!!)
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        pickImagesCamera.launch(takePictureIntent)
                    }
                }
            }



        }

        val galleryButton = view.findViewById<TextView>(R.id.gallery_open)
        galleryButton.text = "Select Document"
        galleryButton.setOnClickListener {
            dialog.dismiss()
            selectPdf()
        }



        dialog.setContentView(view)

        dialog.show()
    }


    private val pickImagesCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                val finalBitmap = ImageRotation.modifyOrientation(
                    ImageRotation.getBitmap(imagePath)!!,
                    imagePath
                )
                val getUri = ImageRotation.getImageUri(this, finalBitmap!!)
                val path = ImageRotation.getRealPathFromURI2(this, getUri!!)
                val imageFile = File(path!!)
                mimeType = ImageRotation.getMimeType(imagePath)!!

                val maxFileSizeInBytes = 5 * 1024 * 1024 // 2 MB in bytes

                if (imageFile.length() > maxFileSizeInBytes) {
                    // Image is larger than 2 MB, compress it asynchronously
                    lifecycleScope.launch {
                        try {
                            val compressedImageFile = withContext(Dispatchers.IO) {
                                // Compress the image with a maximum file size
                                compressImageWithMaxSize(imageFile, maxFileSizeInBytes.toLong())
                            }

                            // Use the compressed image
                            val requestGalleryImageFile: RequestBody =
                                compressedImageFile.asRequestBody(mimeType.toMediaTypeOrNull())

                            documentData[documentPosition].pathName = compressedImageFile.name
                            documentData[documentPosition].showDeleteIcon = true
                            documentData[documentPosition].documentList = requestGalleryImageFile
                            adapterSelectDocument.notifyItemChanged(documentPosition)
                        } catch (e: Exception) {
                            // Handle compression error
                            e.printStackTrace()
                        }
                    }
                } else {
                    val requestGalleryImageFile: RequestBody = imageFile.asRequestBody(mimeType.toMediaTypeOrNull())

                    documentData[documentPosition].pathName = imageFile.name
                    documentData[documentPosition].showDeleteIcon = true
                    documentData[documentPosition].documentList = requestGalleryImageFile
                    adapterSelectDocument.notifyItemChanged(documentPosition)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Function to compress the image with a maximum file size
    private suspend fun compressImageWithMaxSize(inputFile: File, maxFileSize: Long): File {
        val compressedImageFile = Compressor.compress(this@UploadDocumentForServiceActivity, inputFile)
        if (compressedImageFile.length() > maxFileSize) {
            // If the compressed image is still larger than the max size, recursively compress it further
            return compressImageWithMaxSize(compressedImageFile, maxFileSize)
        }
        return compressedImageFile
    }





    override fun deleteDocument(position: Int, pathName: String) {
        documentData[position].pathName = ""
        documentData[position].showDeleteIcon = false
        documentData[position].documentList = null
        adapterSelectDocument.notifyItemChanged(position)

    }


    private fun uploadDocumentsApi() {

        if (androidextention.isOnline(this)) {
            Progresss.start(this)
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<JsonObject> =
                ApiCallBack<JsonObject>(object :
                    ApiResponseListener<JsonObject> {
                    override fun onApiSuccess(
                        response: JsonObject,
                        apiName: String?
                    ) {
                        try {
                            Progresss.stop()
                            documentPopUp(message = "Your documents have been uploaded. You will receive an email for further instruction after verification.")

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }


                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        Progresss.stop()
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(
                                pojo.responseMessage,
                                this@UploadDocumentForServiceActivity
                            )

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()

                    }

                }, "uploadDocumentsApi", this)

            val requestType  = if (mimeType == "") "pdf" else mimeType
            for (i in 0 until documentData.size) {
                if (documentData[i].documentList != null) {
                    imageparts.add(
                        MultipartBody.Part.createFormData(
                            documentData[i].requestKey, "${documentData[i].name}",
                            documentData[i].documentList!!
                        )
                    )
                }

            }
            try {
                serviceManager.UploadDocumentsApi(
                    callBack,
                    "user",
                    "documents",
                    SavedPrefManager.getStringPreferences(this, SavedPrefManager.USER_ID).toString(),
                    "PENDING",
                    3,
                    true,
                    imageparts
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            androidextention.alertBox("Please check your internet connection.", this)

        }
    }


    fun documentPopUp(message:String) {
        var alertDialog: AlertDialog? = null
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setPositiveButton("ok") { _, _ ->
            alertDialog!!.dismiss()

            LoginPresent.isForLogin = "Service_Provider"

            clearActivityStackAndReturnToMain()



        }
        alertDialog = builder.create()
        alertDialog!!.setCancelable(false)
        alertDialog.show()
    }

    override fun isLoginListener() {

    }





    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )

        imagePath = image.absolutePath
        return image
    }



    private fun alertBoxFinish() {
        runOnUiThread{
            var alertDialog: AlertDialog? = null
            var builder = AlertDialog.Builder(this)
            builder.setIcon(R.mipmap.ic_launcher)
            builder.setTitle("eXobe")
            builder.setMessage("Are you sure you want to exit with out uploading documents?")
            builder.setPositiveButton("Ok") { dialogInterface, which ->
                alertDialog!!.dismiss()
                CommonFunctions.logoutApi(this)
                builder = AlertDialog.Builder(this, R.style.ProgressDialogStyle)

            }

            builder.setNegativeButton("Cancel"){dialogInterface, which ->
                alertDialog!!.dismiss()

            }


            alertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

    }


    private fun uploadDocumentsListApi() {

        if (androidextention.isOnline(this)) {
            Progresss.start(this)
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<DocumentListResponse> = ApiCallBack<DocumentListResponse>(object : ApiResponseListener<DocumentListResponse> {
                    override fun onApiSuccess(response: DocumentListResponse, apiName: String?) {
                        try {
                            Progresss.stop()

                            if (response.responseCode == 200){
                                documentData.clear()
                                for (document in response.result.indices){
                                    documentData.add(SelectDocumentData(name = response.result[document].name,
                                        pathName = "", showDeleteIcon = false, documentList = null, requestKey = response.result[document].key))
                                }

                                selectDocumentAdapter()
                            }



                        } catch (e: Exception) {
                            e.printStackTrace()
                        }


                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
                        Progresss.stop()
                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(
                                pojo.responseMessage,
                                this@UploadDocumentForServiceActivity
                            )

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
                        Progresss.stop()

                    }

                }, "uploadDocumentsApi", this)


            try {
                serviceManager.documentListApi(callBack)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            androidextention.alertBox("Please check your internet connection.", this)

        }
    }


    private fun clearActivityStackAndReturnToMain() {
        val intent = Intent(this, Services::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }


}

