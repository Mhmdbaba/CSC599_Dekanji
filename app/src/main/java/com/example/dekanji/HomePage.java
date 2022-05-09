package com.example.dekanji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity implements MyAdapterHP.OnNoteListener {
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    Users profileUser;

    RecyclerView recyclerView;
    MyAdapterHP myAdapterHP;
    private ArrayList<Users> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        TextView tv_name_homepage = (TextView) findViewById(R.id.tv_name_disp_homepage);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profileUser = snapshot.getValue((Users.class));

                tv_name_homepage.setText(profileUser.getName());
                //Log.i("onDataChange: ", profileUser.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomePage.this,"Please try again!", Toast.LENGTH_SHORT);
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.store_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapterHP = new MyAdapterHP(this, list, this);
        recyclerView.setAdapter(myAdapterHP);

        //get the products from database
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Users user = dataSnapshot.getValue(Users.class);
                    if (user.getStoreOwner() == 1) {
                        list.add(user);
                    }
                    myAdapterHP.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    public void TextView (View view) {
        TextView tv = (TextView) view;

        if (tv.getTag().toString().equalsIgnoreCase("profile")){
            finish();
            startActivity(new Intent(HomePage.this,UserProfile.class));
        }
    }

    @Override
    public void onNoteClick(int position) {
        Users u = list.get(position);
        Intent intent = new Intent(this, StoreDisplay.class);
        intent.putExtra("EDIT",u);
        startActivity(intent);
    }
}