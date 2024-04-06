package com.exobe.modelClass

import okhttp3.MultipartBody


data class FileUploadModel(
    var fileName: String,
    var documentList: MultipartBody.Part
)
data class FileUploadModelCommon(
    var fileName: String,
    var pathName: String,
    var documentList: MultipartBody.Part
)