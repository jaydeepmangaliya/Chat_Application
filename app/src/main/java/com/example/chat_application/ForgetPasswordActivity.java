package com.example.chat_application;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgetPasswordActivity extends AppCompatActivity {


    Button sentemail;
    EditText  emai;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);
       emai = findViewById(R.id.forgetEmailal);

       sentemail = findViewById(R.id.sentEmailbtn);
       auth = FirebaseAuth.getInstance();
       String email = emai.getText().toString();
       sentemail.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String email = emai.getText().toString();
               if(!email.equals("")){
                   forget(email);
               }
               else {
                   Toast.makeText(ForgetPasswordActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
               }
           }
       });




    }
    public  void forget(String emial){

        auth.sendPasswordResetEmail(emial)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ForgetPasswordActivity.this, "Cheak Email", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ForgetPasswordActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }




}