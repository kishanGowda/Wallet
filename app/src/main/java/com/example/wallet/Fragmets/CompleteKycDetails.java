package com.example.wallet.Fragmets;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wallet.Api.ApiClient;
import com.example.wallet.Api.GenerateOtpResponse;
import com.example.wallet.Api.GetOtpRequest;
import com.example.wallet.Api.GetUserKycResponse;
import com.example.wallet.Api.GetWalletResponse;
import com.example.wallet.Api.LoginService;
import com.example.wallet.Api.UpdateDetailsRequest;
import com.example.wallet.Api.UpdateUserKycRequest;
import com.example.wallet.Api.UpdateUserKycResponse;
import com.example.wallet.Api.VerifyRequest;
import com.example.wallet.Api.VerifyResponse;
import com.example.wallet.R;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class CompleteKycDetails extends Fragment {
View view;
    Button button;
    ImageView back;
    EditText nameText,number,dob,emailText,c1,c2,c3,c4,c5,c6,an;
    AutoCompleteTextView gender,relation;
    Boolean verifeid=false;
    Boolean send=false;
    ArrayList<String> genderArrayList;
    CountryCodePicker ccp;
    String responseVerifiedNumber;
    GetUserKycResponse g;
    String verifiednumber,phoneNumber;
    String fullNumber="+91";
    String set;
    AutoCompleteTextView textViewSpinner;

    TextView sendotp,verfied;
    Retrofit retrofit;
    LoginService loginService;
    private static final String TAG = "CompleteKycDetails";
    public CompleteKycDetails() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_complete_kyc_details, container, false);
        apiInIt();


        button = view.findViewById(R.id.proceed_button);
        nameText = view.findViewById(R.id.name_editText);
      an=view.findViewById(R.id.name_editText);
        number = view.findViewById(R.id.number);
        gender = view.findViewById(R.id.linearLayout_gender);
        dob = view.findViewById(R.id.linearLayout_dob);
        emailText =view. findViewById(R.id.email_editText);
        relation = view.findViewById(R.id.relation_db);
        ccp=view.findViewById(R.id.ccp);
        sendotp=view.findViewById(R.id.send_otp);
        verfied=view.findViewById(R.id.verifeid);
        AutoCompleteTextView textViewSpinner = (AutoCompleteTextView)
                view.findViewById(R.id.linearLayout_gender);
//        ccp.registerPhoneNumberTextView(number);
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                fullNumber = ccp.getFullNumberWithPlus();
                Log.i(TAG, "onCreateView:fullNumber "+fullNumber);
            }
        });

//get userkyc methgod calling to update the filed





        //date picker
        Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date= new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateCalendar();
            }
            @RequiresApi(api = Build.VERSION_CODES.N)
            private void updateCalendar(){
                String Formate="YYYY-MM-dd";
                SimpleDateFormat sdf= null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    sdf = new SimpleDateFormat(Formate, Locale.US);
                }
                dob.setText(sdf.format(calendar.getTime()));
            }
        };
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(),date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numbers= fullNumber+number.getText().toString();
                Log.i(TAG, numbers);
                phoneNumber=numbers;
                dailog(numbers);


                    }
                });


        //for back

        back =view. findViewById(R.id.backArrow_complete_ur_yvc);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        //for gender
         genderArrayList=new ArrayList<>();
        genderArrayList.add("Male");
        genderArrayList.add("Female");
        genderArrayList.add("Other");



        ArrayAdapter adapterOne = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, genderArrayList);
        textViewSpinner.setAdapter(adapterOne);

        textViewSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                textViewSpinner.showDropDown();
                return false;
            }
        });







        ArrayAdapter<String> adapterRelation = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, getResources()
                .getStringArray(R.array.Relationship));
        AutoCompleteTextView textViewRelation = (AutoCompleteTextView)
               view. findViewById(R.id.relation_db);
        textViewRelation.setAdapter(adapterRelation);
        textViewRelation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                textViewRelation.showDropDown();
                return false;
            }
        });

        nameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                    if(!(number.getText().equals(g.phone))){
                            verfied.setText(View.GONE);
                            sendotp.setText(View.VISIBLE);


                          }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=nameText.getText().toString();
                String phoneNumber=number.getText().toString();
                String gen=gender.getText().toString();
                String dateOfbirth=dob.getText().toString();
                String email=emailText.getText().toString();
                String rela=relation.getText().toString();

                procced();
                Fragment fragment = new KycAddressStep(name,fullNumber+phoneNumber,gen,dateOfbirth,email,rela);
                FragmentManager fragmentManager = ((FragmentActivity)getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                );
                fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        nameText.addTextChangedListener(textWatcher);
        number.addTextChangedListener(textWatcher);
        gender.addTextChangedListener(textWatcher);
        dob.addTextChangedListener(textWatcher);
        emailText.addTextChangedListener(textWatcher);
        relation.addTextChangedListener(textWatcher);
      getUserKvcStatus();




      number.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


          }

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              try {


                  if (responseVerifiedNumber.equals(fullNumber + number.getText().toString())) {
                      verfied.setVisibility(View.VISIBLE);
                      sendotp.setVisibility(View.GONE);
                      verifeid = true;
                  } else if (number.getText().length() != 10 && !responseVerifiedNumber.equals(number.getText().toString())) {
                      sendotp.setVisibility(View.VISIBLE);
                      verfied.setVisibility(View.GONE);
                      verifeid = false;
                      Log.i(TAG, "onTextChanged: " + verifeid);
                  }
              }
              catch (Exception e){
                  Log.i(TAG, "onTextChanged: "+e.getMessage());
              }

          }

          @Override
          public void afterTextChanged(Editable editable) {

          }
      });





        return  view;
    }


    private void dailog(String numbers) {
        String n = number.getText().toString();
        if (!(n.length() == 10)) {
            Toast.makeText(getActivity(), "Enter Valid 10 digit number ", Toast.LENGTH_SHORT).show();
        } else {
            apiInIt();
            generate();
            Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setContentView(R.layout.dialog_otp);
            TextView num = dialog.findViewById(R.id.number_edit);
            num.setText(numbers);
            TextView edit = dialog.findViewById(R.id.edit);
            TextView resend=dialog.findViewById(R.id.resend_otp);
            resend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String numbers= number.getText().toString();
                    generate();
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });



            ImageView cancel = dialog.findViewById(R.id.close_img_dialog_otp);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            Button dialogButton = (Button) dialog.findViewById(R.id.verify_button);
            c1 = (EditText) dialog.findViewById(R.id.code1);
            c2 = (EditText) dialog.findViewById(R.id.code2);
            c3 = (EditText) dialog.findViewById(R.id.code3);
            c4 = (EditText) dialog.findViewById(R.id.code4);
            c5 = (EditText) dialog.findViewById(R.id.code5);
            c6 = (EditText) dialog.findViewById(R.id.code6);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!c1.getText().toString().isEmpty() && !c2.getText().toString().isEmpty() &&
                            !c3.getText().toString().isEmpty() && !c4.getText().toString().isEmpty() &&
                            !c5.getText().toString().isEmpty() && !c6.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "verified", Toast.LENGTH_LONG).show();
                        String a=c1.getText().toString();
                        String b=c2.getText().toString();
                        String c=c3.getText().toString();
                        String d=c4.getText().toString();
                        String e=c5.getText().toString();
                        String f=c6.getText().toString();


                     String  verifyNumber= number.getText().toString();
                        verify(a+b+c+d+e+f,verifyNumber);
                        dialog.dismiss();


                    } else {
                        Toast.makeText(getActivity(), "enter OTP", Toast.LENGTH_LONG).show();

                    }

                }
            });


            c1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (c1.getText().toString().length() == 1) {
                        c2.requestFocus();
                    }
                }
            });

            c2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (c2.getText().toString().length() == 0) {
                        c1.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (c2.getText().toString().length() == 1) {
                        c3.requestFocus();
                    }
                }
            });

            c3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (c3.getText().toString().length() == 0) {
                        c2.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (c3.getText().toString().length() == 1) {
                        c4.requestFocus();
                    }
                }
            });

            c4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (c4.getText().toString().length() == 0 ) {
                        c3.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    // We can call api to verify the OTP here or on an explicit button click
                    if(c4.getText().toString().length()==1){
                        c5.requestFocus();
                    }


                }
            });

            c5.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (c5.getText().toString().length() == 0 ) {
                        c4.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    // We can call api to verify the OTP here or on an explicit button click
                    if(c5.getText().toString().length()==1){
                        c6.requestFocus();
                    }


                }
            });

            c6.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (c6.getText().toString().length() == 0 ) {
                        c5.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    // We can call api to verify the OTP here or on an explicit button click3
                }
            });

            dialog.show();
        }
            }

    private void verify(String s, String verifyNumber) {
        Log.i("pass", s);
            VerifyRequest verifyRequest=new VerifyRequest(fullNumber+verifyNumber,s);
            Call<VerifyResponse> call=loginService.verifyCall(verifyRequest);
            call.enqueue(new Callback<VerifyResponse>() {
                @Override
                public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                    if(!response.isSuccessful())
                    {
                        Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
                    }
                    VerifyResponse generateOtpResponse=response.body();
                    verifeid=false;
                    if(generateOtpResponse.show.type.equals("success")){
                        sendotp.setVisibility(View.GONE);
                        verfied.setVisibility(View.VISIBLE);
                        verifiednumber=verifyNumber;
                        verifeid=true;
                        send=true;


                    }

                    Toast.makeText(getActivity(), generateOtpResponse.show.message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<VerifyResponse> call, Throwable t) {

                }
            });



    }

    public void apiInIt()
    {
        retrofit= ApiClient.getRetrofit();
        loginService= ApiClient.getApiService();
    }
    private void procced() {
        String name=nameText.getText().toString();
        String gen=gender.getText().toString();
        String dateOfbirth=dob.getText().toString();
        String email=emailText.getText().toString();
        String num=number.getText().toString();
        String rela=relation.getText().toString();
        UpdateDetailsRequest updateUserKycRequest=new UpdateDetailsRequest(name,rela,fullNumber+num,gen,dateOfbirth,email);
        Call<UpdateUserKycResponse> call=loginService.detail_call(updateUserKycRequest);
        call.enqueue(new Callback<UpdateUserKycResponse>() {
            @Override
            public void onResponse(Call<UpdateUserKycResponse> call, Response<UpdateUserKycResponse> response) {
                if(!response.isSuccessful())
                {
                    try {
                        Toast.makeText(getActivity(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Log.i("onresponse", e.getMessage());
                    }
                }
                try {

                    UpdateUserKycResponse generateOtpResponse = response.body();


                    Toast.makeText(getActivity(), generateOtpResponse.show.message, Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Log.i(TAG, "onResponse: "+e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<UpdateUserKycResponse> call, Throwable t) {

            }
        });



    }




    public void generate()
    {
        String n=number.getText().toString();
        Log.i("number", n);
        GetOtpRequest getOtpRequest=new GetOtpRequest(fullNumber+n);
        Call<GenerateOtpResponse> call=loginService.otp(getOtpRequest);
        call.enqueue(new Callback<GenerateOtpResponse>() {
            @Override
            public void onResponse(Call<GenerateOtpResponse> call, Response<GenerateOtpResponse> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
                }
                GenerateOtpResponse generateOtpResponse=response.body();
                Toast.makeText(getActivity(), generateOtpResponse.show.message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<GenerateOtpResponse> call, Throwable t) {

            }
        });
    }







            public TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                    String name = nameText.getText().toString().trim();
//                    String noText = number.getText().toString().trim();
//                    String genderText = gender.getText().toString().trim();
//                    String dobText = dob.getText().toString().trim();
//                    String email = emailText.getText().toString().trim();
//                    String relationText = relation.getText().toString().trim();
//
//                    if (!name.isEmpty() && !noText.isEmpty()
//                            && !genderText.isEmpty() && !dobText.isEmpty()&&
//                            !email.isEmpty() && !relationText.isEmpty()&& verifeid){
//                        button.setEnabled(true);
//                    }
//                    Log.i(TAG, verifeid+String.valueOf(noText.length()));



                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String name = nameText.getText().toString().trim();
                    String noText = number.getText().toString().trim();
                    String genderText = gender.getText().toString().trim();
                    String dobText = dob.getText().toString().trim();
                    String email = emailText.getText().toString().trim();
                    String relationText = relation.getText().toString().trim();


                    if (!name.isEmpty()
                            && !genderText.isEmpty() && !dobText.isEmpty()&&
                            !email.isEmpty() && !relationText.isEmpty()&& verifeid) {
                       button.setEnabled(true);
                    }
                    else {
                        button.setEnabled(false);
                    }

                    Log.i(TAG, verifeid+String.valueOf(noText.length()));

                }

                @Override
                public void afterTextChanged(Editable editable) {

                    String name = nameText.getText().toString().trim();

                    String genderText = gender.getText().toString().trim();
                    String dobText = dob.getText().toString().trim();
                    String email = emailText.getText().toString().trim();
                    String relationText = relation.getText().toString().trim();
                    if (!name.isEmpty()
                            && !genderText.isEmpty() && !dobText.isEmpty()&&
                            !email.isEmpty() && !relationText.isEmpty()&& verifeid) {
                        button.setEnabled(true);
                    }
                    else {
                        button.setEnabled(false);
                    }




//                    Log.i(TAG, String.valueOf(nameText.getText().length())+ number.getText().length()+gender.getText().length()+
//                            dob.getText().length() + emailText.getText().length() );
////                    }
//
//                    if (nameText.getText().length()<1 && number.getText().length()==10 && gender.getText().length()<=1 &&
//                            dob.getText().length()<=1 && emailText.getText().length()<=5&& relation.getText().length()<=1){
//                        button.setEnabled(true);
//                    }
//                    else {
//                        button.setEnabled(false);
//                    }



                }
            };

    public void getUserKvcStatus(){
        Call<GetUserKycResponse> call=loginService.getUserCall();
        call.enqueue(new Callback<GetUserKycResponse>() {
            @Override
            public void onResponse(Call<GetUserKycResponse> call, Response<GetUserKycResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    Log.i("not", String.valueOf(response.code()));
                }
                try {

                  g = response.body();
                    if(!g.phone.isEmpty()){
                        responseVerifiedNumber=g.phone.toString();
                        verifeid=true;
                        send=true;
                        sendotp.setVisibility(View.GONE);
                        verfied.setVisibility(View.VISIBLE);



                    }else{
                        verifeid=false;
                    }


                    nameText.setText(g.name);
                    number.setText(g.phone.substring(3));
                    gender.setText(g.gender);
                    dob.setText(g.dob);
                    relation.setText(g.relation);
                    emailText.setText(g.email);
                    send=true;

                } catch (Exception e) {

                    Log.i("E", String.valueOf(e.getMessage()));
                }


            }

            @Override
            public void onFailure(Call<GetUserKycResponse> call, Throwable t) {

            }
        });
    }


}