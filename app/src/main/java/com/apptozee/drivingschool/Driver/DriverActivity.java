package com.apptozee.drivingschool.Driver;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
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
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

public class DriverActivity extends AppCompatActivity {

    //UI references
    private AlertDialog.Builder builder;
    ListView l;

    //Bottombar opens 1st id automatically, using flag to solve this
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //BottomBar code start
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.reg && flag == 1) {
                    // Call RegisterCustomer activity
                    Intent i = new Intent(DriverActivity.this,RegisterCustomer.class);
                    startActivity(i);
                    finish();
                } else if (tabId == R.id.leave) {
                    Intent i = new Intent(DriverActivity.this,DriverLeave.class);
                    startActivity(i);
                    finish();
                } else if (tabId == R.id.upd) {
                    Intent i = new Intent(DriverActivity.this,UpdateCustomer.class);
                    startActivity(i);
                    finish();
                } else if (tabId == R.id.del) {
                    Intent i = new Intent(DriverActivity.this,DeleteCustomer.class);
                    startActivity(i);
                    finish();
                }
            }
        });
        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                if (tabId == R.id.reg) {
                    // Call RegisterCustomer activity
                    Intent i = new Intent(DriverActivity.this,RegisterCustomer.class);
                    startActivity(i);
                    finish();
                } else if (tabId == R.id.leave) {
                    Intent i = new Intent(DriverActivity.this,DriverLeave.class);
                    startActivity(i);
                    finish();
                } else if (tabId == R.id.upd) {
                    Intent i = new Intent(DriverActivity.this,UpdateCustomer.class);
                    startActivity(i);
                    finish();
                } else if (tabId == R.id.del) {
                    Intent i = new Intent(DriverActivity.this,DeleteCustomer.class);
                    startActivity(i);
                    finish();
                }
            }
        });
        //BottomBar code end

        //Decide AlertDialog design based on Android Version.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(DriverActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(DriverActivity.this);
        }

        //Set up customer list view
        l = (ListView) findViewById(R.id.customer_list);
        final DBHome d = new DBHome(DriverActivity.this);
        ArrayAdapter name = new ArrayAdapter(DriverActivity.this,android.R.layout.simple_list_item_1,d.getData().get("name"));
        l.setAdapter(name);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                builder.setMessage("Name: "+d.getData().get("name").get(position)
                        +"\nMobile: "+d.getData().get("number").get(position)
                        +"\nDays: "+d.getData().get("time").get(position)
                        +"\nSlot: "+d.getData().get("slot").get(position))
                        .setPositiveButton("Call?", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (ActivityCompat.checkSelfPermission(DriverActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(DriverActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 123);
                                }
                                else if (ActivityCompat.checkSelfPermission(DriverActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                                {
                                    Intent i = new Intent();
                                    i.setAction(Intent.ACTION_CALL);
                                    i.setData(Uri.parse("tel:+91"+d.getData().get("number").get(position)));
                                    startActivity(i);
                                }
                            }
                        })
                        .show();
            }
        });
        d.close();
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
