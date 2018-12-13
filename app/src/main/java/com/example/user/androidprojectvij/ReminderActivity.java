package com.example.user.androidprojectvij;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReminderActivity extends AppCompatActivity implements View.OnClickListener {
    private int mYear, mMonth, mDay;
    EditText ReminderFromDate, ReminderToDate, ReminderAmount, ReminderDescription;
    Button FromDateBtn, ToDateBtn, ReminderSave, ReminderReset;
    Spinner ReminderType;

    DataBaseHelper mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        getSupportActionBar().hide();

        mydb = new DataBaseHelper(this);

        FromDateBtn=(Button)findViewById(R.id.FromDateBtn);
        ToDateBtn=(Button)findViewById(R.id.ToDateBtn);

        ReminderFromDate=(EditText)findViewById(R.id.ReminderFromDate);
        ReminderToDate=(EditText)findViewById(R.id.ReminderToDate);

        ReminderAmount=(EditText)findViewById(R.id.ReminderAmount);
        ReminderDescription=(EditText)findViewById(R.id.ReminderDescription);

        ReminderType=(Spinner) findViewById(R.id.ReminderType);

        ReminderSave=(Button)findViewById(R.id.ReminderSave);
        ReminderReset=(Button)findViewById(R.id.ReminderReset);

        FromDateBtn.setOnClickListener(this);
        ToDateBtn.setOnClickListener(this);

        ReminderFromDate.setCursorVisible(false);
        ReminderToDate.setCursorVisible(false);

        //Category
        category();

        ReminderSave.setOnClickListener
        (
            new View.OnClickListener()
            {
                 @Override
                 public void onClick(View v)
                 {
                     ReminderAmount.setBackgroundResource(R.drawable.noerror);
                     ReminderFromDate.setBackgroundResource(R.drawable.noerror);
                     ReminderToDate.setBackgroundResource(R.drawable.noerror);
                     ReminderType.setBackgroundResource(R.drawable.noerror);
                     int c=0;
                     if(ReminderAmount.getText().toString().isEmpty())
                     {
                         c++;
                         ReminderAmount.setBackgroundResource(R.drawable.error);
                         ReminderAmount.setError("Can't be blank");
                         ReminderAmount.requestFocus();
                     }
                     if(ReminderFromDate.getText().toString().isEmpty())
                     {
                         c++;
                         ReminderFromDate.setBackgroundResource(R.drawable.error);
                     }
                     if(ReminderToDate.getText().toString().isEmpty())
                     {
                         c++;
                         ReminderToDate.setBackgroundResource(R.drawable.error);
                     }

                     String date1 = ReminderFromDate.getText().toString();
                     String date2 = ReminderToDate.getText().toString();

                     if (date1.compareTo(date2) > 0)
                     {
                         c++;
                         ReminderFromDate.setBackgroundResource(R.drawable.error);
                         ReminderToDate.setBackgroundResource(R.drawable.error);
                     }
                     else if (date1.compareTo(date2) == 0)
                     {
                         c++;
                         ReminderFromDate.setBackgroundResource(R.drawable.error);
                         ReminderToDate.setBackgroundResource(R.drawable.error);
                     }
                     if(ReminderType.getSelectedItem() == null)
                     {
                         c++;
                         ReminderType.setBackgroundResource(R.drawable.error);
                     }

                     if(c==0)
                     {
                         if (ReminderDescription.getText().toString().isEmpty())
                         {
                             ReminderDescription.setText(" ");
                         }
                         boolean isInserted = mydb.insertReminder(ReminderAmount.getText().toString(), ReminderFromDate.getText().toString(), ReminderToDate.getText().toString(), ReminderDescription.getText().toString(), ReminderType.getSelectedItem().toString());
                         if (isInserted = true)
                         {
                             Toast.makeText(ReminderActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                         }
                         else
                         {
                             Toast.makeText(ReminderActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                         }
                         finish();
                         Intent i = new Intent(ReminderActivity.this, ActivityHome.class);
                         startActivity(i);
                     }
                 }
            }
        );

        ReminderReset.setOnClickListener
        (
             new View.OnClickListener()
             {
                  @Override
                  public void onClick(View v)
                  {
                      ReminderAmount.setText("");
                      ReminderFromDate.setText("");
                      ReminderToDate.setText("");
                      ReminderDescription.setText("");
                  }
             }
        );
    }

    public void onClick(View v)
    {

        if (v == FromDateBtn)
        {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener()
                    {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            ReminderFromDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (v == ToDateBtn)
        {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener()
                    {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            ReminderToDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    public void category()
    {
        ReminderType=(Spinner) findViewById(R.id.ReminderType);
        Cursor result = mydb.CategoryView();
        int count = result.getCount();
        int i=0;
        String[] category= new String[count];

        while(result.moveToNext())
        {
            category[i]= new String(result.getString(1));
            // Toast.makeText(IncomeActivity.this, category[i] , Toast.LENGTH_LONG).show();
            i++;
        }
        List<String> list = new ArrayList<String>();
        for(i=0;i<count;i++)
        {
            list.add(category[i]);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ReminderType.setAdapter(dataAdapter);
    }
}
