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
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Space;
import android.widget.TextView;

/**
 * Created by ishan on 7/09/2015.
 */
public class CustomizeActivity extends Activity {
    LinearLayout layout;
    Button submit;
    Button add;
    SQLiteDatabase database;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);

        layout =(LinearLayout) findViewById(R.id.layout_edit);
        submit = (Button) findViewById(R.id.btn_update);
        add = (Button) findViewById(R.id.btn_new_text);

        //GET the database values children.

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomizeActivity.this, AddNewActivity.class);
                startActivity(intent);
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = layout.getChildCount();
                View v = null;
                int i = 0;

                //empty it out!

                database.execSQL("DELETE FROM `inputs`;");

                String sql = "";


                Log.d("ZZDDD","Clicked");
            //could have done for loop
                while (i < count) {
                    v = layout.getChildAt(i);
                    Log.d("ZZDDD","got something");
                    if (v instanceof TextView) {
                        Log.d("ZZDDD","Found textview");
                        TextView temp = (TextView) v;
                        if (temp.getText().toString().contains("Textbox")) {
                            Log.d("ZZDDD","found textbox");
                            v = layout.getChildAt(i+1);
                            EditText e = (EditText) v;

                            String newLabel = e.getText().toString();
                            database.execSQL(String.format("insert into `inputs`(type,label,arg1,arg2,arg3,arg4) values ('tb','%s','','','','');",newLabel));

                        }
                        if(temp.getText().toString().contains("Radio boxes"))
                        {
                            //will ahve 3 mate.
                            String a,b,c,d;
                            v = layout.getChildAt(i + 1);
                            EditText e = (EditText)v;
                            a = e.getText().toString();
                            v=layout.getChildAt(i + 2);
                            e = (EditText) v;
                            b = e.getText().toString();
                            v = layout.getChildAt(i+3);
                            e = (EditText) v;
                            c = e.getText().toString();
                            v = layout.getChildAt(i+4);
                            e = (EditText) v;
                            d = e.getText().toString();

                            database.execSQL(
                                    String.format("insert into `inputs`(type,label,arg1,arg2,arg3,arg4) values " +
                                            "('rb','%s','%s','%s','%s','');",a,b,c,d));
                        }
                        if(temp.getText().toString().contains("Checkbox"))
                        {

                            String a,b;
                            v = layout.getChildAt(i + 1);
                            EditText e = (EditText)v;
                            a = e.getText().toString();
                            v = layout.getChildAt(i + 2);
                            EditText e2 = (EditText)v;
                            b = e2.getText().toString();

                            database.execSQL(String.format("insert into `inputs`(type,label,arg1,arg2,arg3,arg4)" +
                                    " values ('cb','%s','%s','','','');",a,b));
                        }
                    }
                    i++;
                }
                /*Intent intent = new Intent(CustomizeActivity.this, MainActivity.class);
                startActivity(intent);*/
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(!isFinishing()){
                            new AlertDialog.Builder(CustomizeActivity.this)
                                    .setTitle("Done Saving!!")
                                    .setMessage("Going to home screen")
                                    .setCancelable(true)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // whatever...
                                            Intent intent = new Intent(CustomizeActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }).create().show();
                        }
                    }
                });
            }
        });



        //TODO found todays bug that I was just inserting with Checkbox in the data base which created errors
        //of null pointer exception, very hard to debug, but done it successfully mate.!!

        /**
         * <p>Best achievement was right here debugging this undebuggable code! fostered by me!</p>
         * */


        database = openOrCreateDatabase("scouter", MODE_PRIVATE, null);

        //TODO this is stubbed

        Cursor c = database.rawQuery("SELECT * FROM inputs;", null);

        //int limit = Integer.parseInt(c.getString(0));


        if(c.moveToFirst()) {
            do {
                /*TextView tv = new TextView(this);
                tv.setText(c.getString(2));
                layout.addView(tv);*/
                if (c.getString(1).equals("cb")) {
//                Log.d("DBGZ", "Went in the if statement");
                    TextView tv = new TextView(this);
                    tv.setText("Checkbox");
                    tv.setPadding(0, 2, 0, 0);
                    tv.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            try {
                                int index = layout.indexOfChild(view);
                                /*EditText temp = (EditText)layout.getChildAt(index+1);
                                Log.d("adfa",temp.getText().toString());
                                layout.removeViewAt(index); layout.removeViewAt(index);*/
                                for (int i = 0; i < 3; i++) {

                                    layout.removeViewAt(index); //i+index won't work as its no longer there
                                    //this is MINT
                                    //TODO take care here children when debugging your apps
                                    //TODO its essential you can debug your rubbish uncommented code
                                }
                                return true;
                            } catch (Exception e) {
                                Log.d("adfa", e.toString());
                                return true;
                            }
                        }

                    });
                    layout.addView(tv);

                    EditText text = new EditText(getApplicationContext());
                    Log.d("STR", c.getString(2) + "String is the");
                    text.setText((CharSequence) c.getString(2), TextView.BufferType.EDITABLE);
                    text.setTextColor(Color.BLUE);


                    layout.addView(text);
                    EditText text2 = new EditText(getApplicationContext());
                    text2.setText((CharSequence) c.getString(3), TextView.BufferType.EDITABLE);
                    text2.setTextColor(Color.BLUE);
                    layout.addView(text2);

                    /*CheckBox checkBox = new CheckBox(getApplicationContext());
                    checkBox.setText(c.getString(3));
                    checkBox.setTextColor(Color.BLUE);
                    layout.addView(checkBox);*/
                } else if (c.getString(1).equals("rb")) {

                    TextView tv = new TextView(this);
                    tv.setText("Radio boxes");
                    tv.setPadding(0, 2, 0, 0);
                    tv.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            try {
                                int index = layout.indexOfChild(view);
                                /*EditText temp = (EditText)layout.getChildAt(index+1);
                                Log.d("adfa",temp.getText().toString());
                                layout.removeViewAt(index); layout.removeViewAt(index);*/
                                for (int i = 0; i < 5; i++) {

                                    layout.removeViewAt(index); //i+index won't work as its no longer there
                                    //this is MINT
                                    //TODO take care here children when debugging your apps
                                    //TODO its essential you can debug your rubbish uncommented code that is written
                                }
                                return true;
                            } catch (Exception e) {
                                Log.d("adfa", e.toString());
                                return true;
                            }
                        }

                    });
                    layout.addView(tv);

                    EditText text = new EditText(getApplicationContext());
                    text.setText(c.getString(2));
//                        text.setText(c.getString(2),TextView.BufferType.EDITABLE);
                    text.setTextColor(Color.BLUE);

                    layout.addView(text);

                    for(int i = 0; i < 3; i++)
                    {
                        EditText text1 = new EditText(getApplicationContext());
                        text1.setText(c.getString(i+3));
//                        text.setText(c.getString(2),TextView.BufferType.EDITABLE);
                        text1.setTextColor(Color.BLUE);

                        layout.addView(text1);
                    }

                    /*RadioGroup group = new RadioGroup(getApplicationContext());
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

                    layout.addView(group);*/
                } else if (c.getString(1).equals("tb")) {
                    TextView tv = new TextView(this);
                    tv.setText("Textbox");
                    tv.setPadding(0, 2, 0, 0);
                    tv.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            try {
                                int index = layout.indexOfChild(view);
                                /*EditText temp = (EditText)layout.getChildAt(index+1);
                                Log.d("adfa",temp.getText().toString());
                                layout.removeViewAt(index); layout.removeViewAt(index);*/
                                for (int i = 0; i < 2; i++) {

                                    layout.removeViewAt(index); //i+index won't work as its no longer there
                                    //this is MINT
                                    //TODO take care here children when debugging your apps
                                    //TODO its essential you can debug your rubbish uncommented code that is written
                                }
                                return true;
                            } catch (Exception e) {
                                Log.d("adfa", e.toString());
                                return true;
                            }
                        }

                    });
                    layout.addView(tv);

                    EditText text = new EditText(getApplicationContext());
                    text.setText(c.getString(2),TextView.BufferType.EDITABLE);
                    text.setTextColor(Color.BLUE);
                    layout.addView(text);
                }

                /*Space space = new Space(getApplicationContext());
                space.setMinimumHeight(1);
                layout.addView(space);*/

            } while (c.moveToNext());
        }

    }
}
