package com.example.chat_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {


    ImageView bakeimg ;
    TextView usernametext;
    String  key;
    EditText sentmessagetext;
    FloatingActionButton sentmessbtn;
    RecyclerView chatrecycleView;
    FirebaseDatabase database;
    DatabaseReference reference;
    ChatAdapter chatadapter;
    ArrayList<ChatModelClass> listll;

    String username,othername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        listll = new ArrayList<>();

        bakeimg = findViewById(R.id.backarrowimg);
        sentmessagetext = findViewById(R.id.sentmssagetext);
        usernametext = findViewById(R.id.usernametext);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        sentmessbtn =findViewById(R.id.sentmessagebtn);
        chatrecycleView = findViewById(R.id.chatRecycleview);
        chatrecycleView.setLayoutManager(new LinearLayoutManager(this));
       Intent i = getIntent();
       othername = i.getStringExtra("othername");
        username = i.getStringExtra("Username");
       usernametext.setText(othername);

       bakeimg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i  = new Intent(ChatActivity.this , MainActivity.class);
               startActivity(i);
               finish();
           }
       });

/// sentbutton

        sentmessbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = sentmessagetext.getText().toString();

                if(!message.equals("")){
                                       sentmessagetoUser(message);
                    sentmessagetext.setText("");
                }
            }
        });

        // getall message from firebase -->addchildeventlisner;
        getMessages();

    }
    // for sent meassage to user
   public void sentmessagetoUser(String MainMessage){
        key = reference.child("Messages").child(username).child(othername).push().getKey();
       HashMap<String ,Object> messageMAP = new HashMap<>();
      messageMAP.put("message",MainMessage);
      messageMAP.put("Sender",username);
      // store databse
       reference.child("Messages").child(username).child(othername).child(key).setValue(messageMAP)
               .addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
      if (task.isSuccessful()){
          reference.child("Messages").child(othername).child(username).child(key).setValue(messageMAP);
      }
                   }
               });
   }

    public void getMessages() {
        reference.child("Messages").child(username).child(othername)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listll.clear();
                        for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                            ChatModelClass model = dataSnapshot.getValue(ChatModelClass.class);
                            model.setMainMessage((String) dataSnapshot.getKey());
                            listll.add(model);


                        }
                        chatadapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



        chatadapter =new ChatAdapter(listll,username);
        chatrecycleView.setAdapter(chatadapter);
    }
}