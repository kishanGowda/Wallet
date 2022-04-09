package com.example.wallet.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.Models.PaymentRequest;
import com.example.wallet.R;

import java.util.ArrayList;

public class PaymentRequestAdapter extends RecyclerView.Adapter<PaymentRequestAdapter.CardViewHolder> {
    ArrayList<PaymentRequest> card;


    public PaymentRequestAdapter(ArrayList<PaymentRequest> card) {
        this.card=card;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_request_recyclerview,parent,false);
        CardViewHolder cardViewHolder = new CardViewHolder(view);
        return cardViewHolder;

    }
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        PaymentRequest payment = this.card.get(position);



        holder.rupees.setText(payment.getRupees());
        holder.dueDate.setText(payment.getDueDate());
        holder.issuedDateTime.setText(payment.getIssuedDateTime());
        holder.admission.setText(payment.getFeeType());


    }
    @Override
    public int getItemCount() {
            return card.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        public TextView rupees;
        public TextView issuedDateTime;
        public TextView dueDate,admission;


        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            rupees = itemView.findViewById(R.id.rs_100);
            issuedDateTime = itemView.findViewById(R.id.issued_date_time_tv);
            dueDate = itemView.findViewById(R.id.due_date_tv);
            admission=itemView.findViewById(R.id.admission_fee_tv);

        }
    }




}
