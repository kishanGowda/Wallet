package com.example.wallet;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wallet.Api.ApiClient;
import com.example.wallet.Api.GetUserKycResponse;
import com.example.wallet.Api.LoginService;
import com.example.wallet.Fragmets.CompleteKycDetails;
import com.example.wallet.Fragmets.DetailsOfCashFree;
import com.example.wallet.Fragmets.DetailsOfPayment;
import com.example.wallet.Fragmets.Fee;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HomePage extends Fragment {
View view;
Button completeKycButton;
Retrofit retrofit;
LoginService loginService;
int size=0;
Button pay;
TextView note,rs,issueDate,dueDate;
ConstraintLayout admisiion;
    GetFilterWalletResponse getFilterWalletResponse;
    TextView viewText,noteTextView;


    public HomePage() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home_page, container, false);
        note=view.findViewById(R.id.admission_fee_tv);
        rs=view.findViewById(R.id.rs_100);
        issueDate=view.findViewById(R.id.issued_date_time_tv);
        dueDate=view.findViewById(R.id.due_date_tv);

        apiInIt();
        filterWallet();
        getUserKvcStatus();
        viewText=view.findViewById(R.id.view_tv);

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayoutSubjet);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        filterWallet();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );


        viewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Fee();
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
        admisiion=view.findViewById(R.id.admission_layout);
        pay=view.findViewById(R.id.pay_now_bt);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orgId= getFilterWalletResponse.transactions.get(0).orgId;
                int userId=getFilterWalletResponse.transactions.get(0).user.id;
                Log.i("userId", String.valueOf(userId));
//                SimpleDateFormat dateFormat = new SimpleDateFormat("HHmm");
//                String millisInString  = dateFormat.format(new Date());
//                Log.i("time", millisInString);
//                String time=millisInString;
                long millis = System.currentTimeMillis();
                long seconds = millis / 1000;
                Log.i("sec", String.valueOf(seconds));


                String order=orgId+userId+seconds;
                Log.i("TAG", order);
                int id=getFilterWalletResponse.transactions.get(0).id;
                Log.i("id", String.valueOf(id));
                String  amount=getFilterWalletResponse.transactions.get(0).amount;
                Log.i("TAG", amount);
                int position=0;
                Intent intent = new Intent(getActivity(), DetailsOfCashFree.class);
                intent.putExtra("order",order);
                intent.putExtra("ids",String.valueOf(id));
                intent.putExtra("amount",amount);
                intent.putExtra("position",String.valueOf(position));
                getActivity().startActivity(intent);


//                Fragment fragment = new DetailsOfPayment(order,id,amount);
//                FragmentManager fragmentManager = ((FragmentActivity)getActivity()).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(
//                        R.anim.slide_in,  // enter
//                        R.anim.fade_out,  // exit
//                        R.anim.fade_in,   // popEnter
//                        R.anim.slide_out  // popExit
//                );
//                fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            }
        });

            // accesing button
        completeKycButton=(Button)view.findViewById(R.id.complete_ur_kyc_btn);
        completeKycButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new CompleteKycDetails();
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


return view;
            }

    public void apiInIt()
    {
        retrofit= ApiClient.getRetrofit();
        loginService= ApiClient.getApiService();
    }
    public  void  filterWallet() {
        Call<GetFilterWalletResponse> call = loginService.filterWalletCall("Link Sent");
        call.enqueue(new Callback<GetFilterWalletResponse>() {
            @Override
            public void onResponse(Call<GetFilterWalletResponse> call, Response<GetFilterWalletResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "token change", Toast.LENGTH_SHORT).show();

                }
                try {
                    getFilterWalletResponse = response.body();
                    size = getFilterWalletResponse.transactions.size();
                    if (size == 0) {
                        admisiion.setVisibility(View.GONE);
                    } else {
                        admisiion.setVisibility(View.VISIBLE);
                        note.setText(getFilterWalletResponse.transactions.get(0).note);
                        rs.setText("₹ " +getFilterWalletResponse.transactions.get(0).amount);
                        issueDate.setText(getFilterWalletResponse.transactions.get(0).date);
                        dueDate.setText(getFilterWalletResponse.transactions.get(0).dueDate);

                    }
                    Log.i("y", String.valueOf(getFilterWalletResponse.transactions.size()));
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetFilterWalletResponse> call, Throwable t) {
                Log.e("msg", String.valueOf(t.getMessage()));
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
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
                   GetUserKycResponse getUserKycResponse = response.body();
                    Log.i("nodona", getUserKycResponse.kycStatus.toString());
                   
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("E", String.valueOf(e.getMessage()));
                }


            }

            @Override
            public void onFailure(Call<GetUserKycResponse> call, Throwable t) {

            }
        });
    }

}