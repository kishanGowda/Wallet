package com.example.wallet.Fragmets;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wallet.Adapters.DotsIndicatorDecoration;
import com.example.wallet.Adapters.PaymentRequestAdapter;
import com.example.wallet.Api.ApiClient;
import com.example.wallet.Api.GetWalletResponse;
import com.example.wallet.Api.LoginService;
import com.example.wallet.Adapters.GetFilterWalletResponse;
import com.example.wallet.Models.PaymentRequest;
import com.example.wallet.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Fee extends Fragment {
View view;
TextView requestPending,pending,refunded,overdue,requestPendingText,pendingText,refundedText,
        pendingpaymentRequest,overdueText;
GetFilterWalletResponse getFilterWalletResponse;
Retrofit retrofit;
int size;
    Dialog mdialog;
    RecyclerView recyclerView;
LinearLayoutManager layoutManager;
    ArrayList<PaymentRequest> card ;
LoginService loginService;
PaymentRequestAdapter cardAdapter;
TextView viewsummary;
ImageView back,arrow;
    int length=0;


    public Fee() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view= inflater.inflate(R.layout.fragment_fee, container, false);
        requestPending=view.findViewById(R.id.reuest_pending_tv);
        viewsummary=view.findViewById(R.id.view_all_tv_for_fee_summary);
        pending=view.findViewById(R.id.pending_tv);
        refunded=view.findViewById(R.id.refunded_tv);
        overdue=view.findViewById(R.id.overalldue_tv);

        requestPendingText=view.findViewById(R.id.request_paid_tv);
        pendingText=view.findViewById(R.id.four_pending_tv);
        refundedText=view.findViewById(R.id.refundend_tv);
        arrow=view.findViewById(R.id.arrow);
        overdueText=view.findViewById(R.id.one_overdue_tv);
        back=view.findViewById(R.id.backArrow);



        requestPending.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (requestPending.getText().toString().trim().isEmpty()) {
                    mdialog = new Dialog(getActivity());
                    mdialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                    mdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mdialog.setContentView(R.layout.loading_dailog);
                    mdialog.show();
                }
                else {

                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        apiInIt();
        getWallet();
            filterWallet();
            viewsummary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new PaymentRequestFragment();
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
            arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new HistoryPayment();
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
    public void apiInIt()
    {
        retrofit= ApiClient.getRetrofit();
        loginService= ApiClient.getApiService();
    }
    public  void  filterWallet(){
        Call<GetFilterWalletResponse> call= loginService.filterWalletCall("Link Sent");
        call.enqueue(new Callback<GetFilterWalletResponse>() {
            @Override
            public void onResponse(Call<GetFilterWalletResponse> call, Response<GetFilterWalletResponse> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
                }
                getFilterWalletResponse=response.body();
                try {
                    card = new ArrayList<>();

                    length = getFilterWalletResponse.transactions.size();

                    sizes(length);
                    Log.i("length", String.valueOf(length));


                    if (length == 0) {

                    } else if (length >= 3) {
                        for (int i = 0; i <= 2; i++) {
                            card.add(new PaymentRequest("₹ " +getFilterWalletResponse.transactions.get(i).amountPayable, getFilterWalletResponse.transactions.get(i).date, getFilterWalletResponse.transactions.get(i).dueDate, getFilterWalletResponse.transactions.get(i).note,getFilterWalletResponse.transactions.get(i).orgId,getFilterWalletResponse.transactions.get(i).userId,getFilterWalletResponse.transactions.get(i).id,getFilterWalletResponse.transactions.get(i).amount,getFilterWalletResponse.transactions.get(i).status));

                        }
                        buildRecyclerView();
                    } else
                    {
                        for (int i = 0; i <= length - 1; i++) {
                            card.add(new PaymentRequest("₹ " +getFilterWalletResponse.transactions.get(i).amountPayable, getFilterWalletResponse.transactions.get(i).date, getFilterWalletResponse.transactions.get(i).dueDate, getFilterWalletResponse.transactions.get(i).note,getFilterWalletResponse.transactions.get(i).orgId,getFilterWalletResponse.transactions.get(i).userId,getFilterWalletResponse.transactions.get(i).id,getFilterWalletResponse.transactions.get(i).amount,getFilterWalletResponse.transactions.get(i).status));
                        }
                        buildRecyclerView();
                    }
                }
                catch (Exception e){
                    Log.i("length", e.getMessage());
                }


            }

            @Override
            public void onFailure(Call<GetFilterWalletResponse> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buildRecyclerView() {
        recyclerView =view. findViewById(R.id.payment_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        cardAdapter = new PaymentRequestAdapter(card);
        recyclerView.setAdapter(cardAdapter);
        final int radius = getResources().getDimensionPixelSize(androidx.cardview.R.dimen.cardview_default_radius);
        final int dotsHeight = getResources().getDimensionPixelSize(com.google.android.material.R.dimen.abc_action_bar_default_height_material);
        final int color = ContextCompat.getColor(getActivity(), R.color.purple_700);
        recyclerView.addItemDecoration(new DotsIndicatorDecoration(radius, radius * 4, dotsHeight, color, color));
        new PagerSnapHelper().attachToRecyclerView(recyclerView);



    }

    private void sizes(int size) {
      int  num=size;
            size=num;
            pendingpaymentRequest=view.findViewById(R.id.textView23);
            pendingpaymentRequest.setText("("+String.valueOf(size)+")");
        Log.i("hh", String.valueOf(size));

    }

    public void getWallet()
    {
        Call<GetWalletResponse> call=loginService.getWalletCall();
        call.enqueue(new Callback<GetWalletResponse>() {
            @Override
            public void onResponse(Call<GetWalletResponse> call, Response<GetWalletResponse> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
                }
                GetWalletResponse getWalletResponse=response.body();
                requestPending.setText("₹"+getWalletResponse.totalLinksPaid.amount.toString());
                pending.setText("₹"+getWalletResponse.totalLinksPending.amount.toString());
                overdue.setText("₹"+getWalletResponse.totalLinksOverDue.amount.toString());
                refunded.setText("₹"+Float.parseFloat(getWalletResponse.totalRefunded.amount));
                requestPendingText.setText(String.valueOf(getWalletResponse.totalLinksPaid.count+" REQUESTS PAID"));
                pendingText.setText(String.valueOf(getWalletResponse.totalLinksPending.count+" Pending"));
                overdueText.setText(String.valueOf(getWalletResponse.totalLinksOverDue.count+" OVER DUE"));
                refundedText.setText(String.valueOf(""+getWalletResponse.totalRefunded.count+" REFUNDED"));
                Log.i("count", String.valueOf(getWalletResponse.totalLinksPaid.count));
            }

            @Override
            public void onFailure(Call<GetWalletResponse> call, Throwable t) {
                Toast.makeText(getActivity(),String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                Log.i("count", String.valueOf(t.getMessage()));
            }

        });
    }
    


}