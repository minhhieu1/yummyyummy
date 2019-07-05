package com.minhhieu.yummyyummy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minhhieu.yummyyummy.model.Common;
import com.minhhieu.yummyyummy.model.User;

public class Signin extends AppCompatActivity {

    EditText edtPhone, edtPass;
    Button btnSignin1;
    FirebaseDatabase database;
    DatabaseReference tableUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        edtPhone = findViewById(R.id.edtPhoneNum);
        edtPass = findViewById(R.id.edtPassword);
        btnSignin1 = findViewById(R.id.btnSignin1);

        database = FirebaseDatabase.getInstance();
        tableUser = database.getReference("User");


        btnSignin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtPhone.getText().toString()) && !TextUtils.isEmpty(edtPass.getText().toString())) {
                    final ProgressDialog mDialog = new ProgressDialog(Signin.this);
                    mDialog.setMessage("Vui lòng đợi...");
                    mDialog.show();

                    tableUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


//                        check if user not exist in database
                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                                mDialog.dismiss();
//                            //get user information
                                try {
                                    User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                                    if (user.getPassword().equals(edtPass.getText().toString())) {
                                        user.setPhone(edtPhone.getText().toString());
                                        Toast.makeText(Signin.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(Signin.this, HomeActivity.class);
                                        Common.currentUser=user;
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(Signin.this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception ex) {
                                }
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(Signin.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    Toast.makeText(Signin.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }

//

            }
        });
    }
}
