package com.minhhieu.yummyyummy;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.minhhieu.yummyyummy.Database.Database;
import com.minhhieu.yummyyummy.ViewHolder.CartAdapter;
import com.minhhieu.yummyyummy.model.Common;
import com.minhhieu.yummyyummy.model.Order;
import com.minhhieu.yummyyummy.model.Request;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class CartActivity extends AppCompatActivity {
    ListView lvCart;
    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotal;
    Button btnCart;

    List<Order> cart;
    CartAdapter cartAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");
        txtTotal = findViewById(R.id.txtTotal);
        btnCart = findViewById(R.id.btnCart);
        lvCart = findViewById(R.id.lvCart);
        cart = new Database(this).getCart();
        cartAdapter = new CartAdapter(CartActivity.this, R.layout.cartlayout, cart);
        lvCart.setAdapter(cartAdapter);

        int total = 0;
        for (Order order : cart) {
            total += (Integer.parseInt(order.getPrice())) * Integer.parseInt(order.getQuantity());
        }
        Locale locale = new Locale("vi", "VN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txtTotal.setText(fmt.format(total));

            btnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cart.size()!=0){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
                        alertDialog.setTitle("Thêm 1 bước nữa!");
                        alertDialog.setMessage("Nhập vào địa chỉ muốn giao tới: ");
                        final EditText editText = new EditText(CartActivity.this);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                        );
                        editText.setLayoutParams(layoutParams);
                        alertDialog.setView(editText);
                        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
                        alertDialog.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Request request = new Request(
                                        Common.currentUser.getPhone(),
                                        Common.currentUser.getName(),
                                        editText.getText().toString(),
                                        txtTotal.getText().toString(),
                                        cart
                                );

                                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                                new Database(getBaseContext()).cleanCart();


                                Toast.makeText(CartActivity.this, "Đã đặt hàng", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                        alertDialog.setNegativeButton("HUỶ BỎ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }else Toast.makeText(CartActivity.this, "Bạn chưa có món hàng nào trong giỏ hàng", Toast.LENGTH_SHORT).show();
                    }

            });



    }
}
