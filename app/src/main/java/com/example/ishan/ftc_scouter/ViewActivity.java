package com.example.ishan.ftc_scouter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ishan on 7/09/2015.
 */
public class ViewActivity extends Activity {
    SQLiteDatabase database;
    Button submit;
    LinearLayout layout;
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);


        database = openOrCreateDatabase("scouter", MODE_PRIVATE, null);
        layout = (LinearLayout) findViewById(R.id.layout);
        submit = (Button) findViewById(R.id.btn_query);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get all inputs prepare sql
                //TODO crazy sql needs to be done!
                /*
                * SELECT
                *, COUNT(*) AS CountOf
                FROM teamdata
                GROUP BY  team_id
                HAVING COUNT(*)>1
                * */

                /*
                *
                * select team_id from (select *, COUNT(*) AS CountOf
                * from teamdata where (label = 'Hill' and arg1='2' or label = 'Cool?' and arg1 = 'true') group BY  team_id
                HAVING COUNT(*)>0) order by CountOf desc;

                THIS IS NO JOKE GUYS!!
                //todo THIS IS NO JOKE PLEASE UNDERSTAND THE IMPORTANCE OF THAT QUERY!
                //todo IN FUTURE VERSION PROVIDE USERS A SPACE TO EXECUTE THEIR QUERIES!!
                //IT ALSO CHECKS HOW MANY CREITERIA ARE FULFILLED!!


                *
                * */

                String sql;
                sql = "select team_id from (select *, COUNT(*) AS CountOf\n" +
                        "                 from teamdata where ";
                int counter = layout.getChildCount();
                for(int i = 0; i < counter; i++)
                {

                    if(layout.getChildAt(i) instanceof TextView)
                    {
                        String label = ((TextView)layout.getChildAt(i)).getText().toString();
                        if(((Spinner)layout.getChildAt(i+1)).getAdapter().getCount()==2) {
                            String value = ((Spinner) layout.getChildAt(i + 1)).getSelectedItem().toString();
                            if(value.equals("Checked"))
                            {
                                //sql += label + " = " + "true and ";
                                sql += "(label = '"+label + "' and arg1 = 'true') or ";//TODO catch up here!!
                            }
                            else
                            {
                                sql += " (label = '"+ label + "' and arg1 = 'false') or ";
                            }
                            //Log.d("Checckvalues", label + " " + value + " this is checkbox!");
                        }else{
                            String value = ((Spinner) layout.getChildAt(i + 1)).getSelectedItem().toString();
                            sql += "(label = '"+label + "' and arg1 =  '" + value + "') or ";
                            //Log.d("Checckvalues", label + " " + value + " this is radio!!!");
                        }

                    }

                }
                if(sql.substring(sql.length()-3,sql.length()).contains("or"))
                {
                    sql = sql.substring(0,sql.length()-3);
                }
                sql += "group BY  team_id\n" +
                        "                HAVING COUNT(*)>0) order by CountOf desc limit 6;";

                Log.d("sqlitede",sql);

                //alright..
                try{
                Cursor c = database.rawQuery(sql,null);
                    String teams = "";
                if(c.moveToFirst())
                {
                    do{
                        Log.d("sqlitede",c.getString(0));
                        teams += c.getString(0)+"\n";
                    }while(c.moveToNext());

                    alert(teams);

                }
                else
                {
                    alert("No match!");
                    Log.d("sqlitede","no result");
                }}catch (Exception e){
                Log.d("sqlitede",e.toString() + "\n" );
            }}
        });


        //show all options

        Cursor c = database.rawQuery("SELECT * FROM inputs;", null);

        //int limit = Integer.parseInt(c.getString(0));


        if(c.moveToFirst()) {
            do {
                /*TextView tv = new TextView(this);
                tv.setText(c.getString(2));
                layout.addView(tv);*/
                if (c.getString(1).equals("cb")) {
                    //Label checked or not?
                    TextView temp = new TextView(getApplicationContext());
                    temp.setText(c.getString(2));
                    Spinner spinner = new Spinner(getApplicationContext());
                    layout.addView(temp);
                    layout.addView(spinner);
                    final List<String> list = new ArrayList<String>();
                    list.add("Checked");
                    list.add("Not checked");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(dataAdapter);



                } else if (c.getString(1).equals("rb")) {

                    TextView temp = new TextView(getApplicationContext());
                    temp.setText(c.getString(2));
                    Spinner spinner = new Spinner(getApplicationContext());
                    layout.addView(temp);
                    layout.addView(spinner);
                    final List<String> list = new ArrayList<String>();
                    list.add(c.getString(3));
                    list.add(c.getString(4));
                    list.add(c.getString(5));
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(dataAdapter);


                    //Log.d("gg","g");

                } else
                {
                    Log.d("gg","g");
                }

                /*Space space = new Space(getApplicationContext());
                space.setMinimumHeight(1);
                layout.addView(space);*/

            } while (c.moveToNext());


        //event handler

    }
}
    private void alert(final String stringt)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(!isFinishing()){
                    new AlertDialog.Builder(ViewActivity.this)
                            .setTitle("Teams!!")
                            .setMessage(stringt)
                    .setCancelable(true)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // whatever...
                                                /*Intent intent = new Intent(CustomizeActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();*/

                                }
                            }).create().show();
                }
            }
        });
    }
}
