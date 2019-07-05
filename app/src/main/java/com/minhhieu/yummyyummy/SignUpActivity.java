package com.minhhieu.yummyyummy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minhhieu.yummyyummy.model.Common;
import com.minhhieu.yummyyummy.model.User;

public class SignUpActivity extends AppCompatActivity {

    EditText edtPhone, edtPass, edtName;
    Button btnSignUp;
    FirebaseDatabase database;
    DatabaseReference tableUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName=findViewById(R.id.edtName);
        edtPass=findViewById(R.id.edtPassword);
        edtPhone=findViewById(R.id.edtPhoneNum);

        btnSignUp=findViewById(R.id.btnSignup);
        database=FirebaseDatabase.getInstance();
        tableUser=database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(edtPhone.getText().toString())&&!TextUtils.isEmpty(edtName.getText().toString())&&!TextUtils.isEmpty(edtPass.getText().toString())){
                    final ProgressDialog mDialog=new ProgressDialog(SignUpActivity.this);
                    mDialog.setMessage("Vui lòng đợi..");
                    mDialog.show();

                    tableUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(edtPhone.getText().toString()).exists()){
                                    mDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Số điện thoại đã được đăng kí", Toast.LENGTH_SHORT).show();
                            }else{
                                try{
                                    mDialog.dismiss();
                                    User user=new User(edtName.getText().toString(), edtPass.getText().toString());
                                    tableUser.child(edtPhone.getText().toString()).setValue(user);
                                    Toast.makeText(SignUpActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(SignUpActivity.this, HomeActivity.class);
                                    Common.currentUser=user;
                                    startActivity(intent);
                                    finish();
                                }catch (Exception ex){

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                }else {
                    Toast.makeText(SignUpActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
//
            }
        });
    }
}
