package com.example.user.androidprojectvij;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity
{
    private static String TAG = "GraphActivity";
    PieChart pieChart;

    DataBaseHelper mydb;

    public String[] category;
    public int[] amount;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        getSupportActionBar().hide();

        mydb = new DataBaseHelper(this);

        Cursor result = mydb.DistinctCategory();
        int count = result.getCount();
        int i=0;
        category = new String[count];
        amount = new int[count];

        while(result.moveToNext())
        {
            category[i]= new String(result.getString(0));
            //Toast.makeText(GraphActivity.this, category[i] , Toast.LENGTH_LONG).show();
            i++;
        }
        for(i=0;i<count;i++)
        {
           result = mydb.GraphCategory(category[i]);
           while(result.moveToNext())
           {
                amount[i]= result.getInt(0);
           }
           //Toast.makeText(GraphActivity.this,"Category : "+ category[i] +", Amount : "+amount[i] , Toast.LENGTH_LONG).show();
        }


        Log.d(TAG, "onCreate: starting to create chart");

        pieChart = (PieChart) findViewById(R.id.idPieChart);

        pieChart.setDescription("All Expenses");
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Expenses");
        pieChart.setCenterTextSize(10);

        addDataSet();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener()
        {
            @Override
            public void onValueSelected(Entry e, Highlight h)
            {
                Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());

                int pos1 = e.toString().indexOf("(sum): ");
                String amt = e.toString().substring(pos1 + 7);

                for(int i = 0; i < amount.length; i++)
                {
                    if(amount[i] == Float.parseFloat(amt))
                    {
                        pos1 = i;
                        break;
                    }
                }
                String cat = category[pos1];
                Toast.makeText(GraphActivity.this, "Type : " + cat + "\n" + "₹ : " + amt, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected()
            {

            }
        });
    }

    private void addDataSet()
    {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < amount.length; i++)
        {
            yEntrys.add(new PieEntry(amount[i] , i));
        }

        for(int i = 1; i < category.length; i++)
        {
            xEntrys.add(category[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Expense");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
