package com.example.chat_application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MviewHolder> {
    ArrayList<ChatModelClass> list ;
    String username;
    boolean stuts ;
    int sent ;
    int recive ;

    public ChatAdapter(ArrayList<ChatModelClass> list, String username) {
        this.list = list;
        this.username = username;
         stuts = false;
        sent=1;
        recive=2;
    }


    @NonNull
    @Override
    public MviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        if(viewType == sent){
           view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sender_card,parent,false);
        }
        else {
            view    = LayoutInflater.from(parent.getContext()).inflate(R.layout.reciver_card,parent,false);
        }
        return new MviewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MviewHolder holder, int position) {

        if (holder.textView!= null) {




            holder.textView.setText(list.get(position).getMainMessage());


        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public  class MviewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MviewHolder(@NonNull View itemView) {
            super(itemView);

            if (stuts) {

                textView = itemView.findViewById(R.id.sentertext);

            } else {

                textView = itemView.findViewById(R.id.recivertext);

            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).getSender()!= null && list.get(position).getSender().equals(username)){

            stuts= true;

            return 1;

        } else {

            stuts = false;

            return 2;

        }
    }
}

