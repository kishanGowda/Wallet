package com.example.wallet.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.Fragmets.DetailsOfCashFree;
import com.example.wallet.Models.PaymentRequest;
import com.example.wallet.R;

import java.util.ArrayList;

public class PaymentRequestAdapterTwo extends RecyclerView.Adapter<PaymentRequestAdapterTwo.CardViewHolder> {
Context context;
ArrayList<PaymentRequest> card;


    public PaymentRequestAdapterTwo(ArrayList<PaymentRequest> card, Context context) {
        this.card=card;
        this.context=context;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_request_recyclerview,parent,false);
        CardViewHolder cardViewHolder = new CardViewHolder(view);
        return cardViewHolder;

    }
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PaymentRequest payment = this.card.get(position);
        holder.rupees.setText(payment.getRupees());
        holder.dueDate.setText(payment.getDueDate());
        holder.issuedDateTime.setText(payment.getIssuedDateTime());
        holder.admission.setText(payment.getFeeType());
        if(payment.getStatus().equalsIgnoreCase("Overdue")){
        holder.overdue.setVisibility(View.VISIBLE);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, String.valueOf(position), Toast.LENGTH_LONG).show();
                Log.i("TAG", String.valueOf(payment.getId()));
                Log.i("TAG2", String.valueOf(payment.getUserId()));
                Log.i("TAG3", String.valueOf(payment.getOrgID()));
                 String orgId= payment.getOrgID();
                int userId=payment.getUserId();
                Log.i("userid", String.valueOf(userId));
//
                int id=payment.getId();
                Log.i("id", String.valueOf(id));
                String  amount=payment.getAmount();
                long millis = System.currentTimeMillis();
                long seconds = millis / 1000;
                Log.i("sec", String.valueOf(seconds));

                String order=orgId+userId+seconds;
                Log.i("TAG", amount);
                Intent intent = new Intent(context, DetailsOfCashFree.class);
                intent.putExtra("order",order);
                intent.putExtra("ids",String.valueOf(id));
                intent.putExtra("amount",amount);
                intent.putExtra("position",String.valueOf(position));
                context.startActivity(intent);


            }
        });





    }
    @Override
    public int getItemCount() {
        return card.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        public TextView rupees;
        public TextView issuedDateTime;
        public TextView dueDate;
         public TextView admission,overdue;


        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            rupees = itemView.findViewById(R.id.rs_100);
            issuedDateTime = itemView.findViewById(R.id.issued_date_time_tv);
            dueDate = itemView.findViewById(R.id.due_date_tv);
            admission=itemView.findViewById(R.id.admission_fee_tv);
            overdue=itemView.findViewById(R.id.overduee_in_pr);



        }
    }




}
