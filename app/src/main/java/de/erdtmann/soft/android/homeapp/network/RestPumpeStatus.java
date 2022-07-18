package de.erdtmann.soft.android.homeapp.network;

import android.widget.ImageView;
import android.widget.Switch;

import de.erdtmann.soft.android.homeapp.R;
import de.erdtmann.soft.android.homeapp.model.PvHome;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestPumpeStatus implements Callback<String> {

    static final String BASE_URL = "http://pizeropool:9898/";

    public ImageView img_pumpe;
    public Switch sw_pumpe;
    public Switch sw_heizung;

    public void start(ImageView img_pumpe, Switch sw_pumpe, Switch sw_heizung) {
        this.img_pumpe = img_pumpe;
        this.sw_pumpe = sw_pumpe;
        this.sw_heizung = sw_heizung;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        RestApi restApi = retrofit.create(RestApi.class);

        Call<String> call = restApi.getPumpenStatus();
        call.enqueue(this);
    }

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
}
