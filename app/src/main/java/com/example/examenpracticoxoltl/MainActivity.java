package com.example.examenpracticoxoltl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.examenpracticoxoltl.apiStore.StoreService;
import com.example.examenpracticoxoltl.models.Productos;
import com.example.examenpracticoxoltl.models.ProductosRespuesta;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private static  final  String  TAG ="PRODUCTOS";

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
        Call<ProductosRespuesta> productosRespuestaCall=service.obtenerListaProductos();

        productosRespuestaCall.enqueue(new Callback<ProductosRespuesta>() {
            @Override
            public void onResponse(Call<ProductosRespuesta> call, Response<ProductosRespuesta> response) {

                if (response.isSuccessful())
                {

                    ProductosRespuesta productosRespuesta=response.body();
                    ArrayList<Productos> lisaProductos =productosRespuesta.getResults();

                    for (int i=0;i<lisaProductos.size();i++){
                        Productos p=lisaProductos.get(i);
                        Log.i(TAG,"Producto: "+p.getProductDisplayName());
                        //rescuerda cambiar los log por toast
                    }

                }
                else
                {

                    Log.e(TAG,"onResponse"+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ProductosRespuesta> call, Throwable t) {
                Log.e(TAG,"onFailture: "+t.getMessage());

            }
        });
    }
}
