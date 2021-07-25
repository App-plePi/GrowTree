package org.techtown.vacationproject337;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText mName, mEmail, mPwd, mPwdch;
    Button join;
    TextView tv_error_email, tv_error_pwd, tv_error_pwdch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        mAuth = FirebaseAuth.getInstance();

        tv_error_email = findViewById(R.id.tv_error_email);
        tv_error_pwd = findViewById(R.id.tv_error_pwd);
        tv_error_pwdch = findViewById(R.id.tv_error_pwdch);

        mName = findViewById(R.id.et_Name);
        mEmail = findViewById(R.id.et_User_email);
        mPwd = findViewById(R.id.et_User_pwd);
        mPwdch = findViewById(R.id.et_pwdch);

        join = findViewById(R.id.btn_jjoin);

        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()){
                    tv_error_email.setText("이메일 형식으로 입력해주세요.");    // 경고 메세지
                    mEmail.setBackgroundResource(R.drawable.red_edittext);  // 적색 테두리 적용
                }
                else{
                    tv_error_email.setText("");         //에러 메세지 제거
                    mEmail.setBackgroundResource(R.drawable.edit);  //테투리 흰색으로 변경
                }
            }// afterTextChanged()..
        });

        mPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!Pattern.matches("^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#$%^&*])(?=.*[0-9!@#$%^&*]).{8,15}$")
                {
                    tv_error_pwd.setText("숫자, 문자, 특수문자중 2가지를 포함하세요");
                    mPwd.setBackgroundResource(R.drawable.red_edittext);
                }
                else{
                    tv_error_pwd.setText("");
                    mPwd.setBackgroundResource(R.drawable.edit);
                }
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString();
                String email = mEmail.getText().toString();
                String pwd = mPwd.getText().toString();
                String pwdch = mPwdch.getText().toString();

                UserAccount account = new UserAccount();
                //final ProgressDialog mDialog = new ProgressDialog(JoinActivity.this);
                //mDialog.setMessage("기다려주세요");
                //mDialog.show();



            }
        });
    }
}