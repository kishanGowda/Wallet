package com.example.wallet.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.Models.ChildModel;
import com.example.wallet.R;

import java.util.ArrayList;

public class ChildRecyclerViewAdapter extends RecyclerView.Adapter<ChildRecyclerViewAdapter.MyViewHolder> {
public ArrayList<ChildModel> childModelArrayList;
        Context cxt;
private  OnItemClickListener onItemClickListener;


public static class MyViewHolder extends RecyclerView.ViewHolder{
    public TextView type,amount,date;



    public MyViewHolder(View itemView, OnItemClickListener listener) {
        super(itemView);
        type = itemView.findViewById(R.id.note);
        amount = itemView.findViewById(R.id.rs);
        date = itemView.findViewById(R.id.time_and_date_payment_history);



        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        listener.onItemClickListener(position);

                    }

                }
            }
        });


    }
}
public interface OnItemClickListener{
    void onItemClickListener(int position);
}
    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener=listener;
    }

    public ChildRecyclerViewAdapter(ArrayList<ChildModel> arrayList, Context mContext) {
        this.cxt = mContext;
        this.childModelArrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_history_recyclerview, parent, false);
        return new MyViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ChildModel currentItem = childModelArrayList.get(position);

        holder.amount.setText(childModelArrayList.get(position).getAmount());
        holder.type.setText(childModelArrayList.get(position).getType());
        holder.date.setText(childModelArrayList.get(position).getDate());
 }


    @Override
    public int getItemCount() {
        return childModelArrayList.size();
    }
}
