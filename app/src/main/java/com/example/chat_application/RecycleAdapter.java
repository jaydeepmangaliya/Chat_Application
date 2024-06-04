package com.example.chat_application;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder>{
    ArrayList<String> userList ;
    Context mcontext;
    String Username;
// for getdata from database
    FirebaseDatabase database ;
    DatabaseReference databaseReference;



    public RecycleAdapter(ArrayList<String> userList, Context context, String Username) {

        this.userList = userList;
        this.mcontext = mcontext;
        this.Username = Username;


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_items,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        databaseReference.child("User").child(userList.get(position)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Othername = snapshot.child("Username").getValue().toString();
                String UserIMG = snapshot.child("img").getValue().toString();

                holder.username.setText(Othername);

                 //cartview
                holder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override

                    public void onClick(View v) {

                        Context context = holder.itemView.getContext();
                        Toast.makeText(context, ""+Othername, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, ChatActivity.class);
                        intent.putExtra("Username",Username);
                        intent.putExtra("othername",Othername);

                        context.startActivity(intent);

                        // ...

                    }

                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder {


        TextView username;
        CircleImageView circleImageView;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.cardtext);
            circleImageView = itemView.findViewById(R.id.circleImageView);
            cardView   = itemView.findViewById(R.id.cardview);
        }
    }
}
