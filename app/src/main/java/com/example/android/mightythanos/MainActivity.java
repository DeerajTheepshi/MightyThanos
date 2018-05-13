package com.example.android.mightythanos;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
                                                                                                        //ALL GLOBAL VARIABLES
    Integer[] array = new Integer[]{0,1,2,3,4,5};
    String[] stonesList = new String[]{"Time Stone","Space Stone","Mind Stone","Soul Stone","Power Stone","Reality Stone"};
    int[] colorList = new int[]{R.color.TimeStone,R.color.SpaceStone,R.color.MindStone,R.color.SoulStone,R.color.PowerStone,R.color.RealityStone};
    TextView[] viewList;
    List<Integer>  stoneIndex = randomGen();
    int status=0;
    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;
    TextView stoneStatus;
    TextView imageStatus;
    View backgroundStatus;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("KAALA","IT IS NOT EMPTY "+status+" "+stoneIndex);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stoneStatus = (TextView) findViewById(R.id.textview1);                              //INITIALIZATION
        backgroundStatus = findViewById(R.id.home);
        imageStatus = (TextView) findViewById(R.id.color_change);
        viewList = new TextView[]{(TextView) findViewById(R.id.t1), (TextView) findViewById(R.id.t2), (TextView) findViewById(R.id.t3),
                (TextView) findViewById(R.id.t4), (TextView) findViewById(R.id.t5), (TextView) findViewById(R.id.t6)};
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        prefEditor = pref.edit();
        Boolean check = pref.getBoolean("isEmpty",true);
        restorePref(check);                                                                     //RESTORING DATA ONLY IF ITS NOT EMPTY

    }
    @Override
    protected void onStop() {                                                                   //SAVE DATA BEFORE THE APP KILLS/CLOSES ITSELF
        super.onStop();
        savePref();
        Log.w("KAALA","PREFERENCES ARE BEING SAVED");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void stoneSelector(View view)                                                        //STONE SELECTION AND ALL OTHER RELATED ACTIVITIES
    {
        if(status<6)
        { int stoneNo = stoneIndex.get(status);
          String stoneGot = stonesList[stoneNo];
          stoneStatus.setText("You got the "+stoneGot);
          imageStatus.setBackgroundColor(getResources().getColor(colorList[stoneNo]));
          viewList[status].setVisibility(View.VISIBLE);
          viewList[status].setBackgroundColor(getResources().getColor(colorList[stoneNo]));
          viewList[status].setText(stoneGot);
        }
        if(status>5)
        {
            stoneStatus.setText("Have All Stones,Now SNAP!!");
            backgroundStatus.setBackground(getResources().getDrawable(R.drawable.snap));
            imageStatus.setBackground(getResources().getDrawable(R.drawable.snap));
            makeToast();
        }
        status=status+1;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void reset(View view){                                                               //RESET AT ANYTIME
        for(int i=0;i<6;i++)
            viewList[i].setVisibility(View.INVISIBLE);
        imageStatus.setBackground(getResources().getDrawable(R.drawable.infinitywar));
        backgroundStatus.setBackground(getResources().getDrawable(R.drawable.infinitywar));
        stoneStatus.setText("Stone Yet To Be Possessed");
        status=0;
        stoneIndex = randomGen();
    }
    public List randomGen(){                                                                    //GENERATE A RANDOM LIST
        List<Integer> stoneIndex = Arrays.asList(array);
        Collections.shuffle(stoneIndex);
        return stoneIndex;
    }
    public void makeToast(){                                                                       //TOAST TO INDICATE ALL STONES
        Context context = getApplicationContext();
        int remain=6-status+1;
        Toast toast = Toast.makeText(context,"YOU HAVE ALL THE STONES",Toast.LENGTH_SHORT);
        toast.show();
    }
    public void savePref()                                                                          //TO SAVE DATA IN XML
    {
        prefEditor.putInt("savedState",status);
        prefEditor.commit();
        prefEditor.putBoolean("isEmpty",false);
        prefEditor.commit();
        int[] indexArr = new int[6];
        for(int i=0;i<6;i++){
            prefEditor.putInt("savedIndex["+String.valueOf(i)+"]",stoneIndex.get(i));
            prefEditor.commit();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void restorePref(Boolean empty)                                                          //RESTORE DATA, AND RESTORE ALL LAYOUT PROPERTIES
    {
        if(!empty) {
            status = pref.getInt("savedState", 0);
            for (int i = 0; i < 6; i++) {
                stoneIndex.set(i, pref.getInt("savedIndex[" + String.valueOf(i) + "]", 0));
            }
            for (int i = 0; i < status; i++) {
                if (i < 6) {
                    int stoneNo = stoneIndex.get(i);
                    viewList[i].setVisibility(View.VISIBLE);
                    String stoneGot = stonesList[stoneNo];
                    viewList[i].setBackgroundColor(getResources().getColor(colorList[stoneNo]));
                    viewList[i].setText(stoneGot);
                }
                if (i > 5)
                    break;
            }
            if (status > 6) {
                backgroundStatus.setBackground(getResources().getDrawable(R.drawable.snap));
                imageStatus.setBackground(getResources().getDrawable(R.drawable.snap));
                stoneStatus.setText("Have All Stones,Now SNAP!!");
            } else
                imageStatus.setBackgroundColor(getResources().getColor(colorList[stoneIndex.get(status - 1)]));
        }
    }
}


