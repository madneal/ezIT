package com.example.neilvyn.ezitclean;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DisplayEzitActivity extends AppCompatActivity {
    public static final String url = "jdbc:mysql://aston.hd.free.fr:3306/bdd";
    public static final String user = "fip";
    public static final String pw = "fip";
    public String id, name, date, enddate, desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new getEzitByID().execute();

        setContentView(R.layout.activity_display_ezit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intend = getIntent();
        id = intend.getStringExtra(MainActivity.EZIT_ID);

        EditText eventname = (EditText) findViewById(R.id.event_name);
        eventname.setText(id);
        Log.i("AAAAA", name);
        EditText eventdate = (EditText) findViewById(R.id.event_date);
        eventdate.setText(date);
        EditText eventenddate = (EditText) findViewById(R.id.event_enddate);
        eventenddate.setText(enddate);
        EditText eventdesc = (EditText) findViewById(R.id.event_description);
        eventdesc.setText(desc);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public class getEzitByID extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pw);

                Statement st = con.createStatement();

                String sql = "SELECT * FROM ezit WHERE idezit = " + id;
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    name = rs.getString(2);
                    Log.i("AAAAA", name);
                    date = rs.getString(3);
                    enddate = rs.getString(4);
                    desc = rs.getString(5);
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

}
