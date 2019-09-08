package com.example.examenpracticoxolotl.storeapi;

import com.example.examenpracticoxolotl.models.JsonResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StoreService {
    @GET("plp")
    Call<JsonResult> obtenerListaProductos(@Query("search-string") String busqueda, @Query("page-number") int numPage);
}
