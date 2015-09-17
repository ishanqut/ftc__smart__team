package com.example.ishan.ftc_scouter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by ishan on 13/09/2015.
 */
public class EditTeamData extends Activity {

    int team;
    EditText team_num;
    EditText team_name;
    SQLiteDatabase database;
    LinearLayout layout;
    Button submit;
    int teamNumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        team = getIntent().getExtras().getInt("index");
        team_num = (EditText) findViewById(R.id.txt_number);
        team_num.setText("" + team );
                //" Long click me to delete!!");


        teamNumb = team;
        submit = (Button) findViewById(R.id.btn_add_team_dat);

        submit.setText("Edit!");
        team_num.setFocusable(false);
        team_num.setClickable(false);

        try {
            team_name = (EditText) findViewById(R.id.txt_team_name);
            layout = (LinearLayout) findViewById(R.id.layout);

            database = openOrCreateDatabase("scouter", MODE_PRIVATE, null);

            //TODO this is stubbed


            Cursor c = database.rawQuery(String.format("SELECT * FROM inputs i,teamdata t where i.label = t.label and team_id = %s;", "" + team), null);
            //Cursor cc = database.rawQuery("select * from inputs;",null);
            //TODO for those of you who have not done SQL now is the right time in the world to do
            //as the world of big data is appraching soon..
            //take care of the query order as it makes a huge difference.

            //int limit = Integer.parseInt(c.getString(0));


            if (c.moveToFirst()) {
                do {
                    TextView tv = new TextView(this);
                    tv.setText(c.getString(2));
                    layout.addView(tv);
                    Log.d("DBGZ", c.getString(0));
                    if (c.getString(1).equals("cb")) {
                        Log.d("DBGZ", "Went in the if statement");
                        CheckBox checkBox = new CheckBox(getApplicationContext());
                        checkBox.setText(c.getString(3));
                        checkBox.setTextColor(Color.BLUE);
                        boolean check = c.getString(9).equals("true");
                        checkBox.setChecked(check);
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
                        int index = Integer.parseInt(c.getString(9));
                        Log.d("mdfd",index+"");
                        if (index == 1)
                            r_1.setChecked(true);
                        else if (index == 2)
                            r_2.setChecked(true);
                        else if (index == 3)
                            r_3.setChecked(true);

                        layout.addView(group);
                    } else if (c.getString(1).equals("tb")) {
                        EditText text = new EditText(getApplicationContext());
                        text.setText(c.getString(9));
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



            //c = database.rawQuery(String.format("SELECT * FROM inputs i, teamdata t where i.label != t.label and t.team_id = %s;", "" + team), null);
            c = database.rawQuery(String.format("SELECT *\n" +
                    "FROM inputs i\n" +
                    "WHERE NOT EXISTS \n" +
                    "    (SELECT * \n" +
                    "     FROM teamdata t \n" +
                    "     WHERE i.label = t.label and team_id = %s);", "" + team), null);
            //Cursor cc = database.rawQuery("select * from inputs;",null);
            //TODO for those of you who have not done SQL now is the right time in the world to do
            //as the world of big data is appraching soon..
            //take care of the query order as it makes a huge difference.

            //int limit = Integer.parseInt(c.getString(0));


            if (c.moveToFirst()) {
                do {
                    TextView tv = new TextView(this);
                    tv.setText(c.getString(2));
                    layout.addView(tv);
                    Log.d("DBGZmd", c.getString(1));
                    if (c.getString(1).equals("cb")) {
                        Log.d("DBGZ", "Went in the if statement");
                        CheckBox checkBox = new CheckBox(getApplicationContext());
                        checkBox.setText(c.getString(3));
                        checkBox.setTextColor(Color.BLUE);
                        //boolean check = c.getString(9).equals("true");
                        //checkBox.setChecked(check);
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
                        int index = Integer.parseInt(c.getString(9));
                        if(index==1)
                            r_1.setChecked(true);
                        else if(index==2)
                            r_2.setChecked(true);
                        else if(index==3)
                            r_3.setChecked(true);

                        layout.addView(group);
                    } else if (c.getString(1).equals("tb")) {
                        EditText text = new EditText(getApplicationContext());
                        //text.setText(c.getString(9));
//                    text.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                        text.setMaxLines(5);
                        text.setTextColor(Color.BLUE);
                        layout.addView(text);
                    }
                } while (c.moveToNext());
            }


            c = database.rawQuery(String.format("select * from teams where id = %s", "" + team), null);
            c.moveToFirst();
            team_name.setText(c.getString(1));

            c.close();


            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //before inserting check very important thing!!
                    //does the team number already exist?

                    Log.d("Enge", "entered");
                    //int teamNumb = Integer.parseInt(teamNum.getText().toString());
                    try {


                        //TODO delete all instances for them
                        database.execSQL("delete from teamdata where team_id = "+team+";");

                        int max = layout.getChildCount();
                        int i = 0;
                        while (i < max) {

                            Log.d("kldjfkd",""+i);
                            if (layout.getChildAt(i) instanceof CheckBox) {
                                //-1 = its ist its label
                                String a;
                                TextView temp = (TextView) layout.getChildAt(i - 1);
                                a = temp.getText().toString();
                                boolean checked = ((CheckBox) layout.getChildAt(i)).isChecked();
                                database.execSQL(String.format("insert into `teamdata`(type,label,arg1,arg2,arg3,arg4,team_id) values ('cb','%s','%b','','','','%d');", a, checked, teamNumb));
                                Log.d("ZDKDF", i + "found checkbox!" + a + checked);

                            }
                            if (layout.getChildAt(i) instanceof RadioGroup) {
                                //get which one is checked mate??
                                RadioGroup temp = (RadioGroup) layout.getChildAt(i);
                                int index = temp.getCheckedRadioButtonId();

                                String a;
                                a = ((TextView) layout.getChildAt(i - 1)).getText().toString();

                                database.execSQL(String.format("insert into `teamdata`(type,label,arg1,arg2,arg3,arg4,team_id) values ('rb','%s','%d','','','','%d');", a, index, teamNumb));
                                //similar story
                                Log.d("ZDKDF", i + "found radiogroup!" + index);
                            }
                            if (layout.getChildAt(i) instanceof EditText) {
                                String a, b;
                                a = ((EditText) layout.getChildAt(i)).getText().toString();

                                b = ((TextView) layout.getChildAt(i - 1)).getText().toString();
                                database.execSQL(String.format("insert into `teamdata`(type,label,arg1,arg2,arg3,arg4,team_id) values ('tb','%s','%s','','','','%d');", b, a, teamNumb));
                                Log.d("ZDKDF", i + "found textbox!");
                            }
                            //TODO do not FORGET the incremnt,
                            //I am normally used to for loops but for ease of readibility I have a while
                            //loop to iterate through the list.
                            i++;


                        }
                        Intent ins = new Intent(view.getContext(), MainActivity.class);
                        startActivity(ins);
                    } catch (Exception e) {
                        Log.d("seriousdebugging", e.toString() + "magic did not happen");
                    }
                }
            });

           /* {

                }
            });
        }catch(Exception ex)
        {
            Log.d("Mintlad", ex.toString());
        }*/
        }catch (Exception e){
            Log.d(e.toString(), e.toString());
    }}
}
