package com.buscaloaltoq.buscaloaltoq;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.buscaloaltoq.buscaloaltoq.fragments.CategoryFragment;
import com.buscaloaltoq.buscaloaltoq.fragments.LoginFragment;
import com.buscaloaltoq.buscaloaltoq.fragments.OrderFragment;
import com.buscaloaltoq.buscaloaltoq.fragments.ProfileFragment;
import com.buscaloaltoq.buscaloaltoq.fragments.ShopFragment;
import com.buscaloaltoq.buscaloaltoq.fragments.WelcomeFragment;
import com.buscaloaltoq.buscaloaltoq.others.GlobalVariables;

public class Master extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor,new WelcomeFragment()).commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                TextView txtCorreoM = (TextView)drawerView.findViewById(R.id.txtCorreoM);
                TextView txtNombresM =(TextView) drawerView.findViewById(R.id.txtNombresM);
                if (GlobalVariables.isCookieUsuario() == true){
                    txtCorreoM.setText(GlobalVariables.getCorreoUsuario());
                    txtNombresM.setText(GlobalVariables.getNombresUsuario());
                }else{
                    txtCorreoM.setText("Nombre@example.com");
                    txtNombresM.setText("Anonimo");
                }
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.master, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_profile) {

            if (GlobalVariables.isCookieUsuario() == (true)){
                fragmentManager.beginTransaction().replace(R.id.contenedor,new ProfileFragment()).commit();
            }else {
                fragmentManager.beginTransaction().replace(R.id.contenedor,new LoginFragment()).commit();
            }

        } else if (id == R.id.nav_catalog) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new CategoryFragment()).commit();

        } else if (id == R.id.nav_shop) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new ShopFragment()).commit();

        } else if (id == R.id.nav_voucher) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new OrderFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
