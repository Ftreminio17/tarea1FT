package com.tarea1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerificacionActivity extends AppCompatActivity {


    EditText mEditTextCode;
    TextView mTextViewResponse;
    Button mButtonVerificar;

    FirebaseAuth mAuth;
    String intenAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);

        mEditTextCode = findViewById(R.id.textViewCode);
        mTextViewResponse = findViewById(R.id.textViewResponse);
        mButtonVerificar = findViewById(R.id.btnVerificar);

        mAuth = FirebaseAuth.getInstance();

        intenAuth = getIntent().getStringExtra("auth");

        mButtonVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigoVerificacio = mEditTextCode.getText().toString();

                if(!codigoVerificacio.isEmpty()){
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential
                            (intenAuth, codigoVerificacio);
                    iniciarSesion(credential);
                }else{
                    Toast.makeText(VerificacionActivity.this, "Ingrese el código de verificación", Toast.LENGTH_SHORT).show();
                }
            }

            private void iniciarSesion(PhoneAuthCredential credential) {
                mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            inicioActivityHome();
                        }else{
                            Toast.makeText(VerificacionActivity.this, "Error de verifición", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            inicioActivityHome();
        }
    }

    private void inicioActivityHome() {
        Intent intent = new Intent(VerificacionActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}