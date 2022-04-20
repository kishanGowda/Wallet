package com.example.wallet.Fragmets;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wallet.Api.AddressRequest;
import com.example.wallet.Api.ApiClient;
import com.example.wallet.Api.GetUserKycResponse;
import com.example.wallet.Api.LoginService;
import com.example.wallet.Api.PincodeResponse;
import com.example.wallet.Api.UpdateDetailsRequest;
import com.example.wallet.Api.UpdateUserKycResponse;
import com.example.wallet.Models.AdditionData;
import com.example.wallet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class KycAddressStep extends Fragment {
    View view;
    Button button;
    ImageView back;
    ProgressDialog dialog;
    String message;
    JSONObject json;

    EditText house, pincode;
    LoginService loginService,loginServicePin;
    Retrofit retrofit;
    AutoCompleteTextView state, city, country;
    String name, phoneNumber,  gender, dateOfbirth, email, relation;


    public KycAddressStep(String name, String phoneNumber, String gen, String dateOfbirth, String email, String rela) {
        // Required empty public constructor
        this.name=name;
        this.phoneNumber=phoneNumber;
        gender=gen;
        this.dateOfbirth=dateOfbirth;
        this.email=email;
        relation=rela;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_kyc_address_step, container, false);
        apiInit();

        Log.i("num",phoneNumber );
        button =view. findViewById(R.id.proceed_addr_button);
        house = view.findViewById(R.id.house_editText);
        pincode = view.findViewById(R.id.pincode_editText);
        state = view.findViewById(R.id.state_editText);
        city = view.findViewById(R.id.city_editText);
        country = view.findViewById(R.id.country_editText);

        getUserKvcStatus();


        back = view.findViewById(R.id.backArrow_kvc_seconde_step);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        house.addTextChangedListener(textWatcher);
        pincode.addTextChangedListener(textWatcher);
        state.addTextChangedListener(textWatcher);
        city.addTextChangedListener(textWatcher);
        country.addTextChangedListener(textWatcher);


        button = view.findViewById(R.id.proceed_addr_button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String houseNo=house.getText().toString().trim();
                String pincodes=pincode.getText().toString().trim();
                String states=state.getText().toString().trim();
                String citys=city.getText().toString().trim();
                String countrys=country.getText().toString().trim();

                try {

                    json = new JSONObject();
                    json.put("city",citys);
                    json.put("state",states);
                    json.put("pincode",pincodes);
                    json.put("locality",houseNo);
                    json.put("country",countrys);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                message = json.toString();
                Log.i("msg", message);
                procced(message);
//                UpdateUserKycRequest updateUserKycRequest=new UpdateUserKycRequest(name,relation,phoneNumber,gender,dateOfbirth,email,message,null,null,null,null,null,null);
//                Call<UpdateUserKycResponse> call=loginService.updateCall(updateUserKycRequest);
//                call.enqueue(new Callback<UpdateUserKycResponse>() {
//                    @Override
//                    public void onResponse(Call<UpdateUserKycResponse> call, Response<UpdateUserKycResponse> response) {
//                        if(!response.isSuccessful())
//                        {
//                            Toast.makeText(getActivity(),String.valueOf( response.code()), Toast.LENGTH_SHORT).show();
//                        }
//                        UpdateUserKycResponse userKycResponse=response.body();
//                        Toast.makeText(getActivity(), userKycResponse.show.message, Toast.LENGTH_SHORT).show();
//                        Log.i("sucess", response.message());
//                    }
//
//                    @Override
//                    public void onFailure(Call<UpdateUserKycResponse> call, Throwable t) {
//                        Toast.makeText(getActivity(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
//
//                    }
//                });


                    Fragment fragment = new PanDetails(name, phoneNumber, gender, dateOfbirth, email, relation, houseNo, pincodes, states, citys, countrys);
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

        return  view;
    }

    private void apiInit() {
        loginService = ApiClient.getApiService();
        loginServicePin = ApiClient.getApiServicePin();
    }

    private void procced(String message) {
        AddressRequest updateUserKycRequest=new AddressRequest(name,relation,phoneNumber,gender,dateOfbirth,email,message);
            Call<UpdateUserKycResponse> call=loginService.address_call(updateUserKycRequest);
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
                        Log.i("TAG", "onResponse: "+e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<UpdateUserKycResponse> call, Throwable t) {

                }
            });



        }



    TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


               }

        @Override
        public void afterTextChanged(Editable editable) {

            if (pincode.getText().toString().length() == 6) {
                try {
                    Call<List<PincodeResponse>> pincodeResponseCall = loginServicePin.PINCODE_RESPONSE_CALL(Long.parseLong(pincode.getText().toString()));

                    pincodeResponseCall.enqueue(new Callback<List<PincodeResponse>>() {
                        @Override
                        public void onResponse(Call<List<PincodeResponse>> call, Response<List<PincodeResponse>> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();

                            }
                            List<PincodeResponse> pincodeResponse = response.body();
                            PincodeResponse pincodeResponse1 = pincodeResponse.get(0);
                            if (pincodeResponse.get(0).getStatus().equals("Success")) {
                                state.setText(pincodeResponse1.getPostOffice().get(0).getState());
                                city.setText(pincodeResponse1.getPostOffice().get(0).getDistrict());
                                country.setText(pincodeResponse1.getPostOffice().get(0).getCountry());

                            } else {
                                Toast.makeText(getContext(), "Invalid Pincode", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<List<PincodeResponse>> call, Throwable t) {
                            Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Please Enter a Valid Pincode", Toast.LENGTH_SHORT).show();
                }
            }
            String houseNo=house.getText().toString().trim();
            String pincodeT=pincode.getText().toString().trim();
            String stateT=state.getText().toString().trim();
            String cityT=city.getText().toString().trim();
            String countryT=country.getText().toString().trim();
            button.setEnabled(!houseNo.isEmpty() && !pincodeT.isEmpty() && !stateT.isEmpty() && !cityT.isEmpty() && !countryT.isEmpty());

        }
    };

    public void getUserKvcStatus(){
        Call<GetUserKycResponse> call=loginService.getUserCall();
        call.enqueue(new Callback<GetUserKycResponse>() {
            @Override
            public void onResponse(Call<GetUserKycResponse> call, Response<GetUserKycResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    Log.i("not", String.valueOf(response.code()));
                }
                try {

                    GetUserKycResponse g = response.body();
                    String addres = g.address;
                    JSONObject object2 = new JSONObject(addres);
                    String cityOnResponse = object2.getString("city");
                    String stateOnResponse = object2.getString("state");
                    String pincodeOnResponse = object2.getString("pincode");
                    String localityOnResponse = object2.getString("locality");
                    String countryOnResponse= object2.getString("country");
                    house.setText(localityOnResponse);
                    pincode.setText(pincodeOnResponse);
                    state.setText(stateOnResponse);
                    city.setText(cityOnResponse);
                    country.setText(countryOnResponse);


                    Log.i("TAG", "onResponse: hh"+cityOnResponse+stateOnResponse+pincodeOnResponse+localityOnResponse);


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