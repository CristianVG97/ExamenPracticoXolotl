package com.example.examenpracticoxoltl.apiStore;

import com.example.examenpracticoxoltl.models.ProductosRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StoreService {

    @GET("plp?force-plp=true&search-string=h&page-number=2&number-of-items-per-page=1")
    Call<ProductosRespuesta> obtenerListaProductos();
}
