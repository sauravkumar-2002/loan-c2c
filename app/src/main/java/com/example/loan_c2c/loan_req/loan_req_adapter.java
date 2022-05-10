package com.example.loan_c2c.loan_req;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.loan_c2c.R;

import java.util.ArrayList;

public class loan_req_adapter extends RecyclerView.Adapter<loan_req_adapter.myviewholder> {
ArrayList<Model_loan_req>mlist;
Context context;
    public loan_req_adapter(ArrayList<Model_loan_req>mlist, Context context){
        this.context=context;
        this.mlist=mlist;
    }
    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row,null,false);
        return  new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        holder.username.setText(mlist.get(position).getUsername());
        holder.cibil.setText(mlist.get(position).getCibil());
        holder.rate.setText(mlist.get(position).getRate());
        holder.amount.setText(mlist.get(position).getAmount());
        holder.tenure.setText(mlist.get(position).getTenure());



        holder.singleCardView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

// give the option of reject or accept here...

            }
        });

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView rate,tenure,amount,cibil,username;
        CardView singleCardView;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            username=itemView.findViewById(R.id.singleusername);
            cibil=itemView.findViewById(R.id.cibil);
            rate=itemView.findViewById(R.id.rate);
            amount=itemView.findViewById(R.id.Amount);
            tenure=itemView.findViewById(R.id.Tenure);
            singleCardView=itemView.findViewById(R.id.singleCard);
        }

    }

}
