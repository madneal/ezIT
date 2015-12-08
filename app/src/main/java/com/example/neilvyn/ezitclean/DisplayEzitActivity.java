package com.example.neilvyn.ezitclean;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class DisplayEzitActivity extends ListActivity {
    public static final String url = "jdbc:mysql://aston.hd.free.fr:3306/bdd";
    public static final String user = "fip";
    public static final String pw = "fip";
    public String ezit, name, date, end, desc;
    public ArrayList<String> Choice = new ArrayList<>();
    public int id;
    public final static String win="";
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_ezit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent intent = getIntent();
        ezit = intent.getStringExtra(MainActivity.EZIT);
        Log.i("AAAAAAAAAAAAA : ", ezit);
        String[] ezitinfo = ezit.split(";");
        EditText editname = (EditText) findViewById(R.id.event_name);
        editname.setText(ezitinfo[1]);
        EditText editdate = (EditText) findViewById(R.id.event_date);
        editdate.setText(ezitinfo[2]);
        EditText editend = (EditText) findViewById(R.id.event_enddate);
        editend.setText(ezitinfo[3]);
        EditText editdesc = (EditText) findViewById(R.id.event_description);
        editdesc.setText(ezitinfo[4]);

        for (int i = 5; i < ezitinfo.length; i++) {
            String a = ezitinfo[i].substring(2, ezitinfo[i].length());
            Choice.add(a);
        }
        id = Integer.parseInt(ezitinfo[0]);
        if (id<10) {
            Button btn = (Button) findViewById(R.id.btnAdd);
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Choice);
             View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText edit = (EditText) findViewById(R.id.txtItem);
                    Choice.add(edit.getText().toString());
                    edit.setText("");
                    adapter.notifyDataSetChanged();
                }
            };
            btn.setOnClickListener(listener);
            setListAdapter(adapter);
        } else {
            Button btn = (Button) findViewById(R.id.btnAdd);
            btn.setVisibility(View.GONE);
            EditText edit = (EditText) findViewById(R.id.txtItem);
            edit.setVisibility(View.GONE);
            ListView listview= getListView();
            listview.setChoiceMode(listview.CHOICE_MODE_SINGLE);
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Choice);
            setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, Choice));
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        View.OnClickListener listener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id<10) {
                    Toast.makeText(DisplayEzitActivity.this, "ezIT Saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DisplayEzitActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(DisplayEzitActivity.this, Win.class);
                    String choices = "";
                    for (int i=0; i<Choice.size();i++) {
                        choices+=Choice.get(i)+";";
                    }
                    intent.putExtra(win, choices);
                    startActivity(intent);
                }
            }
        };
        fab.setOnClickListener(listener2);

    }
}
