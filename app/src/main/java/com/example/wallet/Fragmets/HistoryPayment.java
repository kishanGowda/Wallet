package com.example.wallet.Fragmets;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wallet.Adapters.ParentRecyclerViewAdapter;
import com.example.wallet.Api.ApiClient;
import com.example.wallet.Api.HistoryResponse;
import com.example.wallet.Api.LoginService;
import com.example.wallet.Models.ChildModel;
import com.example.wallet.Models.HistoryModel;
import com.example.wallet.Models.ParentModel;
import com.example.wallet.Models.PaymentRequest;
import com.example.wallet.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HistoryPayment extends Fragment {
    View view;
    Retrofit retrofit;
    LoginService loginService;
    RecyclerView parentRecyclerView;
    LinearLayoutManager parentLayoutManager;
    ArrayList<String> stdName;
    HistoryResponse historyResponse;
    private static final String TAG = "HistoryPaymentFragment";


    ParentRecyclerViewAdapter adapter;

    ArrayList<ParentModel> card;
   ArrayList <HistoryModel> historyModel;
   ImageView back;


    public HistoryPayment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history_payment, container, false);
        apiInit();
        standard();

        // for back
        back=view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return view;
    }
    public void apiInit() {

        retrofit = ApiClient.getRetrofit();
        loginService = ApiClient.getApiService();

    }

    public void standard() {
        Call<HistoryResponse> call=loginService.historyWalletCall("Paid","true");
        call.enqueue(new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_LONG).show();
                    Log.i("TAG", "onResponseFailure: ");
                }
            historyResponse =response.body();
                historyResponse = response.body();
                ArrayList<String> standard=new ArrayList();
                for(int i=0;i<=historyResponse.transactions.size()-1;i++) {
                    standard.add(historyResponse.transactions.get(i).month);
                }
                Set values=new HashSet(standard);
                Log.i("tag",values.toString());
                ArrayList<String> uni=new ArrayList<>(values);
                historyModel =new ArrayList<>();
                for (int i = 0; i <= values.size()-1; i++) {
                    historyModel.add(new HistoryModel(uni.get(i)));
                }
                buildRecyclerViewAllStudent();
            }

            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("TAG", String.valueOf(t.getMessage()));


            }
        });
    }

    public void buildRecyclerViewAllStudent() {
        parentRecyclerView = view.findViewById(R.id.rv_parent);
        parentRecyclerView.setHasFixedSize(true);
        parentLayoutManager = new LinearLayoutManager(getContext());
        parentRecyclerView.setLayoutManager(parentLayoutManager);
        adapter = new ParentRecyclerViewAdapter(historyModel, getContext(),historyResponse);
        parentRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}