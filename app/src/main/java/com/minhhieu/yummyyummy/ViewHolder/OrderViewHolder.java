package com.minhhieu.yummyyummy.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.minhhieu.yummyyummy.Interface.ItemClickListener;
import com.minhhieu.yummyyummy.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtOrderID, txtORderStatus, txtOrderPhone, txtOrderAddress ;
    private ItemClickListener itemClickListener;



    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        //View v = this.itemView;

 txtOrderID =itemView.findViewById(R.id.order_name);
 txtOrderPhone = itemView.findViewById(R.id.order_phone);
 txtOrderAddress = itemView.findViewById(R.id.order_address);
 txtORderStatus = itemView.findViewById(R.id.order_status);
//        txtOrderID=itemView.findViewById(R.id.order_name);
//        txtOrderPhone=itemView.findViewById(R.id.order_phone);
//        txtORderStatus=itemView.findViewById(R.id.order_status);

//
    //  itemView.setOnClickListener(this);

    }


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
        Log.e("finish onclick", "finish on click");

    }


}
