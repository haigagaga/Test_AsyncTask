package com.example.administrator.test_asynctask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void imageLoader(View view){
        startActivity(new Intent(this,ImageLoader.class));
    }
    public void progressBar(View view){
        startActivity(new Intent(this,MProgressBar.class));
    }

}
