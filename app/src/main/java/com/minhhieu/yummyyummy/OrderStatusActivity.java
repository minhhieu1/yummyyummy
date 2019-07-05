package com.minhhieu.yummyyummy;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.minhhieu.yummyyummy.ViewHolder.MenuViewHolder;
import com.minhhieu.yummyyummy.ViewHolder.OrderStatusAdapter;
import com.minhhieu.yummyyummy.ViewHolder.OrderViewHolder;
import com.minhhieu.yummyyummy.model.Catelogy;
import com.minhhieu.yummyyummy.model.Common;
import com.minhhieu.yummyyummy.model.Order;
import com.minhhieu.yummyyummy.model.Request;

import java.util.ArrayList;
import java.util.List;

public class OrderStatusActivity extends AppCompatActivity  {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
//    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;
    OrderStatusAdapter adapter;
    FirebaseDatabase database;
    DatabaseReference requests;
    List<Request> list=new ArrayList<Request>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");
        recyclerView=findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new OrderStatusAdapter(this, list);
        recyclerView.setAdapter(adapter);


        requests.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Request request=dataSnapshot.getValue(Request.class);
                if(request.getPhone().equals(Common.currentUser.getPhone())){
                    list.add(request);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
