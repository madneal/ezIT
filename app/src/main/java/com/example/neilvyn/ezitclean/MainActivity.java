package com.example.neilvyn.ezitclean;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public static final String url = "jdbc:mysql://aston.hd.free.fr:3306/bdd";
    public static final String user = "fip";
    public static final String pw = "fip";
    private TextView lastName, test;
    //String android_id = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lastName = (TextView) findViewById(R.id.lastName);
        new MyTask().execute();

    }
    public void addEzit(View view) {
        Intent intent = new Intent(this, EzitActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        private String  lName="";
        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pw);

                Statement st = con.createStatement();
                String sql = "select * from test";

                final ResultSet rs = st.executeQuery(sql);
                rs.next();
                lName = rs.getString(2) + rs.getString(1);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            lastName.setText(lName);
            super.onPostExecute(result);
        }
    }
}
