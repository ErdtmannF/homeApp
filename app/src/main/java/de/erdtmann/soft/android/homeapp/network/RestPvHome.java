package de.erdtmann.soft.android.homeapp.network;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.erdtmann.soft.android.homeapp.R;
import de.erdtmann.soft.android.homeapp.model.PvHome;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestPvHome implements Callback<PvHome> {

    static final String BASE_URL = "http://erdtmann.my-router.de:8080/pvService/pv/";

    public TextView tv_pv;
    public TextView tv_batt;
    public TextView tv_batt_stand;
    public TextView tv_haus;
    public TextView tv_netz;
    public ImageView img_batt_haus;
    public ImageView img_netz_haus;

    public void start(TextView tv_pv, TextView tv_batt, TextView tv_batt_stand, TextView tv_haus, TextView tv_netz, ImageView img_batt_haus, ImageView img_netz_haus) {
        this.tv_pv = tv_pv;
        this.tv_batt = tv_batt;
        this.tv_batt_stand = tv_batt_stand;
        this.tv_haus = tv_haus;
        this.tv_netz = tv_netz;
        this.img_batt_haus = img_batt_haus;
        this.img_netz_haus = img_netz_haus;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RestApi restApi = retrofit.create(RestApi.class);

        Call<PvHome> call = restApi.getPvHomeDaten();
        call.enqueue(this);
    }
    @Override
    public void onResponse(Call<PvHome> call, Response<PvHome> response) {
        if (response.isSuccessful()) {
            tv_pv.setText(response.body().getLeistung());
            tv_batt.setText(response.body().getBattLeistung());
            tv_haus.setText(response.body().getHome());
            tv_netz.setText(response.body().getNetz());
            tv_batt_stand.setText(response.body().getBattLadung());
            if (response.body().getBattRichtung().equals("rechts")) {
                img_batt_haus.setImageResource(R.drawable.ic_rechts_rot);
            } else {
                img_batt_haus.setImageResource(R.drawable.ic_links_gruen);
            }
            if (response.body().getNetzRichtung().equals("rechts")) {
                img_netz_haus.setImageResource(R.drawable.ic_rechts_gruen);
            } else {
                img_netz_haus.setImageResource(R.drawable.ic_links_rot);
            }
        } else {
            tv_pv.setText("Fehler!");
            tv_batt.setText("Fehler!");
            tv_batt_stand.setText("Fehler!");
            tv_netz.setText("Fehler!");
            tv_haus.setText("Fehler!");
        }
    }

    @Override
    public void onFailure(Call<PvHome> call, Throwable t) {
        t.printStackTrace();
    }

}
