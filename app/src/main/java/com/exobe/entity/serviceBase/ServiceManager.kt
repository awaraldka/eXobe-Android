package com.exobe.entity.serviceBase

import android.annotation.SuppressLint
import android.content.Context
import com.exobe.utils.SavedPrefManager
import com.exobe.entity.ApiCallBack
import com.exobe.entity.request.*
import com.exobe.entity.response.*
import com.exobe.entity.response.addproduct.AddProductResponse
import com.exobe.entity.response.addproduct.MultipartImageResponse
import com.exobe.entity.response.customer.*
import com.exobe.entity.response.customerservices.*
import com.exobe.entity.response.customerservices.ListCategoryServiceResponse
import com.exobe.entity.response.imageupload.SingleImageUpload
import com.exobe.entity.response.customer.AddDealsCategory
import com.exobe.entity.response.customer.AddDealsOfServices
import com.exobe.entity.response.product.DealBannerResponse
import com.exobe.entity.response.product.GuestProductResponse
import com.exobe.entity.response.serviceTab.AllRequestedProductsResponse
import com.exobe.entity.response.serviceTab.GetAllOrdersCommonResponse
import com.exobe.entity.response.serviceTab.NewServicesResponseResponse
import com.exobe.entity.response.serviceTab.SelectedServiceResponse
import com.exobe.entity.response.serviceTab.ServiceListViewResponse
import com.exobe.entity.response.serviceTab.ServicesListResponse
import com.exobe.entity.response.serviceTab.UpdateSubCategoryService
import com.exobe.entity.response.serviceTab.VerifyOtpServicesResponse
import com.exobe.entity.response.serviceTab.ViewAssignedOrderResponse
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.http.Body


class ServiceManager(var mContext: Context?) {

    private val accessToken: String? = null

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var mServiceManager: ServiceManager? = null
        fun getInstance(context: Context?): ServiceManager? {
            if (mServiceManager == null) {
                mServiceManager = ServiceManager(context)
            }
            return mServiceManager
        }
    }

    fun singleUploadImageApiService(
        callBack: ApiCallBack<DocumentResponse>,
        file: MultipartBody.Part?
    ) {
        mContext?.let { Remotedatasource.current(it, false)!!.addSingleImageService(file) }!!
            .enqueue(callBack)
    }

    fun CountryList(callBack: ApiCallBack<CountryListResponse>) {
        mContext?.let { Remotedatasource.current(it, false)!!.CountryList() }!!
            .enqueue(callBack)
    }

    fun StateList(callBack: ApiCallBack<StateListResponse>, isocode: String) {
        mContext?.let { Remotedatasource.current(it, false)!!.StateList(isocode) }!!
            .enqueue(callBack)
    }

    fun CityList(callBack: ApiCallBack<CityListResponse>, isocode: String, isoState: String) {
        mContext?.let { Remotedatasource.current(it, false)!!.CityList(isocode, isoState) }!!
            .enqueue(callBack)
    }

    fun MyProfileApi(callBack: ApiCallBack<MyProfileResponse>) {
        mContext?.let { Remotedatasource.current(it, true)!!.MyProfileApi() }!!
            .enqueue(callBack)
    }

    fun Edit_Profile(callBack: ApiCallBack<EditProfile_Response>, jsonObject: EditProfileRequest) {
        mContext?.let { Remotedatasource.current(it, true)!!.EditProfileApi(jsonObject) }!!
            .enqueue(callBack)
    }

    fun SignupApi(callBack: ApiCallBack<SignupResponse>, signupRequest: SignupRequest, isSocial:Boolean) {
        mContext?.let { Remotedatasource.current(it, false)!!.SignupApi(signupRequest,isSocial) }!!
            .enqueue(callBack)
    }

    fun LoginApi(callBack: ApiCallBack<LoginResponse>, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, false)!!.LoginApi(jsonObject) }!!
            .enqueue(callBack)
    }

    fun HomeApi(callBack: ApiCallBack<Home_response>) {
        mContext?.let { Remotedatasource.current(it, false)!!.HomeApi() }!!
            .enqueue(callBack)
    }

    fun Services_Service_provider_Api(
        callBack: ApiCallBack<Services_ServiceproviderResponce>,
        jsonObject: JsonObject
    ) {
        mContext?.let {
            Remotedatasource.current(it, true)!!.Services_ServiceproviderApi(jsonObject)
        }!!
            .enqueue(callBack)

    }
//    =================================================
//    fun listCategoryOfServiceProvider(callBack:ApiCallBack<AddDealsCategory>){
//        mContext?.let { Remotedatasource.current(it, true)!!.listCategoryOfServiceProvider()
//        }!!.enqueue(callBack)
//    }
//
//
//    fun listSubCategoryOfServiceProvider(callBack:ApiCallBack<AddDealsCategory>, id:String){
//        mContext?.let { Remotedatasource.current(it, true)!!.listSubCategoryOfServiceProvider(id)
//        }!!.enqueue(callBack)
//    }
//
//    fun AddDealsServices(callBack:ApiCallBack<AddDealsOfServices>, jsonObject: JsonObject){
//        mContext?.let { Remotedatasource.current(it, true)!!.AddDealsServices(jsonObject)
//        }!!.enqueue(callBack)
//    }
//=============================================
//    fun serviceListByCategory(callBack: ApiCallBack<ServicesServiceproviderResponce>, id: String) {
//        mContext?.let { Remotedatasource.current(it, true)!!.serviceListByCategory(id) }!!
//            .enqueue(callBack)
//
//    }

    fun forgotpasswordApi(callBack: ApiCallBack<forgotpassword_response>, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, false)!!.forgotpasswordApi(jsonObject) }!!
            .enqueue(callBack)
    }

    fun ResendOtpApi(callBack: ApiCallBack<ResendOtp>, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, false)!!.ResendotpApi(jsonObject) }!!
            .enqueue(callBack)
    }

    fun otpverficationApi(callBack: ApiCallBack<otpverfication_response>, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, false)!!.otpApi(jsonObject) }!!
            .enqueue(callBack)
    }

    fun ResetPasswordApi(callBack: ApiCallBack<resetpassword_response>, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, false)!!.ResetPasswordApi(jsonObject) }!!
            .enqueue(callBack)
    }

    fun productListByCategoryApi(
        callBack: ApiCallBack<SubCategorySearchResponse>,
        jsonObject: JsonObject
    ) {
        mContext?.let {
            Remotedatasource.current(it, false)!!.productListByCategoryApi(jsonObject)
        }!!
            .enqueue(callBack)
    }

    fun listCategoryApi(callBack: ApiCallBack<CategoryList_response>, jsonObject: JsonObject) {
        if (SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.isLogin) == "true") {
            mContext?.let { Remotedatasource.current(it, true)!!.listCategoryApi(jsonObject) }!!
                .enqueue(callBack)
        } else {
            mContext?.let { Remotedatasource.current(it, false)!!.listCategoryApi(jsonObject) }!!
                .enqueue(callBack)
        }

    }

    fun listCategorySearchApi(
        callBack: ApiCallBack<CategoryList_response>,
        jsonObject: JsonObject
    ) {
        if (SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.isLogin) == "true") {
            mContext?.let { Remotedatasource.current(it, true)!!.listCategorySearchApi(jsonObject) }!!
                .enqueue(callBack)
        } else {
            mContext?.let { Remotedatasource.current(it, false)!!.listCategorySearchApi(jsonObject) }!!
                .enqueue(callBack)
        }

    }

    fun listSubCategoryApi(callBack: ApiCallBack<SubCategoryResponse>, id: String) {
        mContext?.let { Remotedatasource.current(it, false)!!.listSubCategoryApi(id) }!!
            .enqueue(callBack)
    }

    fun serviceListApi(callBack: ApiCallBack<ServiceList_Response>) {
        if (SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.isLogin) == "true") {
            mContext?.let { Remotedatasource.current(it, true)!!.serviceListApi() }!!
                .enqueue(callBack)
        } else {
            mContext?.let { Remotedatasource.current(it, false)!!.serviceListApi() }!!
                .enqueue(callBack)
        }

    }

    fun ProductListApi(callBack: ApiCallBack<SeeallProductsResponse>, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, true)!!.listProductApi(jsonObject) }!!
            .enqueue(callBack)
    }

//    fun MyServiceRequestListApi(
//        callBack: ApiCallBack<ServiceOrderListResponse>,
//        userId: String,
//        fromDate: String,
//        toDate: String
//    ) {
//        mContext?.let { Remotedatasource.current(it, true)!!.MyServiceRequestList(userId,fromDate, toDate) }!!
//            .enqueue(callBack)
//    }

    fun MyServiceRequestListApi(
        callBack: ApiCallBack<NewOrderServiceReqResponse>,
        fromDate: String,
        toDate: String,
        page: Int,
        limit: Int
    ) {
        mContext?.let {
            Remotedatasource.current(it, true)!!.MyServiceRequestList(fromDate, toDate, page, limit)
        }!!
            .enqueue(callBack)
    }


    fun customerdealsViewApi(callBack: ApiCallBack<viewdeals_response>, id: String) {
        mContext?.let { Remotedatasource.current(it, false)!!.dealViewapi(id) }!!
            .enqueue(callBack)
    }

    fun changepasswordApi(callBack: ApiCallBack<changepasswordresponse>, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, true)!!.Changepassword(jsonObject) }!!
            .enqueue(callBack)
    }

    fun profileApi(callBack: ApiCallBack<getprofile_response>) {
        mContext?.let { Remotedatasource.current(it, true)!!.ProfileApi() }!!
            .enqueue(callBack)
    }

    fun staticDataApi(callBack: ApiCallBack<StaticContentResponse>, type: String) {
        mContext?.let { Remotedatasource.current(it, false)!!.static_content(type) }!!
            .enqueue(callBack)
    }

    fun addressListApi(callBack: ApiCallBack<AddresslistResponse>, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, true)!!.listAddressApi(jsonObject) }!!
            .enqueue(callBack)
    }


    fun Paymentfromcustomerapi(callBack: ApiCallBack<PaymentFromCustomer>, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, true)!!.PaymentFeomcustomer(jsonObject) }!!
            .enqueue(callBack)
    }

    fun transactionTowholesaler(
        callBack: ApiCallBack<PaymentFromCustomer>,
        jsonObject: JsonObject
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.transactionTowholesaler(jsonObject) }!!
            .enqueue(callBack)
    }

    fun Updateprofileapi(
        callBack: ApiCallBack<CommonResponseForAll>,
        jsonObject: EditProfileRequest
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.Updateprofileapi(jsonObject) }!!
            .enqueue(callBack)
    }

    fun Updateprofilefillformapi(
        callBack: ApiCallBack<CommonResponseForAll>,
        jsonObject: FillFormRequest
    ) {
        mContext?.let {
            Remotedatasource.current(it, true)!!.Updateprofilefillformapi(jsonObject)
        }!!
            .enqueue(callBack)
    }

    fun sp_Updateprofilefillformapi(
        callBack: ApiCallBack<CommonResponseForAll>,
        jsonObject: SP_FillFormRequest
    ) {
        mContext?.let {
            Remotedatasource.current(it, true)!!.sp_Updateprofilefillformapi(jsonObject)
        }!!
            .enqueue(callBack)
    }

    fun AddAddressapi(callBack: ApiCallBack<AddAddressResponse>, jsonObject: AddAddressRequest) {
        mContext?.let { Remotedatasource.current(it, true)!!.AddAddressapi(jsonObject) }!!
            .enqueue(callBack)
    }

    fun fillformapi(
        callBack: ApiCallBack<RetailerFillformResponse>,
        fillFormRequest: FillFormRequest
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.fillformapi(fillFormRequest) }!!
            .enqueue(callBack)
    }

    fun sp_fillformapi(
        callBack: ApiCallBack<RetailerFillformResponse>,
        fillFormRequest: SP_FillFormRequest
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.sp_fillformapi(fillFormRequest) }!!
            .enqueue(callBack)
    }

//    fun orderListapi(callBack: ApiCallBack<PendingOrderRetailerResponse>, jsonObject: JsonObject) {
//        mContext?.let { Remotedatasource.current(it, true)!!.orderListapi(jsonObject) }!!
//            .enqueue(callBack)
//    }

//    fun pendingorderapi(
//        callBack: ApiCallBack<PendingOrderRetailerResponse>,
//        jsonObject: JsonObject
//    ) {
//        mContext?.let { Remotedatasource.current(it, true)!!.pendingorder(jsonObject) }!!
//            .enqueue(callBack)
//    }

    fun markAsDoneProductApi(
        callBack: ApiCallBack<Any>,
        jsonObject: JsonObject
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.markAsDoneProduct(jsonObject) }!!
            .enqueue(callBack)
    }

    fun listCategoryServiceApi(
        callBack: ApiCallBack<ListCategoryServiceResponse>,
        jsonObject: JsonObject
    ) {
        if (SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.isLogin) == "true") {
            mContext?.let { Remotedatasource.current(it, true)!!.listCategoryServiceApi(jsonObject) }!!
                .enqueue(callBack)
        } else {
            mContext?.let { Remotedatasource.current(it, false)!!.listCategoryServiceApi(jsonObject) }!!
                .enqueue(callBack)
        }

    }

    //============================================================================
//    fun pendingstatusapi(
//        callBack: ApiCallBack<ServiceStatus_Tab_Response>,
//        jsonObject: JsonObject
//    ) {
//        mContext?.let { Remotedatasource.current(it, true)!!.pendingstatus(jsonObject) }!!
//            .enqueue(callBack)
//    }
//    ============================================================================
    fun HomeRetailerApi(callBack: ApiCallBack<Home_response>) {
        mContext?.let { Remotedatasource.current(it, true)!!.HomeRetailerApi() }!!
            .enqueue(callBack)
    }


    fun WishListApi(callBack: ApiCallBack<WishlistResponse>, page: Int, limit: Int) {
        mContext?.let { Remotedatasource.current(it, true)!!.WishlistApi(page, limit) }!!
            .enqueue(callBack)
    }

    fun Edit_Get_Profile(callBack: ApiCallBack<EditProfile_Get_Response>) {
        mContext?.let { Remotedatasource.current(it, true)!!.Edit_Get_ProfileAPI() }!!
            .enqueue(callBack)
    }

    fun addTowishlistApi(callBack: ApiCallBack<MainAddToWishListResponse>, productId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.addwishlistApi(productId) }!!
            .enqueue(callBack)

    }
    fun orderlist2api(callBack: ApiCallBack<OrderViewResponse>, orderId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.orderView2(orderId) }!!
            .enqueue(callBack)
    }

    fun subCategoryView(callBack: ApiCallBack<CustomerdealsSubcategory>, categoryId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.subCategoryViewApi(categoryId) }!!
            .enqueue(callBack)
    }


    fun subCategoryCustomer(callBack: ApiCallBack<CustomerdealsSubcategory>) {
        mContext?.let { Remotedatasource.current(it, false)!!.subCategoryCustomer() }!!
            .enqueue(callBack)
    }

    fun ViewTransactionApi(callBack: ApiCallBack<ViewTransaction_Respomse>, orderId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.ViewTransaction(orderId) }!!
            .enqueue(callBack)
    }

    fun MarkAsDoneapi(callBack: ApiCallBack<MarkAsDone_Response>, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, true)!!.MarkAsDone(jsonObject) }!!
            .enqueue(callBack)
    }


//    fun singleUploadImageApi(callBack: ApiCallBack<SingleImageUpload>, file: MultipartBody.Part?) {
//        mContext?.let { Remotedatasource.current(it, false)!!.addSingleImage(file) }!!
//            .enqueue(callBack)
//    }


    fun addMultiPartImageApi(
        callBack: ApiCallBack<MultipartImageResponse>,
        file: ArrayList<MultipartBody.Part>?
    ) {
        mContext?.let { Remotedatasource.current(it, false)!!.addMultiImage(file) }!!
            .enqueue(callBack)
    }

    fun singleUploadImageApi(callBack: ApiCallBack<SingleImageUpload>, file: MultipartBody.Part?) {
        mContext?.let { Remotedatasource.current(it, false)!!.addSingleImage(file) }!!
            .enqueue(callBack)
    }

    fun addProductApi(callBack: ApiCallBack<AddProductResponse>, jsonObject: AddProductRequest) {
        mContext?.let { Remotedatasource.current(it, true)!!.addproductApi(jsonObject) }!!
            .enqueue(callBack)
    }

    fun myProductListApi(callBack: ApiCallBack<MyProductlist>, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, true)!!.myProductListApi(jsonObject) }!!
            .enqueue(callBack)
    }

    fun faqListApi(callBack: ApiCallBack<FaqResponse>) {
        mContext?.let { Remotedatasource.current(it, true)!!.faqListApi() }!!
            .enqueue(callBack)
    }

    fun viewAddress(callBack: ApiCallBack<ViewAddressResponse>, addressId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.addressApi(addressId) }!!
            .enqueue(callBack)
    }

    fun deleteAddressApi(callBack: ApiCallBack<DeleteAddressResponse>, addressId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.deleteAddress(addressId) }!!
            .enqueue(callBack)
    }

    fun addressEditApi(callBack: ApiCallBack<EditAddressResponse>, jsonObject: AddAddressRequest) {
        mContext?.let { Remotedatasource.current(it, true)!!.editAddress(jsonObject) }!!
            .enqueue(callBack)
    }

    fun MyCartListApi(callBack: ApiCallBack<MyCartListResponse>) {
        mContext?.let { Remotedatasource.current(it, true)!!.myCartListApi() }!!
            .enqueue(callBack)
    }

    fun Dealstocustomerlistapi(callBack: ApiCallBack<DealstocustomerList_Response>) {
        mContext?.let { Remotedatasource.current(it, true)!!.DealstocustomerListApi() }!!
            .enqueue(callBack)
    }

    //    NOTIFICATION API
    fun apiNotification(callBack: ApiCallBack<listNotificationResponse>, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, true)!!.notificationApi(jsonObject) }!!
            .enqueue(callBack)
    }

    fun clearAllNotification(callBack: ApiCallBack<ClearAllNotificationResponse>) {
        mContext?.let { Remotedatasource.current(it, true)!!.clearAllNotificationApi() }!!
            .enqueue(callBack)
    }

    fun deleteNotification(callBack: ApiCallBack<Any>, id: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.deleteNotificationApi(id) }!!
            .enqueue(callBack)
    }


    //PRODUCT ENQUIRY
    fun productEnquiry(callBack: ApiCallBack<ProductEnquiryResponse>, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, true)!!.productEnquiryApi(jsonObject) }!!
            .enqueue(callBack)
    }

    // Delete Cart
    fun MyCartListDeleteApi(callBack: ApiCallBack<MyCartDeleteResponse>, id: String) {
        mContext?.let {
            Remotedatasource.current(it, true)!!.myCartListDeleteApi(id)
        }!!
            .enqueue(callBack)

    }

    // view product details in product management
    fun viewProductDetailsApi(callBack: ApiCallBack<viewProductResponse>, productId: String) {

        mContext?.let { Remotedatasource.current(it, true)!!.viewProductApi(productId) }!!
            .enqueue(callBack)
    }

    fun listSubServiceListApi(callBack: ApiCallBack<ServiceSubCategoryResponse>, id: String) {

        mContext?.let { Remotedatasource.current(it, false)!!.listSubServiceApi(id) }!!
            .enqueue(callBack)
    }

    // customer service
//=====================================================================
//    fun ListServiceNearMe(
//        callBack: ApiCallBack<ListServiceNearMeResponse>,
//        jsonObject: JsonObject
//    ) {
//        mContext?.let { Remotedatasource.current(it, false)!!.ListServiceNearMeApi(jsonObject) }!!
//            .enqueue(callBack)
//    }
//=========================================================================
    fun addAppRating_Api(callBack: ApiCallBack<AddAppRatingResponse>, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, true)!!.addAppRatingApi(jsonObject) }!!
            .enqueue(callBack)

    }

    fun viewProductDetails(callBack: ApiCallBack<WishListProductDetails>, productId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.viewProduct(productId) }!!
            .enqueue(callBack)

    }

    fun viewProductWithoutLoginDetails(
        callBack: ApiCallBack<WishListProductDetails>,
        productId: String
    ) {
        mContext?.let { Remotedatasource.current(it, false)!!.viewProduct(productId) }!!
            .enqueue(callBack)

    }


    fun addToCart(callBack: ApiCallBack<AddToCart>, jsonObject: AddToCartRequest) {
        mContext?.let { Remotedatasource.current(it, true)!!.addToCart(jsonObject) }!!
            .enqueue(callBack)
    }

//    fun Services_Service_provider_Api(
//        callBack: ApiCallBack<Services_ServiceproviderResponce>,
//        jsonObject: JsonObject
//    ) {
//        mContext?.let {
//            Remotedatasource.current(it, true)!!.Services_ServiceproviderApi(jsonObject)
//        }!!
//            .enqueue(callBack)
//
//    }


//    fun serviceListByCategory(callBack: ApiCallBack<ServiceDetailsResponse>, id: String) {
//        mContext?.let { Remotedatasource.current(it, true)!!.serviceListByCategory(id) }!!
//            .enqueue(callBack)
//    }


    fun retailerOrderListapi(
        callBack: ApiCallBack<OrderHistoryRetailerResponse>,
        jsonObject: JsonObject
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.orderListapi(jsonObject) }!!
            .enqueue(callBack)
    }

    fun orderListOfOwnapi(
        callBack: ApiCallBack<OrderHistoryRetailerResponse>,
        jsonObject: JsonObject
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.orderListOfOwnapi(jsonObject) }!!
            .enqueue(callBack)
    }


    fun CategoryList(
        callBack: ApiCallBack<CategoryListResponse>
    ) {
        if (SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.isLogin) == "true") {
            mContext?.let { Remotedatasource.current(it, true)!!.CategoryList() }!!
                .enqueue(callBack)
        } else {
            mContext?.let { Remotedatasource.current(it, false)!!.CategoryList() }!!
                .enqueue(callBack)
        }

    }

    fun myProfileApi(callBack: ApiCallBack<MyProfileResponse>) {
        mContext?.let { Remotedatasource.current(it, true)!!.myProfileApi() }!!
            .enqueue(callBack)
    }

    fun similarProductList(callBack: ApiCallBack<SimilarProducts>, id: String) {
        mContext?.let { Remotedatasource.current(it, false)!!.similarProductList(id) }!!
            .enqueue(callBack)
    }

    fun viewReview(
        callBack: ApiCallBack<ReviewOfProducts>,
        serviceId: String,
        productId: String,
        page: Int,
        limit: Int
    ) {
        mContext?.let {
            Remotedatasource.current(it, false)!!.viewReview(serviceId, productId, page, limit)
        }!!.enqueue(callBack)
    }


    fun productListFOrretailer(
        callBack: ApiCallBack<GuestProductResponse>,
        jsonObject: JsonObject
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.productListFOrretailer(jsonObject) }!!
            .enqueue(callBack)
    }


    fun ServiceProviderSignupApi(
        callBack: ApiCallBack<ServiceProviderSignup_Response>,
        signupRequest: ServiceProviderSignup_Request
    ) {
        mContext?.let {
            Remotedatasource.current(it, false)!!.ServiseProviderSignup(signupRequest)
        }!!
            .enqueue(callBack)
    }


    fun productListRetailerApi(
        callBack: ApiCallBack<GuestProductResponse>,
        jsonObject: ProductCategoryRequest
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.productListRetailer(jsonObject) }!!
            .enqueue(callBack)
    }

    fun dealListForRetailerApi(callBack: ApiCallBack<DealBannerResponse>) {
        mContext?.let { Remotedatasource.current(it, true)!!.dealListRatailer() }!!
            .enqueue(callBack)
    }

    fun customerdealsList(callBack: ApiCallBack<DealBannerResponse>, jsonObject: JsonObject) {
        if (SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.isLogin) == "true") {
            mContext?.let { Remotedatasource.current(it, true)!!.dealListapi(JsonObject()) }!!
                .enqueue(callBack)
        } else {
            mContext?.let { Remotedatasource.current(it, false)!!.dealListapi(JsonObject()) }!!
                .enqueue(callBack)
        }

    }
//==================================================
//    fun customerdealsListApi(callBack: ApiCallBack<DealBannerResponse>,jsonObject: JsonObject) {
//        mContext?.let { Remotedatasource.current(it, false)!!.dealListapi(jsonObject) }!!
//            .enqueue(callBack)
//    } api changed

    fun customerdealsListApi(callBack: ApiCallBack<DealBannerResponse>, jsonObject: JsonObject) {
        if (SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.isLogin) == "true") {
            mContext?.let { Remotedatasource.current(it, true)!!.DealListByLocation(jsonObject) }!!
                .enqueue(callBack)
        } else {
            mContext?.let { Remotedatasource.current(it, false)!!.DealListByLocation(jsonObject) }!!
                .enqueue(callBack)
        }

    }

    fun customerDealsListWithTokenApi(
        callBack: ApiCallBack<DealBannerResponse>,
        jsonObject: JsonObject
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.DealListByLocation(jsonObject) }!!
            .enqueue(callBack)
    }

    //==========================================================
    fun customerdealsListApi(callBack: ApiCallBack<DealsonCustomerResponse>) {
        if (SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.isLogin) == "true") {
            mContext?.let { Remotedatasource.current(it, true)!!.dealListapi() }!!
                .enqueue(callBack)
        } else {
            mContext?.let { Remotedatasource.current(it, false)!!.dealListapi() }!!
                .enqueue(callBack)
        }

    }

    //    fun updatecart(callBack: ApiCallBack<UpdateCart>, id:String,quantity:String){
//        mContext?.let { Remotedatasource.current(it, true)!!.updatecart(id,quantity) }!!
//            .enqueue(callBack)
//    }
    fun paymentRetailerCustomerApi(
        callBack: ApiCallBack<PaymentFromCustomer>
    ) {
        mContext?.let {
            Remotedatasource.current(it, true)!!.paymentRetailerCustomerApi()
        }!!
            .enqueue(callBack)

    }

    fun paymentRetailerWholesalerApi(
        callBack: ApiCallBack<PaymentRetailerCustomerResponse>, jsonObject: JsonObject
    ) {
        mContext?.let {
            Remotedatasource.current(it, true)!!.paymentRetailerWholesalerApi(jsonObject)
        }!!
            .enqueue(callBack)

    }
//==================================================================
//    //service request api
//    fun serviceRequestApi(
//        callBack: ApiCallBack<CreateServiceResponse>,
//        makeServiceRequest: MakeServiceRequest
//    ) {
//        mContext?.let { Remotedatasource.current(it, true)!!.serviceRequest(makeServiceRequest) }!!
//            .enqueue(callBack)
//    }
//====================================================================


    fun ProductListByAdminApi(
        callBack: ApiCallBack<AddProductByAdminResponse>,
        jsonObject: JsonObject
    ) {
        mContext?.let { Remotedatasource.current(it, false)!!.ProductListByAdmin(jsonObject) }!!
            .enqueue(callBack)
    }

    fun viewProductByAdmin(
        callBack: ApiCallBack<ViewAddProductByAdminResponse>,
        productReferenceId: String
    ) {
        mContext?.let {
            Remotedatasource.current(it, false)!!.ViewproductByAdmin(productReferenceId)
        }!!.enqueue(callBack)
    }

    fun UploadDocumentsApi(
        callBack: ApiCallBack<JsonObject>,
        user: String,
        keyType: String,
        id: String,
        userRequestStatus: String,
        flag: Int,
        completeProfile: Boolean,
        imageparts: java.util.ArrayList<MultipartBody.Part>
    ) {
        mContext?.let {
            Remotedatasource.current(it, false)!!.uploadDocuments(
                user,
                keyType,
                id,
                userRequestStatus,
                flag,
                completeProfile,
                imageparts
            )
        }!!.enqueue(callBack)
    }


    fun dealSearchApi(callBack: ApiCallBack<DealSearchResponse>, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, true)!!.dealSearchApi(jsonObject) }!!
            .enqueue(callBack)
    }

    fun dealListApi(callBack: ApiCallBack<DealListResponse>, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, true)!!.dealListApi(jsonObject) }!!
            .enqueue(callBack)
    }

    fun addDealApi(callBack: ApiCallBack<AddDealResponse>, jsonObject: AddDealRequest) {
        mContext?.let { Remotedatasource.current(it, true)!!.addDealsApi(jsonObject) }!!
            .enqueue(callBack)
    }

    fun editproductApi(callBack: ApiCallBack<EditProductResponse>, jsonObject: EditProductRequest) {
        mContext?.let { Remotedatasource.current(it, true)!!.editproduct(jsonObject) }!!
            .enqueue(callBack)
    }

    fun editDealApi(callBack: ApiCallBack<EditDealsResponse>, jsonObject: EditDealRequest) {
        mContext?.let { Remotedatasource.current(it, true)!!.editDealApi(jsonObject) }!!
            .enqueue(callBack)
    }

//    fun updateServiceUser(callBack: ApiCallBack<UpdateSubCategoryService>, servicecategoryRequst:ServicecategoryRequst){
//        mContext?.let { Remotedatasource.current(it, true)!!.updateServiceUser(servicecategoryRequst) }!!
//            .enqueue(callBack)
//    }

    fun updateServiceUser(
        callBack: ApiCallBack<UpdateSubCategoryService>,
        servicecategoryRequst: SPServiceCategoryRequest
    ) {
        mContext?.let {
            Remotedatasource.current(it, true)!!.updateServiceUser(servicecategoryRequst)
        }!!
            .enqueue(callBack)
    }

    fun NotificationCountApi(callBack: ApiCallBack<NotifiactionCountResponse>) {
        mContext?.let { Remotedatasource.current(it, true)!!.notificationCount() }!!
            .enqueue(callBack)
    }


    fun ozowPayment(
        callBack: ApiCallBack<OzowPaymentResponse>,
        orderId: String,
        jsonObject: JsonObject
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.payment(orderId, jsonObject) }!!
            .enqueue(callBack)
    }

    fun BuyProductApi(
        callBack: ApiCallBack<BuyProductResponse>,
        BuyproductRequest: BuyProductRequest
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.BuyProductApi(BuyproductRequest) }!!
            .enqueue(callBack)
    }

    fun FillDetailsView(callBack: ApiCallBack<RetailerFillDeatilsViewResponse>) {
        mContext?.let { Remotedatasource.current(it, true)!!.FillDetailsView() }!!
            .enqueue(callBack)
    }

    fun productListForCustomer(
        callBack: ApiCallBack<GuestProductResponse>,
        jsonObject: JsonObject
    ) {
        if (SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.isLogin) == "true") {
            mContext?.let { Remotedatasource.current(it, true)!!.productListFOrretailer(jsonObject) }!!
                .enqueue(callBack)
        } else {
            mContext?.let { Remotedatasource.current(it, false)!!.productListFOrretailer(jsonObject) }!!
                .enqueue(callBack)
        }


    }

    fun productMightBeLikeRetailer(
        callBack: ApiCallBack<GuestProductResponse>,
        suggestions: MightBeLikeRequest
    ) {

        mContext?.let { Remotedatasource.current(it, true)!!.productMightBeLike(suggestions) }!!
            .enqueue(callBack)
    }

    fun newProductMightBeLikeRetailer(
        callBack: ApiCallBack<GuestProductResponse>,
        suggestions: ProductMightBeLikeRequest
    ) {

        mContext?.let { Remotedatasource.current(it, true)!!.newProductMightBeLike(suggestions) }!!
            .enqueue(callBack)
    }

    fun productMightBeLikeCustomer(
        callBack: ApiCallBack<GuestProductResponse>,
        suggestions: MightBeLikeRequest
    ) {
        if (SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.isLogin) == "true") {
            mContext?.let { Remotedatasource.current(it, true)!!.productMightBeLike(suggestions) }!!
                .enqueue(callBack)
        } else {
            mContext?.let { Remotedatasource.current(it, false)!!.productMightBeLike(suggestions) }!!
                .enqueue(callBack)
        }

    }

    fun newProductMightBeLikeCustomer(
        callBack: ApiCallBack<GuestProductResponse>,
        suggestions: ProductMightBeLikeRequest
    ) {
        if (SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.isLogin) == "true") {
            mContext?.let { Remotedatasource.current(it, true)!!.newProductMightBeLike(suggestions) }!!
                .enqueue(callBack)
        } else {
            mContext?.let { Remotedatasource.current(it, false)!!.newProductMightBeLike(suggestions) }!!
                .enqueue(callBack)
        }

    }

    fun wholesaler_listApi(callBack: ApiCallBack<wholesaler_list_response>, search: JsonObject) {
        mContext?.let { Remotedatasource.current(it, false)!!.wholesalerListapi(search) }!!
            .enqueue(callBack)
    }

    fun service_requestapi(callBack: ApiCallBack<MarkAsDoneOtpResponse>, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, true)!!.servicerequestapi(jsonObject) }!!
            .enqueue(callBack)
    }

    fun viewParticularNotificationApi(
        callBack: ApiCallBack<ParticularNotificationListResponse>,
        id: String
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.viewParticularNotification(id) }!!
            .enqueue(callBack)
    }

    fun DealListByLocationApi(callBack: ApiCallBack<DealBannerResponse>, jsonObject: JsonObject) {
        if (SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.isLogin) == "true") {
            mContext?.let { Remotedatasource.current(it, true)!!.DealListByLocation(jsonObject) }!!
                .enqueue(callBack)
        } else {
            mContext?.let { Remotedatasource.current(it, false)!!.DealListByLocation(jsonObject) }!!
                .enqueue(callBack)
        }


    }

    fun serviceListByCategory(callBack: ApiCallBack<NewServicesResponseResponse>, id: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.getSelectedServicesApi(id) }!!
            .enqueue(callBack)

    }

    fun addDealServiceProviderApi(
        callBack: ApiCallBack<AddDealsOfServices>,
        jsonObject: JsonObject
    ) {
        mContext?.let {
            Remotedatasource.current(it, true)!!.addDealServiceProviderApi(jsonObject)
        }!!.enqueue(callBack)
    }


    fun servicesListApi(callBack: ApiCallBack<AddDealsCategory>, id: String, subid: String) {
        mContext?.let {
            Remotedatasource.current(it, true)!!.servicesListApi(id, subid)
        }!!.enqueue(callBack)
    }

    fun listCategoryOfServiceProvider(callBack: ApiCallBack<AddDealsCategory>) {
        mContext?.let {
            Remotedatasource.current(it, true)!!.listCategoryOfServiceProvider()
        }!!.enqueue(callBack)
    }


    fun listSubCategoryOfServiceProvider(callBack: ApiCallBack<AddDealsCategory>, id: String) {
        mContext?.let {
            Remotedatasource.current(it, true)!!.listSubCategoryOfServiceProvider(id)
        }!!.enqueue(callBack)
    }


    fun ListServiceProvideNearMeApi(
        callBack: ApiCallBack<ListOfServiceProviderResponse>,
        jsonObject: JsonObject
    ) {
        mContext?.let {
            Remotedatasource.current(it, false)!!.ListServiceProvideNearMe(jsonObject)
        }!!
            .enqueue(callBack)
    }

    fun ListServiceNearMe(
        callBack: ApiCallBack<ServicesListNearMeResponse>,
        jsonObject: JsonObject
    ) {
        mContext?.let { Remotedatasource.current(it, false)!!.ListServiceNearMeApi(jsonObject) }!!
            .enqueue(callBack)
    }

    fun serviceRequestApi(
        callBack: ApiCallBack<CreateServiceResponse>,
        makeServiceRequest: MakeServiceRequest
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.serviceRequest(makeServiceRequest) }!!
            .enqueue(callBack)
    }

    fun pendingstatusapi(
        callBack: ApiCallBack<ServicesListResponse>,
        jsonObject: JsonObject
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.pendingstatus(jsonObject) }!!
            .enqueue(callBack)
    }

    fun orderlistViewapi(callBack: ApiCallBack<ServiceListViewResponse>, orderId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.orderView(orderId) }!!
            .enqueue(callBack)
    }

    //    PendingOrderRetailerResponse
    fun pendingorderapi(
        callBack: ApiCallBack<RetailerOrderManagementResponse>,
        jsonObject: JsonObject
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.pendingorder(jsonObject) }!!
            .enqueue(callBack)
    }

    fun paymentFromCustomerNew(
        callBack: ApiCallBack<RetailerOrderManagementResponse>,
        jsonObject: JsonObject,
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.paymentFromCustomerNew(jsonObject) }!!
            .enqueue(callBack)
    }

    fun orderViewapi(callBack: ApiCallBack<ViewOrderResponse>, orderId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.orderViewApi(orderId) }!!
            .enqueue(callBack)
    }

    fun productDeliveredApi(callBack: ApiCallBack<MarkAsDoneOtpResponse>, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, true)!!.productDeliveredApi(jsonObject) }!!
            .enqueue(callBack)
    }

    fun updatecart(callBack: ApiCallBack<UpdateCart>, id: String, updateQuantity: UpdateQuantity?) {
        mContext?.let { Remotedatasource.current(it, true)!!.updatecart(id, updateQuantity) }!!
            .enqueue(callBack)
    }

    fun timeSlotApi(callBack: ApiCallBack<TimeSlotResponse>, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, true)!!.timeSlotApi(jsonObject) }!!
            .enqueue(callBack)
    }

    fun logoutApi(callBack: ApiCallBack<JsonObject>, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, true)!!.logoutApi(jsonObject) }!!
            .enqueue(callBack)
    }

    fun updatePrimaryAddressApi(callBack: ApiCallBack<UpdatePrimaryAddressResponse>, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, true)!!.updatePrimaryAddressApi(jsonObject) }!!.enqueue(callBack)
    }

    fun newBannerApi(callBack: ApiCallBack<HomeBannerResponse>) {
        mContext?.let { Remotedatasource.current(it, true)!!.newBannerApi() }!!.enqueue(callBack)
    }

    fun subcategoryListWithCategory(callBack: ApiCallBack<ProductSubCategoryResponse>,categoryId :String) {
        mContext?.let { Remotedatasource.current(it, false)!!.subcategoryListWithCategory(categoryId) }!!.enqueue(callBack)
    }
    fun serviceSubcategoryListWithCategory(callBack: ApiCallBack<ServiceSubCategoryResponse>,jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, false)!!.serviceSubcategoryListWithCategory(jsonObject) }!!.enqueue(callBack)
    }
     fun serviceListByUserId(callBack: ApiCallBack<ServiceProviderListResponse>) {
        mContext?.let { Remotedatasource.current(it, true)!!.serviceListByUserId() }!!.enqueue(callBack)
    }
     fun deliveryFeeListOption(callBack: ApiCallBack<DeliveryFessCustomerResponse>) {
        mContext?.let { Remotedatasource.current(it, true)!!.deliveryFeeListOption() }!!.enqueue(callBack)
    }
     fun pickupFeeListOption(callBack: ApiCallBack<DeliveryFessResponse>,userType :String) {
        mContext?.let { Remotedatasource.current(it, false)!!.pickupFeeListOption(userType) }!!.enqueue(callBack)
    }
     fun commonBusinessFormApi(callBack: ApiCallBack<CommonResponseForAll>, request: CommonBusinessFormRequest) {
        mContext?.let { Remotedatasource.current(it, true)!!.commonBusinessFormApi(request) }!!.enqueue(callBack)
    }
     fun updateProfileCommonBusinessFormApi(callBack: ApiCallBack<CommonResponseForAll>, request: CommonBusinessFormRequest) {
        mContext?.let { Remotedatasource.current(it, true)!!.updateProfileCommonBusinessFormApi(request) }!!.enqueue(callBack)
    }
     fun getAllOrdersApi(callBack: ApiCallBack<GetAllOrdersCommonResponse>, orderStatus:String,page: Int,limit: Int,userType:String) {
        mContext?.let { Remotedatasource.current(it, true)!!.getAllOrdersApi(orderStatus,page, limit,userType) }!!.enqueue(callBack)
    }
     fun viewAssignedOrderApi(callBack: ApiCallBack<ViewAssignedOrderResponse>, _id:String) {
        mContext?.let { Remotedatasource.current(it, true)!!.viewAssignedOrderApi(_id = _id) }!!.enqueue(callBack)
    }
     fun viewAssignedProductApi(callBack: ApiCallBack<AllRequestedProductsResponse>, _id:String,retailerId:String) {
        mContext?.let { Remotedatasource.current(it, true)!!.viewAssignedProductApi(_id = _id, retailerId = retailerId) }!!.enqueue(callBack)
    }
     fun sendOtpApi(callBack: ApiCallBack<CommonResponseForAll>, _id:String, retailerId:String) {
        mContext?.let { Remotedatasource.current(it, true)!!.sendOtpApi(_id = _id, retailerId = retailerId) }!!.enqueue(callBack)
    }
     fun verifyOtpServiceApi(callBack: ApiCallBack<VerifyOtpServicesResponse>, _id:String, retailerId:String, fieldEntityId:String, otp:String,customerId:String) {
        mContext?.let { Remotedatasource.current(it, true)!!.verifyOtpServiceApi(_id = _id, retailerId = retailerId, fieldEntityId = fieldEntityId, otp = otp,customerId = customerId) }!!.enqueue(callBack)
    }
     fun rejectOrderApi(callBack: ApiCallBack<CommonResponseForAll>, _id:String, rejectedReason:String) {
        mContext?.let { Remotedatasource.current(it, true)!!.rejectOrderApi(_id = _id, rejectedReason = rejectedReason) }!!.enqueue(callBack)
    }
     fun proceedToCheckOutApi(callBack: ApiCallBack<CommonResponseForAll>, deliveryType:String) {
        mContext?.let { Remotedatasource.current(it, true)!!.proceedToCheckOutApi(deliveryType = deliveryType) }!!.enqueue(callBack)
    }
     fun outForDeliveryApi(callBack: ApiCallBack<CommonResponseForAll>, _id:String) {
        mContext?.let { Remotedatasource.current(it, true)!!.outForDeliveryApi(_id = _id) }!!.enqueue(callBack)
    }
     fun getWalletTransactionApi(callBack: ApiCallBack<MyEarningResponse>, page: Int,limit:Int) {
        mContext?.let { Remotedatasource.current(it, true)!!.getWalletTransactionApi(page= page, limit = limit,isCommission = true) }!!.enqueue(callBack)
    }
     fun listUserFeeApi(callBack: ApiCallBack<FeeListResponse>) {
        mContext?.let { Remotedatasource.current(it, true)!!.listUserFeeApi() }!!.enqueue(callBack)
    }
     fun listUserFeeByUserIdAPI(callBack: ApiCallBack<FeeListResponse>) {
        mContext?.let { Remotedatasource.current(it, true)!!.listUserFeeByUserIdAPI() }!!.enqueue(callBack)
    }
     fun listUserStorageFeeAPI(callBack: ApiCallBack<FeeListResponse>) {
        mContext?.let { Remotedatasource.current(it, true)!!.listUserStorageFeeAPI() }!!.enqueue(callBack)
    }
     fun updateUserFeeDetail(callBack: ApiCallBack<CommonResponseForAll>,userType:String,_id:String,pickupFee:String) {
        mContext?.let { Remotedatasource.current(it, true)!!.updateUserFeeDetail(userType = userType, _id= _id, pickupFee= pickupFee)}!!.enqueue(callBack)
    }
     fun updateFeeDetailDeliveryDriverApi(callBack: ApiCallBack<CommonResponseForAll>,_id:String,standardFee:String,deliveryFee:String) {
        mContext?.let { Remotedatasource.current(it, true)!!.updateFeeDetailDeliveryDriverApi(_id= _id, standardFee =standardFee , deliveryFee =deliveryFee)}!!.enqueue(callBack)
    }
     fun updateStorageFeeDetailApi(callBack: ApiCallBack<CommonResponseForAll>,_id:String,dailyFee:String, monthlyFee:String, weeklyFee:String, quarterlyFee:String, minimumFee:String, maximumFee:String) {
        mContext?.let { Remotedatasource.current(it, true)!!.updateStorageFeeDetailApi(_id= _id, daily =dailyFee , monthly =monthlyFee,
            quarterly= quarterlyFee, weekly = weeklyFee,maximumSize= maximumFee , minimumSize = minimumFee)}!!.enqueue(callBack)
    }
     fun reAssignDeliveryApi(callBack: ApiCallBack<CommonResponseForAll>,id:String) {
        mContext?.let { Remotedatasource.current(it, true)!!.reAssignDeliveryApi(id= id)}!!.enqueue(callBack)
    }
     fun allServiceListApi(callBack: ApiCallBack<ServiceConfigResponse>) {
        mContext?.let { Remotedatasource.current(it, flag = false)!!.allServiceListApi()}!!.enqueue(callBack)
    }
     fun updateServiceUserApi(callBack: ApiCallBack<CommonResponseForAll>,request: List<ServiceFeeConfigRequest>,flag: Int) {
        mContext?.let { Remotedatasource.current(it, flag = true)!!.updateServiceUserApi(request = request,flag = flag)}!!.enqueue(callBack)
    }
     fun selectServiceApi(callBack: ApiCallBack<CommonResponseForAll>,serviceArray: List<String>,flag: Int) {
        mContext?.let { Remotedatasource.current(it, flag = true)!!.selectServiceApi(serviceArray = serviceArray,flag)}!!.enqueue(callBack)
    }
     fun mySelectedServiceListApi(callBack: ApiCallBack<SelectedServiceResponse>,) {
        mContext?.let { Remotedatasource.current(it, flag = true)!!.mySelectedServiceListApi()}!!.enqueue(callBack)
    }
     fun documentListApi(callBack: ApiCallBack<DocumentListResponse>,) {
        mContext?.let { Remotedatasource.current(it, flag = true)!!.documentListApi()}!!.enqueue(callBack)
    }
     fun deleteUserApi(callBack: ApiCallBack<JsonObject>,) {
        mContext?.let { Remotedatasource.current(it, flag = true)!!.deleteUserApi()}!!.enqueue(callBack)
    }
     fun homepageApi(callBack: ApiCallBack<HomePageResponse>, lat:Double, lng:Double,dealType:String,userType:String, page:Int,limit: Int,search:String) {
         if(SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.isLogin) == "true") {
             mContext?.let { Remotedatasource.current(it, flag = true)!!.homepageApi(lat=lat, lng=lng, dealType=dealType, userType=userType, page=page, limit=limit, search=search)}!!.enqueue(callBack)
         }else{
             mContext?.let { Remotedatasource.current(it, flag = false)!!.homepageApi(lat=lat, lng=lng, dealType=dealType, userType=userType, page=page, limit=limit, search=search)}!!.enqueue(callBack)
         }

    }


    fun getCustomerWalletTransactionApi(callBack: ApiCallBack<MyEarningResponse>,page:Int,limit:Int) {
        mContext?.let { Remotedatasource.current(it, flag = true)!!.getWalletTransactionApi(page, limit)}!!.enqueue(callBack)
    }


    fun socialSignInApi(callBack: ApiCallBack<SocialSignInResponse>,userType: String,email: String,
                        profilePic: String,firstName: String,lastName: String,deviceType: String,deviceToken: String,googleId: String,) {
        mContext?.let { Remotedatasource.current(it, flag = false)!!.socialSignInApi(userType, email, profilePic, firstName, lastName, deviceType, deviceToken, googleId)}!!.enqueue(callBack)
    }


    fun addInterestPriceOnProduct(callBack: ApiCallBack<CommonResponseForAll>,interestedPrice: String,quantity: String,
                                  productId: String,productSizeId: String,unit: String,value: String,spordicType: String) {
        mContext?.let { Remotedatasource.current(it, flag = true)!!.addInterestPriceOnProduct(interestedPrice,quantity, productId, productSizeId, unit, value, spordicType)}!!.enqueue(callBack)
    }


    fun ozowCheckOutWalletOrderApi(
        callBack: ApiCallBack<OzowPaymentResponse>,
        jsonObject: JsonObject
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.ozowCheckOutWalletOrderApi( jsonObject) }!!
            .enqueue(callBack)
    }


    fun getIntrestedPriceList(
        callBack: ApiCallBack<CampaignListResponse>,
        page:Int, limit:Int,campaignOn:String
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.getIntrestedPriceList(page, limit, campaignOn) }!!
            .enqueue(callBack)
    }


    fun addInterestPriceOnService(
        callBack: ApiCallBack<CommonResponseForAll>,
        serviceId:String, categoryId:String,interestedPrice:String,price:String,spordicType:String
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.addInterestPriceOnService(serviceId,  interestedPrice, price, spordicType) }!!
            .enqueue(callBack)
    }


    fun addCampaignApi(
        callBack: ApiCallBack<CommonResponseForAll>,
        request: AddCampaignOnProductRequest, idForView:String) {
        mContext?.let { Remotedatasource.current(it, true)!!.addCampaignApi(request,idForView) }!!
            .enqueue(callBack)
    }

    fun addCampaignServiceApi(
        callBack: ApiCallBack<CommonResponseForAll>,
        request: AddCampaignOnServiceRequest) {
        mContext?.let { Remotedatasource.current(it, true)!!.addCampaignServiceApi(request) }!!
            .enqueue(callBack)
    }


    fun listCampaignApi(
        callBack: ApiCallBack<AddedCampaignListResponse>,
        page:Int, limit:Int,campaignOn:String) {
        mContext?.let { Remotedatasource.current(it, true)!!.listCampaignApi(page, limit, campaignOn) }!!
            .enqueue(callBack)
    }


    fun viewCampaignApi(
        callBack: ApiCallBack<ViewProductCampaignResponse>,id:String) {
        mContext?.let { Remotedatasource.current(it, true)!!.viewCampaignApi(id) }!!
            .enqueue(callBack)
    }


    fun checkOutWalletOrderApi(
        callBack: ApiCallBack<CommonResponseForAll>,orderId:String,cancelUrl:String,errorUrl:String,successUrl:String,testMode:Boolean) {
        mContext?.let { Remotedatasource.current(it, true)!!.checkOutWalletOrderApi(orderId, cancelUrl, errorUrl, successUrl, testMode) }!!
            .enqueue(callBack)
    }


    fun submitFeedbackApi(
        callBack: ApiCallBack<CommonResponseForAll>,request: FeedBackDataRequest) {
        mContext?.let { Remotedatasource.current(it, true)!!.submitFeedbackApi(request) }!!
            .enqueue(callBack)
    }


    fun getFeedBackByOrderIdApi(
        callBack: ApiCallBack<FeedbackResponse>,userType: String,orderId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.getFeedBackByOrderIdApi(userType, orderId) }!!
            .enqueue(callBack)
    }

    fun getInvoiceApi(
        callBack: ApiCallBack<GetInvoiceDownloadResponse>,orderId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.getInvoiceApi(orderId) }!!
            .enqueue(callBack)
    }

    fun updateServiceStatusApi(
        callBack: ApiCallBack<CommonResponseForAll>,status: String,orderId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.updateServiceStatusApi(status,orderId) }!!
            .enqueue(callBack)
    }

    fun checkoutPayFastApi(
        callBack: ApiCallBack<PayFastResponse>,orderId: String, jsonObject: JsonObject) {
        mContext?.let { Remotedatasource.current(it, true)!!.checkoutPayFastApi(orderId,jsonObject) }!!
            .enqueue(callBack)
    }


}