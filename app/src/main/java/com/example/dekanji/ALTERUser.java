package com.example.dekanji;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ALTERUser {

    private DatabaseReference databaseReference;

    public ALTERUser() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(User.class.getSimpleName());
    }

    public Task<Void> add(User user){

        return databaseReference.push().setValue(user);
    }
}
