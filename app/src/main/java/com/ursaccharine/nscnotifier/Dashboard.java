package com.ursaccharine.nscnotifier;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Dashboard extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;

    FloatingActionButton fb;
    RecyclerView recview;
    myadapter adapter;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
        //Intent intent = new Intent(Intent.ACTION_MAIN);
        //intent.addCategory(Intent.CATEGORY_HOME);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                Fragment fragment = null;
                switch (id) {
                    case R.id.results:
                        fragment=new ResultFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.events:
                        fragment=new EventsFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.facebook:
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/teamsummiteers"));
                        startActivity(browserIntent);
                        break;
                    case R.id.website:
                        Intent websiteIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://newsummit.edu.np/"));
                        startActivity(websiteIntent);
                        break;
                    case R.id.map:
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com/maps/place/New+Summit+College/@27.6864069,85.3469473,16.03z/data=!4m9!1m2!2m1!1snew+summit+college!3m5!1s0x39eb19492962a79b:0xedfd120afcc38109!8m2!3d27.6875088!4d85.3435896!15sChJuZXcgc3VtbWl0IGNvbGxlZ2WSAQdjb2xsZWdl"));
                        startActivity(mapIntent);
                        break;
                    case R.id.notice:
                        fragment=new NoticeFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.logout:
                        Intent logoutIntent = new Intent(Dashboard.this,Login.class);
                        FirebaseAuth.getInstance().signOut();
                        startActivity(logoutIntent);
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });

        recview = (RecyclerView) findViewById(R.id.rec_view);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Model> options = new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("uploadPDF"),Model.class)
                .build();

        adapter = new myadapter(options);
        recview.setAdapter(adapter);


     }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void loadFragment(Fragment fragment) {

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        fragmentTransaction.addToBackStack(null);

    }
}