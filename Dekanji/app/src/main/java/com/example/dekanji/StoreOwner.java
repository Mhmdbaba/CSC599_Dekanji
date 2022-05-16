package com.example.dekanji;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StoreOwner extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private StorageReference storageReference;

    String reg_storeowner_name, reg_storeowner_email, reg_storeowner_password, reg_storeowner_conf_password,
            reg_storeowner_storename, reg_storeowner_location, reg_storeowner_phonenumber, reg_storeowner_description;

    final private static int PICK_IMAGE_REQUEST = 1;
    private ImageView reg_pic_display;
    private Uri mImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_owner);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        reg_pic_display = (ImageView) findViewById(R.id.iv_regStore_pic_display);
    }

    public void ImgButton (View view) {
        ImageView iv = (ImageView) view;

        //when the user presses the back button
        if (iv.getTag().toString().equalsIgnoreCase("back")){
            finish();
            startActivity(new Intent(StoreOwner.this, type.class));
        }
    }

    public void Button (View view) {
        Button btn = (Button) view;

        if (btn.getTag().toString().equalsIgnoreCase("register store owner")){
            //user information
            reg_storeowner_name = ((EditText) findViewById(R.id.input_reg_storeowner_name)).getText().toString();
            reg_storeowner_email = ((EditText) findViewById(R.id.input_storeowner_email)).getText().toString();
            reg_storeowner_password = ((EditText) findViewById(R.id.input_reg_storeowner_password)).getText().toString();
            reg_storeowner_conf_password = ((EditText) findViewById(R.id.input_reg_storeowner_conf_password)).getText().toString();

            //store information
            reg_storeowner_storename = ((EditText) findViewById(R.id.input_reg_storeowner_storename)).getText().toString();
            reg_storeowner_location = ((EditText) findViewById(R.id.input_reg_storeowner_location)).getText().toString();
            reg_storeowner_phonenumber = ((EditText) findViewById(R.id.input_reg_storeowner_phonenumber)).getText().toString();
            reg_storeowner_description = ((EditText) findViewById(R.id.input_reg_storeowner_description)).getText().toString();

            if (!reg_storeowner_name.isEmpty() && !reg_storeowner_email.isEmpty()
            && !reg_storeowner_password.isEmpty() && !reg_storeowner_conf_password.isEmpty() && !reg_storeowner_storename.isEmpty()
            && !reg_storeowner_location.isEmpty() && !reg_storeowner_phonenumber.isEmpty() && !reg_storeowner_description.isEmpty()){
                //check if passwords match
                if (reg_storeowner_password.equals(reg_storeowner_conf_password)){
                    String hashed = null;

                    //hashing the password
                    MessageDigest md = null;
                    try {
                        md = MessageDigest.getInstance("SHA-256");
                        md.update(reg_storeowner_password.getBytes());

                        byte[] digest = md.digest();
                        StringBuffer sb = new StringBuffer();

                        for (byte b : digest) {
                            sb.append(String.format("%02x",b & 0xff));
                        }
                        hashed = sb.toString();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }


                    //check if username is found in database and insert into database all credentials
                    String finalHashed = hashed;

                    //create file name with extension
                    StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));

                    //upload image to firebase
                    fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    mAuth.createUserWithEmailAndPassword(reg_storeowner_email, finalHashed)
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()){
                                                        Users storeOwnerUser = new Users(reg_storeowner_name,reg_storeowner_email, finalHashed, 1,
                                                                reg_storeowner_storename,reg_storeowner_location,reg_storeowner_phonenumber,reg_storeowner_description, uri.toString());

                                                        FirebaseDatabase.getInstance().getReference("Users")
                                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                .setValue(storeOwnerUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()){
                                                                    Log.i("Registration: ", "done");

                                                                    //redirect tp login page
                                                                    finish();
                                                                    startActivity(new Intent(StoreOwner.this, MainActivity.class));
                                                                }
                                                                else {
                                                                    Toast.makeText(StoreOwner.this, "Failed to register! Please try again.", Toast.LENGTH_SHORT).show();
                                                                    return;
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                }
                            });
                        }
                    });
                }
                else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            else {
                Toast.makeText(this, "Enter all required Fields", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (btn.getTag().toString().equalsIgnoreCase("add picture")) {
            openFileChooser();
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

//            Picasso.with(this).load(mImageUri).into(reg_pic_display);
            reg_pic_display.setImageURI(mImageUri);
        }
    }

    //get file extension from image
    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

}