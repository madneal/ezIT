package com.example.neilvyn.ezitclean;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String url = "jdbc:mysql://aston.hd.free.fr:3306/bdd";
    public static final String user = "fip";
    public static final String pw = "fip";
    public final static String EZIT_ID="";
    public int userid;
    public ArrayList<String> list = new ArrayList<>();
    public ArrayList<String> listID = new ArrayList<>();
    ListView mListView;
    ArrayAdapter<String> adapter;
    private String username, password;
    //String android_id = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        login();
        new getEzit().execute();
        mListView = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, list);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DisplayEzitActivity.class);
                String ezitid = listID.get(position);
                intent.putExtra(EZIT_ID, ezitid);
                startActivity(intent);
            }
        });

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

    public class getEzit extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pw);

                Statement st = con.createStatement();

                String sql = "select * from ezit order by date";
                ResultSet rs = st.executeQuery(sql);

                while(rs.next()){
                    list.add(rs.getString(2));
                    listID.add(rs.getString(1));
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

    public void login() {
        final Dialog login = new Dialog(this);
        login.setContentView(R.layout.login_dialog);
        login.setTitle("Login to ezIT");

        Button btnLogin = (Button) login.findViewById(R.id.btnLogin);
        Button btnCancel = (Button) login.findViewById(R.id.btnCancel);
        final EditText txtUsername = (EditText)login.findViewById(R.id.txtUsername);
        final EditText txtPassword = (EditText)login.findViewById(R.id.txtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtUsername.getText().toString().trim().length() > 0 && txtPassword.getText().toString().trim().length() > 0)
                {
                    Toast.makeText(MainActivity.this,
                            "Login Sucessfull", Toast.LENGTH_LONG).show();
                    login.dismiss();
                }
                else
                {
                    Toast.makeText(MainActivity.this,
                            "Please enter Username and Password", Toast.LENGTH_LONG).show();

                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.dismiss();
            }
        });
        login.show();
    }
}
