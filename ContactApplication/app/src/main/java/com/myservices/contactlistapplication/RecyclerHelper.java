package com.myservices.contactlistapplication;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerHelper {
    Context context;
    List<ContactListPojo>contactListPojos;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapterClass recyclerAdapterClass;
    public RecyclerHelper(Context context,List<ContactListPojo>contactListPojos){
        this.context=context;
        this.contactListPojos=contactListPojos;
    }

    public void  setAdapter(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager){
        this.recyclerView=recyclerView;
        this.layoutManager=layoutManager;


    }
}
