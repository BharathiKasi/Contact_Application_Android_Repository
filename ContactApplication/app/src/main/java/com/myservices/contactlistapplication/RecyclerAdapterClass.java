package com.myservices.contactlistapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapterClass extends RecyclerView.Adapter<RecyclerAdapterClass.RecyclerViewHolder> {
    Context context;
    List<ContactListPojo> contactListPojos;

    public RecyclerAdapterClass(Context context, List<ContactListPojo> contactListPojos) {
        this.context = context;
        this.contactListPojos = contactListPojos;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_adapter_view, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view, context, contactListPojos);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        ContactListPojo contactListPojo = contactListPojos.get(position);
        if (contactListPojo.getName().equals("#")) {
            holder.firstLettertextView.setText(contactListPojo.getName());
            holder.nameTextView.setText(contactListPojo.getPhoneNumber());
        }
        /*else if(contactListPojo.getName().substring(0,1).equals("0")||contactListPojo.getName().substring(0,1).equals("1")||contactListPojo.getName().substring(0,1).equals("2")||
                contactListPojo.getName().substring(0,1).equals("3")||contactListPojo.getName().substring(0,1).equals("4")||contactListPojo.getName().substring(0,1).equals("5")||
                contactListPojo.getName().substring(0,1).equals("6")||contactListPojo.getName().substring(0,1).equals("7")||contactListPojo.getName().substring(0,1).equals("8")||
                contactListPojo.getName().substring(0,1).equals("9")){
            holder.firstLettertextView.setText(contactListPojo.getName());
            holder.nameTextView.setText(contactListPojo.getPhoneNumber());

        }*/
        else {


            try
            {
                holder.firstLettertextView.setText(contactListPojo.getName().substring(0, 1));
                holder.nameTextView.setText(contactListPojo.getName());
            }
            catch (Exception e){

                holder.firstLettertextView.setText("#");
                holder.nameTextView.setText(contactListPojo.getPhoneNumber());

            }



        }


    }

    @Override
    public int getItemCount() {
        return contactListPojos.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView firstLettertextView, nameTextView;

        public RecyclerViewHolder(@NonNull final View itemView, final Context context, final List<ContactListPojo> contactListPojos) {
            super(itemView);
            firstLettertextView = itemView.findViewById(R.id.text_view);
            nameTextView = itemView.findViewById(R.id.name_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    ((ContactListMainActicity) itemView.getContext()).getUserSelectPosition(position);
                }
            });
        }


    }
}
