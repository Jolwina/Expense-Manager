package com.example.user.androidprojectvij;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ViewPayment extends AppCompatActivity
{
    DataBaseHelper mydb;
    List<PaymentList> paymentList;
    ListView list;
    PaymentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_payment);
        getSupportActionBar().hide();

        list = (ListView) findViewById(R.id.PaymentView);
        paymentList = new ArrayList<>();

        mydb = new DataBaseHelper(this);

        ViewPayment();
    }

    private void ViewPayment()
    {
        Cursor cursor = mydb.PaymentView();
        if (cursor.moveToFirst())
        {
            do
            {
                paymentList.add(new PaymentList
                        (
                                cursor.getInt(0),
                                cursor.getString(1)
                        ));
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter = new PaymentAdapter(this, R.layout.payment_single_item, paymentList, mydb);

        list.setAdapter(adapter);
    }
}
