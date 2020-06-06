package com.example.programingquote.data.storage;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.programingquote.data.Quote;

@Dao
public interface QuoteDao {

    @Query("SELECT * FROM quote")
    Quote loadQuote();

    @Insert
    void insertQuote(Quote quote);

    @Query("DELETE FROM quote")
    void deleteAllData();
}
