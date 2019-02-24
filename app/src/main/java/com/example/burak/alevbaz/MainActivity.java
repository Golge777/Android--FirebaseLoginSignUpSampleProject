package com.example.burak.alevbaz;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView tvRegister;

    EditText etEmail;
    EditText etPassword;
    Button btnLogin;

    ProgressBar progressBar;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvRegister = (TextView) findViewById(R.id.tvRegister);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance(); // Initializing firebase...

    }

    public void UserLogin(View view)
    {

        String userEmail = etEmail.getText().toString().trim();
        String userPassword = etPassword.getText().toString().trim();

        if(userEmail.isEmpty()){

            etEmail.setError("Lütfen bir e-mail adresi giriniz.");
            etEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){

            etEmail.setError("Lütfen geçerli bir e-mail adresi giriniz.");
            etEmail.requestFocus();
            return;
        }
        if(userPassword.isEmpty()){

            etPassword.setError("Lütfen bir şifre giriniz.");
            etPassword.requestFocus();
            return;
        }
        if(userPassword.length()<6){

            etPassword.setError("Şifrenin uzunluğu en az 6 karakter olmalıdır.");
            etPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressBar.setVisibility(View.GONE);

                        if(task.isSuccessful()){

                            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }else{

                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }

    public void GoToSignUp(View view)
    {

        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

}
