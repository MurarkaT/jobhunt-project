package com.example.jobhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class employee_login extends AppCompatActivity {

    TextView btn;
    EditText inputPassword,inputEmail;
    Button btnToLoginIn;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);
        btn=findViewById(R.id.signUpBtn);
        inputEmail=findViewById(R.id.editTextEmailAddress);
        inputPassword=findViewById(R.id.editTextPassword);

        mAuth= FirebaseAuth.getInstance();
        mLoadingBar=new ProgressDialog(employee_login.this);

        btnToLoginIn=findViewById(R.id.LoginBtn);
        btnToLoginIn.setOnClickListener((v)->{checkCredentials();});
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView btnToLoginIn=findViewById(R.id.textViewLogin);
        btnToLoginIn.setOnClickListener(v->{
            Intent in1 =new Intent(this,employee_login.class);
            startActivity(in1);
        });
    }
    private void checkCredentials(){
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();

        if(email.isEmpty()||!email.contains("@"))
        {
            showError(inputEmail,"Email is not valid");
        }
        else if(password.isEmpty()||password.length()<7)
        {
            showError(inputPassword,"Password must be 7 characters");
        }
        else
        {
            mLoadingBar.setTitle("Login");
            mLoadingBar.setMessage("Please wait");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        mLoadingBar.dismiss();
                        Intent intent = new Intent(employee_login.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(employee_login.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}