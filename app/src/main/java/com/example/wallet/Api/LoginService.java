package com.example.wallet.Api;

import com.example.wallet.GetFilterWalletResponse;
import com.example.wallet.Models.ChildModel;
import com.example.wallet.Models.PaymentRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LoginService {

    String token = "Authorization:Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI5MiwicGhvbmUiOiIrOTE4ODg0ODMxMjgyIiwidXJsIjoidGVzdC50aGVjbGFzc3Jvb20uYml6Iiwib3JnSWQiOiI0Y2IyNTA5ZC03MGY1LTQzNWUtODc5Mi1kMjQ5Mzc3NDNiNTMiLCJicm93c2VyTG9naW5Db2RlIjpudWxsLCJkZXZpY2VMb2dpbkNvZGUiOiIrOTE4ODg0ODMxMjgyMTI5MmJhZmI0ZTJiLTc3M2EtNGVmNS1iYmVkLTU4MjM2MGYzYzdhNyIsImlhdCI6MTY0OTQxNTE0NX0.p6mbvNqpGZXUItdODo_7o4qPCANHyOX9TKs1ymlMFnU";
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
    Call<UpdateUserKycResponse> updateCall(@Body UpdateUserKycRequest userKycRequest);
    // upload doc

    @Multipart
    @Headers({token, link})
    @POST("fee/upload-kyc-doc")
    Call<DocUploadResponse> docCall(@Part MultipartBody.Part image);




    @Headers({token, link})
    @GET("fee/getcfrt")
    Call<GetCFRTResponse> getCFRTCall(@Query("orderId") String orderId, @Query("orderAmount") double orderAmount, @Query("orderCurrency") String orderCurrency, @Query("id") int id);

    @GET("{pincode}")
    Call<List<PincodeResponse>> PINCODE_RESPONSE_CALL(@Path("pincode") Long pincode);


}
