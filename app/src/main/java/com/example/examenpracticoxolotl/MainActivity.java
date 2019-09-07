package com.example.examenpracticoxolotl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.examenpracticoxolotl.models.JsonResult;
import com.example.examenpracticoxolotl.storeapi.StoreService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit=new Retrofit.Builder()
                .baseUrl("https://shoppapp.liverpool.com.mx/appclienteservices/services/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    obtenerDatos();
    }

    private void obtenerDatos() {
        StoreService service =retrofit.create(StoreService.class);
        Call<JsonResult> productosRespuestaCall = service.obtenerListaProductos("h",1,1);
        productosRespuestaCall.enqueue(new Callback<JsonResult>() {


            @Override
            public void onResponse(Call<JsonResult> call, Response<JsonResult> response) {
                
            }

            @Override
            public void onFailure(Call<JsonResult> call, Throwable t) {

            }
        });

    }
}
