package de.erdtmann.soft.android.homeapp.network;

import de.erdtmann.soft.android.homeapp.model.PvHome;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {

    @GET("pvHome")
    Call<PvHome> getPvHomeDaten();

    @GET("pumpe/status")
    Call<String> getPumpenStatus();

    @GET("heizung/status")
    Call<String> getHeizungStatus();

    @POST("pumpe/ein")
    Call<String> schaltePumpeEin();

    @POST("pumpe/aus")
    Call<String> schaltePumpeAus();

    @POST("heizung/ein")
    Call<String> schalteHeizungEin();

    @POST("heizung/aus")
    Call<String> schalteHeizungAus();
}
