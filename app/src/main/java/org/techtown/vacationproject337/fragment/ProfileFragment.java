package org.techtown.vacationproject337.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import org.techtown.vacationproject337.activity.LoginActivity;
import org.techtown.vacationproject337.R;
import org.techtown.vacationproject337.activity.SettingActivity;
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
    DatabaseReference profileRef = databaseReference.child("User").child(uid).child("profile");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        FirebaseDatabase.getInstance().getReference().child("User").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAccount account = snapshot.getValue(UserAccount.class);
                String name = account.getName();
                String email = account.getEmail();
                int profile = account.getProfile();
                int studyTime = account.getStudyTime();
                int count = account.getCount();

                binding.tvName.setText(name);
                binding.tvEmail.setText(email);
                switch (profile) {
                    case 1:
                        binding.profile.setImageResource(R.drawable.profile1);
                        break;
                    case 2:
                        binding.profile.setImageResource(R.drawable.profile2);
                        break;
                    case 3:
                        binding.profile.setImageResource(R.drawable.profile3);
                        break;
                    case 4:
                        binding.profile.setImageResource(R.drawable.profile4);
                        break;
                    case 5:
                        binding.profile.setImageResource(R.drawable.profile5);
                        break;
                }
                int totalH = studyTime / 60;
                int totalM = studyTime % 60;
                binding.totalTime.setText(String.format("%02d" + ":" + "%02d", totalH, totalM));
                binding.totalPlant.setText(String.valueOf(count));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        binding.btnLogout.setOnClickListener(v1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("????????????");
            builder.setMessage("??????????????? ???????????????????");
            builder.setPositiveButton("???", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PreferencesManager();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("?????????", null);
            builder.show();
        });
        binding.layout.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            startActivity(intent);
        });
        return v;
    }

    public void PreferencesManager() {
        SharedPreferences sf = this.getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor autoLogin = sf.edit();
        autoLogin.putString("inputEmail", null);
        autoLogin.putString("inputPwd", null);
        autoLogin.commit();
    }

}