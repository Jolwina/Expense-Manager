package com.example.user.androidprojectvij;

public class PaymentList
{
    int id;
    String PaymentType;

    public PaymentList(int id, String PaymentType)
    {
        this.id = id;
        this.PaymentType = PaymentType;
    }

    public int getId()
    {
        return id;
    }

    public String getPaymentType()
    {
        return PaymentType;
    }
}