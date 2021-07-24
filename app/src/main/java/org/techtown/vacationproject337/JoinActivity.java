package org.techtown.vacationproject337;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JoinActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText mName, mEmail, mPwd, mPwdch;
    Button join;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        mAuth = FirebaseAuth.getInstance();

        mName = findViewById(R.id.et_Name);
        mEmail = findViewById(R.id.et_User_email);
        mPwd = findViewById(R.id.et_User_pwd);
        mPwdch = findViewById(R.id.et_pwdch);

        join = findViewById(R.id.btn_jjoin);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAccount account = new UserAccount();
                final ProgressDialog mDialog = new ProgressDialog(JoinActivity.this);
                mDialog.setMessage("기다려주세요");
                mDialog.show();
                account.uid = mAuth.getUid();

            }
        });
    }
}