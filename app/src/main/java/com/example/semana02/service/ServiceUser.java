package com.example.semana02.service;

import com.example.semana02.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceUser {

    //profit tambien conecta ls servicios web
    @GET("users")
    public abstract Call<List<User>>  listausuarios();
}
