package com.example.wallet.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.wallet.Api.HistoryResponse;
import com.example.wallet.Models.ChildModel;
import com.example.wallet.Models.HistoryModel;
import com.example.wallet.Models.ParentModel;
import com.example.wallet.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class ParentRecyclerViewAdapter extends RecyclerView.Adapter<ParentRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<HistoryModel> parentModelArrayList;
    public Context cxt;
    HistoryResponse historyResponse;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView category;
        public RecyclerView childRecyclerView;


        public MyViewHolder(View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.standard_tv);
            childRecyclerView = itemView.findViewById(R.id.Child_RV);

        }
    }


    public ParentRecyclerViewAdapter(ArrayList<HistoryModel> exampleList, Context context, HistoryResponse historyResponse) {
        parentModelArrayList = exampleList;
        this.cxt = context;
        this.historyResponse=historyResponse;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_recyclerview_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return parentModelArrayList.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        HistoryModel currentItem = parentModelArrayList.get(position);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(cxt,LinearLayoutManager.VERTICAL,false);
        holder.childRecyclerView.setLayoutManager(layoutManager);
        holder.childRecyclerView.setHasFixedSize(true);
        holder.category.setText(currentItem.getMonth());


        ArrayList<ChildModel> arrayList = new ArrayList<>();
        int length = historyResponse.transactions.size();
        ArrayList<String> standard = new ArrayList();
        for (int i = 0; i <= length-1; i++) {
            standard.add(historyResponse.transactions.get(i).month);
        }
        Set values = new HashSet(standard);
        Log.i("kishu", values.toString());

        for (int i = 0; i <= values.size() - 1; i++) ;
        {
            for (int j = 0; j <= historyResponse.transactions.size()-1; j++) {
                if (!parentModelArrayList.get(position).getMonth().equals(historyResponse.transactions.get(j).month)) {

                } else {
                    arrayList.add(new ChildModel(historyResponse.transactions.get(j).note,historyResponse.transactions.get(j).date,
                            historyResponse.transactions.get(j).amount));
//)

                }
            }

        }

        ChildRecyclerViewAdapter childRecyclerViewAdapter = new ChildRecyclerViewAdapter(arrayList, holder.childRecyclerView.getContext());
        holder.childRecyclerView.setAdapter(childRecyclerViewAdapter);
        holder.childRecyclerView.setNestedScrollingEnabled(false);
    }

}




