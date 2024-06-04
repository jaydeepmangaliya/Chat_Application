package com.example.chat_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText loginemial,loginpassword;
    TextView notaccout,forgettext;
    Button loginbtn;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
       loginbtn = findViewById(R.id.sentEmailbtn);
       loginemial = findViewById(R.id.forgetEmailal);
       loginpassword = findViewById(R.id.loginPassword);
       notaccout = findViewById(R.id.Haveaccout);
       forgettext = findViewById(R.id.forgettext);

       auth = FirebaseAuth.getInstance();

       loginbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String useremail = loginemial.getText().toString();
               String userpassword = loginpassword.getText().toString();
               if(!useremail.equals("") && !userpassword.equals("")) {
                   login(useremail, userpassword);
               }
               else {
                   Toast.makeText(LoginActivity.this, "Enter Email and PassWord", Toast.LENGTH_SHORT).show();
               }
           }
       });

       notaccout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
               startActivity(i);
           }
       });


       forgettext.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

        Intent i = new Intent(LoginActivity.this , ForgetPasswordActivity.class);
               startActivity(i);
//               finish();
           }
       });
    }

    // for login auth
    public void login(String uemail, String upassword){
        auth.signInWithEmailAndPassword(uemail,upassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                        Intent i  = new Intent(LoginActivity.this,MainActivity.class);

                        startActivity(i);
                        finish();
                        Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                    }
                        else {
                            Toast.makeText(LoginActivity.this, "Login Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    // if user login once , no neet to relogoin , untill logout

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user!= null){
            Intent i  = new Intent(LoginActivity.this , MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}