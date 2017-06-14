package com.apptozee.drivingschool.Driver;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.apptozee.drivingschool.LoginActivity;
import com.apptozee.drivingschool.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class DriverActivity extends AppCompatActivity {

    //UI references
    private AlertDialog.Builder builder;
    private ListView l;
    //Demo string. This needs to be fetched from database.
    String[] customerlist = {"Customer 1","Customer 2","Customer 3","Customer 4",
            "Customer 5","Customer 6","Customer 7","Customer 8"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int id) {
                if (id == R.id.sched) {
                    //This will be the default page when drawer opens
                    //code will be written later, probably ListView component
                }
                else if (id == R.id.reg) {
                    // Call RegisterCustomer activity
                    Intent i = new Intent(DriverActivity.this,RegisterCustomer.class);
                    startActivity(i);
                } else if (id == R.id.leave) {
                    Intent i = new Intent(DriverActivity.this,DriverLeave.class);
                    startActivity(i);
                }
            }
        });

        //Decide AlertDialog design based on Android Version.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(DriverActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(DriverActivity.this);
        }

        //Set up customer list view
        l = (ListView) findViewById(R.id.customer_list);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, customerlist); //we will use own layout once ready
        l.setAdapter(adapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(DriverActivity.this,CustomerDetails.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        builder.setTitle("Exit?")
                .setMessage("Are you sure you want to exit?")
                .setIcon(android.R.drawable.stat_sys_warning)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close application
                        DriverActivity.this.finish();
                        System.exit(0);
                    }
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.driver, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            //Close the activity
            //Write additional DB code later.
            Intent i = new Intent(DriverActivity.this,LoginActivity.class);
            startActivity(i);
            DriverActivity.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
