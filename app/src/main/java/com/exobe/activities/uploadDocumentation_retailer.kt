package com.exobe.activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.permission.RequestPermission
import com.exobe.modelClass.response_modal_class
import com.exobe.R
import com.exobe.utils.SavedPrefManager
import com.exobe.androidextention
import com.exobe.bottomSheet.CustomerBottomSheet
import com.exobe.customClicks.UpdateIsLoginListener
import com.exobe.entity.ApiCallBack
import com.exobe.entity.serviceBase.ApiResponseListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class uploadDocumentation_retailer : AppCompatActivity(), UpdateIsLoginListener {
    lateinit var back: ImageView
    lateinit var title: TextView
    lateinit var upload_document_button: Button
    var flag: String = ""
    val CAMERA_PERM_CODE = 101
    var imageFile: File? = null
    var photoURI: Uri? = null
    private var CAMERA: Int = 2
    var imagePath = ""
    private val GALLERY = 1
    lateinit var image: Uri
    lateinit var upload_pic1: ImageView
    lateinit var rl_pic1: RelativeLayout
    lateinit var upload_pic2: ImageView
    lateinit var rl_pic2: RelativeLayout
    lateinit var upload_pic3: ImageView
    lateinit var rl_pic3: RelativeLayout
    lateinit var upload_pic4: ImageView
    lateinit var rl_pic4: RelativeLayout
    lateinit var upload_pic5: ImageView
    lateinit var rl_pic5: RelativeLayout
    lateinit var rl_pic6: RelativeLayout
    lateinit var upload_pic6: ImageView
    lateinit var rl_pic7: RelativeLayout
    lateinit var upload_pic7: ImageView
    lateinit var rl_pic8: RelativeLayout
    lateinit var upload_pic8: ImageView
    lateinit var rl_pic9: RelativeLayout
    lateinit var upload_pic9: ImageView
    lateinit var rl_pic10: RelativeLayout
    lateinit var upload_pic10: ImageView
    lateinit var progressbar_upload_document: ProgressBar
    var cameraflag = ""
    var imageparts: ArrayList<MultipartBody.Part> = ArrayList()


    var USER_IMAGE_UPLOADED = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_documentation_retailer)
        window.attributes.windowAnimations = R.style.Fade
        back = findViewById(R.id.back)
        title = findViewById(R.id.title)
        upload_document_button = findViewById(R.id.upload_document_button)
        progressbar_upload_document = findViewById(R.id.progressbar_upload_document)
        upload_pic1 = findViewById(R.id.upload_pic1)
        rl_pic1 = findViewById(R.id.rl_pic1)
        upload_pic2 = findViewById(R.id.upload_pic2)
        rl_pic2 = findViewById(R.id.rl_pic2)
        upload_pic3 = findViewById(R.id.upload_pic3)
        rl_pic3 = findViewById(R.id.rl_pic3)
        upload_pic4 = findViewById(R.id.upload_pic4)
        rl_pic4 = findViewById(R.id.rl_pic4)
        upload_pic5 = findViewById(R.id.upload_pic5)
        upload_pic6 = findViewById(R.id.upload_pic6)
        rl_pic5 = findViewById(R.id.rl_pic5)
        rl_pic6 = findViewById(R.id.rl_pic6)
        rl_pic7 = findViewById(R.id.rl_pic7)
        upload_pic7 = findViewById(R.id.upload_pic7)
        upload_pic8 = findViewById(R.id.upload_pic8)
        rl_pic8 = findViewById(R.id.rl_pic8)
        rl_pic9 = findViewById(R.id.rl_pic9)
        upload_pic9 = findViewById(R.id.upload_pic9)
        rl_pic10 = findViewById(R.id.rl_pic10)
        upload_pic10 = findViewById(R.id.upload_pic10)

        if (intent != null) {
            flag = intent.getStringExtra("flag")!!
        }
        title.setText("Upload Documentation")
        upload_document_button.setSafeOnClickListener {
            if (imageparts.size.equals(0)) {
                alertBox("Please select documents.", this)
            }else {
                uploadDocumentsApi()
            }

        }
        rl_pic1.setSafeOnClickListener {
            cameraflag = "rl_pic1"
            RequestPermission.requestMultiplePermissions(this)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERM_CODE
                )
            } else {
                selectImage()
            }
        }

        rl_pic2.setSafeOnClickListener {
            cameraflag = "rl_pic2"
            RequestPermission.requestMultiplePermissions(this)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERM_CODE
                )
            } else {
                selectImage()
            }
        }
        rl_pic3.setSafeOnClickListener {
            cameraflag = "rl_pic3"
            RequestPermission.requestMultiplePermissions(this)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERM_CODE
                )
            } else {
                selectImage()
            }
        }
        rl_pic4.setSafeOnClickListener {
            cameraflag = "rl_pic4"

            RequestPermission.requestMultiplePermissions(this)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERM_CODE
                )
            } else {
                selectImage()
            }
        }
        rl_pic5.setSafeOnClickListener {
            cameraflag = "rl_pic5"
            RequestPermission.requestMultiplePermissions(this)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERM_CODE
                )
            } else {
                selectImage()
            }
        }
        rl_pic6.setSafeOnClickListener {
            cameraflag = "rl_pic6"
            RequestPermission.requestMultiplePermissions(this)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERM_CODE
                )
            } else {
                selectImage()
            }
        }
        rl_pic7.setSafeOnClickListener {
            cameraflag = "rl_pic7"
            RequestPermission.requestMultiplePermissions(this)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERM_CODE
                )
            } else {
                selectImage()
            }
        }
        rl_pic8.setSafeOnClickListener {
            cameraflag = "rl_pic8"
            RequestPermission.requestMultiplePermissions(this)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERM_CODE
                )
            } else {
                selectImage()
            }
        }
        rl_pic9.setSafeOnClickListener {
            cameraflag = "rl_pic9"
            RequestPermission.requestMultiplePermissions(this)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERM_CODE
                )
            } else {
                selectImage()
            }
        }
        rl_pic10.setSafeOnClickListener {
            cameraflag = "rl_pic10"
            RequestPermission.requestMultiplePermissions(this)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERM_CODE
                )
            } else {
                selectImage()
            }
        }



        back.setSafeOnClickListener {
            finish()
        }
    }
    private fun alertBox(message: String,context: Context) {
        var alertDialog: android.app.AlertDialog? = null
        val builder = android.app.AlertDialog.Builder(context)
        builder.setMessage(message)
        builder.setPositiveButton("ok") { dialogInterface, which ->
            alertDialog!!.dismiss()
        }
        alertDialog = builder.create()
        alertDialog!!.setCancelable(false)
        alertDialog!!.show()
    }
    private fun selectImage() {
        val dialog = BottomSheetDialog(this)

        val view = layoutInflater.inflate(R.layout.choose_camera_bottom_sheet, null)
        dialog.setCancelable(true)

        val camera = view.findViewById<ImageView>(R.id.choose_from_camera)
        camera.setSafeOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
                try {
                    imageFile = createImageFile()!!
                } catch (ex: IOException) {
                }
                if (imageFile != null) {
                    photoURI = FileProvider.getUriForFile(
                        this,
                        "com.exobe.fileprovider",
                        imageFile!!
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, CAMERA)
                    dialog.dismiss()
                }
            }
        }

        val GalleryButton = view.findViewById<ImageView>(R.id.choose_from_gallery)
        GalleryButton.setSafeOnClickListener {
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY)
            dialog.dismiss()
        }

        dialog.setContentView(view)
        dialog.show()
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_CANCELED) {
            return
        } else {
            if (requestCode == GALLERY) {
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        image = data.data!!
                        val path = getPathFromURI(image)

                        if (path != null) {
                            imageFile = File(path)
                            var requestGalleryImageFile: RequestBody =
                                RequestBody.create("image/*".toMediaTypeOrNull(), imageFile!!)

                            if (cameraflag.equals("rl_pic1")) {
                                Glide.with(this).load(imageFile).into(upload_pic1)
                                imageparts.add(
                                    MultipartBody.Part.createFormData(
                                        "uploaded_file",
                                        "cirtificationOfIncorporation_frontImage.jpg",
                                        requestGalleryImageFile
                                    )
                                )
                            } else if (cameraflag.equals("rl_pic2")) {
                                Glide.with(this).load(imageFile).into(upload_pic2)
                                imageparts.add(
                                    MultipartBody.Part.createFormData(
                                        "uploaded_file",
                                        "cirtificationOfIncorporation_backImage.jpg",
                                        requestGalleryImageFile
                                    )
                                )
                            } else if (cameraflag.equals("rl_pic3")) {
                                Glide.with(this).load(imageFile).into(upload_pic3)
                                imageparts.add(
                                    MultipartBody.Part.createFormData(
                                        "uploaded_file",
                                        "VatRegConfirmationDocs_frontImage.jpg",
                                        requestGalleryImageFile
                                    )
                                )

                            } else if (cameraflag.equals("rl_pic4")) {
                                Glide.with(this).load(imageFile).into(upload_pic4)
                                imageparts.add(
                                    MultipartBody.Part.createFormData(
                                        "uploaded_file",
                                        "VatRegConfirmationDocs_backImage.jpg",
                                        requestGalleryImageFile
                                    )
                                )

                            } else if (cameraflag.equals("rl_pic5")) {
                                Glide.with(this).load(imageFile).into(upload_pic5)
                                imageparts.add(
                                    MultipartBody.Part.createFormData(
                                        "uploaded_file",
                                        "directConsentForm_frontImage.jpg",
                                        requestGalleryImageFile
                                    )
                                )

                            } else if (cameraflag.equals("rl_pic6")) {
                                Glide.with(this).load(imageFile).into(upload_pic6)
                                imageparts.add(
                                    MultipartBody.Part.createFormData(
                                        "uploaded_file",
                                        "directConsentForm_backImage.jpg",
                                        requestGalleryImageFile
                                    )
                                )

                            } else if (cameraflag.equals("rl_pic7")) {
                                Glide.with(this).load(imageFile).into(upload_pic7)
                                imageparts.add(
                                    MultipartBody.Part.createFormData(
                                        "uploaded_file",
                                        "directorId_frontImage.jpg",
                                        requestGalleryImageFile
                                    )
                                )

                            } else if (cameraflag.equals("rl_pic8")) {
                                Glide.with(this).load(imageFile).into(upload_pic8)
                                imageparts.add(
                                    MultipartBody.Part.createFormData(
                                        "uploaded_file",
                                        "directorId_backImage.jpg",
                                        requestGalleryImageFile
                                    )
                                )

                            } else if (cameraflag.equals("rl_pic9")) {
                                Glide.with(this).load(imageFile).into(upload_pic9)
                                imageparts.add(
                                    MultipartBody.Part.createFormData(
                                        "uploaded_file",
                                        "bankConfirmationLetter_frontImage.jpg",
                                        requestGalleryImageFile
                                    )
                                )


                            } else if (cameraflag.equals("rl_pic10")) {
                                Glide.with(this).load(imageFile).into(upload_pic10)
                                imageparts.add(
                                    MultipartBody.Part.createFormData(
                                        "uploaded_file",
                                        "bankConfirmationLetter_backImage.jpg",
                                        requestGalleryImageFile
                                    )
                                )

                            }
                        }

                        USER_IMAGE_UPLOADED = true
                    }

                }
            } else if (requestCode == CAMERA) {
                if (resultCode == RESULT_OK) {

                    try {
                        imageFile = File(imagePath)
                        var requestGalleryImageFile: RequestBody =
                            RequestBody.create("image/*".toMediaTypeOrNull(), imageFile!!)

                        if (cameraflag.equals("rl_pic1")) {
                            Glide.with(this).load(imageFile).into(upload_pic1)
                            imageparts.add(
                                MultipartBody.Part.createFormData(
                                    "uploaded_file",
                                    "cirtificationOfIncorporation_frontImage.jpg",
                                    requestGalleryImageFile
                                )
                            )
                        } else if (cameraflag.equals("rl_pic2")) {
                            Glide.with(this).load(imageFile).into(upload_pic2)
                            imageparts.add(
                                MultipartBody.Part.createFormData(
                                    "uploaded_file",
                                    "cirtificationOfIncorporation_backImage.jpg",
                                    requestGalleryImageFile
                                )
                            )
                        } else if (cameraflag.equals("rl_pic3")) {
                            Glide.with(this).load(imageFile).into(upload_pic3)
                            imageparts.add(
                                MultipartBody.Part.createFormData(
                                    "uploaded_file",
                                    "VatRegConfirmationDocs_frontImage.jpg",
                                    requestGalleryImageFile
                                )
                            )

                        } else if (cameraflag.equals("rl_pic4")) {
                            Glide.with(this).load(imageFile).into(upload_pic4)
                            imageparts.add(
                                MultipartBody.Part.createFormData(
                                    "uploaded_file",
                                    "VatRegConfirmationDocs_backImage.jpg",
                                    requestGalleryImageFile
                                )
                            )

                        } else if (cameraflag.equals("rl_pic5")) {
                            Glide.with(this).load(imageFile).into(upload_pic5)
                            imageparts.add(
                                MultipartBody.Part.createFormData(
                                    "uploaded_file",
                                    "directConsentForm_frontImage.jpg",
                                    requestGalleryImageFile
                                )
                            )

                        } else if (cameraflag.equals("rl_pic6")) {
                            Glide.with(this).load(imageFile).into(upload_pic6)
                            imageparts.add(
                                MultipartBody.Part.createFormData(
                                    "uploaded_file",
                                    "directConsentForm_backImage.jpg",
                                    requestGalleryImageFile
                                )
                            )

                        } else if (cameraflag.equals("rl_pic7")) {
                            Glide.with(this).load(imageFile).into(upload_pic7)
                            imageparts.add(
                                MultipartBody.Part.createFormData(
                                    "uploaded_file",
                                    "directorId_frontImage.jpg",
                                    requestGalleryImageFile
                                )
                            )

                        } else if (cameraflag.equals("rl_pic8")) {
                            Glide.with(this).load(imageFile).into(upload_pic8)
                            imageparts.add(
                                MultipartBody.Part.createFormData(
                                    "uploaded_file",
                                    "directorId_backImage.jpg",
                                    requestGalleryImageFile
                                )
                            )

                        } else if (cameraflag.equals("rl_pic9")) {
                            Glide.with(this).load(imageFile).into(upload_pic9)
                            imageparts.add(
                                MultipartBody.Part.createFormData(
                                    "uploaded_file",
                                    "bankConfirmationLetter_frontImage.jpg",
                                    requestGalleryImageFile
                                )
                            )


                        } else if (cameraflag.equals("rl_pic10")) {
                            Glide.with(this).load(imageFile).into(upload_pic10)
                            imageparts.add(
                                MultipartBody.Part.createFormData(
                                    "uploaded_file",
                                    "bankConfirmationLetter_backImage.jpg",
                                    requestGalleryImageFile
                                )
                            )

                        }
                        USER_IMAGE_UPLOADED = true
                    } catch (e: java.lang.Exception) {

                    }


                }
            }
        }
    }


    fun getPathFromURI(contentUri: Uri?): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = contentResolver.query(contentUri!!, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            res = cursor.getString(column_index)
        }
        cursor.close()
        return res
    }


    fun uploadDocumentsApi() {

        if (androidextention.isOnline(this)) {
            progressbar_upload_document.visibility = View.VISIBLE
//            androidextention.showProgressDialog(this)
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<JsonObject> =
                ApiCallBack<JsonObject>(object :
                    ApiResponseListener<JsonObject> {
                    override fun onApiSuccess(
                        response: JsonObject,
                        apiName: String?
                    ) {
//                        androidextention.disMissProgressDialog(this@uploadDocumentation_retailer)
                        progressbar_upload_document.visibility = View.GONE
                        System.out.println(response)
//                        androidextention.alertBox(response.toString(), this@uploadDocumentation_retailer)
                        showPopUp()

                    }

                    override fun onApiErrorBody(response: String, apiName: String?) {
//                        androidextention.disMissProgressDialog(this@uploadDocumentation_retailer)
                        progressbar_upload_document.visibility = View.GONE

                        val gson = GsonBuilder().create()
                        var pojo = response_modal_class()

                        try {
                            pojo = gson.fromJson(response, pojo::class.java)
                            androidextention.alertBox(
                                pojo.responseMessage,
                                this@uploadDocumentation_retailer
                            )

                        } catch (e: Exception) {
                            // handle failure at error parse
                        }
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
//                        androidextention.disMissProgressDialog(this@uploadDocumentation_retailer)
                        progressbar_upload_document.visibility = View.GONE

                    }

                }, "uploadDocumentsApi", this)


            try {
                if (flag.equals("Service_Provider")) {
                    serviceManager.UploadDocumentsApi(
                        callBack, "user", "documents", SavedPrefManager.getStringPreferences(
                            this,
                            SavedPrefManager.USER_ID
                        ).toString(), "PENDING",2, false, imageparts
                    )
                } else {
                    serviceManager.UploadDocumentsApi(
                        callBack, "user", "documents", SavedPrefManager.getStringPreferences(
                            this,
                            SavedPrefManager.USER_ID
                        ).toString(), "PENDING",2, true, imageparts
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            androidextention.alertBox("Please check your internet connection." , this)

        }
    }


    fun showPopUp() {
        if (flag.equals("Service_Provider")) {
            var alertDialog: AlertDialog? = null
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Your documents have been uploaded. You’ll receive an email for further instruction after verification.")
            builder.setPositiveButton("ok") { dialogInterface, which ->
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
            builder.setPositiveButton("ok") { dialogInterface, which ->
                alertDialog!!.dismiss()
                supportFragmentManager.let { it1 ->
                    CustomerBottomSheet("Retailer", this, this).show(
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

    override fun isLoginListener() {

    }
}