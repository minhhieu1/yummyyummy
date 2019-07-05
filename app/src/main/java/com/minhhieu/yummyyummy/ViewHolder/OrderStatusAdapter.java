package com.minhhieu.yummyyummy.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minhhieu.yummyyummy.R;
import com.minhhieu.yummyyummy.model.Request;

import java.util.List;

public class OrderStatusAdapter  extends RecyclerView.Adapter<OrderStatusAdapter.MyViewHolder>{
    private List<Request> requests;
    private LayoutInflater layoutInflater;
    private Context context;

    public OrderStatusAdapter(Context context, List<Request> requests) {
        this.requests = requests;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=layoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Request request=requests.get(i);
        myViewHolder.txtAddress.setText(request.getAddress());
        myViewHolder.txtPhone.setText(request.getPhone());
        myViewHolder.txtStatus.setText(Status(request.getStatus()));
        myViewHolder.txtName.setText(request.getName());
    }

    private String Status(String status) {
        if (status=="0"){
            return "Place Order";
        } else if(status=="1"){
            return "Shipping";
        } else return "Shipped";
    }


    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView txtName, txtStatus, txtPhone, txtAddress;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.order_name);
            txtStatus=itemView.findViewById(R.id.order_status);
            txtPhone=itemView.findViewById(R.id.order_phone);
            txtAddress=itemView.findViewById(R.id.order_address);
        }
    }
}
