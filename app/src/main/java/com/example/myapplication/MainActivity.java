package com.example.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    FrameLayout fram1, fram2;
    LinearLayout area;
    private static int state = 0;
    JSONObject stages = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        area = findViewById(R.id.sabade_kharid);
        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state++;
                renderLayout();
            }
        });
        Button btnPrev = findViewById(R.id.btnPrev);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state--;
                renderLayout();
            }
        });

        try {
            stages = new JSONObject(getString(R.string.test));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        renderLayout();
    }

    public void renderLayout() {
        try {
            if (stages.getJSONArray("stages").getJSONObject(state).getString("type").equals("number")) {
                area.removeAllViews();
                renderNumber(stages.getJSONArray("stages").getJSONObject(state).getJSONArray("parameter"));
            } else if (stages.getJSONArray("stages").getJSONObject(state).getString("type").equals("switch")) {
                area.removeAllViews();
                renderSwitch(stages.getJSONArray("stages").getJSONObject(state));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void renderNumber(JSONArray arr) throws JSONException {
        for (int i = 0; i < arr.length(); i++) {
            View v = getLayoutInflater().inflate(R.layout.select_drycleaner, area, false);
            TextView textView = v.findViewById(R.id.lbl);
            textView.setText(arr.getJSONObject(i).getString("title"));
            area.addView(v);
        }
    }

    private void renderSwitch(JSONObject obj) throws JSONException {
        View v = getLayoutInflater().inflate(R.layout.select_switch, area, false);
        TextView textView = v.findViewById(R.id.chk);
        textView.setText(obj.getString("description"));
        area.addView(v);
    }

    @Override
    public void onBackPressed() {
        if (MainActivity.state == 0)
            onBackPressed();
        else {
            updateUI(R.layout.activity_main, 0);
        }
    }

    private void updateUI(int resID, int state) {
        LayoutInflater inflater = getLayoutInflater();
        View secondView = inflater.inflate(resID, null, false);
        secondView.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
        setContentView(secondView);
        MainActivity.state = state;
    }
}
