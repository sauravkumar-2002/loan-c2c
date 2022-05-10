package com.example.loan_c2c.loan_req;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.loan_c2c.R;

import java.util.ArrayList;


public class Accepted_loan_req extends Fragment {
    RecyclerView icrecv;

    ArrayList<Model_loan_req> list;
    loan_req_adapter icadapter;

    public Accepted_loan_req() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_accepted_loan_req, container, false);

                icrecv = v.findViewById(R.id.icrecview);
        icrecv.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();

        icadapter = new loan_req_adapter(list, getContext());
        icrecv.setAdapter(icadapter);
        return v;
    }
}