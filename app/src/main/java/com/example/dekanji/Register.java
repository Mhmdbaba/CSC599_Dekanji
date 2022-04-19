package com.example.dekanji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    String reg_name;
    String reg_username;
    String reg_email;
    String reg_password;
    String reg_conf_password;
    private RadioGroup rd_grp;
    private RadioButton rd_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        rd_grp = (RadioGroup) findViewById(R.id.radioGroup);

    }

    public void Button (View view) {
        Button btn = (Button) view;
        rd_btn = (RadioButton) findViewById(rd_grp.getCheckedRadioButtonId());

        if (btn.getTag().toString().equalsIgnoreCase("back")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else if (btn.getTag().toString().equalsIgnoreCase("next")){
            reg_name = ((EditText) findViewById(R.id.input_reg_name)).getText().toString();
            reg_email = ((EditText) findViewById(R.id.input_reg_email)).getText().toString();
            reg_username = ((EditText) findViewById(R.id.input_reg_username)).getText().toString();
            reg_password = ((EditText) findViewById(R.id.input_reg_password)).getText().toString();
            reg_conf_password = ((EditText) findViewById(R.id.input_reg_conf_password)).getText().toString();

            if (!reg_name.isEmpty() && !reg_username.isEmpty() && !reg_email.isEmpty() && !reg_password.isEmpty()
                    && !reg_conf_password.isEmpty()){
                //check if passwords match
                if (reg_password.equals(reg_conf_password)){
                    //direct to next page based on buyer or store owner
                    //also send with the credentials
                    if(rd_btn.getText().toString().equalsIgnoreCase("buyer")){
                        //continue to home page
                    }
                    else if (rd_btn.getText().toString().equalsIgnoreCase("store owner")){
                        //direct to store owner page to take extra credentials
                        //Intent intent = new Intent(this,);
                    }
                    else {
                        Toast.makeText(this, "Fill required Fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

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
    }
}