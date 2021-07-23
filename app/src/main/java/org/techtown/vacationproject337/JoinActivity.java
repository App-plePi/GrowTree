package org.techtown.vacationproject337;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JoinActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText mName, mEmail, mPwd, mPwdch;
    Button join;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        mAuth = FirebaseAuth.getInstance();

        mName = findViewById(R.id.et_Name);
        mEmail = findViewById(R.id.et_User_email);
        mPwd = findViewById(R.id.et_User_pwd);
        mPwdch = findViewById(R.id.et_pwdch);

        join = findViewById(R.id.btn_jjoin);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }
}