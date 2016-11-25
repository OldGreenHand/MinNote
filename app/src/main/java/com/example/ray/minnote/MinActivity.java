package com.example.ray.minnote;

/**
 * Created by Ray on 22/03/09.
 * * @Author：Rui(Ray) Min u5679105
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinActivity extends AppCompatActivity {

    public DBManager dbManager;
    private ListView listView;
    EditText text_search;
    int num = 0;

    public final static String EXTRA_MESSAGE = "com.example.ray.minnote.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_min);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("MinNote");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF444B56")));
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        dbManager = new DBManager(this);
        listView = (ListView) findViewById(R.id.listView);
        query(listView);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_min, menu);
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
                showDeleteAllAlert();
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

    //The method to show an alert dialog when deleting
    public void showDeleteAllAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Warning!")
                .setMessage("Are you sure you want to delete all notes?")
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAll();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void deleteAll()
    {
        dbManager.deleteTable();
        query(listView);
    }

    public static ArrayList<Map<String, String>> scanList(List<Note> Notes)
    {
        ArrayList<Map<String, String>> list = new ArrayList<>();
        int pos = 0;
        for (Note Note : Notes)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("title", Note.title);
            map.put("context", Note.context);
            list.add(map);

            NoteProjecter.mapId.put(new Integer(pos++), Note.get_id());
            NoteProjecter.mapNote.put(Note.get_id(), Note);
        }
        return list;
    }

    public void query(View view)
    {
        List<Note> Notes = dbManager.query();
        num = Notes.size();
        ArrayList<Map<String, String>> list = scanList(Notes);
        SimpleAdapter adapter = new SimpleAdapter(this, list,
                android.R.layout.simple_list_item_2, new String[] { "title",
                "context" }, new int[]{ android.R.id.text1,
                android.R.id.text2 });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer nid = NoteProjecter.mapId.get(new Integer(position));
                Note note = (Note) NoteProjecter.mapNote.get(nid);

                Intent launchEditIntent = new Intent(MinActivity.this, EditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Note", note);
                launchEditIntent.putExtras(bundle);
                startActivity(launchEditIntent);
            }
        });
    }

    //The method to create search page and show result.
    public void search (View view)
    {
        Intent launchSearchIntent = new Intent(this, SearchActivity.class);
        text_search = (EditText)findViewById(R.id.edit_search);
        String message = text_search.getText().toString();
        launchSearchIntent.putExtra(EXTRA_MESSAGE, message);
        startActivity(launchSearchIntent);
    }

    //Listen the back button and make it be out of this app
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    public void showNum (View view)
    {
        Intent launchNumIntent = new Intent(this, NumActivity.class);
        String message = String.valueOf(num);
        launchNumIntent.putExtra(EXTRA_MESSAGE, message);
        startActivity(launchNumIntent);
    }

}
