package com.example.examenpracticoxolotl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private  RecyclerView recyclerView;
    private ListaProductosadapter listaProductosadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView =(RecyclerView) findViewById(R.id.recyclerView);
        listaProductosadapter=new ListaProductosadapter(this);
        recyclerView.setAdapter(listaProductosadapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager=new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);



        retrofit=new Retrofit.Builder()
                .baseUrl("https://shoppapp.liverpool.com.mx/appclienteservices/services/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    obtenerDatos();
    }

    private void obtenerDatos() {
        StoreService service =retrofit.create(StoreService.class);
        final Call<JsonResult> productosRespuestaCall = service.obtenerListaProductos("z",1);
        productosRespuestaCall.enqueue(new Callback<JsonResult>() {


            @Override
            public void onResponse(Call<JsonResult> call, Response<JsonResult> response) {
                if (response.isSuccessful()) {

                    JsonResult jsonResult =response.body();
                    ResultProducts resultProducts = jsonResult.getPlpResults();
                    ArrayList<ProductsPropertis> listaProductos =resultProducts.getRecords();
                  //ProductsPropertis[] productsPropertis = resultProducts.getRecords();

                    for (int i =0; i<listaProductos.size(); i++)
                   {
                       //Pokemon pokemon=listaPokemon.get(i);
                       ProductsPropertis productsPropertis =listaProductos.get(i);
                   Log.i("FALLO", "Producto: "+ productsPropertis.getProductDisplayName());


                   }

                    listaProductosadapter.adiccionarListaProductos(listaProductos);


                }
                else
                {
                    Log.e("FALLO","onResponse: " + response.errorBody());

                }

            }

            @Override
            public void onFailure(Call<JsonResult> call, Throwable t) {
                Log.e("FALLO","Error"+t.getMessage());
            }
        });

    }
}
