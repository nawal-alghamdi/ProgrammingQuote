package com.example.programingquote.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.programingquote.data.Quote;
import com.example.programingquote.data.QuoteRepository;

public class QuoteViewModel extends ViewModel {

    private MutableLiveData<Quote> quoteMutableLiveData;
    private QuoteRepository quoteRepository;

    public QuoteViewModel() {
        quoteRepository = new QuoteRepository();
    }

    public LiveData<Quote> getRandomQuote() {
        quoteMutableLiveData = quoteRepository.requestQuote();
        return quoteMutableLiveData;
    }

    public LiveData<Quote> getQuote() {
        if (quoteMutableLiveData == null) {
            quoteMutableLiveData = quoteRepository.requestQuote();
        }
        return quoteMutableLiveData;
    }

}
