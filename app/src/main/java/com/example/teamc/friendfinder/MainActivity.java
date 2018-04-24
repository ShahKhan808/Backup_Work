package com.example.teamc.friendfinder;

import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_my_profile:
                    MyProfile myProfile = new MyProfile();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, myProfile, "Fragment Name");
                    fragmentTransaction.commit();

                    return true;
                case R.id.navigation_browse_user_profiles:
                    BrowseProfiles browseProfiles = new BrowseProfiles();
                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.content, browseProfiles, "Fragment Name");
                    fragmentTransaction2.commit();
                    return true;
                case R.id.navigaion_chat:
                    Chat chat = new Chat();
                    FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.content, chat, "Fragment Name");
                    fragmentTransaction3.commit();
                    return true;
                case R.id.navigation_notifications:
                    Notifications notifications = new Notifications();
                    FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction4.replace(R.id.content, notifications, "Fragment Name");
                    fragmentTransaction4.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_my_items, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();

        if(i == R.id.action_signOut) {
            FirebaseAuth.getInstance().getCurrentUser();
            Toast.makeText(this, "Signed Out", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
