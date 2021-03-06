package org.techtown.vacationproject337.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.techtown.vacationproject337.R;
import org.techtown.vacationproject337.databinding.ActivitySettingBinding;


public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding binding;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference nameRef = databaseReference.child("User").child(uid).child("name");
    DatabaseReference emailRef = databaseReference.child("User").child(uid).child("email");
    DatabaseReference profileRef = databaseReference.child("User").child(uid).child("profile");
    boolean isName = false;
    int profile = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();

        binding.back.setOnClickListener(v -> {
            Intent intent = new Intent(SettingActivity.this, MainActivity.class);
            startActivity(intent);
        });

        binding.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) isName = true;
                else isName = false;
            }
        });

        binding.btnEdit.setOnClickListener(v -> {
            if (isName || profile != 0) {
                if (isName) {
                    String name = binding.etName.getText().toString();
                    nameRef.setValue(name);
                }
                if (profile != 0) profileRef.setValue(profile);
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
            } else
                Toast.makeText(getApplicationContext(), "????????? ????????? ????????????.", Toast.LENGTH_SHORT).show();
        });

        binding.withdrawal.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
            builder.setTitle("????????????");
            builder.setMessage("??????????????? ???????????????????\n*???????????? ????????? ?????? ???????????????!");
            builder.setPositiveButton("???", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
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
            builder.setNegativeButton("?????????", null);
            builder.show();
        });
        emailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String email = snapshot.getValue(String.class);
                binding.tvEmail.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.tvEmail.setText("???????????? ??????");
            }
        });
    }

    public void profile(View v) {
        switch (v.getId()) {
            case R.id.p1:
                profile = 1;
                break;
            case R.id.p2:
                profile = 2;
                break;
            case R.id.p3:
                profile = 3;
                break;
            case R.id.p4:
                profile = 4;
                break;
            case R.id.p5:
                profile = 5;
                break;


        }

    }
}