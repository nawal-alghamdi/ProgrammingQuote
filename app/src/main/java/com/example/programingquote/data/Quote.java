package com.example.programingquote.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Quote {

    @NonNull
    @PrimaryKey
    private String _id;

    // English quote
    @ColumnInfo(name = "english_quote")
    private String en;

    private String author;

    public Quote(String _id, String en, String author) {
        this._id = _id;
        this.en = en;
        this.author = author;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

}
