package com.example.programingquote.data;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.programingquote.MyApplication;
import com.example.programingquote.R;
import com.example.programingquote.data.network.QuoteClient;
import com.example.programingquote.data.storage.AppDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuoteRepository {

    private final String TAG = getClass().getSimpleName();
    private MyApplication myApplication = MyApplication.getInstance();

    public MutableLiveData<Quote> requestQuote() {

        final MutableLiveData<Quote> quoteMutableLiveData = new MutableLiveData<>();

        AppDatabase appDatabase = AppDatabase.getInstance(myApplication);

        if (myApplication.isConnectedToInternet()) {

            QuoteClient.getInstance().getQuote().enqueue(new Callback<Quote>() {
                @Override
                public void onResponse(Call<Quote> call, Response<Quote> response) {
                    Log.d(TAG, "Inside onResponse method of Retrofit");
                    quoteMutableLiveData.setValue(response.body());
                    // Run insert & delete outside the main thread
                    AppExecutors.getInstance().diskIO().execute(() -> {
                        appDatabase.quoteDao().deleteAllData();
                        appDatabase.quoteDao().insertQuote(response.body());
                    });
                }

                @Override
                public void onFailure(Call<Quote> call, Throwable t) {
                    Log.e(TAG, "Error while retrieving the response using retrofit", t);
                }
            });

        } else {

            // When there is no internet, get the data that stored on database

            AppExecutors appExecutors = AppExecutors.getInstance();
            // To run the query outside the main thread
            appExecutors.diskIO().execute(new Runnable() {
                Quote quote;

                @Override
                public void run() {
                    quote = appDatabase.quoteDao().loadQuote();
                    // setValue function must run on the main thread so, this is way I used appExecutors.mainThread()
                    appExecutors.mainThread().execute(() -> {
                        if (quote != null) {
                            Log.i(TAG, "quote " + quote.getAuthor());
                            // Set the value that returned from the query
                            quoteMutableLiveData.setValue(quote);
                        }
                        Toast.makeText(myApplication, myApplication.getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    });
                }
            });
        }
        return quoteMutableLiveData;
    }
}
