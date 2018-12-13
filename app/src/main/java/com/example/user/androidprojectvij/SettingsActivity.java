package com.example.user.androidprojectvij;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity
{
    TextView PaymentView, CategoryView, category_type, payment_type;
    Button CategoryBtn, PaymentBtn;
    DataBaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();

        CategoryView=(TextView) findViewById(R.id.CategoryView);
        PaymentView=(TextView)findViewById(R.id.PaymentView);
        category_type=(TextView)findViewById(R.id.category_type);
        payment_type=(TextView)findViewById(R.id.payment_type);

        CategoryBtn=(Button) findViewById(R.id.CategoryBtn);
        PaymentBtn=(Button) findViewById(R.id.PaymentBtn);

        mydb = new DataBaseHelper(this);

        CategoryBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.insert_category, null);
                final EditText category_type = (EditText) mView.findViewById(R.id.category_type);
                Button add = (Button) mView.findViewById(R.id.add);
                Button cancel = (Button) mView.findViewById(R.id.cancel);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                add.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        String name = category_type.getText().toString().trim();
                        if (name.isEmpty())
                        {
                            category_type.setError("Can't be blank");
                            category_type.requestFocus();
                            return;
                        }
                        boolean isInserted = mydb.insertCategory(category_type.getText().toString());

                        if (isInserted = true)
                        {
                            Toast.makeText(SettingsActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(SettingsActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                        }

                        dialog.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                    }
                });
            }
        });

        PaymentBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.insert_payment_mode, null);
                final EditText payment_type = (EditText) mView.findViewById(R.id.payment_type);
                Button add = (Button) mView.findViewById(R.id.add);
                Button cancel = (Button) mView.findViewById(R.id.cancel);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                add.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        String name = payment_type.getText().toString().trim();
                        if (name.isEmpty())
                        {
                            payment_type.setError("Can't be blank");
                            payment_type.requestFocus();
                            return;
                        }
                        boolean isInserted = mydb.insertPaymentMode(payment_type.getText().toString());

                        if (isInserted = true)
                        {
                            Toast.makeText(SettingsActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(SettingsActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                        }

                        dialog.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                    }
                });
            }
        });

        PaymentView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent j = new Intent(SettingsActivity.this, ViewPayment.class);
                startActivity(j);
            }
        });
        CategoryView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(SettingsActivity.this, ViewCategory.class);
                startActivity(i);
            }
        });
    }
}