package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Size;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.Calendar;
import java.util.List;

import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.app.AlertDialog;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    String[] countries = { "USD", "EUR", "RUB", "JPY" };
    private DatePickerDialog datePickerDialog;
    private Button dateButton, histButton;
    private TextView ans;
    private EditText amount;
    private Spinner spinner1;
    private Spinner spinner2;
    private List<String> history;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        initDatePicker();
        dateButton = findViewById(R.id.button2);
        dateButton.setText(getTodaysDate());
        ans = findViewById(R.id.answer);
        amount = findViewById(R.id.amount);
        histButton = findViewById(R.id.histButton);
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

    }

    public void onClick(View view)
    {
        String date = (String)dateButton.getText();
        String[] splittedDate = date.split(" ");
        //amount.getText() - amount
        if (splittedDate[0].length() < 2)
            splittedDate[0] = "0"+splittedDate[0];
        String ndate = splittedDate[0] + "/" + getMonthNumber(splittedDate[1]) + "/" + splittedDate[2];
        float w = currencyInfoGetter.getInfoByDate((String)spinner1.getSelectedItem(), (String)spinner2.getSelectedItem(), ndate)[0];
        float result = w * Float.parseFloat(amount.getText().toString());
        ans.setText(Float.toString(result));
        if (history.size() == 10){
            history.remove(0);
        }
        history.add(Float.toString(w));
    }

    public void openHistory(View view)
    {
        Intent intent = new Intent(".History");
        String text = new String();
        if (history.size() != 0){
            text = history.get(0);
            for (int i = 1; i<history.size(); i++){
                text = text + '\n' + history.get(i);
            }
        }
        startActivity(intent);
    }

    private String makeDateString(int day, int month, int year)
    {
        return day + " " + getMonthFormat(month) + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return null;
    }

    private String getMonthNumber(String month)
    {
        if(month.equals("JAN"))
            return "01";
        if(month.equals("FEB"))
            return "02";
        if(month.equals("MAR"))
            return "03";
        if(month.equals("APR"))
            return "";
        if(month.equals("MAY"))
            return "05";
        if(month.equals("JUN"))
            return "06";
        if(month.equals("JUL"))
            return "07";
        if(month.equals("AUG"))
            return "08";
        if(month.equals("SEP"))
            return "09";
        if(month.equals("OCT"))
            return "10";
        if(month.equals("NOV"))
            return "11";
        if(month.equals("DEC"))
            return "12";
        return null;
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}
