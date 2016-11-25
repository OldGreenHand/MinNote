package com.example.ray.minnote;

/**
 * Created by Ray on 22/03/12.
 * * @Author：Rui(Ray) Min u5679105
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    public DBManager dbManager;
    public ListView listView;

    public EditText editTitle;
    public EditText editContext;
    public Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        editTitle = (EditText)findViewById(R.id.edit_title);
        editContext = (EditText)findViewById(R.id.edit_context);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF444B56")));
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Intent intent = this.getIntent();
        note =(Note)intent.getSerializableExtra("Note");
        editTitle.setText(note.getTitle());
        editContext.setText(note.getContext());
        dbManager = new DBManager(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_min, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_new:
                launchNewActivit();;
                return true;
            case R.id.action_deleteall:
                showDeleteAlert();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void launchNewActivit()
    {
        Intent launchNewIntent = new Intent(this, NewActivity.class);
        startActivity(launchNewIntent);
    }

    public void updateNote(View view)
    {
        //int id = (int)System.currentTimeMillis();
        note.setTitle(editTitle.getText().toString());
        System.out.println("title is " + note.title);
        note.setContext(editContext.getText().toString());
        System.out.println("context is " + note.context);
        dbManager.update(note);
        this.finish();
        Intent launchMainActivity = new Intent(this, MinActivity.class);
        startActivity(launchMainActivity);
    }

    //kill this page and go back to homepage
    public void cancelEdit(View view)
    {
        this.finish();
    }

    //The method to show an alert dialog when deleting
    public void showDeleteAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Warning!")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteNote();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void deleteNote()
    {
        dbManager.deleteOldNote(note);
        Intent launchMainActivity = new Intent(this, MinActivity.class);
        startActivity(launchMainActivity);
    }

    //Listen the back button and make it be back to Homepage
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MinActivity.class);
        startActivity(intent);
    }
}
