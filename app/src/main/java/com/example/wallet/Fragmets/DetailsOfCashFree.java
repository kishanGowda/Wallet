package com.example.wallet.Fragmets;

import static com.cashfree.pg.CFPaymentService.PARAM_APP_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_CURRENCY;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_NOTE;
import static com.cashfree.pg.CFPaymentService.PARAM_PAYMENT_MODES;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cashfree.pg.CFPaymentService;
import com.example.wallet.Api.ApiClient;
import com.example.wallet.Api.GetCFRTResponse;
import com.example.wallet.Api.LoginService;
import com.example.wallet.Api.TockenResponse;
import com.example.wallet.Api.VerifySignature;
import com.example.wallet.GetFilterWalletResponse;
import com.example.wallet.Models.AdditionData;
import com.example.wallet.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailsOfCashFree extends AppCompatActivity {

    GetFilterWalletResponse getFilterWalletResponse;
    View view, sheetView;
    Retrofit retrofit;
    BottomSheetDialog mBottomSheetDialog;
    EditText c1;
    String position;
    LoginService loginService;
    TextView amount;
    GetCFRTResponse getCFRTResponse;
    TextView issued_date_time_tv;
    TextView dueDate, tokens;
    TextView amountTwo;
    TextView gst;
    TextView convinesce;
    TextView totalAmount;
    TextView dicount,noteTextView;
    ImageView back;
    TockenResponse tockenResponse;
    String token;
    Button pay;
    int index;
    String orderID;
    String ID,ORDERID,ORDERAMOUNT,SIGNATURE,TXSTATUS,PAYMENTMODE,REFERENCEID,TXMSG,TXTIME;
    ArrayList<AdditionData> additionData;

    final int UPI_PAYMNT = 0;

    enum SeamlessMode {
        CARD, WALLET, NET_BANKING, UPI_COLLECT, PAY_PAL
    }

    SeamlessMode currentMode = SeamlessMode.CARD;
private static final String TAG = "DetailsOfCashFree";
    Map<String, String> params;
     TextView upi, other;
    Dialog mdialog;
    String id;
    String amounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_cash_free);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            orderID= extras.getString("order");
            id=extras.getString("ids");
            Log.i("id", String.valueOf(id));
            ID=id;
            Log.i("pID", ID);
            amounts=extras.getString("amount");
            Log.i("TAG", amounts);
         position = extras.getString("position");
            Log.i(TAG, "onCreate:position "+position);

            //The key argument here must match that used in the other activity
        }
        index=Integer.valueOf(position);
        Log.i(TAG, "onCreate: "+index);



        apiInIt();
        getTokenResponse();



        amount = findViewById(R.id.rs_100);

        issued_date_time_tv = findViewById(R.id.issued_date_time_tv);
        dueDate = findViewById(R.id.due_date_tv);
        amountTwo =findViewById(R.id.amount_rs_tv);
        gst = findViewById(R.id.gst_rs_tv);
        convinesce = findViewById(R.id.convenience_rs_tv);
        dicount = findViewById(R.id.discount_rs_tv);
        totalAmount = findViewById(R.id.total_amount_rs_tv);
        noteTextView=findViewById(R.id.admission_fee_tv);


        filterWalletTwo();

        back = findViewById(R.id.backArrow_deatails_of_payment);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
onBackPressed();
            }
        });


        pay = findViewById(R.id.pay_now_button_sss);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mdialog = new Dialog(DetailsOfCashFree.this);
                mdialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                mdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mdialog.setContentView(R.layout.email);
                Button next = mdialog.findViewById(R.id.next);
                next.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        mdialog.dismiss();
                        c1 = (EditText) mdialog.findViewById(R.id.editTextTextPersonEmail);


                        if (c1.getText().toString().isEmpty()) {
                            Toast.makeText(DetailsOfCashFree.this, "email", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(DetailsOfCashFree.this, ":)", Toast.LENGTH_LONG).show();

                            mdialog.dismiss();
                            mBottomSheetDialog = new BottomSheetDialog(DetailsOfCashFree.this, androidx.appcompat.R.style.Base_Theme_AppCompat);
                            mBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            sheetView = LayoutInflater.from(DetailsOfCashFree.this).inflate(R.layout.payment_option, null);
                            mBottomSheetDialog.setContentView(sheetView);
                            mBottomSheetDialog.setCanceledOnTouchOutside(true);
                            upi = sheetView.findViewById(R.id.upi_tv);
                            other = sheetView.findViewById(R.id.other_tv);
                            upi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mdialog.dismiss();
                                    Log.i("TAG", "onClick: ");


                                    // starting the payment method
                                    paymethod(view);

                                }
                            });


                            other.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Log.i("TAG", "hello");
                                    paymethod(view);
                                }
                            });
                            mBottomSheetDialog.setContentView(sheetView);
                            mBottomSheetDialog.show();
                        }


                    }

                });
                mdialog.show();


            }
        });
    }


    // method
    private void paymethod(View view) {
        String stage = "PROD";
        CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
        cfPaymentService.setOrientation(0);

        switch (view.getId()) {
            case R.id.upi_tv: {
                cfPaymentService.doPayment(DetailsOfCashFree.this, getInput(), token, stage, "#784BD2", "#FFFFFF", true);
                cfPaymentService.setOrientation(0);

              break;
            }
            case R.id.other_tv:{
                Log.i("kishan","hello");
                try {
                    cfPaymentService.doPayment(DetailsOfCashFree.this, getInputParams(), token, stage, "#784BD2", "#FFFFFF", true);
                }catch (Exception e){
                    Log.i("kishan", e.getMessage());
                }
                break;

            }


        }


    }
//
    @Override
    protected  void  onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Same request code for all payment APIs.
        Log.d(TAG, "ReqCode : " + CFPaymentService.REQ_CODE);
        Log.d(TAG, "API Response : ");
        //Prints all extras. Replace with app logic.
        if (data != null) {
            Bundle  bundle = data.getExtras();
            Set<String> responseSet = bundle.keySet();
            ArrayList<String> keys = new ArrayList<>();
            for (String s: responseSet) {
                keys.add(s);
            }
            if (bundle != null){
                if (bundle.getString(keys.get(keys.size()-1)).equalsIgnoreCase("CANCELLED")){
                    Toast.makeText(this,"Cancelled",Toast.LENGTH_LONG).show();
                    Dialog dialog = new Dialog(DetailsOfCashFree.this);
                    dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.payment_cancel_dialog);
                    Button dialogButton = (Button) dialog.findViewById(R.id.back_to_home_button);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            onBackPressed();
                        }
                    });
                    dialog.show();

                }
                else if(bundle.getString(keys.get(keys.size()-1)).equalsIgnoreCase("SUCCESS")){
                    PAYMENTMODE=String.valueOf(bundle.getString(keys.get(0).toString()));
                    ORDERID=String.valueOf(bundle.getString(keys.get(1).toString()));
                    TXTIME=String.valueOf(bundle.getString(keys.get(2).toString()));
                    REFERENCEID=String.valueOf(bundle.getString(keys.get(3).toString()));
                    TXMSG=String.valueOf(bundle.getString(keys.get(5).toString()));
                    SIGNATURE=String.valueOf(bundle.getString(keys.get(6).toString()));
                    ORDERAMOUNT=String.valueOf(bundle.getString(keys.get(7).toString()));
                    TXSTATUS=String.valueOf(bundle.getString(keys.get(8).toString()));
                    Call<VerifySignature> call = loginService.VERIFY_SIGNATURE_CALL(ID,ORDERID,ORDERAMOUNT,SIGNATURE,TXSTATUS,PAYMENTMODE,REFERENCEID,TXMSG,TXTIME);
                    call.enqueue(new Callback<VerifySignature>() {
                                     @Override
                                     public void onResponse(Call<VerifySignature> call, Response<VerifySignature> response) {
                                         if (!response.isSuccessful()) {
                                             Toast.makeText(DetailsOfCashFree.this, response.code(), Toast.LENGTH_SHORT).show();
                                         }

                                     }

                                     @Override
                                     public void onFailure(Call<VerifySignature> call, Throwable t) {
                                         Toast.makeText(DetailsOfCashFree.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                                     }
                                 });


                            Toast.makeText(this, "SUCCESS", Toast.LENGTH_LONG).show();
                    Dialog dialog = new Dialog(DetailsOfCashFree.this);
                    dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.payment_succesfull_dialog);
                    Button dialogButton = (Button) dialog.findViewById(R.id.back_to_home_button);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            onBackPressed();
                        }
                    });
                    dialog.show();


                }

                for (String  key  :  bundle.keySet()) {
                    if (bundle.getString(key) != null) {
                        Log.d("kishankishu", key + " : " + bundle.getString(key));
                    }
                }
            }

        }
    }
    private Map<String, String> getInput() {
        String appId = "132251d7dc08cd6f7ae20edc8a152231";
        String orderId = orderID;
        String orderAmount = amounts;
        String orderNote = "Test Order";
        String customerName = "kisha";
        String customerPhone = "8884831284";
        String customerEmail = "kishankishu1996@gmail.com";


        params = new HashMap<>();
        params.put(PARAM_APP_ID, appId);
        params.put(PARAM_ORDER_ID, orderId);
        params.put(PARAM_ORDER_AMOUNT, orderAmount);
        params.put(PARAM_ORDER_NOTE, orderNote);
        params.put(PARAM_CUSTOMER_NAME, customerName);
        params.put(PARAM_CUSTOMER_PHONE, customerPhone);
        params.put(PARAM_CUSTOMER_EMAIL, customerEmail);
        params.put(CFPaymentService.PARAM_NOTIFY_URL, "http://your.backend.webhook");
        params.put(PARAM_ORDER_CURRENCY, "INR");
       params.put(PARAM_PAYMENT_MODES, "upi");
        return params;
    }

    private Map<String, String> getInputParams() {
        String appId = "132251d7dc08cd6f7ae20edc8a152231";
        String orderId = orderID;
        String orderAmount = amounts;
        String orderNote = "Test Order";
        String customerName = "kisha";
        String customerPhone = "8884831284";
        String customerEmail = "kishankishu1996@gmail.com";


        params = new HashMap<>();
        params.put(PARAM_APP_ID, appId);
        params.put(PARAM_ORDER_ID, orderId);
        params.put(PARAM_ORDER_AMOUNT, orderAmount);
        params.put(PARAM_ORDER_NOTE, orderNote);
        params.put(PARAM_CUSTOMER_NAME, customerName);
        params.put(PARAM_CUSTOMER_PHONE, customerPhone);
        params.put(PARAM_CUSTOMER_EMAIL, customerEmail);
        params.put(CFPaymentService.PARAM_NOTIFY_URL, "http://your.backend.webhook");
        params.put(PARAM_ORDER_CURRENCY, "INR");
//        params.put(PARAM_PAYMENT_MODES, "cc,wallet,dc");
        return params;
    }
//
//        @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(data!=null){
//            Bundle bundle=data.getExtras();
//            if (bundle!=null)
//                for (String key:bundle.keySet()){
//                    if (bundle.getString(key)!=null){
//                        Log.i("TAG", key+":"+bundle.getString(key));
//                    }
//                }
//        }
//        Log.d("TAG", "ReqCode : " + CFPaymentService.REQ_CODE);
//        Log.d("TAG", "API Response : ");
//        if (requestCode == CFPaymentService.REQ_CODE && data != null) {
//            Bundle bundle = data.getExtras();
//            if (bundle != null) {
//                showResponse(transformBundleToString(bundle));
//            }
//
//        }
//    }
//
//    private void showResponse(String response) {
//        Log.i(TAG, "showResponse: ");
//        final AlertDialog show = new MaterialAlertDialogBuilder(DetailsOfCashFree.this)
//                .setMessage(response)
//
//                .setTitle("Payment Response")
//
//                .setPositiveButton("ok", (dialog1, which) -> {
//                    dialog1.dismiss();
//                }).show();
//    }
//
//    private String transformBundleToString(Bundle bundle) {
//        String response = "";
//        for (String key : bundle.keySet()) {
//            response = response.concat(String.format("%s : %s\n", key, bundle.getString(key)));
//        }
//        Log.i("response", "transformBundleToString: " + response);
//        return response;
//
//    }












    public void apiInIt() {
        retrofit = ApiClient.getRetrofit();
        loginService = ApiClient.getApiService();
    }

    public void filterWalletTwo() {
        Call<GetFilterWalletResponse> call = loginService.filterWalletCall("Link Sent");
        call.enqueue(new Callback<GetFilterWalletResponse>() {
            @Override
            public void onResponse(Call<GetFilterWalletResponse> call, Response<GetFilterWalletResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(DetailsOfCashFree.this, response.code(), Toast.LENGTH_SHORT).show();
                }
                getFilterWalletResponse = response.body();
                String orgId = getFilterWalletResponse.transactions.get(index).orgId;
                int userId = getFilterWalletResponse.transactions.get(index).user.id;
                orgnization(orgId, userId);
                amount.setText("₹ " + getFilterWalletResponse.transactions.get(index).amount);
                issued_date_time_tv.setText(getFilterWalletResponse.transactions.get(index).date);
                dueDate.setText(getFilterWalletResponse.transactions.get(index).dueDate);
                amountTwo.setText("₹ "+getFilterWalletResponse.transactions.get(index).amount);
                totalAmount.setText("₹ " + getFilterWalletResponse.transactions.get(index).amountPayable);


                //
//                String jsonString2 = String.valueOf(getFilterWalletResponse.transactions.get(index).discount_details);
//                try {
//                    JSONArray jsonArray = new JSONArray(jsonString2);
////                    additionData = new ArrayList<>();
//                    AdditionData adata = new AdditionData();
//                    Log.i(TAG, "onResponse: Length" + jsonArray.length());
//
//                    for (int j = index; j < jsonArray.length(); j++) {
//
//                        JSONObject object2 = jsonArray.getJSONObject(j);
//                        adata.setName(object2.getString("name"));
//                        adata.setAmount(Double.parseDouble(object2.getString("amount")));
//                        adata.setDetails(object2.getString("details"));
//
//
//                    }
//                }
//                catch (Exception e){
//
//                }

                //
                dicount.setText("₹ "+ getFilterWalletResponse.transactions.get(index).total_discount);
                noteTextView.setText(getFilterWalletResponse.transactions.get(index).note);
                if ("institute".equals(getFilterWalletResponse.transactions.get(index).transactionPaidBy)) {
                    convinesce.setText("Free");
                } else {
                    convinesce.setText(String.valueOf(getFilterWalletResponse.transactions.get(index).transactionFee));
                }
                Log.i("y", String.valueOf(getFilterWalletResponse.transactions.size()));
                Toast.makeText(DetailsOfCashFree.this, "on response", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<GetFilterWalletResponse> call, Throwable t) {
                Toast.makeText(DetailsOfCashFree.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }



    private void orgnization(String orgId, int userId) {
        String orgIds = orgId;
        int user = userId;

        Log.i("TAG", "orgnization: " + orgIds);
        Log.i("TAG", "orgnization: " + user);

    }


    public String getTokenResponse() {
        ProgressDialog dialog=new ProgressDialog(DetailsOfCashFree.this);
        dialog.setMessage("Loading");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
        Call<GetCFRTResponse> tokenResponseCall = loginService.getCFRTCall(orderID, Double.parseDouble(amounts), "INR", Integer.valueOf(id));
        tokenResponseCall.enqueue(new Callback<GetCFRTResponse>() {
            @Override
            public void onResponse(Call<GetCFRTResponse> call, Response<GetCFRTResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(DetailsOfCashFree.this, String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                }
                GetCFRTResponse tockenResponse = response.body();
                String t = tockenResponse.body.cftoken;

                final Handler handler = new Handler();
                dialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        token=t;
                        Log.i("TAG", "enterd");
                        Log.i("TAG", String.valueOf(token));
                        dialog.dismiss();
                    }

                }, 5000);
                Log.i("TAG", "enterd after");

                Log.i("orderId", orderID);



            }


            @Override
            public void onFailure(Call<GetCFRTResponse> call, Throwable t) {
                Toast.makeText(DetailsOfCashFree.this, "Error :((", Toast.LENGTH_LONG).show();


            }
        });
        return token;
    }



}