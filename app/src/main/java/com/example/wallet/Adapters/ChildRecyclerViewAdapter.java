package com.example.wallet.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.Models.ChildModel;
import com.example.wallet.R;
import com.example.wallet.ViewReceiptActivity;

import java.util.ArrayList;

public class ChildRecyclerViewAdapter extends RecyclerView.Adapter<ChildRecyclerViewAdapter.MyViewHolder> {
    AlertDialog alertDialog;
    AlertDialog.Builder alertDialogBuilder;
    public ArrayList<ChildModel> childModelArrayList;
    Context cxt;
    TextView amount,issuesDate,paid,invoice,paymentOption,paymentTitle,viewReceipt;

    private OnItemClickListener onItemClickListener;
    NavController navController;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView type, amount, date;


        public MyViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            type = itemView.findViewById(R.id.note);
            amount = itemView.findViewById(R.id.rs);
            date = itemView.findViewById(R.id.time_and_date_payment_history);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClickListener(position);

                        }

                    }
                }
            });


        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public ChildRecyclerViewAdapter(ArrayList<ChildModel> arrayList, Context mContext) {
        this.cxt = mContext;
        this.childModelArrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_history_recyclerview, parent, false);
        return new MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ChildModel currentItem = childModelArrayList.get(position);
        holder.amount.setText(childModelArrayList.get(position).getAmount());
        holder.type.setText(childModelArrayList.get(position).getType());
        holder.date.setText(childModelArrayList.get(position).getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogBuilder = new AlertDialog.Builder(cxt);
                LayoutInflater inflater = LayoutInflater.from(cxt);
                View v=inflater.inflate(R.layout.dialog_box_view_receipt,null,true);
                v.setClipToOutline(true);
                paymentTitle=v.findViewById(R.id.payment_note);
                amount=v.findViewById(R.id.rsText);
                issuesDate=v.findViewById(R.id.issue_text);
                paid=v.findViewById(R.id.paid_text);
                invoice=v.findViewById(R.id.invoice_text);
                paymentOption=v.findViewById(R.id.cash_text);
                viewReceipt=v.findViewById(R.id.view_receipt);

                paymentTitle.setText(currentItem.getType());
                amount.setText(currentItem.getAmount());
                issuesDate.setText(currentItem.getDate());
                paid.setText(currentItem.getIssuedDate());
                invoice.setText(currentItem.getReceipt());
                paymentOption.setText(currentItem.getPaymentOption());




                alertDialogBuilder.setView(v);
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                viewReceipt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String pdf = currentItem.getInvoice();
                        Intent intent=new Intent(cxt, ViewReceiptActivity.class);
                        intent.putExtra("pdfUrl",pdf);
                        cxt.startActivity(intent);


                    }
                });

            }
        });

    }


    @Override
    public int getItemCount() {
        return childModelArrayList.size();
    }
}
