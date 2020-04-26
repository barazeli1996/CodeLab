package com.barazeli.codelab;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import aAdapters.ViewPagerAdapter;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private ViewPager viewPager;
    private int[]layouts={R.layout.fragment_chat,R.layout.fragment_friends,R.layout.fragment_status,};
    private ViewPagerAdapter adapter;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        viewPager=findViewById(R.id.view_pager);
        adapter=new ViewPagerAdapter(getSupportFragmentManager());
        TabLayout tableLayout=(TabLayout)findViewById(R.id.table);
         viewPager.setAdapter(adapter);
         tableLayout.setupWithViewPager(viewPager);

        
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            auth.signOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }
}
