package com.edocent.displayjokes;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayJoke extends Activity {

    public static final String JOKE_INTENT_KEY="JOKE";

    TextView jokeTextId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joke);

        if(getIntent() != null){
            String jokeText = getIntent().getExtras().getString(JOKE_INTENT_KEY);
            if(jokeText != null){
                jokeTextId = (TextView) findViewById(R.id.jokeText);
                jokeTextId.setText(jokeText);
            }
        }
    }
}
