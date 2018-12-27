package com.example.android.wishme;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static android.support.v4.content.ContextCompat.getSystemService;

public class MainActivity extends AppCompatActivity {
    Button date,time,save;
    private ChildEventListener mchildDate,mchildTime;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefDate = database.getReference().child("date");
    DatabaseReference myRefTime = database.getReference().child("time");

    EditText txtDate, txtTime;

    @Override
    public void onStop() {
        super.onStop();
    }

@TargetApi(26)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        date=(Button)findViewById(R.id.date);
        save=findViewById(R.id.save);
        time=(Button)findViewById(R.id.tym);

        txtDate=(EditText)findViewById(R.id.dateText);
        txtTime=(EditText)findViewById(R.id.timeText);


        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");

                if(mchildTime==null) {
                    mchildTime = new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String friendlyMessage = dataSnapshot.getValue(String.class);
                            txtTime.setText(friendlyMessage);
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };
                    myRefTime.addChildEventListener(mchildTime);
                }

            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");

               // Log.v("MAINACTIVITY","EITHI");

                if(mchildDate==null) {
                    mchildDate = new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String friendlyMessage = dataSnapshot.getValue(String.class);
                            txtDate.setText(friendlyMessage);
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };
                    myRefDate.addChildEventListener(mchildDate);
                }
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabbut);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //open main.xml to add new data
            }
        });

       /* AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 5);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
   */
       save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               ringAlarm();
         /*      AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

               Intent notificationIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
               PendingIntent broadcast = PendingIntent.getBroadcast(getApplicationContext(), 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

               Calendar cal = Calendar.getInstance();
               cal.add(Calendar.SECOND, 5);
               alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
*/
           }
       });

    }


    @TargetApi(26)
    void ringAlarm(){
          String d= txtDate.getText().toString();
        String t= txtTime.getText().toString();
        int f=0,day=1,mon=1,yr=1,hr=0,min=0,l=0;// set initial values to corresponding day's n tym

       // Log.v("kn","yearmonthday"+t+d);
        for(int i=0;i<d.length();i++) {

            if(d.charAt(i)=='-')
            {
                if(f==0)
                {
                    String s=d.substring(0,i);
                    day=Integer.parseInt(s);
                    l=i;
                    f++;
                   // Log.v("kn","yearmonthday"+s+day);

                }
                else if(f==1)
                {
                    mon=Integer.parseInt(d.substring(l+1,i));
                    yr=Integer.parseInt(d.substring(i+1,d.length()));
                    f++;
                    l=i;
                   // Log.v("kn","yearmonthday"+mon+yr);

                }

            }
        }
      //  Log.v("kn","yearmonthday");

        for(int i=0;i<t.length();i++) {

            if(t.charAt(i)==':')
            {
               hr=Integer.parseInt(t.substring(0,i));
               min=Integer.parseInt(t.substring(i+1,t.length()));
            }
        }
       // Log.v("kn","yearmonthday"+yr+mon+day+hr+min);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(getApplicationContext(), 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Calendar cal =Calendar.getInstance();
      /* cal.set(Calendar.YEAR,yr);
       cal.set(Calendar.MONTH,mon );
       cal.set(Calendar.DAY_OF_MONTH,day);*/
      cal.set(yr,mon,day,hr,min);/*
       cal.set(Calendar.HOUR_OF_DAY,hr);
       cal.set(Calendar.MINUTE,min);*/
      cal.set(Calendar.SECOND, (int) cal.getTimeInMillis());
        //Log.v("kn","yearmonthday set heigala");

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);

       // Log.v("kn","yearmonthday alarm heigala");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
