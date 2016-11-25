package com.example.ray.minnote;

/**
 * Created by Ray on 22/03/12.
 * * @Author：Rui(Ray) Min u5679105
 */

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class NewActivity extends AppCompatActivity {
    public DBManager dbManager;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("New");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF444B56")));
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        dbManager = new DBManager(this);
    }

    public void saveNote(View view)
    {
        //int id = (int)System.currentTimeMillis();
        EditText editTitle = (EditText)findViewById(R.id.edit_title);
        EditText editContext = (EditText)findViewById(R.id.edit_context);
        Note note = new Note(editTitle.getText().toString(), editContext.getText().toString());
        dbManager.add(note);
        this.finish();
        Intent launchMainActivity = new Intent(this, MinActivity.class);
        startActivity(launchMainActivity);
    }

    public void cancelEdit(View view)
    {
        this.finish();
    }

    //Listen the back button and make it be back to Homepage
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MinActivity.class);
        startActivity(intent);
    }

}
