package com.example.neilvynmadhia.ezit;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;


public class AddEventActivity extends Activity implements OnClickListener {

    //UI References
    private EditText eventDate;
    private EditText eventTime;
    private EditText eventDeadLine;

    private DatePickerDialog eventDatePickerDialog;
    private TimePickerDialog eventTimePickerDialog;
    private TimePickerDialog eventDeadLinePickerDialog;

    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        timeFormatter = new SimpleDateFormat("HH:mm", Locale.US);
        findViewsById();

        setDateTimeField();
    }

    private void findViewsById() {
        eventDate = (EditText) findViewById(R.id.event_date);
        eventDate.setInputType(InputType.TYPE_NULL);
        eventDate.requestFocus();

        eventTime = (EditText) findViewById(R.id.event_time);
        eventTime.setInputType(InputType.TYPE_NULL);
        eventTime.requestFocus();

        eventDeadLine = (EditText) findViewById(R.id.event_deadline);
        eventDeadLine.setInputType(InputType.TYPE_NULL);
    }

    private void setDateTimeField() {
        eventDate.setOnClickListener(this);
        eventTime.setOnClickListener(this);
        eventDeadLine.setOnClickListener(this);

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

        eventDeadLinePickerDialog = new TimePickerDialog(this, new OnTimeSetListener() {
            public void  onTimeSet(TimePicker view, int hourOfDay, int minute){
                Calendar newTime = Calendar.getInstance();
                newTime.set(hourOfDay, minute);
                eventDeadLine.setText(timeFormatter.format(newTime.getTime()));
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
        }else if(view == eventDeadLine) {
            eventDeadLinePickerDialog.show();
        }
    }
}