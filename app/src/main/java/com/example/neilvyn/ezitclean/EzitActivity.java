package com.example.neilvyn.ezitclean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ListActivity;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;


public class EzitActivity extends ListActivity implements OnClickListener {

    public static final String url = "jdbc:mysql://aston.hd.free.fr:3306/bdd";
    public static final String user = "fip";
    public static final String pw = "fip";
    //UI References
    public EditText eventDate;
    public EditText eventTime;
    public EditText eventEndDate;
    public EditText eventEndTime;

    private DatePickerDialog eventDatePickerDialog;
    private TimePickerDialog eventTimePickerDialog;
    private DatePickerDialog eventEndDatePickerDialog;
    private TimePickerDialog eventEndTimePickerDialog;

    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;
    public String name;
    public String desc;
    public String date;
    public String enddate;
    /** Items entered by the user is stored in this ArrayList variable */
    ArrayList<String> list = new ArrayList<>();

    /** Declaring an ArrayAdapter to set items to ListView */
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ezit);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        timeFormatter = new SimpleDateFormat("HH:mm:ss", Locale.US);
        findViewsById();

        setDateTimeField();

        Button btn = (Button) findViewById(R.id.btnAdd);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText) findViewById(R.id.txtItem);
                list.add(edit.getText().toString());
                edit.setText("");
                adapter.notifyDataSetChanged();
            }
        };
        btn.setOnClickListener(listener);
        setListAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        OnClickListener listener2 = new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nametemp = (EditText) findViewById(R.id.event_name);
                name = nametemp.getText().toString();
                EditText desctemp = (EditText) findViewById(R.id.event_description);
                desc = desctemp.getText().toString();
                date = eventDate.getText().toString() +" "+ eventTime.getText().toString();
                enddate = eventEndDate.getText().toString() +" "+ eventEndTime.getText().toString();
                new addNewEzit().execute();
            }
        };
        fab.setOnClickListener(listener2);
    }

    private void findViewsById() {
        eventDate = (EditText) findViewById(R.id.event_date);
        eventDate.setInputType(InputType.TYPE_NULL);
        eventDate.requestFocus();

        eventTime = (EditText) findViewById(R.id.event_time);
        eventTime.setInputType(InputType.TYPE_NULL);
        eventTime.requestFocus();

        eventEndDate = (EditText) findViewById(R.id.event_enddate);
        eventEndDate.setInputType(InputType.TYPE_NULL);
        eventEndDate.requestFocus();

        eventEndTime = (EditText) findViewById(R.id.event_endtime);
        eventEndTime.setInputType(InputType.TYPE_NULL);
        eventEndTime.requestFocus();

    }

    private void setDateTimeField() {
        eventDate.setOnClickListener(this);
        eventTime.setOnClickListener(this);
        eventEndDate.setOnClickListener(this);
        eventEndTime.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        eventDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                eventDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        eventTimePickerDialog = new TimePickerDialog(this, new OnTimeSetListener() {
            public void  onTimeSet(TimePicker view, int hourOfDay, int minute){
                Calendar newTime = Calendar.getInstance();
                newTime.set(hourOfDay, minute);
                eventTime.setText(timeFormatter.format(newTime.getTime()));
            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);

        eventEndDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                eventEndDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        eventEndTimePickerDialog = new TimePickerDialog(this, new OnTimeSetListener() {
            public void  onTimeSet(TimePicker view, int hourOfDay, int minute){
                Calendar newTime = Calendar.getInstance();
                newTime.set(hourOfDay, minute);
                eventEndTime.setText(timeFormatter.format(newTime.getTime()));
            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == eventDate) {
            eventDatePickerDialog.show();
        }else if(view == eventTime) {
            eventTimePickerDialog.show();
        }else if(view == eventEndDate) {
            eventEndDatePickerDialog.show();
        }else if(view == eventEndTime) {
            eventEndTimePickerDialog.show();
        }
    }

    public class addNewEzit extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pw);

                Statement st = con.createStatement();

                String sql = "INSERT INTO ezit VALUES ('', '"+name+"','"+date+"','"+enddate+"','"+desc+"');";

                //final ResultSet rs = st.executeQuery(sql);
                st.executeUpdate(sql);
                //rs.next();
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
