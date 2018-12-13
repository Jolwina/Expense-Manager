package com.example.user.androidprojectvij;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ViewCategory extends AppCompatActivity
{
    DataBaseHelper mydb;
    List<CategoryList> categoryList;
    ListView list;
    CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);
        getSupportActionBar().hide();

        list = (ListView) findViewById(R.id.CategoryView);
        categoryList = new ArrayList<>();

        mydb = new DataBaseHelper(this);

        ViewCategory();
    }

    private void ViewCategory()
    {
        Cursor cursor = mydb.CategoryView();
        if (cursor.moveToFirst())
        {
            do
            {
                categoryList.add(new CategoryList
                        (
                                cursor.getInt(0),
                                cursor.getString(1)
                        ));
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter = new CategoryAdapter(this, R.layout.category_single_item, categoryList, mydb);
        list.setAdapter(adapter);
    }
}
