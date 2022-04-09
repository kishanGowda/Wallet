package com.example.wallet.Fragmets;

import static com.cashfree.pg.CFPaymentService.PARAM_APP_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_BANK_CODE;
import static com.cashfree.pg.CFPaymentService.PARAM_CARD_CVV;
import static com.cashfree.pg.CFPaymentService.PARAM_CARD_HOLDER;
import static com.cashfree.pg.CFPaymentService.PARAM_CARD_MM;
import static com.cashfree.pg.CFPaymentService.PARAM_CARD_NUMBER;
import static com.cashfree.pg.CFPaymentService.PARAM_CARD_YYYY;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_CURRENCY;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_NOTE;
import static com.cashfree.pg.CFPaymentService.PARAM_PAYMENT_OPTION;
import static com.cashfree.pg.CFPaymentService.PARAM_UPI_VPA;
import static com.cashfree.pg.CFPaymentService.PARAM_WALLET_CODE;

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
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.cashfree.pg.CFPaymentService;
import com.example.wallet.Api.ApiClient;
import com.example.wallet.Api.GetCFRTResponse;
import com.example.wallet.Api.LoginService;
import com.example.wallet.Api.TockenResponse;
import com.example.wallet.GetFilterWalletResponse;
import com.example.wallet.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class DetailsOfPayment extends Fragment {
    GetFilterWalletResponse getFilterWalletResponse;
    View view, sheetView;
    Retrofit retrofit;
    BottomSheetDialog mBottomSheetDialog;
    EditText c1;
    LoginService loginService;
    TextView amount;
    GetCFRTResponse getCFRTResponse;
    TextView issued_date_time_tv;
    TextView dueDate, tokens;
    TextView amountTwo;
    TextView gst;
    TextView convinesce;
    TextView totalAmount;
    TextView dicount;
    ImageView back;
    TockenResponse tockenResponse;
    String token;
    Button pay;
    String orderID;

    final int UPI_PAYMNT = 0;

    enum SeamlessMode {
        CARD, WALLET, NET_BANKING, UPI_COLLECT, PAY_PAL
    }

    DetailsOfPayment.SeamlessMode currentMode = DetailsOfPayment.SeamlessMode.CARD;

    private static final String TAG = "DetailsOfPayment";
    Map<String, String> params;
    private TextView upi, other;
    Dialog mdialog;
    int id;
    String amounts;


    public DetailsOfPayment(String order, int id, String amount) {
        orderID = order;
        this.id = id;
        amounts = amount;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_details_of_payment, container, false);
        tokens = view.findViewById(R.id.tokens);
        apiInIt();
        getTokenResponse();



        amount = view.findViewById(R.id.rs_100);

        issued_date_time_tv = view.findViewById(R.id.issued_date_time_tv);
        dueDate = view.findViewById(R.id.due_date_tv);
        amountTwo = view.findViewById(R.id.amount_rs_tv);
        gst = view.findViewById(R.id.gst_rs_tv);
        convinesce = view.findViewById(R.id.convenience_rs_tv);
        dicount = view.findViewById(R.id.discount_rs_tv);
        totalAmount = view.findViewById(R.id.total_amount_rs_tv);


        filterWallet();

        back = view.findViewById(R.id.backArrow_deatails_of_payment);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        pay = view.findViewById(R.id.pay_now_button_sss);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mdialog = new Dialog(getActivity());
                mdialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                mdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mdialog.setContentView(R.layout.email);
                Button next = mdialog.findViewById(R.id.next);
                next.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        c1 = (EditText) mdialog.findViewById(R.id.editTextTextPersonEmail);


                        if (c1.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "email", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getActivity(), ":)", Toast.LENGTH_LONG).show();

                            mdialog.dismiss();
                            mBottomSheetDialog = new BottomSheetDialog(getContext(), androidx.appcompat.R.style.Base_Theme_AppCompat);
                            mBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            sheetView = LayoutInflater.from(getContext()).inflate(R.layout.payment_option, null);
                            mBottomSheetDialog.setContentView(sheetView);
                            mBottomSheetDialog.setCanceledOnTouchOutside(true);
                            upi = sheetView.findViewById(R.id.upi_tv);
                            other = sheetView.findViewById(R.id.other_tv);
                            upi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Log.i("TAG", "onClick: ");
                                    paymethod(view);

                                }
                            });


                            other.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mBottomSheetDialog.dismiss();
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

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String tokenOne = tokens.getText().toString();
        Log.i("bandbidu", String.valueOf(tokenOne));

        Toast.makeText(getActivity(), tokenOne, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        Log.d("TAG", "ReqCode : " + CFPaymentService.REQ_CODE);
        Log.d("TAG", "API Response : ");
        if (requestCode == CFPaymentService.REQ_CODE && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                showResponse(transformBundleToString(bundle));
            }

        }
    }

    private void showResponse(String response) {
        Log.i(TAG, "showResponse: ");
        final AlertDialog show = new MaterialAlertDialogBuilder(getActivity())
                .setMessage(response)

                .setTitle("Payment Response")

                .setPositiveButton("ok", (dialog1, which) -> {
                    dialog1.dismiss();
                }).show();
    }

    private String transformBundleToString(Bundle bundle) {
        String response = "";
        for (String key : bundle.keySet()) {
            response = response.concat(String.format("%s : %s\n", key, bundle.getString(key)));
        }
        Log.i("response", "transformBundleToString: " + response);
        return response;

    }

    public String getTokenResponse() {
        ProgressDialog dialog=new ProgressDialog(getContext());
        dialog.setMessage("Loading");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();

        Call<GetCFRTResponse> tokenResponseCall = loginService.getCFRTCall(orderID, Integer.valueOf(amounts), "INR", id);
        tokenResponseCall.enqueue(new Callback<GetCFRTResponse>() {
            @Override
            public void onResponse(Call<GetCFRTResponse> call, Response<GetCFRTResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
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

                }, 10000);
                Log.i("TAG", "enterd after");

                Log.i("orderId", orderID);



            }


            @Override
            public void onFailure(Call<GetCFRTResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Error :((", Toast.LENGTH_LONG).show();


            }
        });
        return token;
    }


    public void apiInIt() {
        retrofit = ApiClient.getRetrofit();
        loginService = ApiClient.getApiService();
    }

    public void filterWallet() {
        Call<GetFilterWalletResponse> call = loginService.filterWalletCall("Link Sent");
        call.enqueue(new Callback<GetFilterWalletResponse>() {
            @Override
            public void onResponse(Call<GetFilterWalletResponse> call, Response<GetFilterWalletResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
                }
                getFilterWalletResponse = response.body();
                String orgId = getFilterWalletResponse.transactions.get(0).orgId;
                int userId = getFilterWalletResponse.transactions.get(0).user.id;
                orgnization(orgId, userId);
                amount.setText("₹" + getFilterWalletResponse.transactions.get(0).amount);
                issued_date_time_tv.setText(getFilterWalletResponse.transactions.get(0).date);
                dueDate.setText(getFilterWalletResponse.transactions.get(0).dueDate);
                amountTwo.setText(getFilterWalletResponse.transactions.get(0).amount);
                gst.setText(getFilterWalletResponse.transactions.get(0).gst);
                totalAmount.setText("₹ " + getFilterWalletResponse.transactions.get(0).amountPayable);
                dicount.setText(getFilterWalletResponse.transactions.get(0).discount_details);
                if ("institute".equals(getFilterWalletResponse.transactions.get(0).transactionPaidBy)) {
                    convinesce.setText("Free");
                } else {
                    convinesce.setText(String.valueOf(getFilterWalletResponse.transactions.get(0).transactionFee));
                }
                Log.i("y", String.valueOf(getFilterWalletResponse.transactions.size()));
                Toast.makeText(getActivity(), "on response", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<GetFilterWalletResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void paymethod(View view) {
        String stage = "PROD";
        CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
//                                cfPaymentService.doPayment(this,params,token,stage);
        cfPaymentService.setOrientation(0);

        switch (view.getId()) {
            case R.id.upi_tv: {
                //Log.i("Token inside Switch", token);
                cfPaymentService.selectUpiClient("com.google.android.apps.nbu.paisa.user");
                cfPaymentService.upiPayment(getActivity(), getInputParams(), token, stage);
                Log.i("TAG", "paymethod: ");
                Log.i("TAG", token);
                cfPaymentService.doPayment(getActivity(), params, token, stage);
//                break;
            }


        }


    }



    private Map<String, String> getSeamlessCheckoutParams() {
        Map<String, String> params = getInputParams();

        switch (currentMode) {
            case CARD:
                params.put(PARAM_PAYMENT_OPTION, "card");
                params.put(PARAM_CARD_NUMBER, "VALID_CARD_NUMBER");
                params.put(PARAM_CARD_YYYY, "YYYY");
                params.put(PARAM_CARD_MM, "MM");
                params.put(PARAM_CARD_HOLDER, "CARD_HOLDER_NAME");
                params.put(PARAM_CARD_CVV, "CVV");
                break;
            case WALLET:
                params.put(PARAM_PAYMENT_OPTION, "wallet");
                params.put(PARAM_WALLET_CODE, "4007"); // Put one of the wallet codes mentioned here https://dev.cashfree.com/payment-gateway/payments/wallets
                break;
            case NET_BANKING:
                params.put(PARAM_PAYMENT_OPTION, "nb");
                params.put(PARAM_BANK_CODE, "3333"); // Put one of the bank codes mentioned here https://dev.cashfree.com/payment-gateway/payments/netbanking
                break;
            case UPI_COLLECT:
                params.put(PARAM_PAYMENT_OPTION, "upi");
                params.put(PARAM_UPI_VPA, "VALID_VPA");
                break;
            case PAY_PAL:
                params.put(PARAM_PAYMENT_OPTION, "paypal");
                break;
        }
        return params;
    }

    private void orgnization(String orgId, int userId) {
        String orgIds = orgId;
        int user = userId;

        Log.i("TAG", "orgnization: " + orgIds);
        Log.i("TAG", "orgnization: " + user);

    }

    private Map<String, String> getInputParams() {
        String appId = "132251d7dc08cd6f7ae20edc8a152231";
        String orderId = orderID;
        String orderAmount = amounts;
        String orderNote = "Test Order";
        String customerName = "John Doe";
        String customerPhone = "9900012345";
        String customerEmail = "test@gmail.com";


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
        return params;
    }


}
