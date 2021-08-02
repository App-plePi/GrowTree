package org.techtown.vacationproject337;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.techtown.vacationproject337.databinding.ActivityJoinBinding;

import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {
    private ActivityJoinBinding binding;
    private FirebaseAuth mAuth;
    boolean isName = false;
    boolean isEmail = false;
    boolean isPwd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJoinBinding.inflate(getLayoutInflater()); // 1
        setContentView(binding.getRoot()); // 2
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        ProgressDialog progressDialog = new ProgressDialog(JoinActivity.this);
        mAuth = FirebaseAuth.getInstance();

        binding.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(s!=null)isName=true;
                else isName=false;
            }
        });
        binding.etUserEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()){
                    binding.tvErrorEmail.setText("이메일 형식으로 입력해주세요.");
                    binding.etUserEmail.setBackgroundResource(R.drawable.red_edittext);
                }
                else{
                    binding.tvErrorEmail.setText("");
                    binding.etUserEmail.setBackgroundResource(R.drawable.edit);
                    if(s!=null) isEmail=true;
                    else isEmail=false;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        binding.etUserPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!Pattern.matches("^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#$%^&*])(?=.*[0-9!@#$%^&*]).{8,15}$", s))
                {
                    binding.tvErrorPwd.setText("숫자, 문자, 특수문자중 2가지를 포함하세요");
                    binding.etUserPwd.setBackgroundResource(R.drawable.red_edittext);
                }
                else{
                    binding.tvErrorPwd.setText("");
                    binding.etUserPwd.setBackgroundResource(R.drawable.edit);
                    binding.etPwdch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count){
                            String p1 = binding.etUserPwd.getText().toString();
                            String p2 = binding.etPwdch.getText().toString();
                            if(!p2.equals(p1))
                            {
                                binding.tvErrorPwdch.setText("비밀번호와 일치하지 않습니다.");
                                binding.etPwdch.setBackgroundResource(R.drawable.red_edittext);
                            }
                            else{
                                binding.tvErrorPwdch.setText("");
                                binding.etPwdch.setBackgroundResource(R.drawable.edit);
                                if(binding.etPwdch.getText().toString()!=null) isPwd=true;
                                else  isPwd=false;
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {}
                    });
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.btnJjoin.setOnClickListener(v -> {
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("처리중입니다..");
            progressDialog.show();
            if(isName&&isEmail&&isPwd){
                String name = binding.etName.getText().toString();
                String email = binding.etUserEmail.getText().toString();
                String pwd = binding.etUserPwd.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(JoinActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String Uid = user.getUid();
                            UserAccount account = new UserAccount(name, email, Uid);
                            databaseReference.child("User").child(Uid).setValue(account);
                            progressDialog.dismiss();
                            Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                            startActivity(intent);

                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(JoinActivity.this, "등록 에러", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
            }
            else {
                Toast.makeText(getApplicationContext(), "정보를 확인해주세요", Toast.LENGTH_SHORT).show();

            }
        });
    }
}