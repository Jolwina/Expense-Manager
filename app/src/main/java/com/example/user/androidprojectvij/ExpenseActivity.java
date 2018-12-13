package com.example.user.androidprojectvij;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.SyncStateContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class ExpenseActivity extends AppCompatActivity implements View.OnClickListener {

    private int mYear, mMonth, mDay, mHour, mMinute;
    EditText ExpenseDate, ExpenseTime, ExpenseAmount, ExpenseDescription;
    Button DateBtn, TimeBtn, ExpenseSave, ExpenseReset;
    Spinner ExpensePayment,ExpenseType;

    DataBaseHelper mydb;

    final int NOTIFICATION_ID = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        getSupportActionBar().hide();

        mydb = new DataBaseHelper(this);

        DateBtn=(Button)findViewById(R.id.DateBtn);
        TimeBtn=(Button)findViewById(R.id.TimeBtn);

        ExpenseDate=(EditText)findViewById(R.id.ExpenseDate);
        ExpenseTime=(EditText)findViewById(R.id.ExpenseTime);

        ExpenseAmount=(EditText)findViewById(R.id.ExpenseAmount);
        ExpenseDescription=(EditText)findViewById(R.id.ExpenseDescription);

        ExpensePayment=(Spinner) findViewById(R.id.ExpensePayment);
        ExpenseType=(Spinner) findViewById(R.id.ExpenseType);

        DateBtn.setOnClickListener(this);
        TimeBtn.setOnClickListener(this);

        ExpenseSave=(Button)findViewById(R.id.ExpenseSave);
        ExpenseReset=(Button)findViewById(R.id.ExpenseReset);

        ExpenseDate.setCursorVisible(false);
        ExpenseTime.setCursorVisible(false);

        //Category
        category();

        //Payment
        payment();

        ExpenseSave.setOnClickListener
        (
            new View.OnClickListener()
            {
                 @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                 @Override
                 public void onClick(View v)
                 {
                     ExpenseAmount.setBackgroundResource(R.drawable.noerror);
                     ExpenseDate.setBackgroundResource(R.drawable.noerror);
                     ExpenseTime.setBackgroundResource(R.drawable.noerror);
                     ExpensePayment.setBackgroundResource(R.drawable.noerror);
                     ExpenseType.setBackgroundResource(R.drawable.noerror);
                     int c=0;
                     if(ExpenseAmount.getText().toString().isEmpty())
                     {
                         c++;
                         ExpenseAmount.setBackgroundResource(R.drawable.error);
                         ExpenseAmount.setError("Can't be blank");
                         ExpenseAmount.requestFocus();
                     }
                     if(ExpenseDate.getText().toString().isEmpty())
                     {
                         c++;
                         ExpenseDate.setBackgroundResource(R.drawable.error);
                     }
                     if(ExpenseTime.getText().toString().isEmpty())
                     {
                         c++;
                         ExpenseTime.setBackgroundResource(R.drawable.error);
                     }
                     if(ExpensePayment.getSelectedItem() == null)
                     {
                         c++;
                         ExpensePayment.setBackgroundResource(R.drawable.error);
                     }
                     if(ExpenseType.getSelectedItem() == null)
                     {
                         c++;
                         ExpenseType.setBackgroundResource(R.drawable.error);
                     }

                     if(c==0)
                     {
                         if (ExpenseDescription.getText().toString().isEmpty())
                         {
                             ExpenseDescription.setText(" ");
                         }
                         boolean isInserted = mydb.insertExpense(ExpenseAmount.getText().toString(), ExpenseDate.getText().toString(), ExpenseTime.getText().toString(), ExpenseDescription.getText().toString(), ExpensePayment.getSelectedItem().toString(), ExpenseType.getSelectedItem().toString());

                         if (isInserted = true)
                         {
                             Toast.makeText(ExpenseActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                         }
                         else
                         {
                             Toast.makeText(ExpenseActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                         }

                         Cursor result = mydb.CategoryView();
                         int count = result.getCount();
                         int j=0;
                         String[] category = new String[count];
                         int[] amount = new int[count];

                         while(result.moveToNext())
                         {
                             category[j]= new String(result.getString(1));
                             //Toast.makeText(GraphActivity.this, category[i] , Toast.LENGTH_LONG).show();
                             j++;
                         }
                         for(j=0;j<count;j++)
                         {
                             result = mydb.GraphCategory(category[j]);
                             while(result.moveToNext())
                             {
                                 amount[j]= result.getInt(0);
                             }
                             //Toast.makeText(GraphActivity.this,"Category : "+ category[i] +", Amount : "+amount[i] , Toast.LENGTH_LONG).show();
                         }
                         Cursor result1 = null;
                         int k;
                         for(j=0;j<count;j++)
                         {
                             result1 = mydb.ReminderCategory(Integer.toString(amount[j]),category[j]);
                             if(result1.getCount() != 0)
                             {
                                 int amt=0;
                                 while(result1.moveToNext())
                                 {
                                     amt = result1.getInt(0);
                                 }
                                 String subject = "Budget for "+category[j]+" ₹ "+amt;
                                 String body = "Budget exceed with ₹ "+(amount[j]-amt);

                                 Notification notification = new NotificationCompat.Builder(ExpenseActivity.this, "channel01")
                                         .setSmallIcon(android.R.drawable.ic_dialog_info)
                                         .setContentTitle(subject)
                                         .setContentText(body)
                                         .setDefaults(Notification.DEFAULT_ALL)
                                         .setPriority(NotificationCompat.PRIORITY_HIGH)   // heads-up
                                         .build();

                                 NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ExpenseActivity.this);
                                 notificationManager.notify(0, notification);
                             }
                         }

                         finish();
                         Intent i = new Intent(ExpenseActivity.this, ActivityHome.class);
                         startActivity(i);
                     }
                 }
            }
        );

        ExpenseReset.setOnClickListener
        (
             new View.OnClickListener()
             {
                  @Override
                  public void onClick(View v)
                  {
                      ExpenseAmount.setText("");
                      ExpenseDate.setText("");
                      ExpenseTime.setText("");
                      ExpenseDescription.setText("");
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

                            ExpenseDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

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

                            ExpenseTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    public void category()
    {
        ExpenseType=(Spinner) findViewById(R.id.ExpenseType);
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
        ExpenseType.setAdapter(dataAdapter);
    }

    public void payment()
    {
        ExpensePayment=(Spinner) findViewById(R.id.ExpensePayment);
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
        ExpensePayment.setAdapter(dataAdapter);
    }

}
