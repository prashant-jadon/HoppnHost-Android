package chandra.networkdots.hoppnhost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    Button moveToLogin;
    private EditText emailTextView, passwordTextView;
    private Button Btn;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth= FirebaseAuth.getInstance();

        emailTextView = findViewById(R.id.emailEditText);
        passwordTextView = findViewById(R.id.passwordEditText);
        Btn = findViewById(R.id.signupButton);
        progressbar = findViewById(R.id.progressBAr);

        moveToLogin = findViewById(R.id.loginButton);
        moveToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                finish();
            }
        });
        
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    private void registerUser() {

        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);

        // Take the value of two edit texts in Strings
        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           Toast.makeText(getApplicationContext(),
                                           "Registration successful!",
                                           Toast.LENGTH_LONG)
                                   .show();

                           // hide the progress bar
                           progressbar.setVisibility(View.GONE);

                           // if the user created intent to login activity
                           Intent intent
                                   = new Intent(SignupActivity.this,
                                   LoginActivity.class);
                           startActivity(intent);
                       }else{
                           // Registration failed
                           Toast.makeText(
                                           getApplicationContext(),
                                           "Registration failed!!"
                                                   + " Please try again later",
                                           Toast.LENGTH_LONG)
                                   .show();

                           // hide the progress bar
                           progressbar.setVisibility(View.GONE);
                       }
                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {

                    }
                });

    }
}