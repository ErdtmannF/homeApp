package de.erdtmann.soft.android.homeapp.network;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import de.erdtmann.soft.android.homeapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestPumpeOnClick  {

    static final String BASE_URL = "http://pizeropool:9898/";

    public Switch sw_pumpe;

    RestApi restApi;
    Context con;

    public RestPumpeOnClick(Switch sw_pumpe, Context context) {
        this.sw_pumpe = sw_pumpe;
        this.con = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        restApi = retrofit.create(RestApi.class);
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
}
