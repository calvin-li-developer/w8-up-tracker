package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText lEmail, lPassword;
    Button lLoginBtn;
    TextView lRegisterBtn;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Check if there still is a logged in user.
        if (fAuth.getCurrentUser() != null)
        {
            // Log out their account
            fAuth.signOut();
        }

        lEmail = findViewById(R.id.loginEmail);
        lPassword = findViewById(R.id.loginPwd);
        lRegisterBtn = findViewById(R.id.loginRegisterBtn);
        lLoginBtn = findViewById(R.id.loginBtn);

        lEmail.setText("test@hotmail.com");
        lPassword.setText("123456");
        progressBar = findViewById(R.id.progressBarLogin);

        lRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }

    public void onLoginBtnClick(View v)
    {
        String email = lEmail.getText().toString().trim();
        String password = lPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            lEmail.setError("Email is Required.");
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            lPassword.setError("Password is Required.");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // authenticate the user
        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(LoginActivity.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                else{
                    Toast.makeText(LoginActivity.this,"Error ! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        hideKeyboard(this);
    }

    private void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}