package com.example.user.androidprojectvij;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

public class HistoryReminder extends AppCompatActivity
{

    DataBaseHelper mydb;
    private TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_reminder);
        getSupportActionBar().hide();

        mydb = new DataBaseHelper(this);
        Cursor c = mydb.viewReminder();
        //setContentView(R.layout.activity_history_income);
        tableLayout = (TableLayout) findViewById(R.id.tabla_cuerpo);

        while (c.moveToNext()) {
            View tableRow = LayoutInflater.from(this).inflate(R.layout.table_item, null, false);
            TextView history_reminder_category = (TextView) tableRow.findViewById(R.id.c1);
            TextView history_reminder_date = (TextView) tableRow.findViewById(R.id.d1);
            TextView history_reminder_amount = (TextView) tableRow.findViewById(R.id.a1);


            int column_reminder_type = c.getColumnIndex("reminder_type"); //Get the index of the column from your table.
            String column_income_type_value = c.getString(column_reminder_type); //Get the value from the column from the current record.

            int column_to_date = c.getColumnIndex("to_date"); //Get the index of the column from your table.
            String column_income_date_value = c.getString(column_to_date); //Get the value from the column from the current record.

            int column_income_amount = c.getColumnIndex("amount"); //Get the index of the column from your table.
            String column_income_amount_value = c.getString(column_income_amount); //Get the value from the column from the current record.


            history_reminder_category.setText(column_income_type_value);
            history_reminder_date.setText(column_income_date_value);
            history_reminder_amount.setText(column_income_amount_value);
            tableLayout.addView(tableRow);
        }
    }
}
