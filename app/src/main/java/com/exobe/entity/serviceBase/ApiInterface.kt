package com.exobe.entity.serviceBase


import com.exobe.entity.request.*
import com.exobe.entity.response.*
import com.exobe.entity.response.customer.*
import com.exobe.entity.request.AddProductRequest
import com.exobe.entity.request.FillFormRequest
import com.exobe.entity.request.SignupRequest
import com.exobe.entity.response.addproduct.AddProductResponse
import com.exobe.entity.response.addproduct.MultipartImageResponse
import com.exobe.entity.response.customer.AddToCart
import com.exobe.entity.response.customer.ReviewOfProducts
import com.exobe.entity.response.customer.SimilarProducts
import com.exobe.entity.response.customer.WishListProductDetails
import com.exobe.entity.response.customerservices.*
import com.exobe.entity.response.customerservices.CreateServiceResponse
import com.exobe.entity.response.customerservices.ListCategoryServiceResponse
import com.exobe.entity.response.imageupload.SingleImageUpload
import com.exobe.entity.response.product.DealBannerResponse
import com.exobe.entity.response.product.GuestProductResponse
import com.exobe.entity.response.serviceTab.*
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.http.*


interface ApiInterface {

    @POST("user/signUp")
    fun SignupApi(@Body signupRequest: SignupRequest, @Query("isSocial") isSocial:Boolean): retrofit2.Call<SignupResponse>?

    @POST("user/uploadFile")
    fun uploadfileApi(@Body uploadfile: SignupRequest): retrofit2.Call<SignupResponse>?


    @PUT("user/updateProfile")
    fun EditProfileApi(@Body signupRequest: EditProfileRequest): retrofit2.Call<EditProfile_Response>?

    @POST("user/usersLogin")
    fun LoginApi(@Body jsonObject: JsonObject): retrofit2.Call<LoginResponse>?

    @GET("user/homePage")
    fun HomeApi(): retrofit2.Call<Home_response>?

    @POST("user/forgotPassword")
    fun forgotpasswordApi(@Body jsonObject: JsonObject): retrofit2.Call<forgotpassword_response>?

    @POST("user/verifyOTP")
    fun otpApi(@Body jsonObject: JsonObject): retrofit2.Call<otpverfication_response>?

    @POST("user/resendOTP")
    fun ResendotpApi(@Body jsonObject: JsonObject): retrofit2.Call<ResendOtp>?

    @PUT("user/resetPassword")
    fun ResetPasswordApi(@Body jsonObject: JsonObject): retrofit2.Call<resetpassword_response>?

    @POST("product/productListByCategory")
    fun productListByCategoryApi(@Body jsonObject: JsonObject): retrofit2.Call<SubCategorySearchResponse>?

    @POST("admin/listCategory")
    fun listCategoryApi(@Body jsonObject: JsonObject): retrofit2.Call<CategoryList_response>?

    @POST("admin/listCategory")
    fun listCategorySearchApi(@Body jsonObject: JsonObject): retrofit2.Call<CategoryList_response>?

    @GET("admin/subcategoryListWithCategory")
    fun listSubCategoryApi(@Query("categoryId") id: String): retrofit2.Call<SubCategoryResponse>?

    @POST("service/serviceList")
    fun serviceListApi(): retrofit2.Call<ServiceList_Response>?

    @POST("service/listCategoryService")
    fun listCategoryServiceApi(@Body jsonObject: JsonObject): retrofit2.Call<ListCategoryServiceResponse>?


    @POST("admin/AllProductList")
    fun listProductApi(@Body jsonObject: JsonObject): retrofit2.Call<SeeallProductsResponse>?

    //    @FormUrlEncoded
//    @POST("order/serviceOrderlist")
//    fun MyServiceRequestList(
//        @Field("userId") userId: String,
//        @Field("fromDate") fromDate: String,
//        @Field("toDate") toDate: String
//    ): retrofit2.Call<ServiceOrderListResponse>?
//
    @FormUrlEncoded
    @POST("order/orderServiceList")
    fun MyServiceRequestList(
        @Field("fromDate") fromDate: String, @Field("toDate") toDate: String, @Field("page") page: Int, @Field("limit") limit: Int
    ): retrofit2.Call<NewOrderServiceReqResponse>?


    @GET("deal/viewDeal")
    fun dealViewapi(
        @Query("dealId") id: String
    ): retrofit2.Call<viewdeals_response>?

    @PUT("user/changePassword")
    fun Changepassword(@Body jsonObject: JsonObject): retrofit2.Call<changepasswordresponse>?

    @GET("user/myProfile")
    fun ProfileApi(): retrofit2.Call<getprofile_response>?

    @GET("static/viewStaticContent")
    fun static_content(@Query("type") type: String): retrofit2.Call<StaticContentResponse>?

    @POST("user/listAddress")
    fun listAddressApi(@Body jsonObject: JsonObject): retrofit2.Call<AddresslistResponse>?

    @POST("order/paymentFromCustomer")
    fun PaymentFeomcustomer(@Body jsonObject: JsonObject): retrofit2.Call<PaymentFromCustomer>?

    @POST("order/transactionToWholeSaler")
    fun transactionTowholesaler(@Body jsonObject: JsonObject): retrofit2.Call<PaymentFromCustomer>?

    @GET("user/myProfile")
    fun MyProfileApi(): retrofit2.Call<MyProfileResponse>?

    @PUT("user/updateProfile")
    fun Updateprofileapi(@Body jsonObject: EditProfileRequest): retrofit2.Call<CommonResponseForAll>?

    @PUT("user/updateProfile")
    fun Updateprofilefillformapi(@Body jsonObject: FillFormRequest): retrofit2.Call<CommonResponseForAll>?

    @PUT("user/updateProfile")
    fun sp_Updateprofilefillformapi(@Body jsonObject: SP_FillFormRequest): retrofit2.Call<CommonResponseForAll>?

    @POST("user/addAddress")
    fun AddAddressapi(@Body jsonObject: AddAddressRequest): retrofit2.Call<AddAddressResponse>?

    @POST("user/fillDetails")
    fun fillformapi(@Body fillFormRequest: FillFormRequest): retrofit2.Call<RetailerFillformResponse>?

    @POST("user/fillDetails")
    fun sp_fillformapi(@Body fillFormRequest: SP_FillFormRequest): retrofit2.Call<RetailerFillformResponse>?

    @POST("order/orderList")
    fun orderListapi(@Body jsonObject: JsonObject): retrofit2.Call<OrderHistoryRetailerResponse>?


    @POST("order/orderListOfOwn")
    fun orderListOfOwnapi(@Body jsonObject: JsonObject): retrofit2.Call<OrderHistoryRetailerResponse>?


    @POST("admin/listCategory")
    fun CategoryList(): retrofit2.Call<CategoryListResponse>?

    //=================================
//    @GET("deal/listCategoryOfServiceProvider")
//    fun listCategoryOfServiceProvider(): retrofit2.Call<AddDealsCategory>
//
//    @GET("deal/listSubCategoryOfServiceProvider")
//    fun listSubCategoryOfServiceProvider(@Query("categoryId") id: String): retrofit2.Call<AddDealsCategory>
//
//
//    @POST("deal/dealsAddForService")
//    fun AddDealsServices(@Body jsonObject: JsonObject): retrofit2.Call<AddDealsOfServices>
//=================================
//========================================
//    @POST("order/retailerOrderList")
//    fun pendingorder(@Body jsonObject: JsonObject): retrofit2.Call<PendingOrderRetailerResponse>?
//========================================
    @POST("user/productMarkAsDone")
    fun markAsDoneProduct(@Body jsonObject: JsonObject): retrofit2.Call<Any>?

//    ===========================================================
//    @POST("order/serviceOrderList")
//    fun pendingstatus(@Body jsonObject: JsonObject): retrofit2.Call<ServiceStatus_Tab_Response>?
//============================================================

    @GET("user/homePageForRetailer")
    fun HomeRetailerApi(): retrofit2.Call<Home_response>?

    @POST("product/addProduct")
    fun addproductApi(@Body jsonObject: AddProductRequest): retrofit2.Call<AddProductResponse>?

    @GET("user/wishList")
    fun WishlistApi(@Query("page") page: Int, @Query("limit") limit: Int): retrofit2.Call<WishlistResponse>?

    @GET("user/myProfile")
    fun Edit_Get_ProfileAPI(): retrofit2.Call<EditProfile_Get_Response>?


    @POST("user/addWishListProduct")
    fun addwishlistApi(@Query("productId") productId: String): retrofit2.Call<MainAddToWishListResponse>?
//==========================================================
//    @GET("order/viewOrder")
//    fun orderView(@Query("orderId") orderId: String): retrofit2.Call<ServiceProvider_Orderview_Response>?
//==============================================================
//    ========================================
//    @GET("order/viewOrder")
//    fun orderViewApi(@Query("orderId") orderId: String): retrofit2.Call<ViewOrderResponse>?
//    =========================================

    @GET("order/viewOrder")
    fun orderView2(@Query("orderId") orderId: String): retrofit2.Call<OrderViewResponse>?

    @GET("deal/dealsListOnCategory")
    fun subCategoryCustomer(): retrofit2.Call<CustomerdealsSubcategory>?

    @GET("deal/dealsListServiceParticular")
    fun subCategoryViewApi(@Query("categoryId") categoryId : String): retrofit2.Call<CustomerdealsSubcategory>?

    @GET("order/viewOrderTransaction")
    fun ViewTransaction(@Query("orderId") orderId: String): retrofit2.Call<ViewTransaction_Respomse>?

    @POST("user/serviceMarkAsDone")
    fun MarkAsDone(@Body jsonObject: JsonObject): retrofit2.Call<MarkAsDone_Response>?


//    @POST("product/myProductList")
//    fun myProductListApi(): retrofit2.Call<MyProductlist>?


    @POST("product/productOfOwn")
    fun myProductListApi(@Body jsonObject: JsonObject): retrofit2.Call<MyProductlist>?

    @Multipart
    @POST("user/uploadMultipleFile")
    fun addMultiImage(@Part file: ArrayList<MultipartBody.Part>?): retrofit2.Call<MultipartImageResponse>?

    @Multipart
    @POST("user/uploadFile")
    fun addSingleImage(@Part file: MultipartBody.Part?): retrofit2.Call<SingleImageUpload>?


    @GET("static/faqList")
    fun faqListApi(): retrofit2.Call<FaqResponse>

    @GET("user/viewAddress")
    fun addressApi(@Query("addressId") addressId: String): retrofit2.Call<ViewAddressResponse>?

    @DELETE(" user/deleteAddress")
    fun deleteAddress(@Query("addressId") addressId: String): retrofit2.Call<DeleteAddressResponse>?

    @PUT("user/editAddress")
    fun editAddress(@Body jsonObject: AddAddressRequest): retrofit2.Call<EditAddressResponse>?


    @PUT("user/markAddressPrimary")
    fun updatePrimaryAddressApi(@Body jsonObject: JsonObject): retrofit2.Call<UpdatePrimaryAddressResponse>?


    @GET("user/cartList")
    fun myCartListApi(): retrofit2.Call<MyCartListResponse>

    @GET("deal/dealsServiceCategoryPartucular")
    fun DealstocustomerListApi(): retrofit2.Call<DealstocustomerList_Response>

    //NOTIFICATION API
    @POST("notification/listNotification")
    fun notificationApi(@Body jsonObject: JsonObject): retrofit2.Call<listNotificationResponse>?

    @GET("notification/clearNotification")
    fun clearAllNotificationApi(): retrofit2.Call<ClearAllNotificationResponse>?

    @DELETE("notification/deleteNotification")
    fun deleteNotificationApi(@Query("_id") _id: String): retrofit2.Call<Any>?

    // Delete Department  API
    @DELETE("user/removeItemFromCart")
    fun myCartListDeleteApi(@Query("_id") id: String): retrofit2.Call<MyCartDeleteResponse>?

    @POST("product/viewProduct")
    fun viewProductApi(@Query("productId") productId: String): retrofit2.Call<viewProductResponse>?

    @PUT("user/productInquiry")
    fun productEnquiryApi(@Body jsonObject: JsonObject): retrofit2.Call<ProductEnquiryResponse>?

    //CUSTOMER SERVICE
    @GET("service/serviceListByCategory")
    fun listSubServiceApi(@Query("categoryId") id: String): retrofit2.Call<ServiceSubCategoryResponse>?

    //================================================================
//    @POST("user/listServiceNearMe")
//    fun ListServiceNearMeApi(@Body jsonObject: JsonObject): retrofit2.Call<ListServiceNearMeResponse>?
//=================================================================
    // add App Rating Api
    @POST("user/addAppRating")
    fun addAppRatingApi(@Body jsonObject: JsonObject): retrofit2.Call<AddAppRatingResponse>?

    @POST("product/viewProduct")
    fun viewProduct(@Query("productId") productId: String): retrofit2.Call<WishListProductDetails>?

    //=============================================
//    @POST("user/productAddToCart")
//    fun addToCart(@Body jsonObject: AddToCartRequest): retrofit2.Call<AddTocart>
//===============================================
    @POST("service/listCategoryService")
    fun Services_ServiceproviderApi(@Body jsonObject: JsonObject): retrofit2.Call<Services_ServiceproviderResponce>?
//========================================================================================================
//    @GET("service/selectedServices")
//    fun serviceListByCategory(@Query("categoryId") id: String): retrofit2.Call<ServicesServiceproviderResponce>
//===========================================================================================================
//    @POST("order/orderList")
//    fun retailerOrderListapi(@Body jsonObject: JsonObject): retrofit2.Call<PendingOrderRetailerResponse>?

    @Multipart
    @POST("user/uploadFile")
    fun addSingleImageService(@Part file: MultipartBody.Part?): retrofit2.Call<DocumentResponse>?

    @GET("user/listCountry")
    fun CountryList(): retrofit2.Call<CountryListResponse>?

    @GET("user/listState")
    fun StateList(@Query("countryCode") isocode: String): retrofit2.Call<StateListResponse>?

    @GET("user/listCity")
    fun CityList(
        @Query("countryCode") isocode: String, @Query("stateCode") isoState: String
    ): retrofit2.Call<CityListResponse>?


    @GET("user/myProfile")
    fun myProfileApi(): retrofit2.Call<MyProfileResponse>

    @GET("product/similarProductList")
    fun similarProductList(@Query("productId") id: String): retrofit2.Call<SimilarProducts>

    @GET("user/viewReview")
    fun viewReview(
        @Query("serviceId") serviceId: String,
        @Query("productId") productId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): retrofit2.Call<ReviewOfProducts>

    @POST("product/productMightLike")
    fun productMightLike(@Body suggestions: Suggestions): retrofit2.Call<SuggestionResponse>


    @POST("user/signUp")
    fun ServiseProviderSignup(@Body signupRequest: ServiceProviderSignup_Request): retrofit2.Call<ServiceProviderSignup_Response>?


    @POST("deal/listDeal")
    fun dealListapi(@Body jsonObject: JsonObject): retrofit2.Call<DealBannerResponse>?


    @GET("deal/dealsOnserviceCategoryList")
    fun dealListapi(): retrofit2.Call<DealsonCustomerResponse>?

    @POST("deal/listDeal")
    fun dealListBannerApi(@Body jsonObject: JsonObject): retrofit2.Call<DealBannerResponse>?


    // product list for guest user

    @POST("product/productList")
    fun productListForGuest(@Body jsonObject: ProductCategoryRequest): retrofit2.Call<GuestProductResponse>


    @POST("product/listProductBySortion")
    fun productListFOrretailer(@Body jsonObject: JsonObject): retrofit2.Call<GuestProductResponse>

    @POST("product/listProductBySortion")
    fun productMightBeLike(@Body suggestions: MightBeLikeRequest): retrofit2.Call<GuestProductResponse>

    @POST("product/listProductBySortion")
    fun newProductMightBeLike(@Body suggestions: ProductMightBeLikeRequest): retrofit2.Call<GuestProductResponse>

    @POST("product/productListForRetailer")
    fun productListRetailer(@Body jsonObject: ProductCategoryRequest): retrofit2.Call<GuestProductResponse>

    @POST("deal/dealListForRetailer")
    fun dealListRatailer(): retrofit2.Call<DealBannerResponse>?

    @POST("order/transactionFromCustomer")
    fun paymentRetailerCustomerApi(): retrofit2.Call<PaymentFromCustomer>?

    @POST("order/transactionToWholeSaler")
    fun paymentRetailerWholesalerApi(@Body jsonObject: JsonObject): retrofit2.Call<PaymentRetailerCustomerResponse>?


    @POST("admin/productListByAdmin")
    fun ProductListByAdmin(
        @Body jsonObject: JsonObject
    ): retrofit2.Call<AddProductByAdminResponse>

    @GET("admin/viewProductByAdmin")
    fun ViewproductByAdmin(@Query("productReferenceId") productReferenceId: String): retrofit2.Call<ViewAddProductByAdminResponse>


    @Multipart
    @POST("user/uploadsFile")
    fun uploadDocuments(
        @Query("type") type: String,
        @Query("keyType") keyType: String,
        @Query("_id") _id: String,
        @Query("userRequestStatus") userRequestStatus: String,
        @Query("flag") flag: Int,
        @Query("completeProfile") completeProfile: Boolean,
        @Part file: ArrayList<MultipartBody.Part>?
    ): retrofit2.Call<JsonObject>

    @POST("product/productOfOwn")
    fun dealSearchApi(@Body jsonObject: JsonObject): retrofit2.Call<DealSearchResponse>?

    @POST("deal/dealListOfParticular")
    fun dealListApi(@Body jsonObject: JsonObject): retrofit2.Call<DealListResponse>?

    @POST("deal/dealAdd")
    fun addDealsApi(@Body jsonObject: AddDealRequest): retrofit2.Call<AddDealResponse>?

    @PUT("deal/editDeal")
    fun editDealApi(@Body jsonObject: EditDealRequest): retrofit2.Call<EditDealsResponse>?

    @PUT("product/editProduct")
    fun editproduct(@Body jsonObject: EditProductRequest): retrofit2.Call<EditProductResponse>?

    //================================================================================================================
//    @POST("service/updateServiceUser")
//    fun updateServiceUser(@Body servicecategoryRequst: SP_ServicecategoryRequst): retrofit2.Call<UpdateSubCategoryService>
//==================================================================================================================
    @GET("notification/notificationCount")
    fun notificationCount(): retrofit2.Call<NotifiactionCountResponse>?

    @POST("order/ozowCheckOut")
    fun payment(
        @Query("orderId") orderId: String, @Body jsonObject: JsonObject
    ): retrofit2.Call<OzowPaymentResponse>

    @POST("order/buyProduct")
    fun BuyProductApi(@Body BuyProductRequest: BuyProductRequest): retrofit2.Call<BuyProductResponse>

    @GET("user/myProfile")
    fun FillDetailsView(): retrofit2.Call<RetailerFillDeatilsViewResponse>

    @POST("user/wholeSalerList")
    fun wholesalerListapi(@Body search: JsonObject): retrofit2.Call<wholesaler_list_response>?

    @PUT("service/verifyServiceRequest")
    fun servicerequestapi(@Body jsonObject: JsonObject): retrofit2.Call<MarkAsDoneOtpResponse>?

    @GET("notification/viewParticularNotification")
    fun viewParticularNotification(@Query("_id") _id: String): retrofit2.Call<ParticularNotificationListResponse>

    @POST("deal/listDealByLocation")
    fun DealListByLocation(@Body jsonObject: JsonObject): retrofit2.Call<DealBannerResponse>

    //new section api of services
    @GET("v2/service/selectedServices")
    fun getSelectedServicesApi(@Query("categoryId") id: String): retrofit2.Call<NewServicesResponseResponse>

    @POST("v2/service/updateServiceUser2")
    fun updateServiceUser(@Body servicecategoryRequst: SPServiceCategoryRequest): retrofit2.Call<UpdateSubCategoryService>

    @POST("deal/v2/dealsAddForService")
    fun addDealServiceProviderApi(@Body jsonObject: JsonObject): retrofit2.Call<AddDealsOfServices>

    @GET("v2/service/myServiceList")
    fun servicesListApi(
        @Query("categoryId") categoryId: String,
        @Query("subCategoryId") subCategoryId: String
    ): retrofit2.Call<AddDealsCategory>

    @GET("deal/v2/listCategoryOfServiceProvider")
    fun listCategoryOfServiceProvider(): retrofit2.Call<AddDealsCategory>

    @GET("deal/v2/listSubCategoryOfServiceProvider")
    fun listSubCategoryOfServiceProvider(@Query("categoryId") id: String): retrofit2.Call<AddDealsCategory>

    @POST("v2/service/serviceOrderlist")
    fun pendingstatus(@Body jsonObject: JsonObject): retrofit2.Call<ServicesListResponse>?

    @GET("v2/service/viewOrder")
    fun orderView(@Query("orderId") orderId: String): retrofit2.Call<ServiceListViewResponse>?

    //new section of customer service provider flow

    @POST("v2/service/listServiceProviderNearMe")
    fun ListServiceProvideNearMe(
        @Body jsonObject: JsonObject
    ): retrofit2.Call<ListOfServiceProviderResponse>

    @POST("v2/service/listServiceNearMe")
    fun ListServiceNearMeApi(@Body jsonObject: JsonObject): retrofit2.Call<ServicesListNearMeResponse>?


    @POST("v2/service/buyService")
    fun serviceRequest(@Body makeServiceRequest: MakeServiceRequest): retrofit2.Call<CreateServiceResponse>


    @POST("order/v2/retailerOrderList")
    fun pendingorder(@Body jsonObject: JsonObject): retrofit2.Call<RetailerOrderManagementResponse>?

    @POST("order/v2/retailerOrderList")
    fun paymentFromCustomerNew(@Body jsonObject: JsonObject): retrofit2.Call<RetailerOrderManagementResponse>?


    @GET("order/v2/viewOrder")
    fun orderViewApi(@Query("orderId") orderId: String): retrofit2.Call<ViewOrderResponse>?


    @POST("user/verifyDeliveryStatus")
    fun productDeliveredApi(@Body jsonObject: JsonObject): retrofit2.Call<MarkAsDoneOtpResponse>?


    @POST("user/v2/productAddToCart")
    fun addToCart(@Body jsonObject: AddToCartRequest): retrofit2.Call<AddToCart>


    @PUT("user/updateCartItem")
    fun updatecart(
        @Query("_id") id: String, @Body updateQuantity: UpdateQuantity?
    ): retrofit2.Call<UpdateCart>


    @POST("service/makeSlots")
    fun timeSlotApi(
        @Body jsonObject: JsonObject?
    ): retrofit2.Call<TimeSlotResponse>


    @POST("user/logout")
    fun logoutApi(
        @Body jsonObject: JsonObject?
    ): retrofit2.Call<JsonObject>

    @POST("admin/listBanner")
    fun newBannerApi(): retrofit2.Call<HomeBannerResponse>?



    @GET("admin/subcategoryListWithCategory")
    fun subcategoryListWithCategory(@Query("categoryId") categoryId :String): retrofit2.Call<ProductSubCategoryResponse>?

    @POST("admin/listSubCategoryService")
    fun serviceSubcategoryListWithCategory(@Body jsonObject: JsonObject): retrofit2.Call<ServiceSubCategoryResponse>?


    @GET("v2/service/serviceListByUserId")
     fun serviceListByUserId():retrofit2.Call<ServiceProviderListResponse>


     @GET("deliveryFee/listOption")
     fun deliveryFeeListOption():retrofit2.Call<DeliveryFessCustomerResponse>


     @GET("user/listFeeAll")
     fun pickupFeeListOption(@Query("userType")userType :String):retrofit2.Call<DeliveryFessResponse>



    @POST("user/fillDetails")
    fun commonBusinessFormApi(@Body request: CommonBusinessFormRequest): retrofit2.Call<CommonResponseForAll>



    @PUT("user/updateProfile")
    fun updateProfileCommonBusinessFormApi(@Body request: CommonBusinessFormRequest): retrofit2.Call<CommonResponseForAll>



    @FormUrlEncoded
    @POST("deliveryOrders/getAllOrders")
    fun getAllOrdersApi(@Query("orderStatus")OrderStatus :String,@Field("page")page:Int,@Field("limit")limit:Int,@Field("userType")userType:String):retrofit2.Call<GetAllOrdersCommonResponse>


    @GET("deliveryOrders/viewAssignedOrder")
    fun viewAssignedOrderApi(@Query("_id")_id:String):retrofit2.Call<ViewAssignedOrderResponse>



    @GET("deliveryOrders/viewAssignedProduct")
    fun viewAssignedProductApi(@Query("_id")_id:String,@Query("retailerId")retailerId:String):retrofit2.Call<AllRequestedProductsResponse>


    @GET("deliveryOrders/sendOtp")
    fun sendOtpApi(@Query("_id")_id:String,@Query("retailerId")retailerId:String):retrofit2.Call<CommonResponseForAll>

    @FormUrlEncoded
    @POST("deliveryOrders/verifyOtp")
    fun verifyOtpServiceApi(@Query("_id")_id:String,@Query("retailerId")retailerId:String,@Query("fieldEntityId")fieldEntityId:String,
                            @Field("otp")otp:String,@Query("customerId")customerId:String):retrofit2.Call<VerifyOtpServicesResponse>


    @FormUrlEncoded
    @PUT("deliveryOrders/rejectOrder")
    fun rejectOrderApi(@Query("_id")_id:String, @Field("rejectedReason")rejectedReason:String):retrofit2.Call<CommonResponseForAll>


    @GET("deliveryFee/proceedToCheckOut")
    fun proceedToCheckOutApi(@Query("deliveryType")deliveryType:String):retrofit2.Call<CommonResponseForAll>

    @GET("deliveryOrders/outForDelivery")
    fun outForDeliveryApi(@Query("_id")_id:String):retrofit2.Call<CommonResponseForAll>


    @GET("deliveryOrders/getWalletTransaction")
    fun getWalletTransactionApi(@Query("page")page:Int,@Query("limit")limit:Int,@Query("isCommission")isCommission:Boolean):retrofit2.Call<MyEarningResponse>



    @GET("pickupFee/listUserFee")
    fun listUserFeeApi():retrofit2.Call<FeeListResponse>



    @GET("deliveryFee/listUserFeeByUserId")
    fun listUserFeeByUserIdAPI():retrofit2.Call<FeeListResponse>



    @GET("pickupFee/listUserStorageFee")
    fun listUserStorageFeeAPI():retrofit2.Call<FeeListResponse>

    @FormUrlEncoded
    @POST("pickupFee/updateUserFeeDetail")
    fun updateUserFeeDetail(@Query("userType")userType:String,@Field("_id")_id:String,@Field("pickupFee")pickupFee:String):retrofit2.Call<CommonResponseForAll>


    @FormUrlEncoded
    @PUT("deliveryFee/updateFeeDetail")
    fun updateFeeDetailDeliveryDriverApi(@Field("_id")_id:String,@Field("standardFee")standardFee:String,@Field("deliveryFee")deliveryFee:String):retrofit2.Call<CommonResponseForAll>


    @FormUrlEncoded
    @PUT("storageFee/updateStoragageFeeDetail")
    fun updateStorageFeeDetailApi(@Field("_id")_id:String,@Field("daily")daily:String,@Field("monthly")monthly:String
                                    ,@Field("quarterly")quarterly:String,@Field("weekly")weekly:String
                                    ,@Field("maximumSize")maximumSize:String,
                                    @Field("minimumSize")minimumSize:String):retrofit2.Call<CommonResponseForAll>


    @GET("deliveryOrders/reAssignDelivery")
    fun reAssignDeliveryApi(@Query("_id") id:String):retrofit2.Call<CommonResponseForAll>

    @GET("v2/service/allServiceList")
    fun allServiceListApi():retrofit2.Call<ServiceConfigResponse>

    @POST("v2/service/updateServiceUser3")
    fun updateServiceUserApi(@Body request: List<ServiceFeeConfigRequest>, @Query("flag") flag:Int):retrofit2.Call<CommonResponseForAll>


    @FormUrlEncoded
    @POST("user/selectService")
    fun selectServiceApi(@Field("serviceArray") serviceArray: List<String>, @Query("flag") flag:Int):retrofit2.Call<CommonResponseForAll>

    @GET("user/myServiceList")
    fun mySelectedServiceListApi():retrofit2.Call<SelectedServiceResponse>



    @GET("user/documentList")
    fun documentListApi():retrofit2.Call<DocumentListResponse>


    @GET("user/deleteUser")
    fun deleteUserApi():retrofit2.Call<JsonObject>



    @GET("user/homepageApi")
    fun homepageApi(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("dealType") dealType: String,
        @Query("userType") userType: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("search") search: String
    ):retrofit2.Call<HomePageResponse>


    @GET("deliveryOrders/getWalletTransaction")
    fun getWalletTransactionApi(@Query("page")page:Int,@Query("limit")limit:Int):retrofit2.Call<MyEarningResponse>


    @FormUrlEncoded
    @POST("user/socialSignin")
    fun socialSignInApi(
        @Field("userType") userType: String,
        @Field("email") email: String,
        @Field("profilePic") profilePic: String,
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("deviceType") deviceType: String,
        @Field("deviceToken") deviceToken: String,
        @Field("googleId") googleId: String
    ):retrofit2.Call<SocialSignInResponse>


    @FormUrlEncoded
    @POST("campaign/addIntrestPriceOnProduct")
    fun addInterestPriceOnProduct(@Field("interestedPrice")interestedPrice:String,
                                  @Field("quantity")quantity :String,
                                  @Field("productId")productId:String,
                                  @Field("productSizeId")productSizeId:String,
                                  @Field("unit")unit:String,
                                  @Field("value")value:String,
                                  @Field("spordicType")spordicType:String):retrofit2.Call<CommonResponseForAll>


    @POST("order/ozowWalletCheckOut")
    fun ozowCheckOutWalletOrderApi(@Body jsonObject: JsonObject
    ): retrofit2.Call<OzowPaymentResponse>



    @FormUrlEncoded
    @POST("campaign/getIntrestedPriceList")
    fun getIntrestedPriceList(@Field("page")page:Int,@Field("limit")limit:Int,@Field("campaignOn")campaignOn:String):retrofit2.Call<CampaignListResponse>


    @FormUrlEncoded
    @POST("campaign/addIntrestPriceOnService")
    fun addInterestPriceOnService(@Field("serviceId")serviceId:String,@Field("interestedPrice")interestedPrice:String,@Field("price")price:String,@Field("spordicType")spordicType:String):retrofit2.Call<CommonResponseForAll>


    @POST("campaign/addCampaign")
    fun addCampaignApi(@Body request: AddCampaignOnProductRequest, @Query("_id")idForView:String):retrofit2.Call<CommonResponseForAll>


    @POST("campaign/addCampaign")
    fun addCampaignServiceApi(@Body request: AddCampaignOnServiceRequest):retrofit2.Call<CommonResponseForAll>


    @GET("campaign/listCampaign")
    fun listCampaignApi(@Query("page")page:Int,@Query("limit")limit:Int,@Query("campaignOn")campaignOn:String):retrofit2.Call<AddedCampaignListResponse>



    @GET("campaign/viewCampaign")
    fun viewCampaignApi(@Query("_id") id:String):retrofit2.Call<ViewProductCampaignResponse>

    @FormUrlEncoded
    @POST("order/checkOutWalletOrder")
    fun checkOutWalletOrderApi(@Query("orderId") orderId:String, @Field("cancelUrl")cancelUrl:String,
                               @Field("errorUrl")errorUrl :String,
                               @Field("SuccessUrl")successUrl :String,
                               @Field("testMode")testMode :Boolean,):retrofit2.Call<CommonResponseForAll>



    @POST("feedback/submitFeedback")
    fun submitFeedbackApi(@Body request: FeedBackDataRequest):retrofit2.Call<CommonResponseForAll>


    @GET("feedback/getFeedBackByOrderId")
    fun getFeedBackByOrderIdApi(@Query("userType")userType:String,@Query("orderId")orderId:String,):retrofit2.Call<FeedbackResponse>

    @FormUrlEncoded
    @POST("order/getInvoice")
    fun getInvoiceApi(@Field("orderId")orderId:String,):retrofit2.Call<GetInvoiceDownloadResponse>



    @FormUrlEncoded
    @POST("v2/service/updateServiceStatus")
    fun updateServiceStatusApi(@Field("status")status:String,@Field("orderId")orderId:String):retrofit2.Call<CommonResponseForAll>



    @POST("payment/checkoutPayfast")
    fun checkoutPayFastApi(@Query("orderId")orderId:String, @Body jsonObject: JsonObject):retrofit2.Call<PayFastResponse>




}
