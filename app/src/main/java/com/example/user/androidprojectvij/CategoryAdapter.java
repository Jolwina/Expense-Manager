package com.example.user.androidprojectvij;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<CategoryList>
{
    Context mCtx;
    int listLayoutRes;
    List<CategoryList> categoryList;
    DataBaseHelper mydb;

    public CategoryAdapter(Context mCtx, int listLayoutRes, List<CategoryList> categoryList, DataBaseHelper mydb)
    {
        super(mCtx, listLayoutRes, categoryList);
        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.categoryList = categoryList;
        this.mydb = mydb;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);

        final CategoryList categorylist = categoryList.get(position);

        TextView CategoryType = view.findViewById(R.id.CategorType);
        TextView id = view.findViewById(R.id.id);

        CategoryType.setText(categorylist.getCategoryType());
        id.setText(Integer.toString(categorylist.getId()));

        ImageView DeleteBtn = view.findViewById(R.id.DeleteBtn);
        ImageView EditBtn = view.findViewById(R.id.EditBtn);

        EditBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                updateCategory(categorylist);
            }
        });

        DeleteBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        String id = Integer.toString(categorylist.getId());
                        boolean result= mydb.CategoryDelete(id);
                        reload();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }

    private void updateCategory(final CategoryList categorylist)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.category_edit, null);
        builder.setView(view);


        final EditText type = view.findViewById(R.id.type);
        final TextView id = view.findViewById(R.id.id);

        type.setText(categorylist.getCategoryType());
        id.setText(Integer.toString(categorylist.getId()));

        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.update).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String name = type.getText().toString().trim();
                int id1 = Integer.parseInt(id.getText().toString());
                if (name.isEmpty()) {
                    type.setError("Can't be blank");
                    type.requestFocus();
                    return;
                }

                boolean isInserted = mydb.CategoryUpdate(id.getText().toString(), type.getText().toString());
                Toast.makeText(mCtx, "Category Updated", Toast.LENGTH_SHORT).show();
                reload();
                dialog.dismiss();
            }
        });
    }

    private void reload()
    {
        Cursor cursor = mydb.CategoryView();
        if (cursor.moveToFirst())
        {
            categoryList.clear();
            do
            {
                categoryList.add(new CategoryList(
                        cursor.getInt(0),
                        cursor.getString(1)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        notifyDataSetChanged();
    }
}