package com.example.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    FrameLayout fram1,fram2;
    LinearLayout gofram2;
    private static int state = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fram1= findViewById(R.id.fram1);
        fram2= findViewById(R.id.fram2);

        gofram2= findViewById(R.id.gofram2);
        gofram2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUI(R.layout.activity_second,1 );
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (MainActivity.state ==0)
            onBackPressed();
        else{
            updateUI(R.layout.activity_main,0);
            gofram2= findViewById(R.id.gofram2);
            gofram2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateUI(R.layout.activity_second,1 );
                }
            });
        }
    }

    private void updateUI(int resID, int state){
        LayoutInflater inflater=getLayoutInflater();
        View secondView = inflater.inflate(resID,null, false);
        secondView.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
        setContentView(secondView);
        MainActivity.state=state;
    }
}
