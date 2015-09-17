package com.example.ishan.ftc_scouter;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

//TODO team checklist
//TODO Push notifications about your games,award_judging!!
//TODO the complete app for fTC
/*
* http://developer.android.com/training/basics/firstapp/starting-activity.html
* http://www.androidbegin.com/tutorial/android-button-click-new-activity-example/
*
* Android non-sense, Activity and stuff and the stupid Manifest file, the most useless piece of
* rubbish that is around but will have to stick with it. Can't even start an activity without
* specifying it in it. But I guess its useful for other stuff.
*
* */

/**
 * @author Ishan Joshi
 * @since 1.0
 * @see ViewActivity
 * @see CustomizeActivity
 * @see AddActivity
 * <p>
 *     This is the main class children!!
 *     Android is not like C, its event driven and not serial
 *     So you need to be aware of manifest.
 *     This only has the start screen and event handlers for the
 *     whole app.
 *     References include
 *     @link http://developer.android.com/training/basics/firstapp/starting-activity.html
 *     @link http://www.androidbegin.com/tutorial/android-button-click-new-activity-example/
 *     @link http://developer.android.com/training/camera/photobasics.html
 *     Camera intent..
 * </p>
 */

public class MainActivity extends ActionBarActivity {

    SQLiteDatabase database;
    Button add,view,customize,donate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO check documentation
        //TODO create documentation on how to
        //TODO set of predefined questions


        //deleteDatabase("scouter");
        database = openOrCreateDatabase("scouter", MODE_PRIVATE, null);

        //Get the button object
        add = (Button)findViewById(R.id.btn_add);
        view = (Button) findViewById(R.id.btn_view);
        customize = (Button) findViewById(R.id.btn_customize);
        donate = (Button) findViewById(R.id.btn_donate);

        //Add event handlers.
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);

                // close this activity
                //finish();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                startActivity(intent);
            }
        });
        customize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CustomizeActivity.class);
                startActivity(intent);
//                database.execSQL("insert into `inputs` values (2,\"cb\",\"first_checkbox\",\"ischeked\",\"\",\"\",\"\");");
            }
        });
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://smtftc2k15.wix.com/smtrobotics"));
                startActivity(browserIntent);
                //finish();
                //TODO intent for camera
                /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }*/
                /*Cursor c = database.rawQuery("SELECT * FROM student;", null);
                while (c.moveToFirst()) {
                    Log.d("DBZ", c.getString(1));
                    Log.d("DBZ", c.getString(2));
                }
                c.close();*/
            }
        });

        database.execSQL("CREATE TABLE IF NOT EXISTS `inputs` (\n" +
                "\t`id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`type`\tINTEGER,\n" +
                "\t`label`\tTEXT,\n" +
                "\t`arg1`\tTEXT,\n" +
                "\t`arg2`\tTEXT,\n" +
                "\t`arg3`\tTEXT,\n" +
                "\t`arg4`\tTEXT\n" +
                ");");
        database.execSQL("CREATE TABLE IF NOT EXISTS `teamdata` (\n" +
                "\t`type`\tTEXT,\n" +
                "\t`label`\tTEXT,\n" +
                "\t`arg1`\tTEXT,\n" +
                "\t`arg2`\tTEXT,\n" +
                "\t`arg3`\tTEXT,\n" +
                "\t`arg4`\tTEXT,\n" +
                "\t`team_id`\tINTEGER\n" +
                ");");


        //database.execSQL("drop table `teams`;");
        database.execSQL("CREATE TABLE IF NOT EXISTS `teams` (\n" +
                "\t`id`\tINTEGER PRIMARY KEY ,\n" +
                "\t`name`\tTEXT,\n" +
                "\t`extra`\tTEXT\n" +
                ");");
        //database.execSQL("INSERT INTO student VALUES('1aa','2aa','3aa');");
        //database.execSQL("DROP TABLE student;");
        /*database.execSQL("insert into `inputs`(type,label,arg1,arg2,arg3,arg4) values ('tb','textbox??','','','','');");
        database.execSQL("insert into `inputs`(type,label,arg1,arg2,arg3,arg4) values ('rb','radiobutton??','1','2','3','');");
        database.execSQL("insert into `inputs`(type,label,arg1,arg2,arg3,arg4) values ('cb','checkbox??','cb1','','','');");*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
//        getMenuInflater().inflate(R.menu.menu_add_new, menu);
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
        if(id == R.id.clear_input)
        {
            database.execSQL("\n" +
                    "INSERT INTO `inputs` (type,label,arg1,arg2,arg3,arg4) VALUES \n" +
                    " ('rb','Parked','Rescue Beacon','Floor Goal','Touching Floor Mountain',''),\n" +
                    " ('rb','Parked Hill','Low Zone','Mid Zone','High Zone',''),\n" +
                    " ('cb','Rescue Beacon','Emitted','','',''),\n" +
                    " ('cb','Shelter','Climber','','',''),\n" +
                    " ('rb','Debris','Floor','Low','Mid/High',''),\n" +
                    " ('rb','Parking','Low Zone','Mid Zone','High Zone',''),\n" +
                    " ('rb','Zip Line','1','2','3',''),\n" +
                    " ('cb','Shelter Climber','Yes','','',''),\n" +
                    " ('rb','Pull up','Sometimes','Most','Always',''),\n" +
                    " ('cb','All Clear','Yes definitely','','','');\n" +
                    "\n");

            return true;

        }

        return super.onOptionsItemSelected(item);
    }


}



/*
*
* CREATE TABLE `` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`type`	INTEGER,
	`label`	TEXT,
	`arg1`	TEXT,
	`arg2`	TEXT,
	`arg3`	TEXT,
	`arg4`	TEXT
);
*
* */
