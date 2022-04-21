package com.example.wallet.Fragmets;

import static android.app.Activity.RESULT_OK;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wallet.Api.ApiClient;
import com.example.wallet.Api.DeleteDocument;
import com.example.wallet.Api.DocUploadResponse;
import com.example.wallet.Api.GetUserKycResponse;
import com.example.wallet.Api.LoginService;
import com.example.wallet.Api.UpdateUserKycRequest;
import com.example.wallet.Api.UpdateUserKycResponse;
import com.example.wallet.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class PanDetails extends Fragment {
    String name, phoneNumber,  gender, dateOfbirth, email, relation,
            houseNo, pincodes, states,  citys,  countrys;
    View view;
    BottomSheetDialog bottomSheetDialog;
    String message;
    JSONObject json;
    final String pan_pattern = "(([A-Za-z]{5})([0-9]{4})([a-zA-Z]))";
    Boolean aadharfor=false,driving=false,passport=false,voter=false;
    AlertDialog alertDialog;
    String BASE_URL_FOR_IMAGES = "https://s3.ap-south-1.amazonaws.com/test.files.classroom.digital/";

    LoginService loginService;
    Retrofit retrofit;
    File picturePath;

    ImageView imageView,back;
    private static int PICK_Proff_back = 3;
    private static int PICK_Proff_front = 2;
    public static final int PICK_IMAGE = 1;
    String pan,frontPic,backPic;
    TextView panTv,frontTv,backTv;
    AlertDialog.Builder  alertDialogBuilder;
    Dialog dialog;
    Button button;
    ImageView popupImageView;
    ConstraintLayout  panProof,frontProof,backProof,viewPan,viewFront,viewBack;
    LinearLayout linearLayout;
    TextView addharNumber,frontText,backText;
    EditText proof,panNumber,documentNumber;
    ImageView deleteFront,deleteBack,deletePan;
    String panKey,frontKey,backKey;
    Boolean panCheck=false,frontCheck=false,backCheck=false;
    ImageView backing;


    public PanDetails(String name, String phoneNumber, String gender,
                      String dateOfbirth, String email, String relation, String houseNo,
                      String pincodes, String states, String citys, String countrys) {
        // Required empty public constructor
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.gender=gender;
        this.dateOfbirth=dateOfbirth;
        this.email=email;
        this.relation=relation;
        this.houseNo=houseNo;
        this.pincodes=pincodes;
        this.states=states;
        this.citys=citys;
        this.countrys=countrys;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_pan_details, container, false);
        apiInIt();
        button=view.findViewById(R.id.proceed_button);
        proof=view.findViewById(R.id.gender_);
        frontText=view.findViewById(R.id.front_text);
        addharNumber=view.findViewById(R.id.adhar_number_text);
        panProof=view. findViewById(R.id.pan_con);
        backText=view.findViewById(R.id.back_text);
        viewPan=view.findViewById(R.id.pan_view_image);
        viewFront=view.findViewById(R.id.front_view_image);
        panTv=view.findViewById(R.id.pan_view_tv);
        frontTv=view.findViewById(R.id.front_view_tv);
        backTv=view.findViewById(R.id.back_view_tv);
        viewBack=view.findViewById(R.id.back_view_image);
        backProof=view. findViewById(R.id.addhar_back_proff);
        frontProof =view. findViewById(R.id.aadhar_front_image_layout);
        panNumber=view.findViewById(R.id.pan_editText);
        documentNumber=view.findViewById(R.id.aadhar_num_editText);

        documentNumber.addTextChangedListener(textWatcher);
        panNumber.addTextChangedListener(textWatcher);



        panNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String s = panNumber.getText().toString(); // get your editext value here
                Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");

                Matcher matcher = pattern.matcher(s);
// Check if pattern matches
                if (matcher.matches()) {
                    Log.i("Matching","Yes");
                }
                else {
                    button.setEnabled(false);
                    Toast.makeText(getActivity(), "Enter valid pan number", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                String s = panNumber.getText().toString(); // get your editext value here
                Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                Matcher matcher = pattern.matcher(s);
// Check if pattern matches
                if (matcher.matches()) {
                    Log.i("Matching","Yes");
                }
            }
        });






        //address format

        try {

            json = new JSONObject();
            json.put("city",citys);
            json.put("state",states);
            json.put("pincode",pincodes);
            json.put("locality",houseNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        message = json.toString();

        Log.i("msg", message);

        //back fragment

        backing=view.findViewById(R.id.backArrow_remaing_kvc_step_one);
        backing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });



        //deleting the uploded picture

        deleteFront=view.findViewById(R.id.delete_front);
        deleteBack=view.findViewById(R.id.delete_back);
        deletePan=view.findViewById(R.id.delete_pan);

        //delete method
        deleteFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDeleteFront();

            }
        });

        deleteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDeleteback();

            }
        });

        deletePan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDeletepan();

            }
        });
    getUserKvcStatus();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pannumber=panNumber.getText().toString();
                String docNumber=documentNumber.getText().toString();
                Log.i("name", name);
                Log.i("relation", relation);
                Log.i("phone", phoneNumber);
                Log.i("gender", gender);
                Log.i("dateOfbirth", dateOfbirth);
                Log.i("email", email);
                Log.i("message", message);
                Log.i("pannumber",pannumber );
                Log.i("panKey", panKey);


                UpdateUserKycRequest updateUserKycRequest=new UpdateUserKycRequest(name,relation,phoneNumber,gender,dateOfbirth,
                        email,message,pannumber,panKey,"Aadhaar",docNumber,frontKey,backKey);
                Call<UpdateUserKycResponse> call=loginService.updateCall(updateUserKycRequest);
                call.enqueue(new Callback<UpdateUserKycResponse>() {
                    @Override
                    public void onResponse(Call<UpdateUserKycResponse> call, Response<UpdateUserKycResponse> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getActivity(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                        }

                        try {


                            UpdateUserKycResponse userKycResponse = response.body();
                          //  Toast.makeText(getActivity(), userKycResponse.show.message, Toast.LENGTH_SHORT).show();
                            if(userKycResponse.show.type.equalsIgnoreCase("success")){
                                Dialog dialog = new Dialog(getActivity());
                                dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.kvc_complete);
                                Button dialogButton = (Button) dialog.findViewById(R.id.okay_btn);
                                dialogButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        Fragment fragment = new HomePage();
                                        FragmentManager fragmentManager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
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
                                dialog.show();

                            }


                        }
                        catch (Exception e){
                            Log.i("pan", e.getMessage());
                        }
                    }


                    @Override
                    public void onFailure(Call<UpdateUserKycResponse> call, Throwable t) {

                    }
                });
            }
        });


        proof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog = new BottomSheetDialog(getActivity(), androidx.appcompat.R.style.Base_Theme_AppCompat);
                View sheetView = LayoutInflater.from(getActivity()).inflate(R.layout.options, null);
                bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                bottomSheetDialog.setContentView(sheetView);
                bottomSheetDialog.setCanceledOnTouchOutside(true);
                TextView aadhar=(TextView) bottomSheetDialog.findViewById(R.id.aadhar_tv);
                TextView voterr=(TextView) bottomSheetDialog.findViewById(R.id.voter_tv);
                TextView drivingr=(TextView) bottomSheetDialog.findViewById(R.id.driving_tv);
                TextView passportr=(TextView) bottomSheetDialog.findViewById(R.id.passport_tv);
                TextView a1=(TextView) bottomSheetDialog.findViewById(R.id.aadhar_tv);
                TextView b1=(TextView) bottomSheetDialog.findViewById(R.id.voter_tv);
                TextView c1=(TextView) bottomSheetDialog.findViewById(R.id.passport_tv);
                TextView d1=(TextView) bottomSheetDialog.findViewById(R.id.driving_tv);

                aadhar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        proof.setText("Aadhar");
                        addharNumber.setText("Aadhar Number");
                        frontText.setText("Aadhar front");
                        backText.setText("Aadhar Back");
                        documentNumber.setHint("Enter Aadhar card number");
                        aadharfor=true;
                        driving=false;
                        passport=false;
                        voter=false;
                        bottomSheetDialog.dismiss();

                    }

                });
                voterr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        proof.setText("Voter Id");
                        addharNumber.setText("Voter-ID Number");
                        frontText.setText("Voter front");
                        backText.setText("Voter Back");
                        documentNumber.setHint("Enter voter ID number");
                        aadharfor=false;
                        driving=false;
                        passport=false;
                        voter=true;
                        bottomSheetDialog.dismiss();
                    }
                });

                passportr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        proof.setText("Passport");
                        addharNumber.setText("PassPort Number");
                        addharNumber.setHint("Enter passport number");
                        frontText.setText("passport front");
                        backText.setText("passport Back");
                        aadharfor=false;
                        driving=false;
                        passport=true;
                        voter=false;
                        bottomSheetDialog.dismiss();

                    }
                });

                drivingr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        proof.setText("Driving licence");
                        addharNumber.setText("driving-ID Number");
                        documentNumber.setHint("Enter driving Licence number");
                        frontText.setText("driving front");
                        backText.setText("driving Back");
                        aadharfor=false;
                        driving=true;
                        passport=false;
                        voter=false;
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.show();
            }

        });





        documentNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(driving){

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        frontProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              proofFront();

//

            }

        });


        backProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proofBack();
            }

        });

        panProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gallary();
            }

        });

        //view the pic

        panTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(getActivity(), Photo.class);
//                intent.putExtra("key",panKey);
//                getActivity().startActivity(intent);

                         alertDialogBuilder = new AlertDialog.Builder(getContext());
                        final View popupView1 = getLayoutInflater().inflate(R.layout.popup, null);
                        popupView1.setClipToOutline(true);
                        popupImageView = popupView1.findViewById(R.id.documentImageView);
                        Glide.with(view).load(BASE_URL_FOR_IMAGES + panKey).into(popupImageView);
                        alertDialogBuilder.setView(popupView1);
                        alertDialog = alertDialogBuilder.create();
                        alertDialog.setCanceledOnTouchOutside(true);
                        alertDialog.show();

            }
        });

        frontTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), Photo.class);
//                intent.putExtra("key",frontKey);
//                getActivity().startActivity(intent);
                alertDialogBuilder = new AlertDialog.Builder(getContext());
                final View popupView1 = getLayoutInflater().inflate(R.layout.popup, null);
                popupView1.setClipToOutline(true);
                popupImageView = popupView1.findViewById(R.id.documentImageView);
                Glide.with(view).load(BASE_URL_FOR_IMAGES + frontKey).into(popupImageView);
                alertDialogBuilder.setView(popupView1);
                alertDialog = alertDialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(true);
                alertDialog.show();
            }
        });

        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(getActivity(), Photo.class);
//                intent.putExtra("key",backKey);
//                getActivity().startActivity(intent);

                alertDialogBuilder = new AlertDialog.Builder(getContext());
                final View popupView1 = getLayoutInflater().inflate(R.layout.popup, null);
                popupView1.setClipToOutline(true);
                popupImageView = popupView1.findViewById(R.id.documentImageView);
                Glide.with(view).load(BASE_URL_FOR_IMAGES + backKey).into(popupImageView);
                alertDialogBuilder.setView(popupView1);
                alertDialog = alertDialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(true);
                alertDialog.show();
            }
        });
        return  view;
    }

    private void setDeletepan() {

        Call<DeleteDocument> call=loginService.deleteCall(panKey);
        call.enqueue(new Callback<DeleteDocument>() {
            @Override
            public void onResponse(Call<DeleteDocument> call, Response<DeleteDocument> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_LONG).show();
                    Log.i("TAG", "onResponse: ");
                }
                DeleteDocument deleteDocument=response.body();
                Toast.makeText(getActivity(), deleteDocument.show.message, Toast.LENGTH_SHORT).show();
                if ("success".equals(deleteDocument.show.type)){
                    panProof.setVisibility(View.VISIBLE);
                    viewPan.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<DeleteDocument> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("TAG", "onFailure: ");


            }
        });
    }

    private void setDeleteback() {

        Call<DeleteDocument> call=loginService.deleteCall(backKey);
        call.enqueue(new Callback<DeleteDocument>() {
            @Override
            public void onResponse(Call<DeleteDocument> call, Response<DeleteDocument> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_LONG).show();
                    Log.i("TAG", "onResponse: ");
                }
                DeleteDocument deleteDocument=response.body();
                Toast.makeText(getActivity(), deleteDocument.show.message, Toast.LENGTH_SHORT).show();
                if ("success".equals(deleteDocument.show.type)){
                    backProof.setVisibility(View.VISIBLE);
                    viewBack.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<DeleteDocument> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("TAG", "onFailure: ");


            }
        });
    }

    public void setDeleteFront(){
        Call<DeleteDocument> call=loginService.deleteCall(frontKey);
        call.enqueue(new Callback<DeleteDocument>() {
            @Override
            public void onResponse(Call<DeleteDocument> call, Response<DeleteDocument> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_LONG).show();
                    Log.i("TAG", "onResponse: ");
                }
                DeleteDocument deleteDocument=response.body();
                Toast.makeText(getActivity(), deleteDocument.show.message, Toast.LENGTH_SHORT).show();
                if ("success".equals(deleteDocument.show.type)){
                    frontProof.setVisibility(View.VISIBLE);
                    viewFront.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<DeleteDocument> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("TAG", "onFailure: ");


            }
        });

    }

    private void gallary() {

        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        startActivityForResult(pickIntent,PICK_IMAGE);

    }

    private void proofFront(){
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        startActivityForResult(pickIntent,PICK_Proff_front);
    }

    private void proofBack(){
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        startActivityForResult(pickIntent,PICK_Proff_back);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = new File(cursor.getString(columnIndex));
            Log.i("pic", String.valueOf(picturePath));
            cursor.close();
            RequestBody image = RequestBody.create(MediaType.parse("image/*"),picturePath);
            MultipartBody.Part file = MultipartBody.Part.createFormData("file", picturePath.getName(), image);
            Call<DocUploadResponse> call=loginService.docCall(file);
            call.enqueue(new Callback<DocUploadResponse>() {
                @Override
                public void onResponse(Call<DocUploadResponse> call, Response<DocUploadResponse> response) {
                    if(!response.isSuccessful())
                    {
                        Toast.makeText(getActivity(), response.code(), Toast.LENGTH_LONG).show();
                        Log.i("TAG", "onResponse: ");
                    }
                    DocUploadResponse docUploadResponse=response.body();
                    Log.i("TAG", "pan");
                    panKey=docUploadResponse.key.toString();
                    Toast.makeText(getActivity(), docUploadResponse.show.message, Toast.LENGTH_LONG).show();
                    Log.i("pan", panKey);
                    String status=docUploadResponse.show.type;
                    if ("success".equals(docUploadResponse.show.type)){
                        panProof.setVisibility(View.GONE);
                        viewPan.setVisibility(View.VISIBLE);
                        panCheck=true;
                    }
                }

                @Override
                public void onFailure(Call<DocUploadResponse> call, Throwable t) {
                    Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.i("TAG", "onFailure: ");


                }
            });





        }else if (requestCode == PICK_Proff_front && resultCode == RESULT_OK
                && null != data){

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = new File(cursor.getString(columnIndex));
            Log.i("pic", String.valueOf(picturePath));
            cursor.close();
            RequestBody image = RequestBody.create(MediaType.parse("image/*"),picturePath);
            MultipartBody.Part file = MultipartBody.Part.createFormData("file", picturePath.getName(), image);
            Call<DocUploadResponse> call=loginService.docCall(file);
            call.enqueue(new Callback<DocUploadResponse>() {
                @Override
                public void onResponse(Call<DocUploadResponse> call, Response<DocUploadResponse> response) {
                    if(!response.isSuccessful())
                    {
                        Toast.makeText(getActivity(), response.code(), Toast.LENGTH_LONG).show();
                        Log.i("TAG", "onResponse: ");
                    }
                    DocUploadResponse docUploadResponse=response.body();
                    Log.i("TAG", "front");
                    frontKey=docUploadResponse.key.toString();
                    Toast.makeText(getActivity(), docUploadResponse.show.message, Toast.LENGTH_LONG).show();
                    Log.i("front", frontKey);
                    if ("success".equals(docUploadResponse.show.type)){
                        frontProof.setVisibility(View.GONE);
                        viewFront.setVisibility(View.VISIBLE);
                        frontCheck=true;
                    }
                }

                @Override
                public void onFailure(Call<DocUploadResponse> call, Throwable t) {
                    Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.i("TAG", "onFailure: ");


                }
            });

        }
        else {
            if (requestCode == PICK_Proff_back && resultCode == RESULT_OK
                    && null != data) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = new File(cursor.getString(columnIndex));
                Log.i("pic", String.valueOf(picturePath));
                cursor.close();
                RequestBody image = RequestBody.create(MediaType.parse("image/*"), picturePath);
                MultipartBody.Part file = MultipartBody.Part.createFormData("file", picturePath.getName(), image);
                Call<DocUploadResponse> call = loginService.docCall(file);
                call.enqueue(new Callback<DocUploadResponse>() {
                    @Override
                    public void onResponse(Call<DocUploadResponse> call, Response<DocUploadResponse> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getActivity(), response.code(), Toast.LENGTH_LONG).show();
                            Log.i("TAG", "onResponse: ");
                        }
                        DocUploadResponse docUploadResponse = response.body();
                        backKey=docUploadResponse.key.toString();
                        Log.i("TAG", "back");
                        Toast.makeText(getActivity(), docUploadResponse.show.message, Toast.LENGTH_LONG).show();
                        Log.i("back", backKey);
                        if ("success".equals(docUploadResponse.show.type)){
                            backProof.setVisibility(View.GONE);
                            viewBack.setVisibility(View.VISIBLE);
                            backCheck=true;
                        }
                    }

                    @Override
                    public void onFailure(Call<DocUploadResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.i("TAG", "onFailure: ");

                    }
                });

            }
        }


    }
    public void apiInIt()
    {
        retrofit= ApiClient.getRetrofit();
        loginService= ApiClient.getApiService();
    }

    TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String docNumber=documentNumber.getText().toString().trim();
            String panNubre=panNumber.getText().toString().trim();
            button.setEnabled(!docNumber.isEmpty() && !panNubre.isEmpty() && panCheck && frontCheck && backCheck);

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String docNumber=documentNumber.getText().toString().trim();
            String panNubre=panNumber.getText().toString().trim();
        button.setEnabled(!docNumber.isEmpty() && !panNubre.isEmpty() && panCheck && frontCheck && backCheck);

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String docNumber=documentNumber.getText().toString().trim();
            String panNubre=panNumber.getText().toString().trim();
            button.setEnabled(!docNumber.isEmpty() && !panNubre.isEmpty() && panCheck && frontCheck && backCheck);

        }
    };


    public void getUserKvcStatus(){
        try {
        Call<GetUserKycResponse> call=loginService.getUserCall();
        call.enqueue(new Callback<GetUserKycResponse>() {
            @Override
            public void onResponse(Call<GetUserKycResponse> call, Response<GetUserKycResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    Log.i("not", String.valueOf(response.code()));
                }

                    try {


                        GetUserKycResponse g = response.body();
                        panNumber.setText(g.panNo);
                        if (g.addressType.equalsIgnoreCase("Aadhaar")) {
                            proof.setText("Aadhar");
                            addharNumber.setText("Aadhar Number");
                            frontText.setText("Aadhar front");
                            backText.setText("Aadhar Back");
                            documentNumber.setHint("Enter Aadhar card number");
                            documentNumber.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void afterTextChanged(Editable editable) {
                                    if(documentNumber.getText().toString().trim().length()==12){
                                        button.setEnabled(true);
                                    }
                                    else {
                                        button.setEnabled(false);

                                    }
                                }
                            });
                            aadharfor = true;
                            driving = false;
                            passport = false;
                            voter = false;


                        } else if (g.addressType.equalsIgnoreCase("Voter-ID Number")) {
                            proof.setText("Voter Id");
                            addharNumber.setText("Voter-ID Number");
                            frontText.setText("Voter front");
                            backText.setText("Voter Back");
                            documentNumber.setHint("Enter voter ID number");
                            documentNumber.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void afterTextChanged(Editable editable) {
                                    if(documentNumber.getText().toString().trim().length()==10){
                                        button.setEnabled(true);
                                    }
                                    else {
                                        button.setEnabled(false);

                                    }
                                }
                            });

                            aadharfor = false;
                            driving = false;
                            passport = false;
                            voter = true;

                        } else if (g.addressType.equalsIgnoreCase("Driving licence")) {
                            proof.setText("Driving licence");
                            addharNumber.setText("driving-ID Number");
                            documentNumber.setHint("Enter driving Licence number");
                            documentNumber.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void afterTextChanged(Editable editable) {
                                    if(documentNumber.getText().toString().trim().length()==10){
                                        button.setEnabled(true);
                                    }
                                    else {
                                        button.setEnabled(false);

                                    }
                                }
                            });

                            frontText.setText("driving front");
                            backText.setText("driving Back");
                            aadharfor = false;
                            driving = true;
                            passport = false;
                            voter = false;

                        } else {
                            proof.setText("Passport");
                            addharNumber.setText("PassPort Number");
                            addharNumber.setHint("Enter passport number");
                            frontText.setText("passport front");
                            backText.setText("passport Back");
                            aadharfor = false;
                            driving = false;
                            passport = true;
                            voter = false;
                        }

                        if (g.documentBack != null) {
                            backKey = g.documentBack.toString();
                            backProof.setVisibility(View.GONE);
                            viewBack.setVisibility(View.VISIBLE);
                            backCheck = true;
                        }
                        if (g.panFront != null) {
                            panKey = g.panFront.toString();
                            panProof.setVisibility(View.GONE);
                            viewPan.setVisibility(View.VISIBLE);
                            panCheck = true;
                        }
                        if (g.documentFront != null) {
                            frontKey = g.documentFront.toString();
                            frontProof.setVisibility(View.GONE);
                            viewFront.setVisibility(View.VISIBLE);
                            frontCheck = true;
                        }
                    }
                    catch (Exception e){
                        Log.i("TAG", "onResponse: "+e.getMessage());
                    }



                  




            }

            @Override
            public void onFailure(Call<GetUserKycResponse> call, Throwable t) {

            }
        });
        } catch (Exception e) {

            Log.i("E", String.valueOf(e.getMessage()));
        }
    }

}




