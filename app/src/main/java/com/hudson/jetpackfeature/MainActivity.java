package com.hudson.jetpackfeature;

import android.os.Bundle;
import android.view.View;

import com.hudson.jetpackfeature.databinding.BindingTestActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToDataBindingPage(View view) {
        BindingTestActivity.start(this);
    }
}
