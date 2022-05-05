package com.example.wallet.Api;

import com.example.wallet.Adapters.GetFilterWalletResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LoginService {

    String token = "Authorization:Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTIyOCwicGhvbmUiOiIrOTE4MDczMDg5OTEyIiwidXJsIjoidGVzdC50aGVjbGFzc3Jvb20uYml6Iiwib3JnSWQiOiI0Y2IyNTA5ZC03MGY1LTQzNWUtODc5Mi1kMjQ5Mzc3NDNiNTMiLCJicm93c2VyTG9naW5Db2RlIjpudWxsLCJkZXZpY2VMb2dpbkNvZGUiOiIrOTE4MDczMDg5OTEyMTIyOGRjNmE3YTY0LTFmZmMtNDk4ZC1iYTE3LWNhNmNjNTZmYTkzOCIsImlhdCI6MTY1Mzk4MDYxOX0.Ks5gr4diUDZMILIBT3bY_Cyb0zBRBY3fRCNPLctJ4Hw";
    String link = "orgurl:test.theclassroom.biz";

    //get user kyc
    @Headers({token, link})
    @GET("fee/get-user-kyc")
    Call<GetUserKycResponse> getUserCall();


    //wallet filter
    @Headers({token, link})
    @GET("fee/filterwallet")
    Call<GetFilterWalletResponse> filterWalletCall(@Query("status") String status);

    //deletedocument

    @Headers({token, link})
    @DELETE("fee/delete-kyc-doc")
    Call<DeleteDocument> deleteCall(@Query("key") String key);

    //wallet
    @Headers({token, link})
    @GET("fee/wallet")
    Call<GetWalletResponse> getWalletCall();

    //verify signature
    @Headers({token, link})
    @GET("fee/verifysignature")
    Call<VerifySignature> VERIFY_SIGNATURE_CALL(@Query("id")String id,@Query("orderId")String orderId,@Query("orderAmount")String amount,@Query("signature")String signature,
                                                @Query("txStatus")String txStatus,@Query("paymentMode")String paymentMode,@Query("referenceId")String referenceId,@Query("txMsg")String txMsg,@Query("txTime")String txTime);
    //Attempt
    @Headers({token,link})
    @GET("fee/attempt")
    Call<AttempResponse> ATTEMP_RESPONSE_CALL(@Query("id")int id,@Query("value")int value);


    //paid
    @Headers({token, link})
    @GET("fee/filterwallet")
    Call<HistoryResponse> historyWalletCall(@Query("status") String status,@Query("arrayResponse")String history);

    //generate otp
    @Headers({token, link})
    @POST("fee/generateKycOtp")
    Call<GenerateOtpResponse> otp(@Body GetOtpRequest getOtpRequest);

    //verify
    @Headers({token, link})
    @POST("fee/verifyKycOtp")
    Call<VerifyResponse> verifyCall(@Body VerifyRequest verifyRequest);

    //updateUserKYC
    @Headers({token, link})
    @POST("fee/update-user-kyc")
    Call<UpdateUserKycResponse> detail_call(@Body UpdateDetailsRequest userKycRequest);

    //updateUserKYC
    @Headers({token, link})
    @POST("fee/update-user-kyc")
    Call<UpdateUserKycResponse> address_call(@Body AddressRequest addressRequest);

    //updateUserKYC
    @Headers({token, link})
    @POST("fee/update-user-kyc")
    Call<UpdateUserKycResponse> updateCall(@Body UpdateUserKycRequest userKycRequest);
    // upload doc

    @Multipart
    @Headers({token, link})
    @POST("fee/upload-kyc-doc")
    Call<DocUploadResponse> docCall(@Part MultipartBody.Part image);


        //getcfrt

    @Headers({token, link})
    @GET("fee/getcfrt")
    Call<GetCFRTResponse> getCFRTCall(@Query("orderId") String orderId, @Query("orderAmount") double orderAmount, @Query("orderCurrency") String orderCurrency, @Query("id") int id);
//pincode
    @GET("{pincode}")
    Call<List<PincodeResponse>> PINCODE_RESPONSE_CALL(@Path("pincode") Long pincode);

    //update contact info
    @Headers({token, link})
    @PATCH("user/update-contact-info")
    Call<UpdateContactInfoResponse> updateinfoCall(@Body UpdateContactInfoRequest updateContactInfoRequest);
//p



}
