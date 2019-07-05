package com.minhhieu.yummyyummy;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minhhieu.yummyyummy.Database.Database;
import com.minhhieu.yummyyummy.model.Food;
import com.minhhieu.yummyyummy.model.Order;
import com.squareup.picasso.Picasso;

public class FoodDetailActivity extends AppCompatActivity {

    TextView food_name, food_price, food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String foodId="";

    FirebaseDatabase database;
    DatabaseReference foods;
    Food curentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        database=FirebaseDatabase.getInstance();
        foods=database.getReference("food");

        numberButton=findViewById(R.id.number_button);
        food_name=findViewById(R.id.food_name);
        food_price=findViewById(R.id.food_price);
        food_description=findViewById(R.id.food_description);
        food_image=findViewById(R.id.imgfood);
        collapsingToolbarLayout=findViewById(R.id.collapsing);
        btnCart=findViewById(R.id.btnCart);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandrdAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        foodId, 
                        curentFood.getName(), 
                        numberButton.getNumber(),
                        curentFood.getPrice(),
                        curentFood.getDiscount()
                ));
                Toast.makeText(FoodDetailActivity.this, "Thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });

        if(getIntent()!=null){
            foodId=getIntent().getStringExtra("foodID");
            if(!foodId.isEmpty()){
                getDetailFood(foodId);
            }
        }
    }

    private void getDetailFood(final String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                curentFood=dataSnapshot.getValue(Food.class);

                Picasso.with(getBaseContext()).load(curentFood.getImage()).into(food_image);
                collapsingToolbarLayout.setTitle(curentFood.getName());
                food_price.setText(curentFood.getPrice());
                food_name.setText(curentFood.getName());
                food_description.setText(curentFood.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
