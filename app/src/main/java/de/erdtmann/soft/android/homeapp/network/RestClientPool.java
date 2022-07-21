package de.erdtmann.soft.android.homeapp.network;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import de.erdtmann.soft.android.homeapp.R;
import de.erdtmann.soft.android.homeapp.model.PvHome;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestClientPool {

    static final String BASE_URL = "http://pizeropool:9898/";

    RestApi restApi;
    Context con;
    public ImageView img_pumpe;
    public ImageView img_heizung;
    public Switch sw_pumpe;
    public Switch sw_heizung;

    public void start(ImageView img_pumpe, ImageView img_heizung, Switch sw_pumpe, Switch sw_heizung, Context context) {
        this.img_pumpe = img_pumpe;
        this.img_heizung = img_heizung;
        this.sw_pumpe = sw_pumpe;
        this.sw_heizung = sw_heizung;
        this.con = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        restApi = retrofit.create(RestApi.class);
    }

    public void statusPumpe() {
        Call<String> statusPumpe = restApi.getPumpenStatus();

        statusPumpe.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body().equals("1")) {
                        img_pumpe.setImageResource(R.drawable.ic_check);
                        sw_pumpe.setChecked(true);
                        sw_heizung.setEnabled(false);
                    } else {
                        img_pumpe.setImageResource(R.drawable.ic_aus);
                        sw_pumpe.setChecked(false);
                        sw_heizung.setEnabled(true);
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void statusHeizung() {
        Call<String> statusHeizung = restApi.getHeizungStatus();

        statusHeizung.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body().equals("1")) {
                        img_heizung.setImageResource(R.drawable.ic_check);
                        sw_heizung.setChecked(true);
                    } else {
                        img_heizung.setImageResource(R.drawable.ic_aus);
                        sw_heizung.setChecked(false);
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void schaltePumpeEin() {

        Call<String> pumpeEin = restApi.schaltePumpeEin();

        pumpeEin.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(con, R.string.pumpeEin, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void schaltePumpeAus() {

        Call<String> pumpeAus = restApi.schaltePumpeAus();

        pumpeAus.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Toast.makeText(con, R.string.pumpeAus,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void schalteHeizungEin() {

        Call<String> heizungEin = restApi.schalteHeizungEin();

        heizungEin.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(con, R.string.heizungEin, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void schalteHeizungAus() {

        Call<String> heizungAus = restApi.schalteHeizungAus();

        heizungAus.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Toast.makeText(con, R.string.heizungAus,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
