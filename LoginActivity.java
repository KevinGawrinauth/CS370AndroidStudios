package edu.qc.seclass.storesupplyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.qc.seclass.storesupplyapplication.Database.DatabaseHelper;


public class LoginActivity extends AppCompatActivity {
    EditText edUsername, edPassword, edConfirm;
    Button btn;
    TextView tv;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.editTextLoginUsername);
        edPassword = findViewById(R.id.editTextLoginPassword);
        btn = findViewById(R.id.buttonLogin);
        tv = findViewById(R.id.textViewNewUser);

        btn.setOnClickListener(view -> {
            String username = edUsername.getText().toString();
            String password = edPassword.getText().toString();
            DatabaseHelper db = new DatabaseHelper(getApplicationContext(),"StoreSupply",null,1);

            if(username.length()==0 || password.length()==0){
                Toast.makeText(getApplicationContext(), "Please enter credentials", Toast.LENGTH_SHORT).show();

            }else{
                if(db.login(username,password)==1){
                    Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username",username);
                    // to save our data with key and value
                    editor.apply();
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(), "Login Failed, Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this,RegisterActivity.class)));

    }

}
