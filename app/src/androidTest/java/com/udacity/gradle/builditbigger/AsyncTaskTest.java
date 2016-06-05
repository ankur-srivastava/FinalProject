package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import com.example.ankursrivastava.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by ankursrivastava on 6/4/16.
 */
public class AsyncTaskTest extends AsyncTask<String, Void, String> {

    private static final String TAG = "AsyncTaskTest";
    private MyApi myApiService = null;
    private Context context;

    private AsyncTaskTestListener mListener = null;
    private Exception mError = null;

    @Override
    protected String doInBackground(String... params) {
        if(myApiService == null) {

                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });

            //Calling the deployed module
            /*
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://jokes-1332.appspot.com/_ah/api/");
            */
            myApiService = builder.build();
        }

        try {
            return myApiService.sayHi("").execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (this.mListener != null)
            this.mListener.onComplete(result, mError);
    }

    public static interface AsyncTaskTestListener {
        public void onComplete(String jsonString, Exception e);
    }

    public AsyncTaskTest setListener(AsyncTaskTestListener listener) {
        this.mListener = listener;
        return this;
    }
}
