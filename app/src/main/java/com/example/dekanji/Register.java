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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private StorageReference storageReference;


    String reg_name;
    String reg_email;
    String reg_address;
    String reg_password;
    String reg_conf_password;


    final private static int PICK_IMAGE_REQUEST = 1;
    private ImageView reg_pic_display;
    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reg_pic_display = (ImageView) findViewById(R.id.iv_reg_pic_display);


        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

    }

    public void ImgButton (View view) {
        ImageView iv = (ImageView) view;

        //when the user presses the back button
        if (iv.getTag().toString().equalsIgnoreCase("back")){
            finish();
            startActivity(new Intent(Register.this, type.class));
        }
    }

    public void Button (View view) {
        Button btn = (Button) view;

        //register button (submit)
        if (btn.getTag().toString().equalsIgnoreCase("register")){
            //gets variables and puts them in strings
            reg_name = ((EditText) findViewById(R.id.input_reg_name)).getText().toString().trim();
            reg_email = ((EditText) findViewById(R.id.input_reg_email)).getText().toString().trim();
            reg_password = ((EditText) findViewById(R.id.input_reg_password)).getText().toString().trim();
            reg_conf_password = ((EditText) findViewById(R.id.input_reg_conf_password)).getText().toString().trim();
            reg_address = ((EditText) findViewById(R.id.input_reg_address)).getText().toString().trim();

            //checks if fields are empty
            if (!reg_name.isEmpty() && !reg_email.isEmpty() && !reg_password.isEmpty()
                    && !reg_conf_password.isEmpty() && !reg_address.isEmpty() && mImageUri != null){
                //check if passwords match
                if (reg_password.equals(reg_conf_password)){
                    String hashed = null;

                    //hashing the password
                    MessageDigest md = null;
                    try {
                        md = MessageDigest.getInstance("SHA-256");
                        md.update(reg_password.getBytes());

                        byte[] digest = md.digest();
                        StringBuffer sb = new StringBuffer();

                        for (byte b : digest) {
                            sb.append(String.format("%02x",b & 0xff));
                        }
                        hashed = sb.toString();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

//                    Toast.makeText(this, hashed, Toast.LENGTH_SHORT).show();

                    //save to database
                    String finalHashed = hashed;
                    //create a name for time .filetype
                    StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));

                    //upload image to firebase
                    fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Toast.makeText(Register.this, "ImageUploaded", Toast.LENGTH_SHORT).show();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    mAuth.createUserWithEmailAndPassword(reg_email,finalHashed)
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        Users user = new Users(reg_name,reg_email, finalHashed, reg_address,
                                                                uri.toString(), 0);

                                                        FirebaseDatabase.getInstance().getReference("Users")
                                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                if (task.isSuccessful()){
                                                                    Log.i("Registration: ", "done");

                                                                    //redirect tp login page
                                                                    finish();
                                                                    startActivity(new Intent(Register.this, MainActivity.class));
                                                                }
                                                                else { //if the email already exists
                                                                    Toast.makeText(Register.this, "Failed to register! Please try again.1", Toast.LENGTH_SHORT).show();
                                                                    return;
                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        Toast.makeText(Register.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            });

                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Register.this, "Image upload failed!", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            else {
                Toast.makeText(this, "Enter all Fields", Toast.LENGTH_SHORT).show();
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