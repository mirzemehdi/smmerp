package com.android.erp;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccesActivity extends AppCompatActivity {

    ImageView imageView;
    TextView cong,done;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes);
        String succes = getIntent().getStringExtra("succes");

        imageView = findViewById(R.id.doneLog);
        cong = findViewById(R.id.cong);
        done = findViewById(R.id.congDone);
        Typeface avenir_book = Typeface.createFromAsset(getAssets(),"fonts/AvenirBook.ttf");
        Typeface avenir_medium = Typeface.createFromAsset(getAssets(),"fonts/AvenirMedium.ttf");
        cong.setTypeface(avenir_medium);
        done.setTypeface(avenir_book);
        if (succes.equals("0")){
            imageView.setImageResource(R.drawable.ico);
            cong.setText("Sorry,we can't complete your process");
            done.setText("Please,try again!");
        }
    }
}
