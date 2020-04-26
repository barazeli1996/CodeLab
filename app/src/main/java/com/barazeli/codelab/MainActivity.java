package com.barazeli.codelab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private Button login,register;
    private TextInputEditText email_edit,password_edit;
    private FirebaseAuth auth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWidgets();
        auth=FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { setRegister(); }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= Objects.requireNonNull(email_edit.getText()).toString();
                String password= Objects.requireNonNull(password_edit.getText()).toString();
                if (validateEmail()||validatePassword()){
                    setLogin(email,password);
                
                }
            }
        });

    }
    private void setWidgets(){
        login=findViewById(R.id.login_login);
        register=findViewById(R.id.register_login);
        email_edit=findViewById(R.id.email_login);
        password_edit=findViewById(R.id.password_login);
    }
    private void setRegister(){
        Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void setLogin(String email,String password){
        auth.signInWithEmailAndPassword(email,password)
              .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()){
                  Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                  startActivity(intent);
                  finish();
              
              }else {
                  Toast.makeText(MainActivity.this, "Problem..",
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
    private boolean validatePassword(){
        String email= Objects.requireNonNull(password_edit.getText()).toString();
        if (email.isEmpty()){
            password_edit.setError("Require field ..");
            password_edit.setFocusable(true);
            return false;
        }else {
            return  true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth!=null){
            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
