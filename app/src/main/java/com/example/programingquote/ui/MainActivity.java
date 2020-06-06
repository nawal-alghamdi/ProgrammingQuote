package com.example.programingquote.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.programingquote.R;
import com.example.programingquote.data.Quote;
import com.example.programingquote.databinding.ActivityMainBinding;
import com.example.programingquote.viewModel.QuoteViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    ActivityMainBinding binding;
    QuoteViewModel quoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        quoteViewModel = new ViewModelProvider(this).get(QuoteViewModel.class);

        setupViewModel();

        binding.swipeRefresh.setOnRefreshListener(() -> {
            setupRandomQuote();
            binding.swipeRefresh.setRefreshing(false);
        });
    }

    private void setupViewModel() {
        showProgressPar();
        quoteViewModel.getQuote().observe(this, quote -> {
            Log.d(TAG, "Updating the Quote from LiveData in ViewModel");
            showQuote();
            setDataOnTextViews(quote);
        });
    }

    private void setupRandomQuote() {
        binding.loadingIndicator.setVisibility(View.VISIBLE);
        quoteViewModel.getRandomQuote().observe(this, new Observer<Quote>() {
            @Override
            public void onChanged(Quote quote) {
                Log.d(TAG, "Updating the Quote from LiveData in ViewModel");
                showQuote();
                setDataOnTextViews(quote);
            }
        });
    }

    private void setDataOnTextViews(Quote quote) {
        binding.quoteTextView.setText(quote.getEn());
        binding.authorTextView.setText(quote.getAuthor());
    }

    private void showProgressPar() {
        binding.loadingIndicator.setVisibility(View.VISIBLE);
        binding.cardView.setVisibility(View.INVISIBLE);
    }

    private void showQuote() {
        binding.loadingIndicator.setVisibility(View.INVISIBLE);
        binding.cardView.setVisibility(View.VISIBLE);
    }

}
