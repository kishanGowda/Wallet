package com.example.wallet.Fragmets;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.wallet.R;


public class RemainingKYC extends Fragment {
View view;
    String name, phoneNumber,  gender, dateOfbirth, email, relation,
    houseNo, pincodes, states,  citys,  countrys;
    Button button;




    public RemainingKYC(String name, String phoneNumber, String gender, String dateOfbirth, String email, String relation,
                        String houseNo, String pincodes, String states, String citys, String countrys) {
       this.name=name;
       this.phoneNumber=phoneNumber;
       this.gender=gender;
       this.dateOfbirth=dateOfbirth;
       this.email=email;
       this.relation=relation;
       this.houseNo=houseNo;
       this.pincodes=pincodes;
       this.states=states;
       this.citys=citys;
       this.countrys=countrys;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_remaining_k_y_c, container, false);
        button=view.findViewById(R.id.continue_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new PanDetails(name,phoneNumber,gender,dateOfbirth,email,relation,houseNo,pincodes,states,citys,countrys);
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
}