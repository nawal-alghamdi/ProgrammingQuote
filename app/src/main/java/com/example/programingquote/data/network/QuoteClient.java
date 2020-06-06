package com.example.programingquote.data.network;

import com.example.programingquote.data.Quote;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuoteClient {

    private static final String BASE_URL = "https://programming-quotes-api.herokuapp.com/";
    private static QuoteClient instance;
    private ApiInterface apiInterface;

    public QuoteClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);

    }

    public static QuoteClient getInstance() {
        if (instance == null) {
            instance = new QuoteClient();
        }
        return instance;
    }

    public Call<Quote> getQuote() {
        return apiInterface.getQuote();
    }
}
