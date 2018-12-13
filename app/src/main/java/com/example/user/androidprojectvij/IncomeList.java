package com.example.user.androidprojectvij;

import java.sql.Time;
import java.util.Date;

public class IncomeList
{
    int id;
    Date date;
    Time time;
    Number amount;
    String income_description, payment_type, income_type;

    public IncomeList(int id, Number amount, Date date, Time time, String income_description, String payment_type, String income_type)
    {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.income_description = income_description;
        this.payment_type = payment_type;
        this.income_type = income_type;
    }

    public int getId()
    {
        return id;
    }
    public Number getAmount()
    {
        return amount;
    }
    public Date getDate()
    {
        return date;
    }
    public Time getTime()
    {
        return time;
    }
    public String getIncome_description()
    {
        return income_description;
    }
    public String getPayment_type()
    {
        return payment_type;
    }
    public String getIncome_type()
    {
        return income_type;
    }


}
