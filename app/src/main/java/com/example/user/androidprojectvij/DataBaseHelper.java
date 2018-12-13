package com.example.user.androidprojectvij;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static javax.xml.xpath.XPathConstants.NUMBER;

public class DataBaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "ExpenseManager.db";
    public static final String TABLE_INCOME = "income";

    public static final String TABLE_EXPENSE = "expense";
    public static final String TABLE_REMINDER = "reminder";
    public static final String TABLE_CATEGORY = "category";
    public static final String TABLE_PAYMENT = "payment";

    public DataBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + TABLE_INCOME + "(id INTEGER PRIMARY KEY AUTOINCREMENT, amount NUMBER, income_date DATE, income_time TIME, income_description TEXT, payment_type TEXT, income_type TEXT)");
        db.execSQL("create table " + TABLE_EXPENSE + "(id INTEGER PRIMARY KEY AUTOINCREMENT, amount NUMBER, expense_date DATE, expense_time TIME, expense_description TEXT, payment_type TEXT, expense_type TEXT)");
        db.execSQL("create table " + TABLE_REMINDER + "(id INTEGER PRIMARY KEY AUTOINCREMENT, amount NUMBER, from_date DATE, to_date DATE, reminder_description TEXT, reminder_type TEXT)");
        db.execSQL("create table " + TABLE_CATEGORY + "(id INTEGER PRIMARY KEY AUTOINCREMENT, category_type TEXT)");
        db.execSQL("create table " + TABLE_PAYMENT + "(id INTEGER PRIMARY KEY AUTOINCREMENT, payment_type TEXT)");

        ContentValues CategoryValues = new ContentValues();
        CategoryValues.put("category_type","Transport");
        db.insert(TABLE_CATEGORY, null, CategoryValues);
        CategoryValues.put("category_type","Rent");
        db.insert(TABLE_CATEGORY, null, CategoryValues);
        CategoryValues.put("category_type","Salary");
        db.insert(TABLE_CATEGORY, null, CategoryValues);

        ContentValues PaymentValues = new ContentValues();
        PaymentValues.put("payment_type","Cash");
        db.insert(TABLE_PAYMENT, null, PaymentValues);
        PaymentValues.put("payment_type","Cheque");
        db.insert(TABLE_PAYMENT, null, PaymentValues);
        PaymentValues.put("payment_type","Debit Card");
        db.insert(TABLE_PAYMENT, null, PaymentValues);
        PaymentValues.put("payment_type","Credit Card");
        db.insert(TABLE_PAYMENT, null, PaymentValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_INCOME);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_REMINDER);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_PAYMENT);
        onCreate(db);
    }

    public boolean insertIncome(String amount, String income_date, String income_time, String income_description, String payment_type, String income_type)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("amount",amount);
        contentValues.put("income_date",income_date);
        contentValues.put("income_time",income_time);
        contentValues.put("income_description",income_description);
        contentValues.put("payment_type",payment_type);
        contentValues.put("income_type",income_type);

        long result = db.insert(TABLE_INCOME, null, contentValues);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean insertExpense(String amount, String expense_date, String expense_time, String expense_description, String payment_type, String expense_type)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("amount",amount);
        contentValues.put("expense_date",expense_date);
        contentValues.put("expense_time",expense_time);
        contentValues.put("expense_description",expense_description);
        contentValues.put("payment_type",payment_type);
        contentValues.put("expense_type",expense_type);

        long result = db.insert(TABLE_EXPENSE, null, contentValues);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean insertReminder(String amount, String from_date, String to_date, String reminder_description, String reminder_type)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("amount",amount);
        contentValues.put("from_date",from_date);
        contentValues.put("to_date",to_date);
        contentValues.put("reminder_description",reminder_description);
        contentValues.put("reminder_type",reminder_type);

        long result = db.insert(TABLE_REMINDER, null, contentValues);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean insertCategory(String category_type)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("category_type",category_type);

        long result = db.insert(TABLE_CATEGORY, null, contentValues);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean insertPaymentMode(String payment_type)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("payment_type",payment_type);

        long result = db.insert(TABLE_PAYMENT, null, contentValues);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Cursor CategoryView()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+TABLE_CATEGORY,null);
        return result;
    }

    public boolean CategoryUpdate(String id,String category_type)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("category_type",category_type);
        db.update(TABLE_CATEGORY, contentValues, "id = ?",new String[] { id });
        return true;
    }

    public boolean CategoryDelete(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY, "id = ?",new String[] {id});
        return true;
    }

    public Cursor PaymentView()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+TABLE_PAYMENT,null);
        return result;
    }

    public boolean PaymentUpdate(String id,String payment_type)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("payment_type",payment_type);
        db.update(TABLE_PAYMENT, contentValues, "id = ?",new String[] { id });
        return true;
    }

    public boolean PaymentDelete(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PAYMENT, "id = ?",new String[] {id});
        return true;
    }

    public Cursor GraphCategory(String type)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select SUM(amount) from "+TABLE_EXPENSE+" where expense_type = ?",new String[] {type});
        return result;
    }
    public Cursor DistinctCategory()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select DISTINCT(expense_type) from "+TABLE_EXPENSE,null);
        return result;
    }

    public Cursor ReminderCategory(String cost, String type)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select amount from "+TABLE_REMINDER+" where reminder_type = ? AND amount <= ?",new String[] {type, cost});
        return result;
    }

    public Cursor viewIncome()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+TABLE_INCOME+" ORDER BY income_date DESC",null);
        return result;
    }

    public Cursor viewExpense()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+TABLE_EXPENSE,null);
        return result;
    }

    public Cursor viewReminder()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+TABLE_REMINDER,null);
        return result;
    }

    public Cursor TotalExpense()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select SUM(amount) from "+TABLE_EXPENSE,null);
        return result;
    }
    public Cursor LastExpense()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT amount FROM "+TABLE_EXPENSE+" ORDER BY  expense_date DESC", new String[] {});
        return result;
    }
    public Cursor TotalIncome()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select SUM(amount) from "+TABLE_INCOME,null);
        return result;
    }
    public Cursor LastIncome()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT amount FROM "+TABLE_INCOME+" ORDER BY  income_date DESC", new String[] {});
        return result;
    }

}