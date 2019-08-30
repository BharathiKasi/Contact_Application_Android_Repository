package com.myservices.contactlistapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class ShowData extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapterClass recyclerAdapterClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        List<ContactListPojo> contactListPojoList= (List<ContactListPojo>) getIntent().getSerializableExtra("contactList");
        recyclerView=findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(recyclerAdapterClass==null){
            recyclerAdapterClass=new RecyclerAdapterClass(this,contactListPojoList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(recyclerAdapterClass);
        }
    }
}
