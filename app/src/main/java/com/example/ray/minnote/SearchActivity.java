package com.example.ray.minnote;

/**
 * Created by Ray on 22/03/25.
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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    public DBManager dbManager;
    private ListView listView;
    String keyword, result;
    TextView text_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Search");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF444B56")));
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        dbManager = new DBManager(this);
        listView = (ListView) findViewById(R.id.listView2);
        text_result = (TextView)findViewById(R.id.text_result);

        Intent intent = this.getIntent();
        keyword = intent.getStringExtra(MinActivity.EXTRA_MESSAGE).toString();
        search();
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
        search();
    }

    //Listen the back button and go back to homepage
    public void backHome(View view)
    {
        Intent launchMainActivity = new Intent(this, MinActivity.class);
        startActivity(launchMainActivity);
    }

    //The method to create search page and show result.
    public void search()
    {
        List<Note> Notes = dbManager.query();
        ArrayList<Map<String, String>> list = new ArrayList<>();
        int pos = 0;
        int count = 0;
        for (Note Note : Notes)
        {
            HashMap<String, String> map = new HashMap<>();
            if ((Note.title+Note.context).contains(keyword))
            {
                map.put("title", Note._id + " " + Note.title);
                map.put("context", Note.context);
                list.add(map);
                count++;
            }
            NoteProjecter.mapId.put(new Integer(pos++), Note.get_id());
            NoteProjecter.mapNote.put(Note.get_id(), Note);
        }

        if (count == 0) {
            result = "No result!";
            text_result.setText(result);
        }

        else {
            result = "There are " + count +" results.";
            text_result.setText(result);
            SimpleAdapter adapter = new SimpleAdapter(this, list,
                    android.R.layout.simple_list_item_2, new String[]{"title",
                    "context"}, new int[]{android.R.id.text1,
                    android.R.id.text2});
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Integer nid = NoteProjecter.mapId.get(new Integer(position));
                    Note note = (Note) NoteProjecter.mapNote.get(nid);

                    Intent launchEditIntent = new Intent(SearchActivity.this, EditActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Note", note);
                    launchEditIntent.putExtras(bundle);
                    startActivity(launchEditIntent);
                }
            });
        }
    }

    public void searchText(View view)
    {
        EditText edit_search = (EditText)findViewById(R.id.edit_search);
        keyword = edit_search.getText().toString();
        search();
    }

    //Listen the back button and make it be back to Homepage
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MinActivity.class);
        startActivity(intent);
    }
}
