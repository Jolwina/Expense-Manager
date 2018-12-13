package com.example.user.androidprojectvij;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class ActivityHome extends AppCompatActivity
{
    int index=0;
    String[] Type = {"Last Income ₹", "Last Expense ₹", "Total Income ₹", "Total Expense ₹", "Balance ₹"};
    int[] Amount = {0,0,0,0,0};
    TextView text_view;
    Timer timer;
    DataBaseHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        mydb = new DataBaseHelper(this);

        Cursor result1 = mydb.TotalExpense();
        if(result1 != null)
        {
            while(result1.moveToNext())
            {
                Amount[3] = result1.getInt(0);
            }
        }

        Cursor result2 = mydb.TotalIncome();
        if(result2 != null)
        {
            while(result2.moveToNext())
            {
                Amount[2] = result2.getInt(0);
            }
        }

        Amount[4] = Amount[2] - Amount[3];

        Cursor result3 = mydb.LastIncome();
        if(result3 != null)
        {
            while(result3.moveToNext())
            {
                Amount[0] = result3.getInt(0);

            }
        }

        Cursor result4 = mydb.LastExpense();
        if(result4 != null)
        {
            while(result4.moveToNext())
            {
                Amount[1] = result4.getInt(0);

            }
        }

        final Handler mHandler = new Handler();

        text_view = (TextView)findViewById(R.id.text_view);

        text_view.setText(Type[4]+" "+Amount[4]);

        final Runnable start = new Runnable()
        {
            public void run()
            {
                text_view.setText(Type[index%Type.length]+" "+Amount[index%Amount.length]);

                index++;

                Animation fadein = AnimationUtils.loadAnimation(ActivityHome.this, R.anim.fadein);

                text_view.startAnimation(fadein);
            }
        };
        int delay = 4000;

        int period = 4000;

        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask()
        {

            public void run()
            {

                mHandler.post(start);

            }

        }, delay, period);


        TextView textviewHistory = (TextView) findViewById(R.id.historyTextId);
        textviewHistory.bringToFront();
        textviewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntentHistory = new Intent(ActivityHome.this, HistoryActivity.class);
                startActivity(myIntentHistory);
            }
        });

        TextView textviewSettings = (TextView) findViewById(R.id.settingsTextId);
        textviewSettings.bringToFront();
        textviewSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntentSetting = new Intent(ActivityHome.this, SettingsActivity.class);
                startActivity(myIntentSetting);
            }
        });

        TextView textviewIncome = (TextView) findViewById(R.id.incomeTextId);
        textviewIncome.bringToFront();
        textviewIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntentIncome = new Intent(ActivityHome.this, IncomeActivity.class);
                startActivity(myIntentIncome);
            }
        });

        TextView textviewProfile = (TextView) findViewById(R.id.graphTextId);
        textviewProfile.bringToFront();
        textviewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntentProfile = new Intent(ActivityHome.this, GraphActivity.class);
                startActivity(myIntentProfile);
            }
        });

        TextView textviewReminder = (TextView) findViewById(R.id.reminderTextId);
        textviewReminder.bringToFront();
        textviewReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntentReminder = new Intent(ActivityHome.this, ReminderActivity.class);
                startActivity(myIntentReminder);
            }
        });

        TextView textviewExpense = (TextView) findViewById(R.id.expenseTextId);
        textviewExpense.bringToFront();
        textviewExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntentExpense = new Intent(ActivityHome.this, ExpenseActivity.class);
                startActivity(myIntentExpense);
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(ActivityHome.this)
                .setMessage("Are you sure you want to close Expense Manager?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finish();
                        System.exit(0);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


}