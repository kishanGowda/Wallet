package com.example.wallet.Api;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Transactions {
    private static final String TAG = "Transactions";
    HashMap<String, ArrayList<Transaction>> listHashMap;
    public HashMap<String, ArrayList<Transaction>> getListHashMap() {
        Log.i(TAG, ": "+listHashMap);
        return listHashMap;
    }
}
