package com.minhhieu.yummyyummy;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnSignup, btnSignin;
    TextView txtSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignup=findViewById(R.id.btnSignup);
        btnSignin=findViewById(R.id.btnSignin);

        txtSlogan=findViewById(R.id.txtSlogan);
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/Nabila.ttf");
        txtSlogan.setTypeface(face);


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup=new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(signup);

            }
        });
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin=new Intent(MainActivity.this, Signin.class);
                startActivity(signin);
            }
        });

    }
}
