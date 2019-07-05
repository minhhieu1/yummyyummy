package com.minhhieu.yummyyummy.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.minhhieu.yummyyummy.R;
import com.minhhieu.yummyyummy.model.Order;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends ArrayAdapter<Order> {

    Activity context;
    int resource;
    List<Order> objects;

    public CartAdapter( Activity context, int resource, List<Order> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater=this.context.getLayoutInflater();
        View row=layoutInflater.inflate(this.resource, null);
        TextView txtFoodName=row.findViewById(R.id.cart_item_name);
        TextView txtFoodPrice=row.findViewById(R.id.cart_item_price);
        ImageView imageView=row.findViewById(R.id.cart_item_count);

        Locale locale=new Locale("vi", "VN");
        NumberFormat fmt=NumberFormat.getCurrencyInstance(locale);

        final Order order=this.objects.get(position);
        txtFoodName.setText(order.getProductName());
        txtFoodPrice.setText(fmt.format(Integer.parseInt(order.getPrice())));
        TextDrawable textDrawable=TextDrawable.builder().buildRound(""+order.getQuantity(), Color.RED);
        imageView.setImageDrawable(textDrawable);


        return row;
    }
}
