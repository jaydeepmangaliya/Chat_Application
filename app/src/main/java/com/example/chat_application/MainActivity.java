package com.example.chat_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   MaterialToolbar toolbar;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    RecyclerView recyclerView;
    TextView textView;
    String Usernamebase;
    ArrayList<String> userlist;
    RecycleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbarid);
        toolbar.setOverflowIcon(getDrawable(R.drawable.threedot));
        auth = FirebaseAuth.getInstance();
        userlist = new ArrayList<>();
        recyclerView  = findViewById(R.id.RECYCLEVIEW);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // for get data from database
        firebaseUser = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        // for get username from data base;;
        databaseReference.child("User").child(auth.getUid()).child("Username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Usernamebase = snapshot.getValue().toString();

                getuser();
                adapter = new RecycleAdapter(userlist,MainActivity.this,Usernamebase);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//

      toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
          @Override
          public boolean onMenuItemClick(MenuItem item) {
              if(item.getItemId() == R.id.action_Signout){
                  auth.signOut();
                  startActivity(new Intent(MainActivity.this, LoginActivity.class));
                  finish();
              }
              if(item.getItemId() == R.id.action_profile){
                  startActivity(new Intent(MainActivity.this, ProfileActivity.class));
              }
              return true;
          }
      });
    }

    // for menu options


    // get users for crt user

    public void getuser(){
        databaseReference.child("User").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // expect crtuser all user will added into arraylist
                String key = snapshot.getKey();
                if(!key.equals(firebaseUser.getUid())){
                    userlist.add(key);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}