package com.example.hostlex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;



import com.example.hostlex.databinding.ActivityMain2Binding;
import com.example.hostlex.models.users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity {
    ActivityMain2Binding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        binding =ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth= FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        progressDialog =new ProgressDialog(MainActivity2.this);
        progressDialog.setTitle("Creating account");
        progressDialog.setMessage("creating account");

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    startActivity(new Intent(MainActivity2.this,MainActivity3.class));

                                                }
                                            }
        );
        binding.btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this,MainActivity3.class));
            }
        });


        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                auth.createUserWithEmailAndPassword(binding.editTextTextEmailAddress.getText().toString(),
                        binding.editTextTextPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            users user= new users(binding.editTextTextPersonName.getText().toString()
                                    ,binding.editTextTextEmailAddress.getText().toString()
                                    ,binding.editTextTextPassword.getText().toString());
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("users").child(id).setValue(user);
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity2.this,"Sucessfully signed up ",Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(MainActivity2.this,MainActivity3.class));


                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity2.this ,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}