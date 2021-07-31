package org.techtown.vacationproject337;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.techtown.vacationproject337.Fragment.AboutFragment;
import org.techtown.vacationproject337.Fragment.HomeFragment;
import org.techtown.vacationproject337.Fragment.ProfileFragment;

public class MainActivity2 extends AppCompatActivity {
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mBottomNavigationView=findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_view,new HomeFragment()).commit();

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_view,new HomeFragment()).commit();
                        break;
                    case R.id.menu_about:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_view,new AboutFragment()).commit();
                        break;
                    case R.id.menu_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_view,new ProfileFragment()).commit();
                        break;

                }
                return true;
            }
        });
    }
}