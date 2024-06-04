package com.example.chat_application;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    TextView haveaccout;
    EditText semail,spassword,signusername;
    CircleImageView circleImageView;
    FirebaseAuth auth;
    DatabaseReference reference;
    FirebaseDatabase database;
    Button signUpbtn;
    boolean imgcontrol = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        haveaccout = findViewById(R.id.Haveaccout);
        semail = findViewById(R.id.SignEmail);
        spassword = findViewById(R.id.SignPassword);
        signusername = findViewById(R.id.SignUsername);
        signUpbtn = findViewById(R.id.sentEmailbtn);
        circleImageView = findViewById(R.id.circleImageView);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        reference =database.getReference();

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImg();
            }
        });

        haveaccout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this , LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uemail = semail.getText().toString();
                String upass = spassword.getText().toString();
                String uname = signusername.getText().toString();
                
                createAccount(uemail,upass,uname);
            }
        });


    }

    // for create Use

    public void createAccount(String Uemai , String Upassword, String uname){

        auth.createUserWithEmailAndPassword(Uemai ,Upassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //store data in firebase database;
                                reference.child("User").child(auth.getUid()).child("Username").setValue(uname);
                            //
                            if(imgcontrol){

                            }
                            else {
                                reference.child("User").child(auth.getUid()).child("img").setValue("null");
                            }
                            Toast.makeText(RegisterActivity.this, "User Created Successfull", Toast.LENGTH_SHORT).show();
                            Intent i  = new Intent(RegisterActivity.this, LoginActivity.class);

                            startActivity(i);
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    // for choose img;

    public  void chooseImg(){
        Intent i = new Intent();
        i.setType("Image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==1 && requestCode == RESULT_OK) {
            Uri url = data.getData();
            Picasso.get().load(url).into(circleImageView);
            imgcontrol = true;
        }
        else {
            imgcontrol = false;
        }
    }
}