package com.example.mcqapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText password;
    EditText email;
    EditText username;
    public static final String TAG="Imp User Auth Msg. ";
    public void updateUI(FirebaseUser currentUser){
        if(currentUser!=null){
            Intent intent= new Intent(SignupActivity.this,QuizActivity.class);
            SignupActivity.this.startActivity(intent);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email= findViewById(R.id.email);
        password = findViewById(R.id.password);


        mAuth= FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    public void loginintent(View view){
        Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
        SignupActivity.this.startActivity(intent);
    }
    public void onsignup(View view){
        if(email.getText().toString()!=""&&password.getText().toString()!="") {
            try{
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
//                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                Toast.makeText(SignupActivity.this, "Authentication failed. Maybe your account already exists.",
                                        Toast.LENGTH_LONG).show();
                                updateUI(null);
                            }

                            // ...
                        }
                    });
        }catch(Exception e){
                Toast.makeText(this, "Invalid fill!", Toast.LENGTH_SHORT).show();

            }
        }



    }
}
