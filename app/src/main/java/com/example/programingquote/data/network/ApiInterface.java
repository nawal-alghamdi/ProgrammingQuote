package com.example.programingquote.data.network;

import com.example.programingquote.data.Quote;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("quotes/random")
    public Call<Quote> getQuote();
}
