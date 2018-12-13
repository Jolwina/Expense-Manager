package com.example.user.androidprojectvij;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {

    DataBaseHelper mydb;
    private TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().hide();



    }
    public void goIncome(View view)
    {
        Intent intent = new Intent(HistoryActivity.this, HistoryIncome.class);
        startActivity(intent);
    }

    public void goExpense(View view)
    {
        Intent intent = new Intent(HistoryActivity.this, HistoryExpense.class);
        startActivity(intent);
    }

    public void goReminder(View view)
    {
        Intent intent = new Intent(HistoryActivity.this, HistoryReminder.class);
        startActivity(intent);
    }

}
