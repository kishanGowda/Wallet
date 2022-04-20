package com.example.wallet.Fragmets;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.Adapters.PaymentRequestAdapterTwo;
import com.example.wallet.Api.ApiClient;
import com.example.wallet.Api.LoginService;
import com.example.wallet.GetFilterWalletResponse;
import com.example.wallet.Models.PaymentRequest;
import com.example.wallet.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class PaymentRequestFragment extends Fragment {
    View view;
    private static final String TAG = "PaymentRequestFragment";
    ArrayList<PaymentRequest> card ;
    RecyclerView recyclerView;

    RecyclerView.Adapter cardAdapter;
    Retrofit retrofit;
    LoginService loginService;
    RecyclerView.LayoutManager cardLayoutManager;
    ImageView back;
    TextView count;

    public PaymentRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_payment_request, container, false);
        apiInIt();
        filterWallet();
        count = view.findViewById(R.id.toolbarTitle);

        back = view.findViewById(R.id.backArrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return view;
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
                    Toast.makeText(getActivity(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
                GetFilterWalletResponse getFilterWalletResponse = response.body();
                int size = getFilterWalletResponse.transactions.size();
                count.setText("Payment requests (" + size + ")");
                card = new ArrayList<>();
                if (size > 0) {
                    for (int i = 0; i <= size - 1; i++) {
                        Log.i("TAG_id", String.valueOf(getFilterWalletResponse.transactions.get(i).id));
                        Log.i("TAG_userId", String.valueOf(getFilterWalletResponse.transactions.get(i).userId));
//
                        card.add(new PaymentRequest("₹"+getFilterWalletResponse.transactions.get(i).amount,
                                getFilterWalletResponse.transactions.get(i).date,
                                getFilterWalletResponse.transactions.get(i).dueDate,
                                getFilterWalletResponse.transactions.get(i).note,
                                getFilterWalletResponse.transactions.get(i).orgId,
                                getFilterWalletResponse.transactions.get(i).userId,
                                getFilterWalletResponse.transactions.get(i).id,
                                getFilterWalletResponse.transactions.get(i).amount,
                                getFilterWalletResponse.transactions.get(i).status));
                    }
                    Log.i(TAG, "onResponse:Id "+card.get(0).getId());
                    Log.i(TAG, "onResponse:UserID "+card.get(0).getUserId());
                }
                buildRecyclerView();

            }

            @Override
            public void onFailure(Call<GetFilterWalletResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void buildRecyclerView() {
        recyclerView = view.findViewById(R.id.payment_request_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        cardLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(cardLayoutManager);
        cardAdapter = new PaymentRequestAdapterTwo(card, getContext());
        recyclerView.setAdapter(cardAdapter);
    }
}