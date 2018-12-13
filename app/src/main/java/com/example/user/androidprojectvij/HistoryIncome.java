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


public class HistoryIncome extends AppCompatActivity
{
    DataBaseHelper mydb;
    private TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_income);
        getSupportActionBar().hide();

        mydb = new DataBaseHelper(this);
        Cursor c = mydb.viewIncome();
        //setContentView(R.layout.activity_history_income);
        tableLayout = (TableLayout)findViewById(R.id.tabla_cuerpo);

        while (c.moveToNext())
        {
            View tableRow = LayoutInflater.from(this).inflate(R.layout.table_item,null,false);
            TextView history_income_category  = (TextView) tableRow.findViewById(R.id.c1);
            TextView history_income_date  = (TextView) tableRow.findViewById(R.id.d1);
            TextView history_income_amount  = (TextView) tableRow.findViewById(R.id.a1);


            int column_income_type = c.getColumnIndex("income_type"); //Get the index of the column from your table.
            String column_income_type_value = c.getString(column_income_type); //Get the value from the column from the current record.

            int column_income_date = c.getColumnIndex("income_date"); //Get the index of the column from your table.
            String column_income_date_value = c.getString(column_income_date); //Get the value from the column from the current record.

            int column_income_amount = c.getColumnIndex("amount"); //Get the index of the column from your table.
            String column_income_amount_value = c.getString(column_income_amount); //Get the value from the column from the current record.


            history_income_category.setText(column_income_type_value);
            history_income_date.setText(column_income_date_value);
            history_income_amount.setText(column_income_amount_value);
            tableLayout.addView(tableRow);
        }
    }
}
