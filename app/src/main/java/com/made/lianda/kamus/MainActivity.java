package com.made.lianda.kamus;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fl_content_main)
    FrameLayout flContentMain;
    @BindView(R.id.nv_main)
    NavigationView nvMain;
    @BindView(R.id.drawer_main)
    DrawerLayout drawerMain;

    ActionBarDrawerToggle toggle;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.in_en);

        nvMain.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null){
            Fragment currentFragment = new InEnFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_content_main, currentFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerMain.isDrawerOpen(GravityCompat.START)) {
            drawerMain.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        toggle = new ActionBarDrawerToggle(this, drawerMain, toolbar,R.string.open_drawer, R.string.close_drawer);
        drawerMain.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        drawerMain.removeDrawerListener(toggle);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        Fragment fragment = null;

        title = " ";

        switch (id){
            case R.id.nav_in_en:
                title = getResources().getString(R.string.in_en);
                fragment = new InEnFragment();
                break;
            case R.id.nav_en_in:
                title = getString(R.string.en_in);
                fragment = new EnInFragment();
                break;
        }

        if (fragment != null){
            getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fl_content_main, fragment)
            .commit();
        }

        getSupportActionBar().setTitle(title);

        drawerMain.closeDrawer(GravityCompat.START);
        return true;
    }
}
