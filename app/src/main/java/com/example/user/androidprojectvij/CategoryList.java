package com.example.user.androidprojectvij;

public class CategoryList
{
    int id;
    String CategoryType;

    public CategoryList(int id, String CategoryType)
    {
        this.id = id;
        this.CategoryType = CategoryType;
    }

    public int getId()
    {
        return id;
    }

    public String getCategoryType()
    {
        return CategoryType;
    }
}