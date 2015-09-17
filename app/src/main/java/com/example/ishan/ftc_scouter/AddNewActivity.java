package com.example.ishan.ftc_scouter;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class AddNewActivity extends ActionBarActivity {

    LinearLayout layout;
    SQLiteDatabase database;
    Spinner spinner;
    Button button;
    int ind = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        database = openOrCreateDatabase("scouter", MODE_PRIVATE, null);

        layout = (LinearLayout) findViewById(R.id.layout_actual_business);
        spinner = (Spinner) findViewById(R.id.spinner);

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ind == 0)
                {

                    //Mint !!
                    //get two inputs
                    String a,b;
                    EditText temp = (EditText)layout.getChildAt(1);
                    a = temp.getText().toString();
                    temp = (EditText)layout.getChildAt(3);
                    b = temp.getText().toString();

                    database.execSQL(String.format("insert into `inputs`(type,label,arg1,arg2,arg3,arg4)" +
                            " values ('cb','%s','%s','','','');",a,b));
                    Intent intent = new Intent(AddNewActivity.this, CustomizeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(ind==1)
                {

                    String a,b,c,d;
                    EditText temp = (EditText)layout.getChildAt(1);
                    a = temp.getText().toString();
                    temp = (EditText)layout.getChildAt(3);
                    b = temp.getText().toString();
                    temp = (EditText)layout.getChildAt(5);
                    c = temp.getText().toString();
                    temp = (EditText)layout.getChildAt(7);
                    d = temp.getText().toString();

                    database.execSQL(String.format("insert into `inputs`" +
                            "(type,label,arg1,arg2,arg3,arg4) " +
                            "values ('rb','%s','%s','%s','%s','');",a,b,c,d));

                    Intent intent = new Intent(AddNewActivity.this, CustomizeActivity.class);
                    startActivity(intent);
                    finish();

                }
                else if(ind==2)
                {

                    String a;
                    EditText temp = (EditText)layout.getChildAt(1);
                    a = temp.getText().toString();
                    /*temp = (EditText)layout.getChildAt(3);
                    b = temp.getText().toString();*/

                    database.execSQL(String.format("insert into `inputs`(type,label,arg1,arg2,arg3,arg4)" +
                            " values ('tb','%s','','','','');",a));
                    Intent intent = new Intent(AddNewActivity.this, CustomizeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        final List<String> list = new ArrayList<String>();
        list.add("Checkbox");
        list.add("Radio Buttons");
        list.add("Textbox");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
//        spinner.setPrompt("Select type of input.");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                Log.e("klkl", "klkl");
                int index = arg0.getSelectedItemPosition();
                Toast.makeText(getBaseContext(),
                        "You have selected item : " + list.get(index),
                        Toast.LENGTH_SHORT).show();
                if(index == 0)
                {
                    //clear all before
                    /*for(int i = 2; i < layout.getChildCount(); i++)
                    {
                        layout.removeViewAt(i);
                    }*/

                    ind = 0;
                    layout.removeAllViews();
                    //checkbox
                    for(int i = 0; i < 2; i++)
                    {
                        TextView temp = new TextView(getApplicationContext());
                        if(i==0)
                            temp.setText("Set your title");
                        else
                            temp.setText("Checkbox label");

                        EditText edit = new EditText(getApplicationContext());
                        edit.setTextColor(Color.BLUE);
                        layout.addView(temp);
                        layout.addView(edit);
                    }

                }
                else if(index == 1)
                {
                    ind = 1;
                    //radio buttons.
                    /*for(int i = 2; i < layout.getChildCount(); i++)
                    {
                        layout.removeViewAt(i);

                    }*/
                    layout.removeAllViews();
                    for(int i = 0; i < 4; i++)
                    {
                        TextView temp = new TextView(getApplicationContext());
                        if(i==0)
                            temp.setText("Set your title");
                        else
                            temp.setText((i)+" Option");

                        EditText edit = new EditText(getApplicationContext());
                        edit.setTextColor(Color.BLUE);
                        layout.addView(temp);
                        layout.addView(edit);
                    }
                }
                else if(index == 2)
                {

                    ind = 2;
                    layout.removeAllViews();
                    for(int i = 0; i < 1; i++)
                    {
                        TextView temp = new TextView(getApplicationContext());
                        if(i==0)
                            temp.setText("Set your title");
                        else
                            temp.setText((i+1)+" Option");

                        EditText edit = new EditText(getApplicationContext());
                        edit.setTextColor(Color.BLUE);
                        layout.addView(temp);
                        layout.addView(edit);
                    }

                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                Log.e("klkl", "klkl");

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
