package org.techtown.vacationproject337.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.techtown.vacationproject337.MainActivity;
import org.techtown.vacationproject337.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    FirebaseAuth mAuth;
    boolean isEmail = false;
    boolean isPwd = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        SharedPreferences sf = getSharedPreferences("Login", MODE_PRIVATE);
        String loginEmail = sf.getString("inputEmail", null);
        String loginPwd = sf.getString("inputPwd", null);

        binding.etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(s!=null) isEmail = true;
                else isEmail = false;
            }
        });
        binding.etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(s!=null) isPwd = true;
                else  isPwd =true;
            }
        });

        binding.btnJoin.setOnClickListener(v -> {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
        });

        if(loginEmail != null&&loginPwd!=null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if(loginEmail == null&&loginPwd==null){
            binding.btnLogin.setOnClickListener(v -> {
                if (isEmail && isPwd) {
                    String email = binding.etEmail.getText().toString();
                    String pwd = binding.etPwd.getText().toString();
                    mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                SharedPreferences sf = getSharedPreferences("Login", MODE_PRIVATE);
                                SharedPreferences.Editor autoLogin = sf.edit();
                                autoLogin.putString("inputEmail", email);
                                autoLogin.putString("inputPwd", pwd);
                                autoLogin.commit();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else
                                Toast.makeText(getApplicationContext(), "로그인오류", Toast.LENGTH_SHORT).show();
                            //어디서 틀려서 로그인 안돼는지 알려주기
                        }
                    });
                } else {
                    if (!isEmail && !isPwd)
                        Toast.makeText(getApplicationContext(), "정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                    else if (!isEmail)
                        Toast.makeText(getApplicationContext(), "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                    else if (!isPwd)
                        Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}