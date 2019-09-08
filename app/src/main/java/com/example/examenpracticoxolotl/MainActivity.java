package com.example.examenpracticoxolotl;

import android.annotation.SuppressLint;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private Retrofit retrofit;
    FloatingActionButton fb_izq ;
    FloatingActionButton fb_der ;


    private  RecyclerView recyclerView;
    private ListaProductosadapter listaProductosadapter;

    public int getNumPague() {
        return numPague;
    }

    public void setNumPague(int numPague) {
        this.numPague = numPague;
    }

    private int numPague =1;

    public String getTextSearch() {
        return textSearch;
    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
    }

    private String textSearch="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fb_izq=(FloatingActionButton)findViewById(R.id.fb_izq);
        fb_der=(FloatingActionButton)findViewById(R.id.fb_der);

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



    }

    private void obtenerDatos(String textSearch, final int numPague) {
        StoreService service =retrofit.create(StoreService.class);
        final Call<JsonResult> productosRespuestaCall = service.obtenerListaProductos(textSearch,numPague);
        productosRespuestaCall.enqueue(new Callback<JsonResult>() {


            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(Call<JsonResult> call, Response<JsonResult> response) {
                if (response.isSuccessful()) {

                    JsonResult jsonResult =response.body();
                    ResultProducts resultProducts = jsonResult.getPlpResults();
                    ArrayList<ProductsPropertis> listaProductos =resultProducts.getRecords();
                  //ProductsPropertis[] productsPropertis = resultProducts.getRecords();

                    if (listaProductos.size()==0)
                    {

                        Toast toast1 =Toast.makeText(getApplicationContext(),"¡No hay mas productos!", Toast.LENGTH_SHORT);

                        toast1.show();
                        setNumPague(1);
                        listaProductosadapter.delete();
                        obtenerDatos(getTextSearch(),getNumPague());


                    }
                    else
                    {
                        

                        listaProductosadapter.adiccionarListaProductos(listaProductos);
                        fb_der.setVisibility(View.VISIBLE);
                        fb_izq.setVisibility(View.VISIBLE);


                    }




                }
                else
                {

                    Toast toast1 =Toast.makeText(getApplicationContext(),"¡Error al cargar datos.!", Toast.LENGTH_SHORT);

                    toast1.show();
                     setNumPague(1);
                    listaProductosadapter.delete();


                }

            }

            @Override
            public void onFailure(Call<JsonResult> call, Throwable t) {
                Log.e("FALLO","Error"+t.getMessage());
                Toast toast1 =Toast.makeText(getApplicationContext(),"¡Error al cargar datos.!", Toast.LENGTH_SHORT);

                toast1.show();
            }
        });

    }

    public  boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_buscador,menu);
        MenuItem item=menu.findItem(R.id.buscador);
        SearchView searchView =(SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(this);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String s) {

        listaProductosadapter.delete();
        textSearch=s;
        numPague=1;
        obtenerDatos(textSearch,numPague);



        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        return false;
    }

    public void siguiente(View view)
    {   listaProductosadapter.delete();
        numPague+=1;
        obtenerDatos(textSearch,numPague);
        Toast toast1 = Toast.makeText(getApplicationContext(),"Pagina: "+numPague, Toast.LENGTH_SHORT);
        toast1.show();
    }
    public  void atras(View view)
    {


        numPague-=1;
        if (numPague==0)
        {
            numPague=1;
            Toast toast1 =Toast.makeText(getApplicationContext(),"Pagina: "+numPague, Toast.LENGTH_SHORT);

            toast1.show();

            listaProductosadapter.delete();
            obtenerDatos(textSearch,numPague);

        }
        else
            {
                Toast toast1 =Toast.makeText(getApplicationContext(),"Pagina: "+numPague, Toast.LENGTH_SHORT);

                toast1.show();
                listaProductosadapter.delete();
                obtenerDatos(textSearch,numPague);
        }


    }
}
