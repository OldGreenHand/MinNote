package com.example.ray.minnote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num);

        Intent intent = this.getIntent();
        String message = intent.getStringExtra(MinActivity.EXTRA_MESSAGE).toString();
        TextView textView = (TextView)findViewById(R.id.editText);
        textView.setText("The are " + message + " notes!");
    }
}
