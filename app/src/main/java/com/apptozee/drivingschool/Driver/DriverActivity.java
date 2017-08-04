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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.apptozee.drivingschool.LoginActivity;
import com.apptozee.drivingschool.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

public class DriverActivity extends AppCompatActivity {

    //UI references
    private AlertDialog.Builder builder;
    private List<Customer> customerList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CustomerAdapter mAdapter;

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
                    i.putExtra("update",0);
                    startActivity(i);
                    finish();
                } else if (tabId == R.id.leave) {
                    Intent i = new Intent(DriverActivity.this,DriverLeave.class);
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
                    i.putExtra("update",0);
                    startActivity(i);
                    finish();
                } else if (tabId == R.id.leave) {
                    Intent i = new Intent(DriverActivity.this,DriverLeave.class);
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
        final DBHome d = new DBHome(DriverActivity.this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new CustomerAdapter(customerList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view,final int position) {
                popupmenu(view,position);
            }
            @Override
            public void onLongClick(View view, final int position) {
                popupmenu(view,position);
            }
        }));
        for (int i=0;i<d.getProfilesCount();i++)
        {
            Customer customer = new Customer(d.getData().get("name").get(i),
                    d.getData().get("number").get(i),
                    d.getData().get("time").get(i),
                    d.getData().get("slot").get(i));
            customerList.add(customer);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void popupmenu(final View view, final int position)
    {
        final DBHome d = new DBHome(DriverActivity.this);
        PopupMenu popup = new PopupMenu(DriverActivity.this, view);
        //inflating menu from xml resource
        popup.inflate(R.menu.options_menu);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu1:
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
                        break;
                    case R.id.menu2:
                        Intent i = new Intent(DriverActivity.this,RegisterCustomer.class);
                        //Send values to RegisterCustomer class
                        i.putExtra("update",1);
                        i.putExtra("cname",d.getData().get("name").get(position));
                        i.putExtra("cnumber",d.getData().get("number").get(position));
                        i.putExtra("cdays",d.getData().get("time").get(position));
                        i.putExtra("cslot",d.getData().get("slot").get(position));
                        startActivity(i);
                        finish();
                        break;
                    case R.id.menu3:
                        builder.setMessage("Name: "+d.getData().get("name").get(position)
                                +"\nMobile: "+d.getData().get("number").get(position)
                                +"\nDays: "+d.getData().get("time").get(position)
                                +"\nSlot: "+d.getData().get("slot").get(position))
                                .setPositiveButton("DELETE?", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        DBHome dbh = new DBHome(DriverActivity.this);
                                        dbh.deleteData(d.getData().get("number").get(position));
                                        dbh.close();

                                        // update recycler view
                                        customerList.remove(position);
                                        recyclerView.removeViewAt(position);
                                        mAdapter.notifyItemRemoved(position);
                                        mAdapter.notifyItemRangeChanged(position, customerList.size());
                                        mAdapter.notifyDataSetChanged();
                                    }
                                })
                                .show();
                        break;
                }
                return false;
            }
        });
        //displaying the popup
        popup.show();
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
