package de.erdtmann.soft.android.homeapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Timer;
import java.util.TimerTask;

import de.erdtmann.soft.android.homeapp.network.RestClientPool;
import de.erdtmann.soft.android.homeapp.network.RestPvHome;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    ImageButton bt_update;
    RestClientPool restPool;
    RestPvHome restPv;

    ImageView img_batt_haus;
    ImageView img_netz_haus;
    ImageView img_pumpe;
    ImageView img_heizung;

    TextView tv_pv;
    TextView tv_batt;
    TextView tv_batt_stand;
    TextView tv_haus;
    TextView tv_netz;

    Switch sw_pumpe;
    Switch sw_heizung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        navigationView = findViewById(R.id.NavigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home: {
                        Toast.makeText(MainActivity.this, "Start wurde gedrückt", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    case R.id.menu_chart: {
                        Toast.makeText(MainActivity.this, "Chart wurde gedrückt", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return true;
            }
        });
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        setWerte();
        makeButtons();
        updateButtons();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                setWerte();
                updateButtons();
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task,5000L, 5000L);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    protected void init() {
        restPool = new RestClientPool();
        restPv = new RestPvHome();

        img_batt_haus = (ImageView) findViewById(R.id.img_batt_haus);
        img_netz_haus = (ImageView) findViewById(R.id.img_netz_haus);
        img_pumpe = (ImageView) findViewById(R.id.img_pumpe);
        img_heizung = (ImageView) findViewById(R.id.img_heizung);

        tv_pv = (TextView) findViewById(R.id.tv_pv);
        tv_batt = (TextView) findViewById(R.id.tv_batt);
        tv_batt_stand = (TextView) findViewById(R.id.tv_batt_stand);
        tv_haus = (TextView) findViewById(R.id.tv_haus);
        tv_netz = (TextView) findViewById(R.id.tv_netz);

        sw_pumpe = (Switch) findViewById(R.id.sw_pumpe);
        sw_heizung = (Switch) findViewById(R.id.sw_heizung);

        restPool.start(img_pumpe, img_heizung, sw_pumpe, sw_heizung, MainActivity.this);
    }
    protected void setWerte() {
        restPv.start(tv_pv, tv_batt, tv_batt_stand, tv_haus, tv_netz, img_batt_haus, img_netz_haus);
    }
    protected void updateButtons() {
        restPool.statusPumpe();
        restPool.statusHeizung();
    }

    protected void makeButtons() {
        sw_pumpe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sw_pumpe.isChecked()){
                    restPool.schaltePumpeEin();
                } else {
                    restPool.schaltePumpeAus();
                }
                updateButtons();
            }
        });

        sw_heizung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sw_heizung.isChecked()) {
                    restPool.schalteHeizungEin();
                } else {
                    restPool.schalteHeizungAus();
                }
            }
        });
    }
}
