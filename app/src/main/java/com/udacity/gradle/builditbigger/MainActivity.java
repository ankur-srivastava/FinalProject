package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.edocent.displayjokes.DisplayJoke;
import com.example.ankursrivastava.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import java.io.IOException;


public class MainActivity extends Activity {

    private static final String NAME = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        new EndpointsAsyncTask(this).execute(new Pair<Context, String>(this, ""));
    }


    //GCE Module Test
    class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
        private ProgressDialog dialog;

        private MyApi myApiService = null;
        private Context context;

        public EndpointsAsyncTask(Activity activity){
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.setMessage("Getting a fresh Joke !!");
            dialog.show();
        }

        @Override
        protected String doInBackground(Pair<Context, String>... params) {
            if(myApiService == null) {
                /*
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                */
                //Calling the deployed module
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://jokes-1332.appspot.com/_ah/api/");

                myApiService = builder.build();
            }

            context = params[0].first;
            String name = params[0].second;

            try {
                return myApiService.sayHi(name).execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            dialog.dismiss();
            Intent intent = new Intent(getApplicationContext(), DisplayJoke.class);
            intent.putExtra(DisplayJoke.JOKE_INTENT_KEY, result);
            startActivity(intent);
        }
    }
}