package com.example.examenpracticoxolotl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.examenpracticoxolotl.models.JsonResult;
import com.example.examenpracticoxolotl.models.ProductsPropertis;
import com.example.examenpracticoxolotl.models.ResultProducts;
import com.example.examenpracticoxolotl.storeapi.StoreService;

import java.util.ArrayList;

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
                if (response.isSuccessful()) {

                    JsonResult jsonResult =response.body();
                    ResultProducts resultProducts = jsonResult.getPlpResults();

                    ProductsPropertis[] productsPropertis = resultProducts.getRecords();

                    Log.e("FALLO","ID producto"+ productsPropertis[0].getProductId()
                    );
                }
                else
                {
                    Log.e("FALLO","onResponse: " + response.errorBody());

                }

            }

            @Override
            public void onFailure(Call<JsonResult> call, Throwable t) {
                Log.e("FALLO",t.getMessage());
            }
        });

    }
}
