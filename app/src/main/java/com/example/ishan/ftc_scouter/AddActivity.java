package com.example.ishan.ftc_scouter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;



import java.util.LinkedList;

/**
 * Created by ishan on 7/09/2015.
 */
//TODO prevent the keyboard from being displayed!
public class AddActivity extends Activity {
    LinearLayout layout;

    SQLiteDatabase database;
    Button submit;
    EditText teamNum;
    EditText teamName;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        layout = (LinearLayout) findViewById(R.id.layout);

        submit = (Button) findViewById(R.id.btn_add_team_dat);

        teamNum = (EditText) findViewById(R.id.txt_number);
        teamName = (EditText) findViewById(R.id.txt_team_name);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //before inserting check very important thing!!
                //does the team number already exist?

                Log.d("Enge","entered");
                int teamNumb = Integer.parseInt(teamNum.getText().toString());
                try{
                Cursor c = database.rawQuery(String.format("select * from teams where id = %s",teamNumb), null);
                if(c.moveToFirst()) {
                    Log.d("Enge","found a match mate");

                   alert(teamNumb);

                    /*Intent newActivity1;
                    newActivity1 = new Intent(view.getContext(), EditTeamData.class);
                    newActivity1.putExtra("index", teamNumb);
                    startActivity(newActivity1);*/
                    /*int a = Integer.parseInt(c.getString(0));
                    if(a == Integer.parseInt(teamNum.getText().toString()))
                    {

                        Log.d("TADDFD","Team already exists in the database, do you wish to edit??");


                    }*/
                }
                else{




                    String name = teamName.getText().toString();
                    database.execSQL(String.format("insert into `teams` (id,name) values ('%d','%s');",teamNumb,name));
                int max = layout.getChildCount();
                int i = 0;
                while(i < max)
                {

                    if(layout.getChildAt(i) instanceof CheckBox)
                    {
                        //-1 = its ist its label
                        String a;
                        TextView temp = (TextView) layout.getChildAt(i-1);
                        a = temp.getText().toString();
                        boolean checked = ((CheckBox) layout.getChildAt(i)).isChecked();
                        database.execSQL(String.format("insert into `teamdata`(type,label,arg1,arg2,arg3,arg4,team_id) values ('cb','%s','%b','','','','%d');",a,checked,teamNumb));
                        Log.d("ZDKDF",i+"found checkbox!" + a + checked);

                    }
                    if(layout.getChildAt(i) instanceof RadioGroup)
                    {
                        //get which one is checked mate??
                        RadioGroup temp = (RadioGroup) layout.getChildAt(i);
                        int radioButtonID = temp.getCheckedRadioButtonId();
                        View radioButton = temp.findViewById(radioButtonID);
                        int index = temp.indexOfChild(radioButton)+1;

                        String a;
                        a = ((TextView)layout.getChildAt(i-1)).getText().toString();

                        database.execSQL(String.format("insert into `teamdata`(type,label,arg1,arg2,arg3,arg4,team_id) values ('rb','%s','%d','','','','%d');",a,index,teamNumb));
                        //similar story
                        Log.d("ZDKDF",i+"found radiogroup!"+index);
                    }
                    if(layout.getChildAt(i) instanceof EditText)
                    {
                        String a,b;
                        a = ((EditText)layout.getChildAt(i)).getText().toString();

                        b = ((TextView)layout.getChildAt(i-1)).getText().toString();
                        database.execSQL(String.format("insert into `teamdata`(type,label,arg1,arg2,arg3,arg4,team_id) values ('tb','%s','%s','','','','%d');",b,a,teamNumb));
                        Log.d("ZDKDF",i+"found textbox!");
                    }
                    //TODO do not FORGET the incremnt,
                    //I am normally used to for loops but for ease of readibility I have a while
                    //loop to iterate through the list.
                    i++;

                }
            }
            }catch(Exception e){
                    Log.d("seriousdebugging",e.toString()+"magic did not happen");
                }
            }
            {

            }
        });

        database = openOrCreateDatabase("scouter", MODE_PRIVATE, null);

        //TODO this is stubbed

        Cursor c = database.rawQuery("SELECT * FROM inputs;", null);

        //int limit = Integer.parseInt(c.getString(0));


        if(c.moveToFirst()) {
            do {
                TextView tv = new TextView(this);
                tv.setText(c.getString(2));
                layout.addView(tv);
                if (c.getString(1).equals("cb")) {
//                Log.d("DBGZ", "Went in the if statement");
                    CheckBox checkBox = new CheckBox(getApplicationContext());
                    checkBox.setText(c.getString(3));
                    checkBox.setTextColor(Color.BLUE);
                    layout.addView(checkBox);
                } else if (c.getString(1).equals("rb")) {
                    RadioGroup group = new RadioGroup(getApplicationContext());
                    RadioButton r_1 = new RadioButton(getApplicationContext());
                    RadioButton r_2 = new RadioButton(getApplicationContext());
                    RadioButton r_3 = new RadioButton(getApplicationContext());
                    r_1.setText(c.getString(3));
                    r_1.setTextColor(Color.BLUE);
                    r_2.setText(c.getString(4));
                    r_2.setTextColor(Color.BLUE);
                    r_3.setText(c.getString(5));
                    r_3.setTextColor(Color.BLUE);
                    group.addView(r_1);
                    group.addView(r_2);
                    group.addView(r_3);

                    layout.addView(group);
                } else if (c.getString(1).equals("tb")) {
                    EditText text = new EditText(getApplicationContext());
//                    text.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    text.setMaxLines(5);
                    text.setTextColor(Color.BLUE);
                    layout.addView(text);
                }
            } while (c.moveToNext());
        }

        /*if (c.moveToFirst()) {
            *//*Log.d("DBZ", c.getString(1));
            Log.d("DBZ", c.getString(2));*//*
            TextView tv = new TextView(this);
            tv.setText(c.getString(2));
            layout.addView(tv);
            if(c.getString(1).equals("cb"))
            {
                Log.d("DBGZ", "Went in the if statement");
                CheckBox checkBox = new CheckBox(getApplicationContext());
                checkBox.setText(c.getString(3));
                checkBox.setTextColor(Color.BLUE);
                layout.addView(checkBox);
            }
        }*/
        c.close();

        /*for(int i = 0; i < 100; i++) {
            TextView tv = new TextView(this);
            tv.setText("Dynamic Text!");
            list.add(tv);
        }

        for(int i = 0; i < list.size(); i++)
        {
            if(list.get(i) instanceof TextView){
                TextView tem = (TextView) list.get(i);
                layout.addView(tem);
            }
        }*/

        /*
        * http://stackoverflow.com/questions/14017720/how-to-pass-value-using-intent-between-activity-in-android
        *
        * */
    }
    private void alert(final int index)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (!isFinishing()) {
                    new AlertDialog.Builder(AddActivity.this)
                            .setTitle("Already entered about this team")
                            .setMessage("Do you want to edit?")
                            .setCancelable(true)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // whatever...
                                    Intent intent = new Intent(AddActivity.this, EditTeamData.class);
                                    intent.putExtra("index",index);
                                    startActivity(intent);
                                    finish();

                                    /*Intent newActivity1;
                                    newActivity1 = new Intent(view.getContext(), EditTeamData.class);
                                    newActivity1.putExtra("index", teamNumb);
                                    startActivity(newActivity1);*/
                                }
                            }).create().show();
                }
            }
        });
    }
}
