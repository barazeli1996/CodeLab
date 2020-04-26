package com.barazeli.codelab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

import aModel.User;

public class RegisterActivity extends AppCompatActivity {
    private Button register;
    private TextInputEditText username_edit,email_edit,password_edit;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setWidgets();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName= Objects.requireNonNull(username_edit.getText()).toString();
                String email= Objects.requireNonNull(email_edit.getText()).toString();
                String password= Objects.requireNonNull(password_edit.getText()).toString();
                if (validatePassword()||validateEmail()||validateUserName()){
                    setRegister(userName,email,password);
                }
            }
        });
        
    }
    private void setWidgets(){
        register=findViewById(R.id.register_register);
        username_edit=findViewById(R.id.username_register);
        email_edit=findViewById(R.id.email_register);
        password_edit=findViewById(R.id.password_register);
    }
    private void setRegister(final String username, String email, String password){
        auth=FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        firebaseUser=auth.getCurrentUser();
                        assert firebaseUser != null;
                        String userID=firebaseUser.getUid();
                        reference= FirebaseDatabase.getInstance()
                                .getReference("User").child(userID);
                        HashMap<String,String> hashMap=new HashMap<>();
                        hashMap.put("id",userID);
                        hashMap.put("username",username);
                        hashMap.put("image_url","default");
                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(RegisterActivity.this,
                                            "Can't add this user",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(RegisterActivity.this,
                                "Authentication Failed",
                                Toast.LENGTH_SHORT).show();
                    }
                    }
                });
    }
    private boolean validateEmail(){
        String email= Objects.requireNonNull(email_edit.getText()).toString();
        if (email.isEmpty()){
            email_edit.setError("Require field ..");
            email_edit.setFocusable(true);
            return false;
        }else {
            return  true;
        }
    }
    private boolean validateUserName(){
        String email= Objects.requireNonNull(username_edit.getText()).toString();
        if (email.isEmpty()){
            username_edit.setError("Require field ..");
            username_edit.setFocusable(true);
            return false;
        }else {
            return  true;
        }
    }
    private boolean validatePassword(){
        String email= Objects.requireNonNull(password_edit.getText()).toString();
        if (email.isEmpty()){
            password_edit.setError("Require field ..");
            password_edit.setFocusable(true);
            return false;
        }else {
            if (email.length()<6){
                password_edit.setError("Short password 6 character at least ");
                password_edit.setFocusable(true);
                return false;
            }else {
                return true;
            }
        }
    }
}

