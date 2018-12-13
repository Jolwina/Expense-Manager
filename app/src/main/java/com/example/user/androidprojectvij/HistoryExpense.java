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


public class HistoryExpense extends AppCompatActivity {

    DataBaseHelper mydb;
    private TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_expense);
        getSupportActionBar().hide();


        mydb = new DataBaseHelper(this);
        Cursor c = mydb.viewExpense();
        tableLayout = (TableLayout)findViewById(R.id.tabla_cuerpo);

        while (c.moveToNext()){
            View tableRow = LayoutInflater.from(getApplicationContext()).inflate(R.layout.table_item,null,false);
            TextView history_expense_category  = (TextView) tableRow.findViewById(R.id.c1);
            TextView history_expense_date  = (TextView) tableRow.findViewById(R.id.d1);
            TextView history_expense_amount  = (TextView) tableRow.findViewById(R.id.a1);


            int column_expense_type = c.getColumnIndex("expense_type"); //Get the index of the column from your table.
            String column_expense_description_value = c.getString(column_expense_type); //Get the value from the column from the current record.

            int column_expense_date = c.getColumnIndex("expense_date"); //Get the index of the column from your table.
            String column_expense_date_value = c.getString(column_expense_date); //Get the value from the column from the current record.

            int column_expense_amount = c.getColumnIndex("amount"); //Get the index of the column from your table.
            String column_expense_amount_value = c.getString(column_expense_amount); //Get the value from the column from the current record.


            history_expense_category.setText(column_expense_description_value);
            history_expense_date.setText(column_expense_date_value);
            history_expense_amount.setText(column_expense_amount_value);
            tableLayout.addView(tableRow);
        }
    }
}
