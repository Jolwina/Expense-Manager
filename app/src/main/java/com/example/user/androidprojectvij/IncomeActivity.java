package com.example.user.androidprojectvij;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

public class IncomeActivity extends AppCompatActivity implements View.OnClickListener {

    private int mYear, mMonth, mDay, mHour, mMinute;
    EditText IncomeDate, IncomeTime, IncomeAmount, IncomeDescription;
    Button DateBtn, TimeBtn, IncomeSave, IncomeReset;
    Spinner IncomePayment,IncomeType;
    DataBaseHelper mydb;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        getSupportActionBar().hide();

        mydb = new DataBaseHelper(this);

        DateBtn=(Button)findViewById(R.id.DateBtn);
        TimeBtn=(Button)findViewById(R.id.TimeBtn);

        IncomeDate=(EditText)findViewById(R.id.IncomeDate);
        IncomeTime=(EditText)findViewById(R.id.IncomeTime);

        IncomeAmount=(EditText)findViewById(R.id.IncomeAmount);
        IncomeDescription=(EditText)findViewById(R.id.IncomeDescription);

        IncomePayment=(Spinner) findViewById(R.id.ExpenseReset);
        IncomeType=(Spinner) findViewById(R.id.IncomeType);

        DateBtn.setOnClickListener(this);
        TimeBtn.setOnClickListener(this);

        IncomeSave=(Button)findViewById(R.id.IncomeSave);
        IncomeReset=(Button)findViewById(R.id.ReminderReset);

        IncomeDate.setCursorVisible(false);
        IncomeTime.setCursorVisible(false);

        //Category
        category();

        //Payment
        payment();

        IncomeSave.setOnClickListener
        (
            new View.OnClickListener()
            {
                  @Override
                  public void onClick(View v)
                  {
                      IncomeAmount.setBackgroundResource(R.drawable.noerror);
                      IncomeDate.setBackgroundResource(R.drawable.noerror);
                      IncomeTime.setBackgroundResource(R.drawable.noerror);
                      IncomePayment.setBackgroundResource(R.drawable.noerror);
                      IncomeType.setBackgroundResource(R.drawable.noerror);
                      int c=0;
                      if(IncomeAmount.getText().toString().isEmpty())
                      {
                          c++;
                          IncomeAmount.setBackgroundResource(R.drawable.error);
                          IncomeAmount.setError("Can't be blank");
                          IncomeAmount.requestFocus();
                      }
                      if(IncomeDate.getText().toString().isEmpty())
                      {
                          c++;
                          IncomeDate.setBackgroundResource(R.drawable.error);
                      }
                      if(IncomeTime.getText().toString().isEmpty())
                      {
                          c++;
                          IncomeTime.setBackgroundResource(R.drawable.error);
                      }
                      if(IncomePayment.getSelectedItem() == null)
                      {
                          c++;
                          IncomePayment.setBackgroundResource(R.drawable.error);
                      }
                      if(IncomeType.getSelectedItem() == null)
                      {
                          c++;
                          IncomeType.setBackgroundResource(R.drawable.error);
                      }

                      if(c==0)
                      {
                          if(IncomeDescription.getText().toString().isEmpty())
                          {
                              IncomeDescription.setText(" ");
                          }

                          boolean isInserted = mydb.insertIncome(IncomeAmount.getText().toString(), IncomeDate.getText().toString(), IncomeTime.getText().toString(), IncomeDescription.getText().toString(), IncomePayment.getSelectedItem().toString(), IncomeType.getSelectedItem().toString());

                          if (isInserted = true)
                          {
                              Toast.makeText(IncomeActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                          }
                          else
                          {
                              Toast.makeText(IncomeActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                          }
                          finish();
                          Intent i = new Intent(IncomeActivity.this, ActivityHome.class);
                          startActivity(i);

                      }
                  }
            }
        );

        IncomeReset.setOnClickListener
        (
             new View.OnClickListener()
             {
                 @Override
                 public void onClick(View v)
                 {
                     IncomeAmount.setText("");
                     IncomeDate.setText("");
                     IncomeTime.setText("");
                     IncomeDescription.setText("");
                 }
             }
        );
    }
    public void onClick(View v)
    {

        if (v == DateBtn)
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

                            IncomeDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == TimeBtn) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            IncomeTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    public void category()
    {
        IncomePayment=(Spinner) findViewById(R.id.ExpenseReset);
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
        IncomeType.setAdapter(dataAdapter);
    }

    public void payment()
    {
        IncomeType=(Spinner) findViewById(R.id.IncomeType);
        Cursor result = mydb.PaymentView();
        int count = result.getCount();
        String[] payment= new String[count];
        int i=0;
        while(result.moveToNext())
        {
            payment[i]= new String(result.getString(1));
            //Toast.makeText(IncomeActivity.this, payment[i] , Toast.LENGTH_LONG).show();
            i++;
        }
        List<String> list = new ArrayList<String>();
        for(i=0;i<count;i++)
        {
            list.add(payment[i]);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        IncomePayment.setAdapter(dataAdapter);
    }
}