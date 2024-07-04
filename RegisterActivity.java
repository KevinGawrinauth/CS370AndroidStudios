package edu.qc.seclass.storesupplyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.qc.seclass.storesupplyapplication.Database.DatabaseHelper;


public class RegisterActivity extends AppCompatActivity {

    EditText edUsername, edPassword, edConfirm;
    Button btn;
    TextView tv;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edUsername = findViewById(R.id.editTextRegUsername);
        edPassword = findViewById(R.id.editTextReqPassword);
        edConfirm = findViewById(R.id.editTextReqconfirmPassword);
        btn = findViewById(R.id.buttonRegister);
        tv = findViewById(R.id.textViewExistingUser);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                String confirm = edConfirm.getText().toString();
                DatabaseHelper db = new DatabaseHelper(getApplicationContext(),"StoreSupply",null,1);


                if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter credentials", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirm)) {
                    Toast.makeText(getApplicationContext(), "Password and Confirm password didn't match", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValid(password)) {
                    Toast.makeText(getApplicationContext(), "Password must contain at least 8 characters, containing letters, digits, and a special character", Toast.LENGTH_LONG).show();
                    return;
                }

                // Insert user data into database
                boolean success = db.register(username, password);

                if (success) {
                    Toast.makeText(getApplicationContext(), "Record Inserted successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to insert user record", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isEmpty(String string) {
        return string.length() == 0;
    }


    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    private static boolean isValid(String password){
        int f1 = 0, f2 = 0, f3 = 0;
        if (password.length() < 8) {
            return false;
        } else {
            for (int p = 0; p < password.length(); p++) {
                if (Character.isLetter(password.charAt(p))) {
                    f1 = 1;
                }
            }
            for (int r = 0; r < password.length(); r++) {
                if (Character.isDigit(password.charAt(r))) {
                    f2 = 1;
                }
            }
            for (int s = 0; s < password.length(); s++) {
                char c = password.charAt(s);
                if (c >= 33 && c <= 46 || c == 64) {
                    f3 = 1;
                }
            }
            if (f1 == 1 && f2 == 1 && f3 == 1)
                return true;
            return false;
        }
    }
}