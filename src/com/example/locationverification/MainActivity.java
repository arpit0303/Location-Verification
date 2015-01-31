package com.example.locationverification;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getActionBar().hide();
//        InputStream inputStream = getResources().openRawResource(
//				R.raw.hackathon_location_data);
//		CSVFile csvFile = new CSVFile(inputStream);
//		List<Object[]> scoreList = csvFile.read();
//
//		for(Object[] obj : scoreList){
//			
//			String houseId0 = String.valueOf(obj[0]);
//			String houseId1 = String.valueOf(obj[1]);
//			String houseId2 = String.valueOf(obj[2]);
//			
//			Log.i("main", houseId0 + " "+houseId1 +" "+houseId2);
//		}
		
		final EditText loc = (EditText) findViewById(R.id.editloc);
		Button locsearch = (Button) findViewById(R.id.locSearch);
		Button currentLoc = (Button) findViewById(R.id.currentloc);
		
		locsearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Map2Activity.class);
				intent.putExtra("location", loc.getText().toString());
				startActivity(intent);
			}
		});
		
		currentLoc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, MapActivity.class));
			}
		});
    }
}
