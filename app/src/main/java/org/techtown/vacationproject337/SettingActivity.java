package org.techtown.vacationproject337;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.techtown.vacationproject337.databinding.ActivitySettingBinding;


public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding binding;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference emailRef = databaseReference.child("User").child(uid).child("email");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
        binding.back.setOnClickListener(v -> {
            Intent intent = new Intent(SettingActivity.this, MainActivity2.class);
            startActivity(intent);
        });
    }

}