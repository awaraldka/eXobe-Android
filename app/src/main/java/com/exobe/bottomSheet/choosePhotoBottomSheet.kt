package com.exobe.bottomSheet

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.FileProvider
import com.callisdairy.extension.setSafeOnClickListener
import com.exobe.R
import com.exobe.customClicks.AddProductListener
import com.exobe.entity.serviceBase.ServiceManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class choosePhotoBottomSheet(var flag: String, var openCamera: AddProductListener
) :
    BottomSheetDialogFragment() {
    var pic_id = 123
    private val pickImage = 100
    lateinit var cancel: TextView
    lateinit var gallery: TextView
    lateinit var camera: TextView
    lateinit var captureVideo: TextView
    private val GALLERY = 1
    private val CAMERA: Int = 2
    protected val CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 3

    private var openFlag = ""
    var imageList: ArrayList<Bitmap?> = ArrayList()
    lateinit var bitmap: Bitmap
    val threeImageFlag = 0
    lateinit var image: Uri
    lateinit var mContext: Context
    var imageFile: File? = null
    var imagePath = ""
    lateinit var serviceManager: ServiceManager
    val CAMERA_PERM_CODE = 101
    var photoURI: Uri? = null

    companion object {
        var count = 0
        var captureVideoCount = 0

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.bottom_drawer, container, false)
        mContext = requireActivity().applicationContext
        serviceManager = ServiceManager(mContext)
        gallery = v.findViewById(R.id.gallery_open)
        camera = v.findViewById(R.id.camera_open)
        captureVideo = v.findViewById(R.id.capture_video)
        cancel = v.findViewById(R.id.cancel)
//        if(flag == "addpost") {
//            captureVideo.visibility = View.VISIBLE
//        }
        cancel.setSafeOnClickListener {
            dismiss()
        }


        gallery.setSafeOnClickListener { view: View? ->
            if (flag == "AddProduct") {

                choosePhotoFromGallery()

            } else {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                startActivityForResult(intent, GALLERY)
            }
        }

        camera.setSafeOnClickListener {
            if (flag == "AddProduct") {

                dispatchTakePictureIntent()

            } else {

                dispatchTakePictureIntent()

            }
        }

        captureVideo.setSafeOnClickListener{
            val takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)

            if (takeVideoIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivityForResult(
                    takeVideoIntent,
                    CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE,


                    )
                captureVideoCount++
            }
        }
//        }
        return v
    }


    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)

    fun choosePhotoFromGallery() {
        val intent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY)

    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
            try {
                imageFile = createImageFile()!!
            } catch (ex: IOException) {
            }
            // Continue only if the File was successfully created
            if (imageFile != null) {
                photoURI = FileProvider.getUriForFile(
                    mContext,
                    "com.exobe.fileprovider",
                    imageFile!!
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, CAMERA)
            }
        }
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
        if (flag == "AddProduct") {

            openCamera.addProduct(requestCode, resultCode, data, this, imagePath)
        }

    }



}

