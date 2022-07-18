package de.erdtmann.soft.android.homeapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import de.erdtmann.soft.android.homeapp.network.RestHeizungStatus;
import de.erdtmann.soft.android.homeapp.network.RestPumpeOnClick;
import de.erdtmann.soft.android.homeapp.network.RestPumpeStatus;
import de.erdtmann.soft.android.homeapp.network.RestPvHome;

public class MainActivity extends AppCompatActivity {

    ImageButton bt_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        setWerte();
        makeButtons();
        checkStatus();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                setWerte();
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task,5000L, 5000L);
    }

    protected void setWerte() {

        ImageView img_batt_haus = (ImageView) findViewById(R.id.img_batt_haus);
        ImageView img_netz_haus = (ImageView) findViewById(R.id.img_netz_haus);

        TextView tv_pv = (TextView) findViewById(R.id.tv_pv);
        TextView tv_batt = (TextView) findViewById(R.id.tv_batt);
        TextView tv_batt_stand = (TextView) findViewById(R.id.tv_batt_stand);
        TextView tv_haus = (TextView) findViewById(R.id.tv_haus);
        TextView tv_netz = (TextView) findViewById(R.id.tv_netz);

        RestPvHome rest = new RestPvHome();
        rest.start(tv_pv, tv_batt, tv_batt_stand, tv_haus, tv_netz, img_batt_haus, img_netz_haus);
    }
    protected void checkStatus() {
        ImageView img_pumpe = (ImageView) findViewById(R.id.img_pumpe);
        ImageView img_heizung = (ImageView) findViewById(R.id.img_heizung);
        Switch sw_pumpe = (Switch) findViewById(R.id.sw_pumpe);
        Switch sw_heizung = (Switch) findViewById(R.id.sw_heizung);

        RestPumpeStatus restPumpe = new RestPumpeStatus();
        restPumpe.start(img_pumpe, sw_pumpe, sw_heizung);

        RestHeizungStatus restHeizung = new RestHeizungStatus();
        restHeizung.start(img_heizung, sw_heizung);
    }

    protected void makeButtons() {
        Switch sw_pumpe = (Switch) findViewById(R.id.sw_pumpe);

        RestPumpeOnClick restPumpeOnClick = new RestPumpeOnClick(sw_pumpe, MainActivity.this);

        sw_pumpe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sw_pumpe.isChecked()){
                    restPumpeOnClick.schaltePumpeEin();
                } else {
                    restPumpeOnClick.schaltePumpeAus();
                }
                checkStatus();
            }
        });
    }
}
