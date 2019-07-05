package com.minhhieu.yummyyummy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.minhhieu.yummyyummy.Interface.ItemClickListener;
import com.minhhieu.yummyyummy.ViewHolder.FoodViewHolder;
import com.minhhieu.yummyyummy.ViewHolder.MenuViewHolder;
import com.minhhieu.yummyyummy.model.Catelogy;
import com.minhhieu.yummyyummy.model.Food;
import com.minhhieu.yummyyummy.model.Request;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference foodList;
    String catelogyID="";
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        database=FirebaseDatabase.getInstance();
        foodList=database.getReference("food");

        recyclerView=findViewById(R.id.recycleFood);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }
    protected void onStart() {
        super.onStart();
        loadAdapter();
        if (adapter != null)  {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null)  {
            adapter.stopListening();
        }
    }

    private void loadAdapter(){
        if(getIntent()!=null){
            catelogyID=getIntent().getStringExtra("CatelogyID");
        }
        if(!catelogyID.isEmpty()&&catelogyID!=null){
            loadListFood();
        }
    }

    private void loadListFood() {
        FirebaseRecyclerOptions<Food> options =
                new FirebaseRecyclerOptions.Builder<Food>()
                        .setQuery(foodList.orderByChild("menuID").equalTo(catelogyID), Food.class)
                        .build();
        adapter=new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
                holder.txtFoodName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(holder.imgFood);

                final Food local=model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent=new Intent(FoodListActivity.this, FoodDetailActivity.class);
                        intent.putExtra("foodID", adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.food_item, viewGroup, false);

                return new FoodViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
