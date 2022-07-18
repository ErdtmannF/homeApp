package de.erdtmann.soft.android.homeapp.network;

import android.widget.ImageView;
import android.widget.Switch;

import de.erdtmann.soft.android.homeapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestHeizungStatus implements Callback<String> {

    static final String BASE_URL = "http://pizeropool:9898/";

    public ImageView img_heizung;
    public Switch sw_heizung;

    public void start(ImageView img_heizung, Switch sw_heizung) {
        this.img_heizung = img_heizung;
        this.sw_heizung = sw_heizung;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        RestApi restApi = retrofit.create(RestApi.class);

        Call<String> call = restApi.getHeizungStatus();
        call.enqueue(this);
    }

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
}
