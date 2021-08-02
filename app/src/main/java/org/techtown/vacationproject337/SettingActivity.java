package org.techtown.vacationproject337;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.techtown.vacationproject337.databinding.ActivitySettingBinding;

import java.util.regex.Pattern;


public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding binding;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference emailRef = databaseReference.child("User").child(uid).child("email");
    boolean isName = false;
    boolean isPwd = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();

        binding.back.setOnClickListener(v -> {
            Intent intent = new Intent(SettingActivity.this, MainActivity2.class);
            startActivity(intent);
        });

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
                                if(s!=null)isPwd=true;
                                else isPwd=false;
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

        binding.withdrawal.setOnClickListener(v -> {
            if(isPwd){
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setTitle("회원탈퇴");
                builder.setMessage("회원탈퇴를 하시겠습니까?\n*회원님의 정보가 모두 사라집니다!");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void> () {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                databaseReference.child("User").child(uid).setValue(null);
                                SharedPreferences sf = getSharedPreferences("Login", MODE_PRIVATE);
                                SharedPreferences.Editor autoLogin = sf.edit();
                                autoLogin.putString("inputEmail", null);
                                autoLogin.putString("inputPwd", null);
                                autoLogin.commit();
                                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                });
                builder.setNegativeButton("아니오", null);
                builder.show();
            }
        });

        emailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String email = snapshot.getValue(String.class);
                binding.tvEmail.setText(email);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.tvEmail.setText("불러오기 오류");
            }
        });
    }

}