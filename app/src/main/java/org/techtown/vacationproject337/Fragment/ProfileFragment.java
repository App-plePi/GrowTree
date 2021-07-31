package org.techtown.vacationproject337.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.techtown.vacationproject337.LoginActivity;
import org.techtown.vacationproject337.R;
import org.techtown.vacationproject337.UserAccount;
import org.techtown.vacationproject337.databinding.FragmentProfileBinding;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference nameRef = databaseReference.child("User").child(uid).child("name");
    DatabaseReference emailRef = databaseReference.child("User").child(uid).child("email");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        binding.btnLogout.setOnClickListener(v1 -> {
            SharedPreferences sf = this.getActivity().getSharedPreferences("Login", MODE_PRIVATE);
            SharedPreferences.Editor autoLogin = sf.edit();
            autoLogin.putString("inputEmail", null);
            autoLogin.putString("inputPwd", null);
            autoLogin.commit();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        nameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                binding.tvName.setText(name);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.tvName.setText("불러오기 오류");
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